package idea.conf.url
/**
 * Factory for creating Url objects.
 *
 * @author tomichj
 */
interface UrlFactory
{
    String file(File path)
    String file(String path)
    String http(String path)
    String jar(File path)
    String jar(String path)
}

