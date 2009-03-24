package idea.conf.java.depend

import idea.conf.Visitable
import org.apache.tools.ant.types.Path
import static idea.conf.Validator.*


/**
 * Looks for jars matching a pattern... if found, removes the jar from dependencies and
 * replaces it with a module depencency instead.
 */
public class JarToModule implements Dependency, Exportable, Filter
{
    boolean exported
    String jarPattern
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

    public void validate()
    {
        notNull(jarPattern, "jarToModule jarPattern cannot be empty")
        notNull(moduleName, "jarToModule moduleName cannot be empty")
    }

    public Path filter(Path classes)
    {
        return filter.filter(classes)
    }


    public List<Visitable> getChildren()
    {
        if (filter.matches(deps.list)) return new Module(moduleName)
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