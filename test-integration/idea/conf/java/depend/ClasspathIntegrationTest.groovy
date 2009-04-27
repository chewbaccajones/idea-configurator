package idea.conf.java.depend

import idea.conf.BaseBuildFileTester

/**
 * 
 * User: tomichj
 * Date: Apr 24, 2009
 * Time: 3:05:19 PM
 */

public class ClasspathIntegrationTest extends BaseBuildFileTester
{
    void testClasspathReference()
    {
        executeTarget 'classpath.reference'

        def moduleLibs = orderEntries('module-library')
        assertEquals 2, moduleLibs.size()

        def final urls = ['jar://$MODULE_DIR$/lib/module2.jar!/',
                'jar://$MODULE_DIR$/lib/dumb.jar!/']
        assertEquals urls, moduleLibs.library.CLASSES.root.collect { it.'@url'[0] }
    }
}
