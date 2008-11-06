package idea.conf.java.depend

import org.apache.tools.ant.Project
import org.apache.tools.ant.BuildException
import org.apache.tools.ant.types.Path

/**
 * Inspect an ant Path (intended to be a classpath). Each path element is assumed to be
 * a jar in a library. For each path element, find the first 
 * property that has that path element as it's value. Then look for corresponding
 * source, javadoc, and javadocurl properties defined for th
 *
 * @author tomichj
 */
class PathInspector
{
    final Project project
    final String sourceProperty
    final String javadocProperty
    final String javadocUrlProperty
    final def log

    PathInspector(Project project,
                  String sourceProperty,
                  String javadocProperty,
                  String javadocUrlProperty)
    {
        this.project = project;
        this.sourceProperty = endWithDot(sourceProperty)
        this.javadocProperty = endWithDot(javadocProperty)
        this.javadocUrlProperty = endWithDot(javadocUrlProperty)

        if (project == null) throw new BuildException("null project!")
        log = {msg -> project.log(msg, Project.MSG_ERR) }
    }

    /**
     * @return list of little lib-lets, with classes() sources() javadoc() & javadocUrl()
     */
    def libs(Path path)
    {
        path.list().collect {jar ->
            String jarProperty = findPropertyNameForJar(jar);
            log("jar=${jar} jarProperty=${jarProperty}")
            String src = source(jarProperty)
            String jdoc = javadoc(jarProperty)
            String jdocUrl = javadocUrl(jarProperty)
            def lib = [classes:{jar}, sources:{src}, javadoc:{jdoc}, javadocUrl:{jdocUrl}]
            return lib
        }
    }

    private String endWithDot(String property)
    {
        if (!property) return null
        if (property.endsWith(".")) return property
        return property + "."
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
        return null
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

    // propertyTypeFragment is sourceproperty, etc
    private String lookup(String propertyTypeFragment, String libProperty)
    {
        //println("lookup propertyTypeFrag=${propertyTypeFragment} lib=${libProperty}")
        if (!propertyTypeFragment) return null
        String property = propertyTypeFragment + libProperty
        return getProject().getProperty(property)
    }

    String toString()
    {
        "PathInspector{" +
                "sourceProperty=${sourceProperty}," +
                "javadocProperty=${javadocProperty}," +
                "javadocUrlProperty=${javadocUrlProperty}}"
    }

}