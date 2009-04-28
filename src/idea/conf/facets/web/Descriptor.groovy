package idea.conf.facets.web

import idea.conf.Visitable

/**
 * 
 */
public interface Descriptor extends Visitable
{
    String getName()
    File getUrl()
    String getVersion()
    String getOptional()
}
