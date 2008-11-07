package idea.conf.java.depend

import org.apache.tools.ant.Project
import org.apache.tools.ant.types.Path

/**
 *
 *
 * @author tomichj
 */
class SimpleModuleLibraryTest extends GroovyTestCase 
{
    void testConstructor()
    {
        Project project = new Project()
        SimpleModuleLibrary lib =
            new SimpleModuleLibrary(project, "/foo/bar", null, null, null, false)
        assertNotNull(lib.getClasses())
        assertEquals(Path.class, lib.getClasses().getClass())
        assertEquals(Path.class, lib.getSources().getClass())
        assertEquals(Path.class, lib.getJavadocs().getClass())
        assertEquals(ArrayList.class, lib.getJavadocUrls().getClass())
    }

    void testPath()
    {
        Project project = new Project()
        SimpleModuleLibrary lib =
            new SimpleModuleLibrary(project, "/foo/bar", null, null, null, false)
        assertEquals(1, lib.getClasses().size())
        assertNotNull(lib.getSources())
        assertEquals(0, lib.getSources().size())
        lib.getSources().createPathElement().setPath("/foo/bar.src1")
        lib.getSources().createPathElement().setPath("/foo/bar.src2")
        assertEquals(1, lib.getClasses().size())
        assertEquals(2, lib.getSources().size())
    }

}