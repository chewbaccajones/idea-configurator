package idea.conf.depend
/**
 * Dumb little wrapper class to wrap a javadoc url. Used by ModuleLibrary.
 *
 * @author tomichj
 * @see idea.conf.depend.ModuleLibrary
 */
class JavadocUrl
{
    String url;

    String toString()
    {
        return "JavadocUrl{url=${url}}"
    }
}