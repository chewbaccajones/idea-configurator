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
    void testBasic()
    {
        executeTarget 'web.basic'
        println getOutput()

    }

    void testBasicSettings()
    {
        executeTarget 'web.basic.with.attributes'
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
        assertEquals 'file://$MODULE_DIR$/web', root.'@url'
        assertEquals '/', root.'@relative'
    }

    void testWebVersion()
    {
        executeTarget 'web.specify.version'

        final def facet = facet('web')
        def descriptors = facet.configuration.descriptors.deploymentDescriptor
        assertEquals 1, descriptors.size()

        def webXml = descriptors[0]
        assertEquals "2.5", webXml.'@version'
    }

    void testVersionAndWebXmlExpectException()
    {
        expectBuildException('web.specify.version.and.webxml',
                'specifying <web version="..."> and <webxml> should explodify')
    }

}
