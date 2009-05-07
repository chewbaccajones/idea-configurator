package idea.conf.build

import idea.conf.Visitable

/**
 * 
 * User: tomichj
 * Date: Apr 29, 2009
 * Time: 5:31:47 PM
 */
public interface Package extends Visitable
{
    // not everything has a name
    PackageMethod method // change var name to packaging ??
    String relativePath
}