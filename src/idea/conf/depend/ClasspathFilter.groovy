package idea.conf.depend

import idea.conf.Visitable
import org.apache.tools.ant.BuildException

/**
* Specifies a jdk 1.5 regex pattern used to remove any matching classes from a
* <classpath>.  
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