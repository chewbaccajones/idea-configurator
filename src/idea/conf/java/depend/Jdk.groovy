package idea.conf.java.depend

import idea.conf.Visitable

/**
 * Specify a JDK for the project. If a name is specified, that named jdk will be used.
 * If no jdkName is specified then the project JDK will be inherited.
 *
 * @author tomichj
 */
class Jdk implements Dependency
{
    String jdkName // if null, we use the jdk inherited from project
    

    List<Visitable> getChildren()
    {
        return null;
    }


    void validate()
    {
        // nothing to validate just now
    }


    String toString()
    {
        "Jdk{jdkName=${jdkName}}}"
    }
    
}

