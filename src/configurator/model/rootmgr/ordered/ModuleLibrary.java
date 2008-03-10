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

package configurator.model.rootmgr.ordered;

import configurator.model.url.*;
import configurator.util.ElementCreator;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * A module library: a jar of classes, and any associated javadoc and sources.
 *
 * @author Justin Tomich
 */
public class ModuleLibrary implements OrderEntry
{
    private boolean exported;
    private Root classes;
    private List<Root> javadoc = new ArrayList<Root>();
    private List<Root> sources = new ArrayList<Root>();


    public ModuleLibrary(String classes,
                         String[] sources,
                         String[] javadocs,
                         String[] javadocUrls,
                         boolean exported)
    {
        this(exported);
        setClasses(classes);
        addSources(sources);
        addJavadocs(javadocs);
        addJavadocUrls(javadocUrls);
    }


    public ModuleLibrary(boolean exported)
    {
        this.exported = exported;
    }


    /**
     * Remove any 'classes' that matches the given pattern. The pattern uses the
     * jdk 1.5 java.util.regex.Pattern syntax.
     *
     * @param pattern matching 'classes' Roots are removed.
     * @see java.util.regex.Pattern
     */
    public void removeMatchingClasses(String pattern)
    {
        if (classes.matches(pattern)) classes = null;
        // todo test this
    }


    /**
     * Does the library contain the supplied path in it's classes?
     *
     * @param path of contained elements
     * @return true if any of the classes roots contain the supplied path.
     */
    public boolean containsClasses(String path)
    {
        return classes.contains(path);
    }


    public void setClasses(String path)
    {
        if (path == null) return;
        Url url = url(path);
        classes = new Root(url);
    }


    public void addJavadocs(String[] paths)
    {
        if (paths == null) return;
        for (String path : paths)
        {
            addJavadoc(path);
        }
    }


    public void addJavadocUrls(String[] paths)
    {
        if (paths == null) return;
        for (String path : paths)
        {
            addJavadocUrl(path);
        }
    }


    /**
     * Add path to javadoc, must be a directory. IDEA doesn't like javadoc
     * jars.
     *
     * @param path javadoc directory
     */
    public void addJavadoc(String path)
    {
        if (path == null) return;
        addJavadoc(new FileUrl(path));
    }


    public void addJavadocUrl(String url)
    {
        if (url == null) return;
        addJavadoc(new HttpUrl(url));
    }


    private void addJavadoc(Url url)
    {
        javadoc.add(new Root(url));
    }


    public void addSources(String[] paths)
    {
        if (paths == null) return;
        for (String path : paths)
        {
            addSources(path);
        }
    }


    public void addSources(String path)
    {
        if (path == null) return;
//        if (!path.endsWith(".zip") && !path.endsWith(".jar"))
//        {
//            throw new BuildException("Source for lib must be a zip or jar! " +
//                                     "faulty source:" + path + "   " + this);
//        }
        addSources(url(path));
    }


    private void addSources(Url url)
    {
        sources.add(new Root(url));
    }


    public void addToDom(Element parent)
    {
        if (classes == null) return;

//        Document dom = parent.getOwnerDocument();
//        Element entry = dom.createElement("orderEntry");
//        parent.appendChild(entry);

        Element entry = new ElementCreator("orderEntry", parent).create();
        entry.setAttribute("type", "module-library");

//        Element library = dom.createElement("library");
//        entry.appendChild(library);

        Element library = new ElementCreator("library", entry).create();

//        Element classesElem = dom.createElement("CLASSES");
//        library.appendChild(classesElem);
        Element classesElem = new ElementCreator("CLASSES", library).create();
        classes.addToDom(classesElem);

//        Element javadocElem = dom.createElement("JAVADOC");
//        library.appendChild(javadocElem);
        Element javadocElem = new ElementCreator("JAVADOC", library).create();
        for (Root root : javadoc)
        {
            root.addToDom(javadocElem);
        }

//        Element sourcesElem = dom.createElement("SOURCES");
//        library.appendChild(sourcesElem);
        Element sourcesElem = new ElementCreator("SOURCES", library).create();
        for (Root root : sources)
        {
            root.addToDom(sourcesElem);
        }


    }


    private Url url(String path)
    {
        if (path.endsWith(".jar")) return new JarUrl(path);
        if (path.endsWith(".zip")) return new JarUrl(path);
        return new FileUrl(path);
    }


    public String toString()
    {
        return "ModuleLibrary{" +
               "exported=" + exported +
               ", classes=" + classes +
               ", javadoc=" + javadoc +
               ", sources=" + sources +
               "}";
    }
}
