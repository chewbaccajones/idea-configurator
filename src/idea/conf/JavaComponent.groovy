package idea.conf

import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;



/**
 *
 *
 * @author tomichj
 */
class JavaComponent implements Visitable
{
    boolean excludeOutputPaths;
    boolean excludeExploded;
    Path sources;
    Path tests;
    Path excludes;
    File outputDir;
    File testsOutputDir;
    Path classpath;
    Dependencies dependencies


    public JavaComponent(Project project)
    {
        sources = new Path(project);
        tests = new Path(project);
        excludes = new Path(project);
        classpath = new Path(project);
        dependencies = new Dependencies(project);
    }

    public List<Visitable> getChildren()
    {
        return [dependencies];
    }
    

    public String toString()
    {
        return "JavaBuild{" +
               "excludeOutputPaths=" + excludeOutputPaths +
               ", excludeExploded=" + excludeExploded +
               ", sources=" + sources +
               ", tests=" + tests +
               ", excludes=" + excludes +
               ", outputDir=" + outputDir +
               ", testsOutputDir=" + testsOutputDir +
               ", classpath=" + classpath +
//               ", filters=" + filters +
//               ", inheritCompilerOutput=" + inheritCompilerOutput +
               ", dependencies.size()=" + dependencies.size() +
               '}';
    }

}