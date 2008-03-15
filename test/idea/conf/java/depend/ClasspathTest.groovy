package idea.conf.java.depend

import org.apache.tools.ant.Project

/**
*
*
* @author tomichj
*/
class ClasspathTest extends GroovyTestCase
{
    void testEndWithDot()
    {
        def cp = new Classpath([] as Project, "", "", "")
        assertNull(cp.endWithDot(null))
        assertNull(cp.endWithDot(""))
    }
}