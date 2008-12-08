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
    String name // name of the jar in idea's lib dir
    boolean exported
    ModuleLibGenerator libGenerator


    IdeaLibrary(Project project)
    {
        this.project = project
    }

    ModuleLibraryType asModuleLibrary()
    {
        def lib = new ModuleLibrary(project)
        lib.ideaLibs.add(name)
        lib.addLibraryAssets(libGenerator)
        return lib
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
        return "IdeaLibrary{name=${name}, exported=${exported}}"
    }
}


