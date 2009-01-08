package idea.conf.build

import org.apache.tools.ant.types.Path
import org.apache.tools.ant.Project
import idea.conf.Visitable

/**
 *
 *
 * @author tomichj
 */

public class BuildComponent implements Visitable
{
    Project project

    // default jar to moduleName.jar in module root dir 
    File jar

    String mainClass

    def kids = []

    
    BuildComponent(Project project)
    {
        this.project = project
    }

    Path createClasspath()
    {
        ClasspathContainer lib = new ClasspathContainer(project)
        kids << lib
        return lib.createPath()
    }

    ModuleContainer createModule()
    {
        addKid(new ModuleContainer())
    }

    ModuleLibraryContainer createModuleLibrary()
    {
        addKid(new ModuleLibraryContainer())
    }

    ProjectLibraryContainer createProjectLibrary()
    {
        addKid(new ProjectLibraryContainer())
    }

    GlobalLibraryContainer createGlobalLibrary()
    {
        addKid(new GlobalLibraryContainer())
    }


    def addKid(kid)
    {
        kids << kid
        return kid
    }

    // add an "all dependencies" flag?
    // add a filter to all dependencies?
    
    List<Visitable> getChildren()
    {
        return kids
    }

    String toString()
    {
        "BuildComponent{jar=${jar},mainClass=${mainClass}}"
    }
}