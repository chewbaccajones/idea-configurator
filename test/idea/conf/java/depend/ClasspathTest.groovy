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

        final ModuleLibraryType lib = kids[0]
        assertEquals(1, lib.getClasses().size())
        assertEquals(0, lib.getSources().size()) 
    }

    void testGetChildrenWithStuff()
    {
        final String fooJar = "/lib/foo.jar"
        final String fooSrcJar = "/lib/foo.src.jar"

        Project p = new Project()
        p.setInheritedProperty("foo.jar", fooJar)
        p.setInheritedProperty("source.foo.jar", fooSrcJar)

        ModuleLibGenerator libGen = [p]

        Classpath cp = new Classpath(p, libGen)
        cp.createPathElement().setPath(fooJar)
        def kids = cp.getChildren()

        final ModuleLibraryType lib = kids[0]
        assertEquals(1, lib.getClasses().size()) 
        assertEquals(1, lib.getSources().size()) 
    }

}
