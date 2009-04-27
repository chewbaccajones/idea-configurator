package idea.conf.java.depend

import idea.conf.Visitable

import org.apache.tools.ant.BuildException;
import static idea.conf.Validator.*

/**
 * 
 *
 * @author tomichj
 */
class ProjectLibrary implements Dependency, Exportable
{
    String name;
    boolean exported


    public List<Visitable> getChildren()
    {
        return null;
    }

    void validate()
    {
        //if (!name) throw new BuildException("ProjectLibrary name must not be null!")
        notNull(name, "PorjectLibrary name must not be null")
    }

    String toString()
    {
        "ProjectLibrary{name=${name}, exported=${exported}}"  
    }
}

