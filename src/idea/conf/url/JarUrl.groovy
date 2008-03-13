package idea.conf.url
/**
 *
 *
 * @author tomichj
 */
class JarUrl extends BaseUrl
{
    JarUrl(String path)
    {
        this.path = path
    }
    
    
    String contents()
    {
        return "jar://" + path + "!/";
    }


    String toString()
    {
        return "JarUrl{" +
               "path='" + path + "'" +
               "}";
    }
}


