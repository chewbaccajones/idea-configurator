package idea.conf.facets.web


public class WebXml extends BaseDescriptor
{
    public static final def NAME = "web.xml"
    public static final def DISPLAY_NAME = "WebXml"
    public static final def DEFAULT_VERSION = "2.5"

    def WebXml()
    {
        super(NAME, DISPLAY_NAME, DEFAULT_VERSION)
    }
}
