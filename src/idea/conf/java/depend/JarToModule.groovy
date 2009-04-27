package idea.conf.java.depend

import idea.conf.Visitable
import org.apache.tools.ant.types.Path
import static idea.conf.Validator.*
import org.apache.tools.ant.BuildException


/**
 * Looks for jars matching a pattern... if found, removes the jar from dependencies and
 * replaces it with a module depencency instead.
 */
public class JarToModule implements Dependency, Exportable, Filter
{
    boolean exported
    String jarPattern
    String jarName
    String moduleName
    PathFilter filter
    Dependencies deps

    
    def JarToModule(deps)
    {
        this.deps = deps;
    }

    public void setJarPattern(String pattern)
    {
        this.jarPattern = pattern
        filter = new PathFilter(jarPattern);
    }

    public void setJarName(String jarName)
    {
        this.jarName = jarName
        filter = new PathFilter(".*${jarName}")
    }

    public String getModuleName()
    {
        if (this.moduleName != null) return this.moduleName
        if (jarName != null) return jarName.replace(".jar", "")
        throw new BuildException("populate moduleName or jarName")
    }
    
    public void validate()
    {
        if (jarName && jarPattern) huck("<jarToModule> requires jarPatten OR jarName")
        if (!jarName && !jarPattern) huck("<jarToModule> requires jarPatten or jarName")
        if (jarPattern)
            notNull(moduleName, "<jarToModule> with jarPattern requires moduleName")
    }

    public Path filter(Path classes)
    {
        return filter.filter(classes)
    }

    public List<Visitable> getChildren()
    {
        if (filter.matches(deps.list()))
        {
            def module = new Module()
            module.name = getModuleName()
            module.exported = exported
            return [module]
        }
        return null
    }

    
    public String toString()
    {
        return "JarToModule{" +
                "exported=" + exported +
                ", jarPattern='" + jarPattern + '\'' +
                ", moduleName='" + moduleName + '\'' +
                '}';
    }

}