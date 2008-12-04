package idea.conf.java.depend

import org.apache.tools.ant.Project

/**
*
*
* @author tomichj
*/
class ClasspathTest extends GroovyTestCase
{
    void testGetChildrenNoStuff()
    {
        Project p = new Project()
        ModuleLibGenerator libGen = [p]

        Classpath cp = new Classpath(p, libGen)
        cp.createPathElement().setPath("/foo/bar") 
        def kids = cp.getChildren()

        assertTrue(kids.size() == 1)
    }
}
