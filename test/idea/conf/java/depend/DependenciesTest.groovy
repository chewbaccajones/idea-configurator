package idea.conf.java.depend

/**
 *
 *
 * @author tomichj
 */
class DependenciesTest extends GroovyTestCase
{
    void testHasJdk()
    {
        def d = new Dependencies(null, null)
        assertFalse(d.hasJdk())

        d.createJdk()

        assertTrue(d.hasJdk())
    }

    void testPutBefore()
    {
        def d = new Dependencies(null, null)
        d.createModuleSource()
        d.createJdk()
        assertEquals(2, d.deps.size())

        Module module = new Module()
        d.putBefore(Jdk, module)
        assertEquals(1, d.deps.indexOf(module))
    }

    void testPutAfter()
    {
        def d = new Dependencies(null, null)
        d.createModuleSource()
        d.createJdk()
        assertEquals(2, d.deps.size())

        Module module = new Module()
        d.putAfter(Jdk, module)
        assertEquals(2, d.deps.indexOf(module))
    }
}

