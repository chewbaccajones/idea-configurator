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

import configurator.model.ModuleRootManagerComponent;
import configurator.model.rootmgr.NewModuleRootManagerComponent;
import configurator.model.url.UrlFactory;
import configurator.model.url.UrlFactoryImpl;

import org.w3c.dom.Document;

import java.io.File;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Model and configure an IDEA Java module.
 *
 * @author Justin Tomich
 */
public class JavaModule
{
    private final static int VERSION = 4;
    private final static String TYPE = "JAVA_MODULE";

    private final Module innards;
    private final NewModuleRootManagerComponent rootManager;


    public JavaModule(String name,
                      File rootDirectory,
                      boolean relativePathsOutsideModule)
    {
        UrlFactory urlFactory = new UrlFactoryImpl(rootDirectory,
                relativePathsOutsideModule);
        this.rootManager = new NewModuleRootManagerComponent(urlFactory);

        innards = new Module(name, TYPE, VERSION, relativePathsOutsideModule,
                new ModuleRootManagerComponent(), rootManager);
    }


    public NewModuleRootManagerComponent javaComponent()
    {
        return rootManager;
    }


    public Document toDom() throws ParserConfigurationException
    {
        return innards.toDom();
    }


    public String toString()
    {
        return "JavaModule{" +
               "version=" + VERSION +
               ", type='" + TYPE + '\'' +
               ", innards=" + innards +
               '}';
    }
}


