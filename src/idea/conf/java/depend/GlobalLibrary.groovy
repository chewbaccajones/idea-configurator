package idea.conf.java.depend

import idea.conf.Visitable

import org.apache.tools.ant.BuildException;
import static idea.conf.Validator.*

/**
 * A global library declaration.
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

    void validate()
    {
        //if (!name) throw new BuildException("GlobalLibrary name must not be null!")
        notNull(name, "GlobalLibrary name must not be null!")
    }

    String toString()
    {
        "GlobalLibrary{name=${name}, exported=${exported}}"
    }
}

