package idea.conf.facets.web

import idea.conf.Visitable

/**
 *
 * User: tomichj
 * Date: Apr 28, 2009
 * Time: 2:06:46 PM
 */

public class AdditionalResource implements Descriptor
{
    private static final String OPTION_NAME = "DEFAULT_DIR"

    File file
    String deploymentPath


    public String getName()
    {
        return null;
    }

    public String getVersion()
    {
        return null;
    }

    public String getOptional()
    {
        return null;
    }

    public List<Visitable> getChildren()
    {
        return [new Option(OPTION_NAME, deploymentPath)]
    }

    public String toString()
    {
        return "AdditionalResource{" +
                "file=" + file +
                ", deploymentPath='" + deploymentPath + '\'' +
                '}';
    }
}
