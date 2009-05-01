package idea.conf.facets.web

import idea.conf.Visitable
import idea.conf.build.ModuleContainer
import idea.conf.build.GlobalLibraryContainer
import idea.conf.build.ProjectLibraryContainer
import idea.conf.build.ModuleLibraryContainer
import idea.conf.build.ClasspathContainer
import org.apache.tools.ant.Project
import org.apache.tools.ant.BuildException
import idea.conf.Logger

/**
 *
 * User: tomichj
 * Date: Apr 28, 2009
 * Time: 9:26:45 AM
 */

public class WebFacet implements Visitable
{
    private Project project

    Descriptors descriptors = new Descriptors()
    def packaging = new Packaging()
    def webRoots = new WebRoots()

    ////////////////////////////////
    // attributes the user sets

    /** the name of the IDEA web facet, defaults to "web"  */
    String name = "web"

    File explodedDir
    boolean excludeExploded = true

    File war

    /**
     * specify webxml version here OR in WebXml child
     */
    String version



    WebFacet(Project project)
    {
        this.project = project
    }

    void setVersion(String v)
    {
        def webXml = new WebXml()
        webXml.version = v
        descriptors << webXml
    }

    void addWebXml(WebXml webXml)
    {
        if (descriptors.hasWebXml())
        {
            throw new BuildException("Specify web version *OR* a webxml entry.")
        }
        
        descriptors << webXml
    }

    void addGeronimo(Geronimo geronimo)
    {
        descriptors << geronimo
    }

    void addGlassfish(Glassfish glassfish)
    {
        descriptors << glassfish
    }

    void addJBoss(JBoss jboss)
    {
        descriptors << jboss
    }

    void addWeblogic(Weblogic weblogic)
    {
        descriptors << weblogic
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
        def result = [descriptors, packaging, webRoots]

        return result
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
