package idea.conf.facets.web

import idea.conf.Visitable

/**
 * 
 * User: tomichj
 * Date: Apr 28, 2009
 * Time: 10:55:58 AM
 */

public class WebRoot implements Visitable
{
    File url;
    String relative;
    

    public List<Visitable> getChildren()
    {
        return null;
    }
}
