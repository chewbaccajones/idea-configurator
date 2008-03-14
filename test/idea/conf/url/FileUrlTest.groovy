package idea.conf.url


/**
 * @author tomichj
 */
class FileUrlTest extends GroovyTestCase
{
    public void testAddToDom()
    {
        String path = "foo"
        FileUrl fu = new FileUrl(path);
        assertEquals("file://foo", fu.url())
    }

}