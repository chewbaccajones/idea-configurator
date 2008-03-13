package idea.conf.depend

import idea.conf.Visitable

/**
*
*
* @author tomichj
*/
class IdeaLibrary
{
    String name


    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        return "IdeaLibrary{" << "name=" << name << "}"
    }
}