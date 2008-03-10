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

package configurator.model.module;

import configurator.model.rootmgr.ordered.*;
import configurator.model.rootmgr.NewModuleRootManagerComponent;
import configurator.model.rootmgr.Content;
import configurator.model.DomSerializer;

import junit.framework.TestCase;
import org.w3c.dom.Document;

import java.io.File;


/**
 * @author Justin Tomich
 */
public class JavaModuleTest extends TestCase
{
    public void testToDom() throws Exception
    {
        String dir = "/vobs/foo";
        boolean relative = false;

        // setup module
        JavaModule m = new JavaModule("testModule", new File(dir), relative);
        NewModuleRootManagerComponent root = m.javaComponent();
        Content content = root.content();
        content.addSource("/vobs/foo/src");
        content.addSource("/vobs/outsideModule/src2");
        content.addTest("/vobs/foo/test");
        content.addExclude("/vobs/foo/exclude");

        root.setOutput("/vobs/foo/build/classes");
        root.setOutputTest("/vobs/foo/build/test-classes");

        root.add(new InheritedJdk());
        root.add(new SourceFolderEntry());
        root.add(new GlobalLibrary("some-dumb-library"));

        ModuleLibrary lib = new ModuleLibrary(false);
        lib.setClasses("/lib/jmock/jmock.jar");
        lib.addJavadoc("/lib/jmock/docs");
        lib.addJavadocUrl("http://foo.com/docs");
        lib.addSources("/lib/jmock/src.zip");
        root.add(lib);

        // get document
        Document d = m.toDom();

//        System.out.println(DomSerializer.toString(d));
    }
}
