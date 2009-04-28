package idea.conf.facets.web

import idea.conf.Visitable
import idea.conf.build.ModuleContainer
import idea.conf.build.GlobalLibraryContainer
import idea.conf.build.ProjectLibraryContainer
import idea.conf.build.ModuleLibraryContainer
import idea.conf.build.ClasspathContainer

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

    void addClasspath(ClasspathContainer classpath)
    {
        packaging << classpath
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


    

    public List<Visitable> getChildren()
    {
        return descriptors + packaging + webRoots
    }
}
