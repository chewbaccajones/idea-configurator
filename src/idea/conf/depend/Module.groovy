package idea.conf.depend

import idea.conf.Visitable

/**
*
*
* @author tomichj
*/
class Module implements Dependency
{
    String moduleName
    boolean exported
    

    List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "Module{moduleName=${moduleName}, exported=${exported}}"
    }
}

