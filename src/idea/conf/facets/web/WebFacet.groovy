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


    // set if dir is specified:
    // <setting name="EXPLODED_URL" value="file://$MODULE_DIR$/../out/exploded/web-module3Web" />
    // <setting name="EXPLODED_ENABLED" value="false" />
    // <setting name="EXCLUDE_EXPLODED_DIRECTORY" value="true" />
    File explodedDir
    boolean excludeExploded = true 

    // <setting name="JAR_URL" value="file://$MODULE_DIR$/../out/Web.war" />
    // <setting name="JAR_ENABLED" value="true" />
    File war


    Project project


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
}
