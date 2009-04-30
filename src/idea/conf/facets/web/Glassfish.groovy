package idea.conf.facets.web


public class Glassfish extends BaseDescriptor
{
    public static final def NAME = "sun-web.xml"
    public static final def DISPLAY_NAME = "Glassfish"
    public static final def DEFAULT_VERSION = "2.5.0"

    def Glassfish()
    {
        super(NAME, DISPLAY_NAME, DEFAULT_VERSION)
    }

}