package idea.conf.facets

import idea.conf.BaseBuildFileTester

/**
 * 
 * User: tomichj
 * Date: Apr 28, 2009
 * Time: 4:56:08 PM
 */

public class WebIntegrationTest extends BaseBuildFileTester
{
    void testBasicSettings()
    {
        executeTarget 'web.basic'
        assertSetting 'web', 'EXPLODED_URL', 'file://$MODULE_DIR$/build/exploded'
        assertSetting 'web', 'EXPLODED_ENABLED', 'true'
        assertSetting 'web', 'JAR_URL', 'file://$MODULE_DIR$/build/foo.war'
        assertSetting 'web', 'JAR_ENABLED', 'true'
        assertSetting 'web', 'EXCLUDE_EXPLODED_DIRECTORY', 'false'

    }

    void testDeploymentDescriptors()
    {
        executeTarget 'web.complex'
        //println getOutput()
        final def facet = facet('web')

        def deployments = facet.configuration.descriptors.deploymentDescriptor
        assertEquals 4, deployments.size()
        def expectedNames = ["web.xml", "sun-web.xml", null, null]
        assertEquals expectedNames, deployments.'@name'

        def roots = facet.configuration.webroots.root
        assertEquals 1, roots.size()
        def root = roots[0]
        //println root
        assertEquals 'file://$MODULE_DIR$/web', root.'@url'
        assertEquals '/', root.'@relative'
    }
}
