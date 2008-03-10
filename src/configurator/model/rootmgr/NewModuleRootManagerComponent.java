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

package configurator.model.rootmgr;

import configurator.model.Component;
import configurator.model.rootmgr.ordered.*;
import configurator.model.url.FileUrl;
import configurator.model.url.UrlFactory;
import configurator.util.ElementCreator;

import org.apache.tools.ant.BuildException;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;


/**
 * Container of most java-module stuff.
 * <p/>
 * <component name="NewModuleRootManager">...</component>
 *
 * @author Justin Tomich
 */
public class NewModuleRootManagerComponent implements Component
{
    private static final String COMPONENT_NAME = "NewModuleRootManager";

    private final UrlFactory urlFactory;
    private final Content content;
    private boolean excludeOutputPaths = false;
    private boolean inheritCompilerOutput;
    private Output output;
    private OutputTest outputTest;
    private List<OrderEntry> orderEntries = new ArrayList<OrderEntry>();
    private ClasspathFilter cpFilter = new ClasspathFilter();
    private boolean excludeExploded = false;
    private FileUrl explodedDir;


    public NewModuleRootManagerComponent(UrlFactory urlFactory)
    {
        this.urlFactory = urlFactory;
        this.content = new Content(urlFactory);
    }


    /**
     * If selected, output folders will be marked as excluded, and IDEA won't
     * pay any attention to them. This is equivalent to manually calling
     * <code>module.root().content().addExclude(path)</code> for each output
     * folder.
     */
    public void setExcludeOutputPaths(boolean excludeOutputPaths)
    {
        this.excludeOutputPaths = excludeOutputPaths;
    }


    public void setExcludeExploded(boolean excludeExploded)
    {
        this.excludeExploded = excludeExploded;
    }


    public void setExplodedDirectory(String path)
    {
        explodedDir = urlFactory.fileUrl(path);
    }


    public void inheritCompilerOutput(boolean inheritCompilerOutput)
    {
        this.inheritCompilerOutput = inheritCompilerOutput;
        inheritOrSetOutput();
    }


    public void setOutput(String path)
    {
        FileUrl url = urlFactory.fileUrl(path);
        this.output = new Output(url);
        inheritOrSetOutput();
    }


    public void setOutputTest(String path)
    {
        FileUrl url = urlFactory.fileUrl(path);
        this.outputTest = new OutputTest(url);
        inheritOrSetOutput();
    }


    private void inheritOrSetOutput()
    {
        if (inheritCompilerOutput && (output != null || outputTest != null))
        {
            throw new BuildException("Set either inheritCompilerOutput or " +
                                     "output/outputTest, not both.");
        }
    }


    public Content content()
    {
        return content;
    }


    public void add(OrderEntry orderEntry)
    {
        if ((orderEntry instanceof Jdk) && hasJdk())
        {
            throw new RuntimeException("Cannot add a second Jdk entry");
        }
        // check for sourcefolder too?
        orderEntries.add(orderEntry);
    }


    public void addClasspathFilter(String pattern)
    {
        cpFilter.addPattern(pattern);
    }


    /**
     * If the user doesn't specify a jdk and source order entry, we plunk them
     * in automagically.
     */
    private void completeJdkAndSourceOrderEntries()
    {
        if (hasJdk() && hasSource()) return;
        if (hasSource())
        {
            putAfter(SourceFolderEntry.class, new InheritedJdk());
        }
        else if (hasJdk())
        {
            putBefore(Jdk.class, new SourceFolderEntry());
        }
        else
        {
            // neither source nor jdk in dependencies, so put them at beginning
            orderEntries.add(0, new SourceFolderEntry());
            orderEntries.add(1, new InheritedJdk());
        }
    }


    private boolean hasJdk()
    {
        return containsEntry(Jdk.class);
    }


    private boolean hasSource()
    {
        return containsEntry(SourceFolderEntry.class);
    }


    private boolean containsEntry(Class clazz)
    {
        try
        {
            return findEntry(clazz) != null;
        }
        catch (RuntimeException e)
        {
            return false;
        }
    }


    private OrderEntry findEntry(Class entryClass)
    {
        for (OrderEntry entry : orderEntries)
        {
            if (entryClass.isInstance(entry)) return entry;
        }
        throw new RuntimeException("No order entry of class: " + entryClass);
    }


    // todo needs test
    private void putBefore(Class entryClass, OrderEntry newEntry)
    {
        OrderEntry entry = findEntry(entryClass);
        int i = orderEntries.indexOf(entry);
        orderEntries.add(i, newEntry);
    }


    private void putAfter(Class entryClass, OrderEntry newEntry)
    {
        OrderEntry entry = findEntry(entryClass);
        int i = orderEntries.indexOf(entry);
        orderEntries.add(i, newEntry);
    }


    /**
     * Retrieve a ModuleLibrary by searching for the given jar's path.
     *
     * @param pathToJar
     * @return ModuleLibrary
     */
    public ModuleLibrary get(String pathToJar)
    {
        for (OrderEntry entry : orderEntries)
        {
            if (!(entry instanceof ModuleLibrary)) continue;
            ModuleLibrary lib = (ModuleLibrary) entry;
            if (lib.containsClasses(pathToJar)) return lib;
        }
        return null;
    }


    public void addToDom(Element parent)
    {
        // finish up missing but implied order entries
        completeJdkAndSourceOrderEntries();

//        Document dom = parent.getOwnerDocument();
//        Element component = dom.createElement("component");
//        parent.appendChild(component);

        Element component = new ElementCreator("component", parent).create();

        component.setAttribute("name", COMPONENT_NAME);
        component.setAttribute("inherit-compiler-output",
                "" + inheritCompilerOutput);

        if (output != null)
        {
            output.addToDom(component);
        }

        if (excludeOutputPaths)
        {
            new ElementCreator("exclude-output", component).create();
//            Element exclude = dom.createElement("exclude-output");
//            component.appendChild(exclude);
        }

        if (explodedDir != null)
        {
            Element exploded =
                    new ElementCreator("exploded", component).create();
//            component.appendChild(exploded);

            explodedDir.setAttribute(exploded);
            if (excludeExploded)
            {
                new ElementCreator("exclude-exploded", component);
//                component.appendChild(dom.createElement("exclude-exploded"));
            }
        }

        if (outputTest != null)
        {
            outputTest.addToDom(component);
        }

        if (content != null)
        {
            content.addToDom(component);
        }

        for (OrderEntry entry : orderEntries)
        {
            cpFilter.filter(entry);
            entry.addToDom(component);
        }

        new ElementCreator("orderEntryProperties", component).create();
//        Element properties = dom.createElement("orderEntryProperties");
//        component.appendChild(properties);
    }


    public String toString()
    {
        return "NewModuleRootManager{" +
               "excludeOutputPaths=" + excludeOutputPaths +
               ", output=" + output +
               ", outputTest=" + outputTest +
               ", content=" + content +
               ", orderEntries=" + orderEntries +
               "}";
    }
}
