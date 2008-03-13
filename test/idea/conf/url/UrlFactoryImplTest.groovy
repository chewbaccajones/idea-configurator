package idea.conf.url

/**
 *
 *
 * @author tomichj
 */
class UrlFactoryImplTest extends GroovyTestCase
{
    void testRelativePath()
    {
        File home = new File("/vobs/ssp/cci");
        File file1 = new File("/vobs/ssp/cci/main/src");
        File file2 = new File("/vobs/ssp/cci/main/src/core/web/stuff");
        File file3 = new File("/vobs/rws/adobefacade/web");

        UrlFactoryImpl f = new UrlFactoryImpl(home, true);

        String relativePath = f.relativePath(home, file1)
        assertEquals("main/src", relativePath);
        assertEquals("main/src/core/web/stuff", f.relativePath(home, file2));
        assertEquals("../../rws/adobefacade/web", f.relativePath(home, file3));
    }


    void testRelativeOutsideRoot()
    {
        File home = new File("/vobs/ssp/cci");
        String file = "/vobs/rws/adobefacade/web";

        UrlFactoryImpl f = new UrlFactoryImpl(home, false);

        // should be relative... (we're using protected api, always relative)
        String path = f.relativePath(file);
        assertTrue(path.contains(".."));

        // should NOT be relative, constructor specifies no relative paths
        // outside of root directory.
        String computed = f.computePath(file);
        assertFalse(computed.contains(".."));
    }

}