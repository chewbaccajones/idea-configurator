package idea.conf.url
/**
 *
 *
 * @author tomichj
 */
abstract class BaseUrl implements Url
{
    String path;
    
    abstract String contents();
    
    public boolean matches(String pattern)
    {
        return contents().matches(pattern);
    }

    public boolean contains(String candidatePath)
    {
        return contents().contains(candidatePath)
    }
}

