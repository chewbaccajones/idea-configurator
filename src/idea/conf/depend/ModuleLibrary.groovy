package idea.conf.depend

import org.apache.tools.ant.types.Path
import org.apache.tools.ant.Project
import idea.conf.depend.JavadocUrl
import idea.conf.Visitable;


/**
 * A module library.
 *
 * @author tomichj
 */
class ModuleLibrary implements Dependency
{
    private Path classes // roots with jar entries
    private Path jarDirs // roots with file entries, plus separate jarDirectory directive
    private Path sources;
    private Path javadocs;
    private List<JavadocUrl> javadocUrls = []

    boolean exported = false


    ModuleLibrary(Project project)
    {
        classes = new Path(project)
        jarDirs = new Path(project)
        sources = new Path(project)
        javadocs = new Path(project)
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
        javadocUrls << ju
        return ju
    }


    List<Visitable> getChildren()
    {
        return null;
    }
    

    String toString()
    {
        "ModuleLibrary{" <<
                "classes=" << classes <<
                ", jarDirs=" << jarDirs <<
                ", sources=" << sources <<
                ", javadocs=" << javadocs <<
                ", javadocUrls=" << javadocUrls <<
                ", exported=" << exported <<
                "}"
    }
}

