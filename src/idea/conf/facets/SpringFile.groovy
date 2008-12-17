package idea.conf.facets

import idea.conf.Visitable

/**
 *
 *
 * @author tomichj
 */

public class SpringFile implements Visitable
{
    File location

    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "SpringFile{location=${location}}"
    }
}