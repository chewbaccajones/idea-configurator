package idea.conf.url
/**
 *
 *
 * @author tomichj
 */
class FileUrl extends BaseUrl
{
    FileUrl(String path)
    {
        this.path = path
    }


    String url()
    {
        "file://" + path
    }


    String toString()
    {
        return "FileUrl{" +
               "path='" + path + "'" +
               "}";
    }

}


