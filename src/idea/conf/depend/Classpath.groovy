package idea.conf

import org.apache.tools.ant.types.Path
import org.apache.tools.ant.Project
import org.apache.tools.ant.BuildException


/**
 *
 *
 * @author tomichj
 */
class Classpath extends Path implements Dependency
{
    Classpath(Project project)
    {
        super(project);
        if (project == null) throw new BuildException("null project!");
    }


    List<Visitable> getChildren()
    {
        return null;
    }


    String toString()
    {
        return "Classpath{}"
    }
}


