package idea.conf.url

/**
 *
 *
 * @author tomichj
 */
interface Url
{
    boolean matches(String pattern)
    boolean contains(String candidatePath)
}