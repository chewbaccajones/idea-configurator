package idea.conf.java.depend
/**
 * Dumb little wrapper class to wrap a javadoc url. Used by ModuleLibrary.
 *
 * @author tomichj
 * @see idea.conf.java.depend.ModuleLibrary
 */
class JavadocUrl
{
    String url;

    def JavadocUrl()
    {
    }

    def JavadocUrl(url)
    {
        this.url = url;
    }

    String toString()
    {
        return "JavadocUrl{url=${url}}"
    }
}