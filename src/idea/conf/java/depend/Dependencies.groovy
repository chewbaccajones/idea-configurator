package idea.conf.java.depend

import idea.conf.Visitable
import org.apache.tools.ant.Project

/**
 * Containes all Dependency instances, squirts some logic into the processing of them.
 *
 * @author tomichj
 */
class Dependencies implements Visitable
{
    Project project;
    def deps = []
    def filters = []


    /**
     * Set string that source properties must begin with to be automagically
     * identified as source for a corresponging library. Other ant properties
     * beginning with this string will be treated as a source path for a library
     * jar of the name: sourceProperty + "." + path-to-jar.
     * <p/>
     * Example: sourceProperty is set to "source". A jar in the classpath has
     * the path "/lib/some-library-1.0.jar". If another property exists with the
     * name "source./lib/some-library-1.0.jar", the source property will be
     * treated as the source accompanying "some-library-1.0.jar".
     */
    ModuleLibGenerator moduleLibGenerator

    public Dependencies(Project project, ModuleLibGenerator moduleLibGenerator)
    {
        this.project = project
        this.moduleLibGenerator = moduleLibGenerator
    }


    int size()
    {
        return deps.size()
    }


    List<Visitable> getChildren()
    {
        completeJdkAndSourceOrderEntries()
        deps.each { it.validate() }
        return deps + filters
    }


    def moduleLibraries()
    {
        deps.findAll { it instanceof ModuleLibrary }
    }


    void completeJdkAndSourceOrderEntries()
    {
        if (hasJdk() && hasSource()) return;
        if (hasSource())
        {
            putAfter(ModuleSource, new Jdk())
        }
        else if (hasJdk())
        {
            putBefore(Jdk, new ModuleSource())
        }
        else
        {
            deps.add(0, new ModuleSource())
            deps.add(1, new Jdk())
        }
    }


    def hasJdk()
    {
        deps.any { it instanceof Jdk }
    }


    def hasSource()
    {
        deps.any { it instanceof ModuleSource }
    }


    void putAfter(Class clazz, Dependency insert)
    {
        def entry = deps.find { clazz.isInstance(it) }
        int index = deps.indexOf(entry)
        deps.add(index + 1, insert)
    }


    void putBefore(Class clazz, Dependency insert)
    {
        def entry = deps.find { clazz.isInstance(it) }
        int index = deps.indexOf(entry)
        deps.add(index, insert)
    }


    ClasspathFilter createFilter()
    {
        add new ClasspathFilter()
    }


    IdeaLibrary createIdeaLib()
    {
        add new IdeaLibrary(project)
    }

    void addConfiguredModuleLibrary(ModuleLibrary lib)
    {
        lib.addLibraryAssets(moduleLibGenerator)
        deps << lib
    }

    ProjectLibrary createProjectLibrary()
    {
        add new ProjectLibrary()
    }


    GlobalLibrary createGlobalLibrary()
    {
        add new GlobalLibrary()
    }


    Jdk createJdk()
    {
        add new Jdk()
    }


    ModuleSource createModuleSource()
    {
        add new ModuleSource()
    }


    Module createModule()
    {
        add new Module()
    }


    Classpath createClasspath()
    {
        add new Classpath(project, moduleLibGenerator)
    }

    def add(dependency)
    {
        deps << dependency
        return dependency
    }

    String toString()
    {
        return "Dependencies{" +
                "deps.size()=" + deps.size() +
                ", filters.size()=" + filters.size() +
                "}"
    }


}