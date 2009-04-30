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
    private static final String EMPTY_MODULE =
        'JavaModule{rootDir=null,moduleFile=moduleFile,relativePaths=true}'

    void testConstructor()
    {
        Project p = new Project()
        JavaModule module = new JavaModule()
        module.setProject(p)
        assertNotNull module.rootDir
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

