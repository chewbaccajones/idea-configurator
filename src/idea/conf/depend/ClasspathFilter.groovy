package idea.conf.depend

import idea.conf.Visitable
import org.apache.tools.ant.BuildException

/**
*
*
* @author tomichj
*/
class ClasspathFilter implements Dependency
{
    String pattern;

    
    boolean matches(String jar)
    {
        return jar.matches(pattern)
    }

    List<Visitable> getChildren()
    {
        return null;
    }

    void validate()
    {
        if (!pattern) throw new BuildException("ClasspathFilter pattern cannot be null!")
    }


    String toString()
    {
        "ClasspathFilter{" << "pattern=" << pattern << "}"
    }
}