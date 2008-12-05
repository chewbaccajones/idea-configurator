package idea.conf.java.depend

import org.apache.tools.ant.types.Path
import org.apache.tools.ant.Project
import idea.conf.Visitable

/**
 * Basic implementation of ModuleLibraryType. 
 *
 * @author tomichj
 */
class SimpleModuleLibrary implements ModuleLibraryType
{
    Project project
    Path classes
    Path sources
    Path javadocs
    List<JavadocUrl> javadocUrls
    boolean exported
    List<String> ideaLibs = []
    

    def SimpleModuleLibrary(project, jar, source, javadoc, javadocUrl, exported)
    {
        this.project = project;
        this.classes = new Path(project, jar)
        this.sources = new Path(project, source)
        this.javadocs = new Path(project, javadoc)
        this.javadocUrls = []
        this.exported = exported

        if (javadocUrl != null) javadocUrls << new JavadocUrl(javadocUrl)
    }

    String getName() { null }

    Path getJarDirs() { new Path(project) }

    void validate()  { } // check for non-empty classes?

    List<Visitable> getChildren() { return null }

    String toString()
    {
        return "SimpleModuleLibrary{" +
                "classes=" + classes +
                ", ideaLibs=${ideaLibs}" +
                ", sources=" + sources +
                ", javadocs=" + javadocs +
                ", javadocUrls=" + javadocUrls +
                ", exported=" + exported +
                "}"
    }
}
