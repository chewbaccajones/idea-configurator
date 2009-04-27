package idea.conf.java.depend

import org.apache.tools.ant.BuildFileTest
import idea.conf.BaseBuildFileTester

public class PathFilterIntegrationTest extends BaseBuildFileTester
{
    public void setUp()
    {
        configureProject("build-test.xml")
    }

    public void testManyJarToModulesElements()
    {
        executeTarget 'path.filter'
        def libs = moduleManager().orderEntry.findAll { it.'@type' == 'module-library' }
        def urls = libs.library.CLASSES.root.collect { it.'@url'[0] }

        assertTrue urls.any { it.contains ('garble.jar') }
        assertTrue urls.any { it.contains ('dumb.jar') }
        assertTrue urls.any { it.contains ('survives.jar') }
        assertFalse urls.any { it.contains ('module2.jar') }
    }
}
