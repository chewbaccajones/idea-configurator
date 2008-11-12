package idea.conf

import org.apache.tools.ant.Project
import idea.conf.render.DebugVisitor

/**
*
*
* @author tomichj
*/
class ModuleTest extends GroovyTestCase
{
    void testConstructor()
    {
        Module module = new Module()
        println module
    }

    void testSetProject()
    {
        Module module = new Module()
        module.setProject([] as Project)
        println module
    }

    void testDebug()
    {
        Module module = new Module()
        module.setProject([] as Project)
        DebugVisitor v = new DebugVisitor();
        v.visit module
        println v
    }

}

