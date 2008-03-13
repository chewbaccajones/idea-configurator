package idea.conf.url
/**
 *
 *
 * @author tomichj
 */
interface UrlFactory
{
    FileUrl fileUrl(File path);

    FileUrl fileUrl(String path);

    HttpUrl httpUrl(String path);

    JarUrl jarUrl(String path);

    JarUrl jarUrl(File file);
}

