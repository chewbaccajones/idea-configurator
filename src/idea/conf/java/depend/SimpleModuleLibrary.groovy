package idea.conf.java.depend

import org.apache.tools.ant.types.Path
import org.apache.tools.ant.Project

/**
 *
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
    boolean exported // add to constructor

    def SimpleModuleLibrary(project, jar, source, javadoc, javadocUrl)
    {
        this.project = project;
        this.classes = new Path(project, jar)
        this.sources = new Path(project, source)
        this.javadocs = new Path(project, javadoc)
        this.javadocUrls = []

        if (javadocUrl != null) javadocUrls << new JavadocUrl(javadocUrl)
    }

    String getName() { null }
    Path getJarDirs() { null }

    String toString()
    {
        return "SimpleModuleLibrary{" +
                "classes=" + classes +
                ", sources=" + sources +
                ", javadocs=" + javadocs +
                ", javadocUrls=" + javadocUrls +
                "}"
    }
}
