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
//        ClasspathFilter filter = new ClasspathFilter()
//        filters << filter
//        return filter
    }


    IdeaLibrary createIdeaLib()
    {
        add new IdeaLibrary(project)
//        IdeaLibrary lib = new IdeaLibrary(project)
//        deps << lib
//        return lib
    }

    void addConfiguredModuleLibrary(ModuleLibrary lib)
    {
        lib.addLibraryAssets(moduleLibGenerator)
        deps << lib
    }

    ProjectLibrary createProjectLibrary()
    {
        add new ProjectLibrary()
//        ProjectLibrary lib = new ProjectLibrary()
//        deps << lib
//        return lib
    }


    GlobalLibrary createGlobalLibrary()
    {
        add new GlobalLibrary()
//        GlobalLibrary lib = new GlobalLibrary()
//        deps << lib
//        return lib;
    }


    Jdk createJdk()
    {
        add new Jdk()
//        Jdk jdk = new Jdk();
//        deps << jdk
//        return jdk;
    }


    ModuleSource createModuleSource()
    {
        add new ModuleSource()
//        ModuleSource source = new ModuleSource()
//        deps << source
//        return source
    }


    Module createModule()
    {
        add new Module()
//        Module module = new Module();
//        deps << module
//        return module
    }


    Classpath createClasspath()
    {
        add new Classpath(project, moduleLibGenerator)
//        Classpath cp = new Classpath(project, moduleLibGenerator)
//        deps << cp
//        return cp
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