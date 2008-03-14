package idea.conf.depend

import idea.conf.Visitable

/**
 *
 *
 * @author tomichj
 */
class Jdk implements Dependency
{
    String jdkName // if null, we use the jdk inherited from project
    boolean exported
    

    List<Visitable> getChildren()
    {
        return null;
    }


    void validate() {
        // nothing to validate just now
    }


    String toString()
    {
        "Jdk{jdkName=${jdkName}, exported=${exported}}"
    }
    
}

