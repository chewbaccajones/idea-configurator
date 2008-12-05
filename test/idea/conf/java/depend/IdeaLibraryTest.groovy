package idea.conf.java.depend

import org.apache.tools.ant.Project

/**
 *
 *
 * @author tomichj
 */

public class IdeaLibraryTest extends GroovyTestCase
{
    static final String NAME = 'foo.jar'

    void testAsModule()
    {
        Project project = new Project()
        IdeaLibrary idealib = new IdeaLibrary(project)    
        idealib.setName(NAME)
        ModuleLibraryType modlib = idealib.asModuleLibrary()
        assertNotNull(modlib)
        assertTrue(modlib.ideaLibs[0] == NAME)
    }
}
