package idea.conf.depend

import idea.conf.Visitable

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

