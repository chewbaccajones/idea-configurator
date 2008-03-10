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

import configurator.model.rootmgr.NewModuleRootManagerComponent;
import configurator.model.ModuleRootManagerComponent;
import configurator.model.AppServerSpecificValidatorsManagerComponent;
import configurator.model.deployment.WebPropertiesComponent;
import configurator.model.build.WebBuildComponent;
import configurator.model.url.UrlFactory;
import configurator.model.url.UrlFactoryImpl;

import org.w3c.dom.Document;

import java.io.File;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Model and configure an IDEA Web module.
 *
 * @author Justin Tomich
 */
public class WebModule
{
    private final static int VERSION = 4;
    private final static String TYPE = "J2EE_WEB_MODULE";

    private final Module innards;
    private final NewModuleRootManagerComponent rootManager;
    private final WebBuildComponent webBuild;
    private final WebPropertiesComponent webProperties;


    public WebModule(String name,
                     File rootDirectory,
                     boolean relativePathsOutsideModule)
    {
        UrlFactory urlFactory = new UrlFactoryImpl(rootDirectory,
                relativePathsOutsideModule);
        rootManager = new NewModuleRootManagerComponent(urlFactory);
        webBuild = new WebBuildComponent(urlFactory);
        webProperties = new WebPropertiesComponent(urlFactory);

        // innards holds all the refs to the components, cranks the xml, etc
        innards = new Module(name, TYPE, VERSION, relativePathsOutsideModule,
                new AppServerSpecificValidatorsManagerComponent(), // empty
                new ModuleRootManagerComponent(), // empty
                rootManager, webBuild, webProperties);
    }


    public NewModuleRootManagerComponent javaComponent()
    {
        return rootManager;
    }


    public WebBuildComponent webBuild()
    {
        return webBuild;
    }


    public WebPropertiesComponent webProperties()
    {
        return webProperties;
    }


    public Document toDom() throws ParserConfigurationException
    {
        return innards.toDom();
    }


    public String toString()
    {
        return "WebModule{" +
               "innards=" + innards +
               ", rootManager=" + rootManager +
               ", webBuild=" + webBuild +
               ", webProperties=" + webProperties +
               '}';
    }
}
