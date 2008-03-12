package idea.conf
/**
 *
 *
 * @author tomichj
 */
class Module implements Dependency
{

    List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "Module{}"
    }
}