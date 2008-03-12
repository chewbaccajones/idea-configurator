package idea.conf
/**
 *
 *
 * @author tomichj
 */
class ClasspathFilter implements Dependency
{

    List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        return "ClasspathFilter{}"
    }
}