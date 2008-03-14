package idea.conf.depend

import idea.conf.Visitable

import org.apache.tools.ant.BuildException;

/**
*
*
* @author tomichj
*/
class GlobalLibrary implements Dependency, Exportable
{
    String name;
    boolean exported


    public List<Visitable> getChildren()
    {
        return null;
    }

    void validate() {
        if (!name) throw new BuildException("GlobalLibrary name must not be null!")
    }

    String toString()
    {
        return "GlobalLibrary{" << "name=" << name << ", exported=" << exported << "}"
    }
}

