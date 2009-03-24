package idea.conf.java.depend

import org.apache.tools.ant.types.Path

/**
 * 
 * User: tomichj
 * Date: Feb 3, 2009
 * Time: 1:51:34 PM
 */
public interface Filter
{
    Path filter(Path classes)
}

