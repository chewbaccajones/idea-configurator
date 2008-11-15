package idea.conf.java.depend

import idea.conf.Visitable
import org.apache.tools.ant.BuildException
import org.apache.tools.ant.Project
import org.apache.tools.ant.types.Path

/**
 * An ant path that will generate module library entries for each entry in the path.
 *
 * @author tomichj
 */
class Classpath extends Path implements Dependency, Exportable
{
    boolean exported
    private ModuleLibGenerator inspector

    Classpath(Project project, ModuleLibGenerator inspector)
    {
        super(project)
        this.inspector = inspector
        if (project == null) throw new BuildException("null project!")
    }

    List<Visitable> getChildren()
    {
        return inspector.moduleLibsForPath(this, exported)
    }

    void validate()
    {
        // nothing to validate
    }

    String toString()
    {
        "Classpath{exported=${exported}, list=" + list() + "}"
    }
}


