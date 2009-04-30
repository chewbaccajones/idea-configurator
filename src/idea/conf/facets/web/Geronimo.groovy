package idea.conf.facets.web


public class Geronimo extends BaseDescriptor
{
    private static final def NAME = "geronimo-web.xml"
    private static final def DISPLAY_NAME = "Geronimo"
    private static final def DEFAULT_VERSION = "1.2"
    

    def Geronimo()
    {
        super(NAME, DISPLAY_NAME, DEFAULT_VERSION)
    }
}
