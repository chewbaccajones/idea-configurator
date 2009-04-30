package idea.conf.facets.web

import idea.conf.Visitable

/**
 * 
 */
public interface Descriptor extends Visitable
{
    String getName()
    File getFile()
    String getVersion()
    String getOptional()
}
