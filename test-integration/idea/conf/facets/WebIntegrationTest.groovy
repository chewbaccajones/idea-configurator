package idea.conf.facets

import idea.conf.BaseBuildFileTester


public class WebIntegrationTest extends BaseBuildFileTester
{
    void testBasic()
    {
        executeTarget 'web.basic'

        final def web = facet('web')
        def deployment = web.configuration.descriptors.deploymentDescriptor
        assertEquals 1, deployment.size()
        assertEquals 'file://$MODULE_DIR$/web/WEB-INF/web.xml', deployment[0].'@url'
        assertEquals 'web.xml', deployment[0].'@name'
        assertEquals '2.5', deployment[0].'@version'

        def packaging = web.configuration.packaging.containerElement
        assertEquals 1, packaging.size()
        assertEquals 'build-test', packaging[0].'@name'
        assertEquals 'module', packaging[0].'@type'

        assertRoot('file://$MODULE_DIR$/web', '/')
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

    void assertRoot(String url, String relative)
    {
        def roots = facet('web').configuration.webroots.root
        assertNotNull roots
        assertEquals 1, roots.size()
        assertEquals url, roots[0].'@url'
        assertEquals relative, roots[0].'@relative'
    }

    void testWebRoot()
    {
        executeTarget 'web.root'
        assertRoot('file://$MODULE_DIR$/web2', '/')
    }
    
    void testComplexSetup()
    {
        executeTarget 'web.complex'
        //println getOutput()
        final def facet = facet('web')

        def deployments = facet.configuration.descriptors.deploymentDescriptor
        assertEquals 4, deployments.size()
        def expectedNames = ["web.xml", "sun-web.xml", null, null]
        assertEquals expectedNames, deployments.'@name'

        assertRoot('file://$MODULE_DIR$/web', '/')

        
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
