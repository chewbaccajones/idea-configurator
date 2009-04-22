package idea.conf.facets

import org.apache.tools.ant.BuildFileTest

/**
 * 
 * User: tomichj
 * Date: Apr 21, 2009
 * Time: 5:48:27 PM
 */

public class GwtIntegrationTest extends BuildFileTest
{
    public void setUp()
    {
        configureProject("build-test.xml")
    }

    public void testGroovyNoSdk()
    {
        executeTarget "gwt"
        def module = new XmlParser().parseText(getOutput())
        println getOutput()
    }
}