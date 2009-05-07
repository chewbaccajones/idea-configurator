package idea.conf.build

import org.apache.tools.ant.types.Path
import org.apache.tools.ant.Project
import idea.conf.Visitable

/**
 * The <build> element.
 *
 * @author tomichj
 */

public class BuildComponent implements Visitable
{
    static final String DEFAULT_RELATIVE_PATH = "/"

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
        // path-based class requires project in constructor, which breaks addConfiguredXXX
        ClasspathContainer lib =
            new ClasspathContainer(project, PackageMethod.COPY_OUTPUT, "/")
        kids << lib
        return lib.createPath()
    }

    void addConfiguredModule(ModuleContainer module)
    {
        defaults(module)
        kids << module
    }

    void addConfiguredModuleLibrary(ModuleLibraryContainer library)
    {
        defaults(library)
        kids << library
    }

    void addConfiguredProjectLibrary(ProjectLibraryContainer library)
    {
        defaults(library)
        kids << library
    }

    void addConfiguredGlobalLibrary(GlobalLibraryContainer library)
    {
        defaults(library)
        kids << library
    }
    
    def defaults(container)
    {
        if (!container.method) container.method = PackageMethod.COPY_OUTPUT
        if (!container.relativePath) container.relativePath = defaultPath(container)

    }
    
    def defaultPath(container)
    {
        if (container.method == PackageMethod.COPY_OUTPUT) return DEFAULT_RELATIVE_PATH
        return "/" + container.name + ".jar"
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