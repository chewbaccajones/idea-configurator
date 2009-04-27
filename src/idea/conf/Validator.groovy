package idea.conf

import org.apache.tools.ant.BuildException

/**
 * 
 * User: tomichj
 * Date: Mar 19, 2009
 * Time: 2:44:57 PM
 */

public class Validator
{
    static def notNull(field, String msg)
    {
        if (field == null) huck(msg)
    }
    
    static def huck(String msg)
    {
        throw new BuildException(msg)
    }
}