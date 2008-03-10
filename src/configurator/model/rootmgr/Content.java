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

import configurator.model.XmlElement;
import configurator.model.url.FileUrl;
import configurator.model.url.Url;
import configurator.model.url.UrlFactory;
import configurator.util.ElementCreator;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * Contains SourceFolder, TestFolder, and ExcludeFolder leaves.
 *
 * @author Justin Tomich
 */
public class Content implements XmlElement
{
    private final UrlFactory urlFactory;
    private final Url url;
    private List<SourceFolder> sources = new ArrayList<SourceFolder>();
    private List<TestFolder> tests = new ArrayList<TestFolder>();
    private List<ExcludeFolder> excludes = new ArrayList<ExcludeFolder>();


    Content(UrlFactory urlFactory)
    {
        this.urlFactory = urlFactory;
        this.url = new FileUrl("$MODULE_DIR$");
    }


    public void addSource(String... paths)
    {
        for (String path : paths)
        {
            sources.add(new SourceFolder(urlFactory.fileUrl(path)));
        }
    }


    public void addTest(String... paths)
    {
        for (String path : paths)
        {
            tests.add(new TestFolder(urlFactory.fileUrl(path)));
        }
    }


    public void addExclude(String... paths)
    {
        for (String path : paths)
        {
            excludes.add(new ExcludeFolder(urlFactory.fileUrl(path)));
        }
    }


    // the next three methods can probably get whacked... no need for sep meths
    void add(SourceFolder source)
    {
        sources.add(source);
    }


    void add(TestFolder test)
    {
        tests.add(test);
    }


    void add(ExcludeFolder exclude)
    {
        excludes.add(exclude);
    }


    Url getUrl()
    {
        return url;
    }


    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element content = dom.createElement("content");
//        parent.appendChild(content);

        Element content = new ElementCreator("content", parent).create();
        url.setAttribute(content);

        for (SourceFolder source : sources)
        {
            source.addToDom(content);
        }

        for (TestFolder test : tests)
        {
            test.addToDom(content);
        }

        for (ExcludeFolder exclude : excludes)
        {
            exclude.addToDom(content);
        }
    }


    public String toString()
    {
        return "Content{" +
               "url=" + url +
               ", sources=" + sources +
               ", tests=" + tests +
               ", excludes=" + excludes +
               "}";
    }
}
