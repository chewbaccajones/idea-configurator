package idea.conf.java.depend

import idea.conf.Visitable

import org.apache.tools.ant.BuildException;
import static idea.conf.Validator.*

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
        notNull(name, "JavaModule requires name attribute be set")
        //if (!name) throw new BuildException("JavaModule dependency requires name attribute")
    }

    String toString()
    {
        "Module{name=${name}, exported=${exported}}"
    }

}

