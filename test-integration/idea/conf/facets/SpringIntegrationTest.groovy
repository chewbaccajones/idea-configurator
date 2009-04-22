package idea.conf.facets

import org.apache.tools.ant.BuildFileTest

/**
 * 
 * User: tomichj
 * Date: Apr 21, 2009
 * Time: 5:32:58 PM
 */

public class SpringIntegrationTest extends BuildFileTest
{
    public void setUp()
    {
        configureProject("build-test.xml")
    }

    public void testGroovyNoSdk()
    {
        executeTarget "spring.complex"
        def module = new XmlParser().parseText(getOutput())
        println getOutput()
        def spring = module.component.find { it.'@name' == 'FacetManager' }.
                facet.find { it.'@name' == 'Spring' }
        def filesets = spring.configuration.fileset
        def filesetIds = filesets.'@id'
        assertEquals(['fileset1', 'fileset2', 'fileset3'], filesetIds)

        // just dig into a single fileset... can't stand to do them all
        def fileset1 = filesets[0]
        assertEquals 'Production Fileset', fileset1.'@name'
        assertEquals 2, fileset1.file.size()
        assertEquals 'file://$MODULE_DIR$/src/spring-conf.xml', fileset1.file[0].text()
        assertEquals 'file://$MODULE_DIR$/src/spring-conf2.xml', fileset1.file[1].text()
    }
}