package idea.conf.build

import idea.conf.Visitable

/**
 * Created by IntelliJ IDEA.
 * User: tomichj
 * Date: Jan 6, 2009
 * Time: 2:26:41 PM
 * To change this template use File | Settings | File Templates.
 */

public class ProjectLibraryContainer implements PackagingContainer
{
    String name

    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "ProjectLibrary{name=${name}}"
    }
}