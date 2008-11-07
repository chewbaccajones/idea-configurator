package idea.conf.java.depend

import org.apache.tools.ant.types.Path

/**
 *
 *
 * @author tomichj
 */
interface ModuleLibraryType extends Dependency, Exportable
{
    String getName()
    Path getClasses()
    Path getJarDirs()
    Path getSources()
    Path getJavadocs()
    List<JavadocUrl> getJavadocUrls()
    boolean isExported()
}
