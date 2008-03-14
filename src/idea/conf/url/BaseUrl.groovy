package idea.conf.url
/**
 *
 *
 * @author tomichj
 */
abstract class BaseUrl implements Url
{
    String path;
    
    abstract String url();
    
    public boolean matches(String pattern)
    {
        return url().matches(pattern);
    }

    public boolean contains(String candidatePath)
    {
        return url().contains(candidatePath)
    }
}

