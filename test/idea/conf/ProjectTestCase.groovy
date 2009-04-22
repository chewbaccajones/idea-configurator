package idea.conf

import org.apache.tools.ant.Project

/**
 * 
 * User: tomichj
 * Date: Apr 21, 2009
 * Time: 4:03:34 PM
 */

public abstract class ProjectTestCase extends GroovyTestCase
{
    static final Project project = new Project()

    protected void setUp()
    {
        Logger.init project
    }


}