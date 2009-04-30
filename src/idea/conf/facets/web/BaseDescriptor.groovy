package idea.conf.facets.web

import idea.conf.Visitable
import org.apache.tools.ant.BuildException

/**
 * 
 */
public abstract class BaseDescriptor implements Descriptor
{
    private static final def OPTIONAL = "false"
    private static final def DEFAULT_URL = "web/WEB-INF/"

    private String name
    private String displayName
    private String defaultVersion


    String url
    String version


    def BaseDescriptor(name, displayName, defaultVersion)
    {
        this.name = name;
        this.displayName = displayName
        this.defaultVersion = defaultVersion;
    }
    
    public File getUrl()
    {
        if (url) return new File(url)
        return new File(DEFAULT_URL + name)
    }

    public String getVersion()
    {
        if (version) return version
        return defaultVersion
    }

    public String getName()
    {
        return name;
    }

    public String getOptional()
    {
        return OPTIONAL;
    }

    public List<Visitable> getChildren()
    {
        return null;
    }

    public String toString()
    {
        "${displayName}{" +
                "url=" + url +
                ", version='" + version + '\'' +
                '}';

    }
}