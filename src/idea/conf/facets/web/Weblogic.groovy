package idea.conf.facets.web


public class Weblogic extends BaseDescriptor
{
    public static final def NAME = "weblogic.xml"
    public static final def DISPLAY_NAME = "Weblogic"
    public static final def DEFAULT_VERSION = "9.x"

    def Weblogic()
    {
        super(NAME, DISPLAY_NAME, DEFAULT_VERSION)
    }
}
