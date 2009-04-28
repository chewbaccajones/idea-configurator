package idea.conf.facets.web

import idea.conf.Visitable

/**
 * 
 * User: tomichj
 * Date: Apr 28, 2009
 * Time: 9:55:37 AM
 */

public class WebXml implements Descriptor, Visitable
{
    public static final def DEFAULT_VERSION = "2.5"

    File url
    String version


    public File getUrl()
    {
        if (url == null) return new File("web/META-INF/web.xml")
        return url
    }
    
    public String getVersion()
    {
        if (version == null) return DEFAULT_VERSION
        return version
    }

    public String getName()
    {
        return "web.xml";
    }

    public String getOptional()
    {
        return "false";
    }

    public List<Visitable> getChildren()
    {
        return null;
    }
}
