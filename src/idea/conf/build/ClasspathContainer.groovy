package idea.conf.build

import org.apache.tools.ant.types.Path
import idea.conf.Visitable

/**
 * 
 */

public class ClasspathContainer extends Path implements PackagingContainer 
{
    def ClasspathContainer(project)
    {
        super(project);
    }


    List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "Classpath{list=${list()}}"
    }
}