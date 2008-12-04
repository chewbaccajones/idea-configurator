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
    private static final String LIB_DIR = '$APPLICATION_HOME_DIR$/lib/'

    Project project
    String name // name of the jar in idea's lib dir
    boolean exported


    IdeaLibrary(Project project)
    {
        this.project = project
    }

    ModuleLibraryType asModuleLibrary()
    {
        def lib = new ModuleLibrary(project)
        lib.createClasses().setPath(path())
        return lib
    }

    def path()
    {
        return LIB_DIR + name
    }

    List<Visitable> getChildren()
    {
        return [asModuleLibrary()];
    }

    void validate()
    {
        if (!name) throw new BuildException("IdeaLibrary name must not be null!")
        // don't check to see if library actually exists
    }

    String toString()
    {
        "IdeaLibrary{name=${name}, exported=${exported}}"
    }
}


