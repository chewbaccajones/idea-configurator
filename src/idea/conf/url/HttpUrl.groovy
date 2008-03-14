package idea.conf.url
/**
 *
 *
 * @author tomichj
 */
class HttpUrl extends BaseUrl
{
    HttpUrl(String path)
    {
        this.path = path.endsWith("/") ? path : path + "/"
        if (!valid()) throw new RuntimeException("Malformed http url:" + path);
    }


    String url()
    {
        return path;
    }

    private boolean valid()
    {
        try
        {
            new URL(path);
            return path.startsWith("http://");
        }
        catch (MalformedURLException e)
        {
            return false;
        }
    }


    String toString()
    {
        return "HttpUrl{" +
               "path='" + path + "'" +
               "}";
    }
}