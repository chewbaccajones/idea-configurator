package idea.conf.java.depend

import idea.conf.Visitable

/**
 * A dependency is anything showing up in idea's 'Dependencies' tab in the module config.
 * I.e. jars, other modules, global libraries, etc.
 *
 * @author tomichj
 */
interface Dependency extends Visitable
{
    void validate()
}
