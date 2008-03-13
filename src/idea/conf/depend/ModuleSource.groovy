package idea.conf.depend

import idea.conf.Visitable

/**
 *
 *
 * @author tomichj
 */
class ModuleSource implements Dependency
{

    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "ModuleSource{}"
    }
}

