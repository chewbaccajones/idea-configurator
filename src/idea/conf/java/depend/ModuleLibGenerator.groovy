package idea.conf.java.depend

import org.apache.tools.ant.Project
import org.apache.tools.ant.BuildException
import org.apache.tools.ant.types.Path

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
    final Project project
    private String sourceProperty = "source."
    private String javadocProperty = "javadoc."
    private String javadocUrlProperty = "javadocurl."

    final def log


    static String endWithDot(String property)
    {
        if (!property) return null
        if (property.endsWith(".")) return property
        return property + "."
    }
    

    ModuleLibGenerator(Project project)
    {
        this.project = project;
        if (project == null) throw new BuildException("null project!")
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
        path.list().collect { jar ->
            String jarProperty = findPropertyNameForJar(jar);
            log("jar=${jar} jarProperty=${jarProperty}")
            String src = source(jarProperty)
            String jdoc = javadoc(jarProperty)
            String jdocUrl = javadocUrl(jarProperty)
            new SimpleModuleLibrary(project, jar, src, jdoc, jdocUrl, exported)
            //def lib = [classes:jar, sources:src, javadoc:jdoc, javadocUrl:jdocUrl]
            //return lib
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
    private String findPropertyNameForJar(String jar)
    {
        if (!jar.endsWith(".jar")) return null;
        
        // could cache properties table...
        Project project = getProject()
        Set<Map.Entry> entries = project.getProperties().entrySet()

        for (Map.Entry entry: entries)
        {
            String key = (String) entry.getKey()
            String value = (String) entry.getValue()
            if (value == null) continue
            String[] paths = Path.translatePath(project, value)
            if (paths.length == 0) continue
            if (paths.length > 1) continue
            if (jar.equals(paths[0])) return key
        }

        // if we didn't find the jar property, return the jar's filename
        return new File(jar).name
//        return null
    }

    private String javadoc(String libProperty)
    {
        return lookup(javadocProperty, libProperty)
    }

    private String javadocUrl(String libProperty)
    {
        return lookup(javadocUrlProperty, libProperty)
    }

    private String source(String libProperty)
    {
        //println "calling source(${libProperty})"
        return lookup(sourceProperty, libProperty)
    }

    /**
     * 
     */
    private String lookup(String propertyTypeFragment, String libProperty)
    {
        if (!propertyTypeFragment) return null
        String property = propertyTypeFragment + libProperty
        return getProject().getProperty(property)
    }

    String toString()
    {
        "ModuleLibGenerator{" +
                "sourceProperty=${sourceProperty}," +
                "javadocProperty=${javadocProperty}," +
                "javadocUrlProperty=${javadocUrlProperty}}"
    }

}