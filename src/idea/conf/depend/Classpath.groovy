package idea.conf.depend

import idea.conf.Visitable
import org.apache.tools.ant.BuildException
import org.apache.tools.ant.Project
import org.apache.tools.ant.types.Path

/**
 * Wrapper class that takes a classpath and will generate module library entries.
 *
 * @author tomichj
 */
class Classpath extends Path implements Dependency, Exportable
{
    boolean exported

    String sourceProperty;
    String javadocProperty;
    String javadocUrlProperty;

    def log
    
    Classpath(Project project, sourceProperty, javadocProperty, javadocUrlProperty)
    {
        super(project)
        if (project == null) throw new BuildException("null project!")

        log = { msg -> project.log(msg, Project.MSG_DEBUG) }

        this.sourceProperty = endWithDot(sourceProperty)
        this.javadocProperty = endWithDot(javadocProperty)
        this.javadocUrlProperty = endWithDot(javadocUrlProperty)

        log("sourceProperty=${this.sourceProperty}")
    }


    List<Visitable> getChildren()
    {
        list().collect { jar ->
            String jarProperty = findPropertyNameForJar(jar);
            log("jar=${jar} jarProperty=${jarProperty}")
            def lib = new ModuleLibrary(getProject())
            lib.setExported(exported)
            lib.createClasses().setPath(jar)
            lib.createSources().setPath(source(jarProperty))
            lib.createJavadoc().setPath(javadoc(jarProperty))
            lib.createJavadocUrl().setUrl(javadocUrl(jarProperty))
            return lib
        }
    }

    String endWithDot(String property)
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
        // could cache properties table...
        Project project = getProject()
        Set<Map.Entry> entries = project.getProperties().entrySet()

        for (Map.Entry entry : entries)
        {
            String key = (String) entry.getKey()
            String value = (String) entry.getValue()
//            log("key=${key}   value=${value}")
            if (value == null) continue
            String[] paths = Path.translatePath(project, value)
//            log("path=" + paths.join(":"))
            if (paths.length == 0) continue
            if (paths.length > 1) continue
            if (jar.equals(paths[0])) return key
        }
        return null
    }


    private String javadoc(String lib)
    {
        return lookup(javadocProperty, lib)
    }


    private String javadocUrl(String lib)
    {
        return lookup(javadocUrlProperty, lib)
    }


    private String source(String lib)
    {
        return lookup(sourceProperty, lib)
    }


    private String lookup(String propertyFrag, String lib)
    {
        if (!propertyFrag) return null
        String property = propertyFrag + lib
        return getProject().getProperty(property)
    }
    

    void validate() {
        // nothing to validate
    }


    String toString()
    {
        "Classpath{exported=${exported}, list=" + list() + "}"
    }
}


