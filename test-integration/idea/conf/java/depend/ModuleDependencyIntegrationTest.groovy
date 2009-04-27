package idea.conf.java.depend

import idea.conf.BaseBuildFileTester

public class ModuleDependencyIntegrationTest extends BaseBuildFileTester
{
    void testModuleDependency()
    {
        executeTarget 'module.dependency'
        def moduleDep = orderEntries('module')
        assertNotNull moduleDep
        assertEquals 1, moduleDep.size()
        assertEquals 'another-module', moduleDep[0].'@module-name'
    }
}