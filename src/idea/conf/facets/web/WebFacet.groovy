package idea.conf.facets.web

import idea.conf.Visitable
import idea.conf.build.ModuleContainer
import idea.conf.build.GlobalLibraryContainer
import idea.conf.build.ProjectLibraryContainer
import idea.conf.build.ModuleLibraryContainer
import idea.conf.build.ClasspathContainer
import org.apache.tools.ant.Project

/**
 *
 * User: tomichj
 * Date: Apr 28, 2009
 * Time: 9:26:45 AM
 */

public class WebFacet implements Visitable
{
    def descriptors = []
    def packaging = []
    def webRoots = []

    File explodedDir
    boolean excludeExploded = true

    File war

    private Project project


    WebFacet(Project project)
    {
        this.project = project
    }

    void addWebXml(WebXml webXml)
    {
        descriptors << webXml
    }

    void addRoot(WebRoot root)
    {
        webRoots << root
    }

    void addModule(ModuleContainer module)
    {
        packaging << module
    }

    ClasspathContainer createClasspath()
    {
        ClasspathContainer classpath = new ClasspathContainer(project)
        packaging << classpath
        return classpath
    }

    void addModuleLibrary(ModuleLibraryContainer lib)
    {
        packaging << lib
    }

    void addGlobalLibrary(GlobalLibraryContainer lib)
    {
        packaging << lib
    }

    void addProjectLibrary(ProjectLibraryContainer lib)
    {
        packaging << lib
    }

    void addResource(AdditionalResource resource)
    {
        descriptors << resource
    }

    public List<Visitable> getChildren()
    {
        return descriptors + packaging + webRoots
    }

    public String toString()
    {
        return "WebFacet{" +
                "descriptors.size()=" + descriptors.size() +
                ", packaging.size()=" + packaging.size() +
                ", webRoots.size()=" + webRoots.size() +
                ", explodedDir=" + explodedDir +
                ", excludeExploded=" + excludeExploded +
                ", war=" + war +
                '}';
    }
}
