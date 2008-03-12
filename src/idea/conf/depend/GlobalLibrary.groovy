package idea.conf
/**
 *
 *
 * @author tomichj
 */
class GlobalLibrary implements Dependency
{
    String name;
    boolean exported = false;


    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        return "GlobalLibrary{" << "name=" << name << ", exported=" << exported << "}"
    }
}