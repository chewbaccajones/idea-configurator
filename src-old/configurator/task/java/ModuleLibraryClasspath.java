/**
 IDEA Configurator
 Copyright (C) 2006 Justin Tomich<tomichj at gmail dot com>

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License as
 published by the Free Software Foundation; either version 2 of the
 License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package configurator.task.java;

import configurator.model.rootmgr.NewModuleRootManagerComponent;
import configurator.model.rootmgr.ordered.ModuleLibrary;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;

import java.util.*;


/**
 * Create ModuleLibrary instances for all jars in the (self-contained) Path. Can
 * potentially configure source, javadoc, and/or javadocUrl entries for each
 * ModuleLibrary it creates.
 * <p/>
 * ModuleLibraryClasspath extends Path. Add a classpath to it and then invoke
 * addTo(ModuleBuilder), and ModuleLibraryClasspath will construct ModuleLibrary
 * instances for each jar in the Path.
 * <p/>
 * Iterates through Classpath, taking each jar and scanning for ant properties
 * whose name is the same as the jar but starts with the sourceproperty,
 * javadocproperty, or javadocurl property.
 *
 * @author Justin Tomich
 */
public class ModuleLibraryClasspath extends Path implements Dependency
{
    private String sourceProperty;
    private String javadocProperty;
    private String javadocUrlProperty;


    /**
     * @param project used to scan for matching properties.
     * @param sourceProperty source properties begin with this
     * @param javadocProperty javadoc properties begin with this
     * @param javadocUrlProperty javadocurl properties begin with this
     */
    public ModuleLibraryClasspath(Project project,
                                  String sourceProperty,
                                  String javadocProperty,
                                  String javadocUrlProperty)
    {
        super(project);
        this.sourceProperty = endWithDot(sourceProperty);
        this.javadocProperty = endWithDot(javadocProperty);
        this.javadocUrlProperty = endWithDot(javadocUrlProperty);
        log("sourceProject=" + sourceProperty, Project.MSG_VERBOSE);
        log("javadocProject=" + javadocProperty, Project.MSG_VERBOSE);
        log("javadocUrlProperty=" + javadocUrlProperty, Project.MSG_VERBOSE);
        if (project == null) throw new BuildException("null project!");
    }


    private String endWithDot(String property)
    {
        if (property == null) return null;
        if (property.equals("")) return null;
        if (property.endsWith(".")) return property;
        return property + ".";
    }


    public void configure(NewModuleRootManagerComponent java)
    {
        String[] allClasses = list();
        for (String jar : allClasses)
        {
            String property = findPropertyNameForJar(jar);
            log("found a jar's property! property=" + property + " jar=" + jar,
                    Project.MSG_DEBUG);

            // todo builder should check to see if library already exists?
            // if it exists, add to existing lib, skip it, or..????

            java.add(new ModuleLibrary(jar, source(property), javadoc(property),
                    javadocUrls(property), true));
        }
    }


    /**
     * Find the corresponding property name for a jar.
     * <p/>
     * Unfortunately this is slow and stupid. We have to look through
     * [potentially] every property, turn it's value into a path, and then see
     * if it matches the given jar. If it does, it's our boy, and we return the
     * property name.
     *
     * @param jar fully qualified path to the jar
     * @return name of jar's defining property, or null if not found.
     */
    private String findPropertyNameForJar(String jar)
    {
        // could cache properties table...
        Project p = getProject();
        assert p != null;
        Hashtable properties = p.getProperties();
        Set<Map.Entry> entries = properties.entrySet();

        for (Map.Entry entry : entries)
        {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (value == null) continue;
            String[] paths = Path.translatePath(p, value);
            if (paths.length == 0) continue;
            if (paths.length > 1) continue;
            if (jar.equals(paths[0])) return key;
        }
        return null;
    }


    private String[] javadoc(String lib)
    {
        return lookup(javadocProperty, lib);
    }


    private String[] javadocUrls(String lib)
    {
        return lookup(javadocUrlProperty, lib);
    }


    private String[] source(String lib)
    {
        return lookup(sourceProperty, lib);
    }


    private String[] lookup(String propertyFrag, String lib)
    {
        log("looking up property for a lib... lib=" + lib +
            "  propertyFrag=" + propertyFrag, Project.MSG_DEBUG);
        if (propertyFrag == null) return null;
        String property = propertyFrag + lib;
        String value = getProject().getProperty(property);
        log("looked up property for lib... property=" + property +
            " value=" + value, Project.MSG_DEBUG);
        return new String[] {value};
    }


    public String toString()
    {
        return "ModuleLibraryClasspath{" +
               "sourceProperty='" + sourceProperty + '\'' +
               ", javadocProperty='" + javadocProperty + '\'' +
               ", javadocUrlProperty='" + javadocUrlProperty + '\'' +
               '}';
    }
}
