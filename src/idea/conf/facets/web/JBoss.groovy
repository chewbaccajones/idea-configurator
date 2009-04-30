package idea.conf.facets.web


public class JBoss extends BaseDescriptor
{
    public static final def NAME = "jboss-web.xml"
    public static final def DISPLAY_NAME = "JBoss"
    public static final def DEFAULT_VERSION = "4.2"

    def JBoss()
    {
        super(NAME, DISPLAY_NAME, DEFAULT_VERSION)
    }

}
