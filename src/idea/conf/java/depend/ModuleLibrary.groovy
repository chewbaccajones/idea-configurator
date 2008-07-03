package idea.conf.java.depend

import idea.conf.java.depend.JavadocUrl
import idea.conf.Visitable;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path
import org.apache.tools.ant.Project


/**
 * A module library. The most complex dependency available. 
 *
 * @author tomichj
 */
class ModuleLibrary implements Dependency, Exportable
{
    String name
    private Path classes // roots with jar entries
    private Path jarDirs // roots with file entries, plus separate jarDirectory directive
    private Path sources;
    private Path javadocs;
    private List<JavadocUrl> javadocUrls = []

    boolean exported


    ModuleLibrary(Project project)
    {
        classes = new Path(project)
        jarDirs = new Path(project)
        sources = new Path(project)
        javadocs = new Path(project)
    }

    Path getClasses() { return classes }
    Path getJarDirs() { return jarDirs }
    Path getSources() { return sources }
    Path getJavadocs() { return javadocs }

    List<JavadocUrl> getJavadocUrls()
    {
        javadocUrls.findAll { it.url != null }
    }


    void validate()
    {
        if (!classes.size()) throw new BuildException("ModuleLibrary requires classes")
    }


    void setClasses(File jar)
    {
        this.classes.setLocation(jar)
    }

    void setSource(File sources)
    {
        this.sources.setLocation(sources)
    }


    void setJavadoc(File javadoc)
    {
        this.javadocs.setLocation(javadoc)
    }


    void setJavadocUrl(String javadocUrl)
    {
        javadocUrls.add(new JavadocUrl(url:javadocUrl))
    }


    Path createClasses()
    {
        return classes.createPath()
    }
    

    Path createJarDirectories()
    {
        return jarDirs.createPath()
    }


    Path createSources()
    {
        return sources.createPath()
    }


    Path createJavadoc()
    {
        return javadocs.createPath()
    }


    JavadocUrl createJavadocUrl()
    {
        JavadocUrl ju = new JavadocUrl()
        javadocUrls.add(ju)
        return ju
    }


    /**
     * Remove any 'classes' that matches the given pattern. The pattern uses the
     * jdk 1.5 java.util.regex.Pattern syntax.
     *
     * @param pattern matching 'classes' Roots are removed.
     * @see java.util.regex.Pattern
     */
    void removeMatchingClasses(List<ClasspathFilter> filters)
    {
        def classesCopy = classes.list() as List
        ListIterator iter = classesCopy.listIterator()
        while (iter.hasNext())
        {
            String path = iter.next()
            if (filters.any { filter -> filter.matches(path) } ) iter.remove()
        }
        classes = new Path( classes.getProject() )
        classesCopy.each { path -> classes.createPathElement().setPath(path) }
    }


    List<Visitable> getChildren()
    {
        return null;
    }
    

    String toString()
    {
        "ModuleLibrary{" <<
                "classes=${classes}" <<
                ", jarDirs=${jarDirs}" <<
                ", sources=${sources}" <<
                ", javadocs=${javadocs}" <<
                ", javadocUrls=${javadocUrls}" <<
                ", exported=${exported}" <<
                "}"
    }
}
