package idea.conf.java.depend

import idea.conf.java.depend.JavadocUrl
import idea.conf.Visitable;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path
import org.apache.tools.ant.Project


/**
 * A module library.
 *
 * @author tomichj
 */
class ModuleLibrary implements ModuleLibraryType
{
    String name
    boolean exported
    private Path classes // roots with jar entries
    private Path jarDirs // roots with file entries, plus separate jarDirectory directive
    private Path sources;
    private Path javadocs;
    private List<JavadocUrl> javadocUrls = []


    ModuleLibrary(Project project)
    {
        this.classes = new Path(project)
        this.jarDirs = new Path(project)
        this.sources = new Path(project)
        this.javadocs = new Path(project)
    }

    /**
     * Dependencies calls this on a populated ModuleLibrary to search for properties
     * that are associated by names. This should be called AFTER the library is otherwise
     * populated.
     *
     */
    def addLibraryAssets(ModuleLibGenerator inspector)
    {
        def addons = inspector.moduleLibsForPath(classes, exported)
        addons.each { ModuleLibraryType lib -> add(lib) }
    }

    def add(ModuleLibraryType other)
    {
        classes.add(other.getClasses())
        jarDirs.add(other.getJarDirs())
        sources.add(other.getSources())
        javadocs.add(other.getJavadocs())
        javadocUrls.addAll(other.getJavadocUrls())
    }
    
    Path getClasses() { return classes }
    Path getJarDirs() { return jarDirs }
    Path getSources() { return sources }
    Path getJavadocs() { return javadocs }

    List<JavadocUrl> getJavadocUrls()
    {
        javadocUrls.findAll { it.url != null }
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
    
    List<Visitable> getChildren()
    {
        return null;
    }
    
    void validate()
    {
        if (!isValid()) throw new BuildException("ModuleLibrary requires classes")
    }

    boolean isValid()
    {
        if (classes.size() > 0) return true
        if (jarDirs.size() > 0) return true
        return false
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

