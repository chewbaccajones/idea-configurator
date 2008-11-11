package idea.conf.java.depend

import idea.conf.Visitable

import org.apache.tools.ant.BuildException;


/**
 * A Dependency to another module. 
 *
 * @author tomichj
 */
class Module implements Dependency, Exportable
{
    String name
    boolean exported
    

    List<Visitable> getChildren()
    {
        return null;
    }

    void validate()
    {
        if (!name) throw new BuildException("Module dependency requires name attribute")
    }

    String toString()
    {
        "Module{name=${name}, exported=${exported}}"
    }

}

