package idea.conf.java.depend

import org.apache.tools.ant.Project

/**
 *
 *
 * @author tomichj
 */

public class IdeaLibraryTest extends GroovyTestCase
{
    void testAsModule()
    {
        Project project = new Project()
        IdeaLibrary idealib = new IdeaLibrary(project)
        idealib.setName('foo.jar')
        println idealib
        ModuleLibraryType modlib = idealib.asModuleLibrary()
        assertNotNull(modlib)
        assertTrue(modlib.getClasses().toString().startsWith('$APPLICATION'))
    }
}
