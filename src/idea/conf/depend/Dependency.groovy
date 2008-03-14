package idea.conf.depend

import idea.conf.Visitable

/**
 *
 *
 * @author tomichj
 */
interface Dependency extends Visitable
{
    // add a validate() method
    void validate()
}