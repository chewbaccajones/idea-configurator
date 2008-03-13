package idea.conf.depend

import idea.conf.Visitable

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