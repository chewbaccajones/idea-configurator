package idea.conf.java.depend

import idea.conf.Visitable
import org.apache.tools.ant.BuildException
import org.apache.tools.ant.Project
import org.apache.tools.ant.types.Path
import static idea.conf.Validator.*

/**
 * An ant path that will generate module library entries for each entry in the path.
 *
 * @author tomichj
 */
class Classpath extends Path implements Dependency, Exportable, Classpathed
{
    boolean exported
    private ModuleLibGenerator modLibGenerator

    Classpath(Project project, ModuleLibGenerator modLibGenerator)
    {
        super(project)
        this.modLibGenerator = modLibGenerator
        notNull(project, "null project!")
        //if (project == null) throw new BuildException("null project!")
    }

    List<Visitable> getChildren()
    {
        return modLibGenerator.moduleLibsForPath(this, exported)
    }

    void validate()
    {
        // nothing to validate
    }

    String toString()
    {
        "Classpath{exported=${exported}, list=" + list() + "}"
    }


    public Path getClasspath()
    {
        return this
    }
}


