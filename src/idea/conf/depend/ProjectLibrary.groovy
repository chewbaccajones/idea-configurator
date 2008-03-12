package idea.conf
/**
 *
 *
 * @author tomichj
 */
class ProjectLibrary implements Dependency
{
    String name;
    boolean exported = false;


    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "ProjectLibrary{name=" << name << ", exported=" << exported << "}"  
    }
}