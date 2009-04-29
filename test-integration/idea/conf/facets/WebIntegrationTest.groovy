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
    void testBlah()
    {
        executeTarget 'web.basic'
        println getOutput()
        //assertSetting 'web', 'EXPLODED_',

    }
}