package idea.conf.depend

import org.apache.tools.ant.types.Path
import org.apache.tools.ant.Project
import org.apache.tools.ant.BuildException
import idea.conf.Visitable


/**
 * Wrapper class that takes a classpath and will generate module library entries.
 *
 * @author tomichj
 */
class Classpath extends Path implements Dependency, Exportable
{
    boolean exported

    
    Classpath(Project project)
    {
        super(project);
        if (project == null) throw new BuildException("null project!");
    }


    List<Visitable> getChildren()
    {
        return null;
    }

    void validate() {
        // nothing to validate
    }


    String toString()
    {
        return "Classpath{}"
    }
}


