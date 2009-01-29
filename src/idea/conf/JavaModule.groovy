package idea.conf

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference

import idea.conf.render.DebugVisitor
import idea.conf.render.ImlVisitor
import idea.conf.java.depend.Dependencies
import idea.conf.url.UrlFactory
import idea.conf.url.UrlFactoryImpl
import idea.conf.java.JavaComponent
import idea.conf.facets.FacetManager
import idea.conf.build.BuildComponent

/**
 * The java module type. This is the top level ant task for Java Modules.
 *
 * Most of the methods are proxying to JavaComponent.  
 *
 * @author tomichj
 */
class JavaModule extends Task implements Visitable
{
    File rootDir
    private File moduleFile
    boolean relativePaths
    JavaComponent java
    FacetManager facets
    BuildComponent build
    boolean debug


    void execute()
    {
        super.execute();

        // check for debug flag
        if (!debug && project.getProperty("debug")) {
            debug = Boolean.parseBoolean(project.getProperty("debug"))
        }

        if (debug)
        {
            def debuggery = new DebugVisitor()
            debuggery.visit(this)
            println debuggery
            println ""
        }

        UrlFactory urlFactory = new UrlFactoryImpl(getRootDir(), relativePaths)
        def xml = new ImlVisitor(urlFactory);
        xml.visit(this)

        if (debug)
        {
            println xml
            println "\n"
        }
        else
        {
            getModuleFile().write(xml.toString())
        }
    }

    /**
     * <facets>
     *   <groovy/>
     *   <spring/>
     * </facets>
     */
    void setProject(Project project)
    {
        super.setProject(project)
        java = new JavaComponent(project)
    }


    void setModuleFile(File moduleFile)
    {
        if (moduleFile == null) throw new BuildException("Null moduleFile!")
        if (!moduleFile.getAbsolutePath().endsWith(".iml"))
        {
            String p = moduleFile.getAbsolutePath() + ".iml";
            moduleFile = new File(p);
        }

        this.moduleFile = moduleFile;
    }


    File getRootDir()
    {
        if (rootDir != null) return rootDir;
        return project.getBaseDir();
    }


    File getModuleFile()
    {
        if (moduleFile != null) return moduleFile

        // default is ant project name, .iws, in the base dir.
        return new File(project.getBaseDir(), project.getName() + ".iml")
    }


    def createFacets()
    {
        final Dependencies dependencies = java.dependencies
        facets = new FacetManager(dependencies);
        return facets;
    }

    
    BuildComponent createBuild()
    {
        build = new BuildComponent(project)
        return build
    }
    

    /////////////////////////////////////////////////////////
    // Java stuff here
    // we could do some crazy property delegation stuff, but this might be simpler


    void setSourceProperty(String sourceProperty)
    {
        java.setSourceProperty(sourceProperty);
    }


    void setJavadocProperty(String javadocProperty)
    {
        java.setJavadocProperty(javadocProperty);
    }


    void setJavadocUrlProperty(String javadocUrlProperty)
    {
        java.setJavadocUrlProperty(javadocUrlProperty);
    }


    void setSrcDir(File sourceDir)
    {
        java.setSrcDir(sourceDir);
    }


    void setSrcPathRef(Reference srcPathRef)
    {
        java.setSrcPathRef(srcPathRef);
    }


    Path createSrc()
    {
        return java.createSrc();
    }


    void setTestsDir(File testDir)
    {
        java.setTestsDir(testDir);
    }


    void setTestsPathRef(Reference testsPathRef)
    {
        java.setTestsPathRef(testsPathRef);
    }


    Path createTests()
    {
        return java.createTests();
    }


    void setInheritCompilerOutput(boolean inheritCompilerOutput) throws BuildException
    {
        java.setInheritCompilerOutput(inheritCompilerOutput);
    }


    void setExcludeOutputPaths(boolean excludeOutputPaths)
    {
        java.setExcludeOutputPaths(excludeOutputPaths);
    }


    void setOutputDir(File outputDir)
    {
        java.setOutputDir(outputDir);
    }


    void setTestsOutputDir(File testsOutputDir)
    {
        java.setTestsOutputDir(testsOutputDir);
    }


    void setExcludes(Path excludes)
    {
        java.setExcludes(excludes);
    }


    void setExcludesPathRef(Reference excludesPathRef)
    {
        java.setExcludesPathRef(excludesPathRef);
    }


    Path createExcludes()
    {
        return java.createExcludes();
    }


    void setClasspath(Path classpath)
    {
        java.setClasspath(classpath);
    }

    void setClasspathRef(Reference ref)
    {
        java.setClasspathRef(ref);
    }

    Dependencies createDependencies()
    {
        return java.createDependencies();
    }

    
    List<Visitable> getChildren()
    {
        def kids = []
        kids << java
        if (facets) kids << facets
        if (build) kids << build
        return kids as List<Visitable>;
    }


    String toString()
    {
        "JavaModule{rootDir=${rootDir},moduleFile=moduleFile," +
                "relativePaths=${relativePaths}}"
    }
    
}
