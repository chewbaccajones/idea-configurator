package idea.conf

import org.apache.tools.ant.Project
import idea.conf.render.DebugVisitor

/**
*
*
* @author tomichj
*/
class JavaModuleTest extends GroovyTestCase
{
    void testConstructor()
    {
        JavaModule module = new JavaModule()
        println module
    }

    void testSetProject()
    {
        JavaModule module = new JavaModule()
        module.setProject([] as Project)
        println module
    }

    void testDebug()
    {
        JavaModule module = new JavaModule()
        module.setProject([] as Project)
        DebugVisitor v = new DebugVisitor();
        v.visit module
        println v
    }

}

