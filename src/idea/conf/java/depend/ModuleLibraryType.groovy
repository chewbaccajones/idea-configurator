package idea.conf.java.depend

import org.apache.tools.ant.types.Path

/**
 *
 *
 * @author tomichj
 */
interface ModuleLibraryType extends Dependency, Exportable
{
    /** @Nullable */
    String getName()

    List<String> getIdeaLibs()
    
    Path getClasses()
    Path getJarDirs()
    Path getSources()
    Path getJavadocs()
    List<JavadocUrl> getJavadocUrls()
}
