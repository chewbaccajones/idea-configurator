package idea.conf.java.depend

import idea.conf.Visitable

/**
 * Every dependency must implement Dependency.  A dependency is aanalogous to anything
 * showing up in idea's 'Dependencies' tab in the module config. I.e. jars, other modules,
 * global libraries, etc.
 *
 * @author tomichj
 */
interface Dependency extends Visitable
{
    // add a validate() method
    void validate()
}
