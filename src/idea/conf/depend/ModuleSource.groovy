package idea.conf.depend

import idea.conf.Visitable

/**
 * ModuleSource asserts the order of the module's source in the
 * dependency order.
 *
 * @author tomichj
 */
class ModuleSource implements Dependency
{

    void validate() {
        // absolutely nothing to validate
    }
    
    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "ModuleSource{}"
    }

}

