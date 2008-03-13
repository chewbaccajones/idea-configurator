package idea.conf.depend

import idea.conf.Visitable

/**
 *
 *
 * @author tomichj
 */
class Jdk implements Dependency
{
    String jdkName // if null, use inherited jdk
    boolean exported
    

    List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "Jdk{jdkName=${jdkName}, exported=${exported}}"
    }

}

