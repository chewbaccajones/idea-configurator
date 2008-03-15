package idea.conf.java.depend

import idea.conf.Visitable

import org.apache.tools.ant.BuildException;


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
        if (!name) throw new BuildException("ProjectLibrary name must not be null!")
    }

    String toString()
    {
        "ProjectLibrary{name=${name}, exported=${exported}}"  
    }
}

