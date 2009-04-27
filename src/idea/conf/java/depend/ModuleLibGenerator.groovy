package idea.conf.java.depend

import org.apache.tools.ant.Project
import org.apache.tools.ant.BuildException
import org.apache.tools.ant.types.Path
import static idea.conf.Validator.*

/**
 * Inspect an ant Path being used as a classpath. Each path element is assumed to be
 * a jar in a library. For each path element jar, find the property defining that jar.
 * Then find corresponding source, javadoc, and javadocurl properties.
 *
 * @todo if no property found for jar, take the filename (minus .jar) as property name
 * 
 * @author tomichj
 */
class ModuleLibGenerator
{
    private final Project project
    private final def log
    private String sourceProperty = "source." // default values
    private String javadocProperty = "javadoc."
    private String javadocUrlProperty = "javadocurl."


    static String endWithDot(String property)
    {
        if (!property) return null
        if (property.endsWith(".")) return property
        return property + "."
    }
    

    ModuleLibGenerator(Project project)
    {
        this.project = project;
        //if (project == null) throw new BuildException("null project!")
        notNull(project, "null project!!")
        log = {msg -> project.log(msg, Project.MSG_ERR) }
    }

    /**
     * Inspect the ant Path. For each path element, assume it's a jar and see if there
     * are corresponding sources, javadoc, and/or javadocUrl. Each jar and it's
     * (potentially null) associeted sources, etc, are returned as a SimpleModuleLibrary
     *
     * @path Path to inspect
     * @return list of SimpleModuleLibs
     */
    def moduleLibsForPath(Path path, boolean exported)
    {
        path.list().collect { jarPath ->
            String jarProperty = findPropertyNameForJar(jarPath)
            String src = source(jarProperty)
            if (src == null) src = findSourceJarByDir(jarPath)
            String jdoc = javadoc(jarProperty)
            String jdocUrl = javadocUrl(jarProperty)
            return new SimpleModuleLibrary(project, jarPath, src, jdoc, jdocUrl, exported)
        }
    }

    def moduleLibsForIdeaLibs(ideaLibs, boolean exported)
    {
        ideaLibs.collect { lib ->
            String jarProperty = findPropertyNameForJar(lib)
            String src = source(jarProperty)
            String jdoc = javadoc(jarProperty)
            String jdocUrl = javadocUrl(jarProperty)
            return new SimpleModuleLibrary(project, lib, null, src, jdoc, jdocUrl,
                    exported)
        }
    }

    def setSourceProperty(String sourceProperty)
    {
        this.sourceProperty = endWithDot(sourceProperty)
    }

    def setJavadocProperty(String javadocProperty)
    {
        this.javadocProperty = endWithDot(javadocProperty)
    }

    def setJavadocUrlProperty(String javadocUrlProperty)
    {
        this.javadocUrlProperty = endWithDot(javadocUrlProperty)
    }
    
    def findSourceJarByDir(String jarPath)
    {
        File jarFile = new File(jarPath)
        File parentDir = jarFile.getParentFile()
        String jarName = jarFile.getName()
        
        String srcJarName = jarName.replace('.jar', '-src.jar')
        File srcJarFile = new File(parentDir, srcJarName)
        
        if (srcJarFile.exists()) return srcJarFile.getAbsolutePath()
        return null
    }

    /**
     * Find the corresponding property name for a jar.
     * <p/>
     * Unfortunately this is slow and stupid. We have to look through
     * [potentially] every property, turn it's value into a path, and then see
     * if it matches the given jar. If it does, it's our boy, and we return the
     * property name.
     *
     * @param jar fully qualified path to the jar
     * @return name of jar's defining property, or null if not found.
     */
    protected String findPropertyNameForJar(String jarPath)
    {
        if (!jarPath.endsWith(".jar")) return null;

        // could cache properties table...?
        Set<Map.Entry> properties = project.getProperties().entrySet()

        for (Map.Entry property: properties)
        {
            String name = (String) property.getKey()
            String value = (String) property.getValue()
            if (value == null) continue
            String[] paths = Path.translatePath(project, value)
            if (paths.length == 0) continue
            if (paths.length > 1) continue
            if (jarPath.equals(paths[0])) return name
        }

        // if we didn't find a property defining the jar, return the jar's filename.
        return new File(jarPath).name
    }

    private String javadoc(String libProperty)
    {
        return propertyLookup(javadocProperty, libProperty)
    }

    private String javadocUrl(String libProperty)
    {
        return propertyLookup(javadocUrlProperty, libProperty)
    }

    private String source(String libProperty)
    {
        //println "calling source(${libProperty})"
        return propertyLookup(sourceProperty, libProperty)
    }
    
   /**
    * @attribute propertyDescriptor source, javadoc, or javadocUrl identifier
    * @attribute libPropertyName the library's property name
    */
    private String propertyLookup(String propertyDescriptor, String libPropertyName)
    {
//        println "propertyDescriptor=${propertyDescriptor} libPropertyName=${libPropertyName}}"
        if (!propertyDescriptor) return null
        String property = propertyDescriptor + libPropertyName
        return project.getProperty(property)
    }
    
    String toString()
    {
        "ModuleLibGenerator{" +
                "sourceProperty=${sourceProperty}," +
                "javadocProperty=${javadocProperty}," +
                "javadocUrlProperty=${javadocUrlProperty}}"
    }

}