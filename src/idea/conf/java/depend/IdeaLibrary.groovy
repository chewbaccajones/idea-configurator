package idea.conf.java.depend

import idea.conf.Visitable

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project


/**
 * Refers to a library housed inside IDEA's interior lib dir.
 *
 * @author tomichj
 */
class IdeaLibrary implements Dependency, Exportable
{
    Project project
    String name
    boolean exported


    IdeaLibrary(Project project)
    {
        this.project = project
    }

    ModuleLibrary asModuleLibrary()
    {
        String path = '$APPLICATION_HOME_DIR$/lib/' + name
        def lib = new ModuleLibrary(project)
        lib.createClasses().setPath(path)
        return lib
    }

    List<Visitable> getChildren()
    {
        return [asModuleLibrary()];
    }

    void validate()
    {
        if (!name) throw new BuildException("IdeaLibrary name must not be null!")
    }

    String toString()
    {
        "IdeaLibrary{name=${name}, exported=${exported}"
    }
}


