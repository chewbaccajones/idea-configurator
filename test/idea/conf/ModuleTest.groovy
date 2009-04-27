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
    private static final String EMPTY_MODULE =
        'JavaModule{rootDir=null,moduleFile=moduleFile,relativePaths=false}'
    void testConstructor()
    {
        JavaModule module = new JavaModule()
        assertTrue module.toString().trim().equals(EMPTY_MODULE)
    }

    void testSetProject()
    {
        JavaModule module = new JavaModule()
        module.setProject([] as Project)
        assertTrue module.toString().trim().equals(EMPTY_MODULE)
    }

    void testDebug()
    {
        JavaModule module = new JavaModule()
        module.setProject([] as Project)
        DebugVisitor v = new DebugVisitor();
        v.visit module
        String out = v.toString()
        assertTrue out.startsWith('JavaModule{')
        assertTrue out.contains('Jdk{jdkName=null}')
    }

}

