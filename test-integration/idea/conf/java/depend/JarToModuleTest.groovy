package idea.conf.java.depend

import org.apache.tools.ant.BuildFileTest
import idea.conf.BaseBuildFileTester

/**
 * 
 */
public class JarToModuleTest extends BaseBuildFileTester
{
    public void testManyJarToModulesElements()
    {
        executeTarget 'jarToMmodule'

        // test the module libs... should only be one
        def moduleLibs = orderEntries('module-library')
        assertEquals 1, moduleLibs.size()
        assertEquals 'jar://$MODULE_DIR$/survives.jar!/',
                moduleLibs[0].library.CLASSES.root[0].'@url'

        // test the module declarations... should be one for each of the filtered jars
        def moduleDeps = orderEntries('module')
        assertEquals(['garble', 'dumbModule2', 'dumbModule'], moduleDeps.'@module-name')
        assertEquals ([null, "", null], moduleDeps.'@exported')
    }
    

    public void testTopLevelJarsToModules()
    {
        executeTarget 'jarsToModules.toplevel'
        println getOutput()

        // check that two jars were converted to modules
        def moduleDeps = orderEntries('module')
        assertEquals(['dumb', 'garble'], moduleDeps.'@module-name')
        assertEquals ([null, null], moduleDeps.'@exported')

        def libs = orderEntries('module-library')
        def jars = ['jar://$MODULE_DIR$/survives.jar!/',
                'jar://$MODULE_DIR$/lib/module2.jar!/']
        assertEquals jars, libs.library.CLASSES.root.collect { it[0].'@url' }
    }

}
