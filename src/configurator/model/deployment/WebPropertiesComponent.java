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

package configurator.model.deployment;

import configurator.model.Component;
import configurator.model.url.FileUrl;
import configurator.model.url.JarUrl;
import configurator.model.url.UrlFactory;

import org.w3c.dom.Element;

import java.io.File;


/**
 * Deployment properties for the web module.
 *
 * @author Justin Tomich
 */
public class WebPropertiesComponent implements Component, DeploymentComponent
{
    private static final String NAME = "WebModuleProperties";

    private final ModuleProperties moduleProperties;
    private final UrlFactory urlFactory;

    private int unknowns = 0;


    public WebPropertiesComponent(UrlFactory urlFactory)
    {
        this.urlFactory = urlFactory;
        this.moduleProperties = new ModuleProperties(NAME);
    }


    /**
     * Add a deployment descriptor.
     * <p/>
     * The ant task offers multiple descriptor types, but all boil down to three
     * attributes... the three parameters of the method below.
     * <p/>
     * All descriptors entered through this method should be of a name and
     * version known by IDEA. The ant task(s) enforce that requirement - no
     * checking is done at this point. No archivePath is required, as IDEA
     * already know where to place the file in the web archive.
     *
     * @param name filename-style name, e.g. <code>weblogic.xml</code>
     * @param file path to the file in the project.
     * @param version string representation of the version, descriptor-specific.
     * E.g. "5.x".
     */
    public void addDescriptor(String name, File file, String version)
    {
        FileUrl url = urlFactory.fileUrl(file);
        moduleProperties.add(new DeploymentDescriptor(name, url, version));
    }


    /**
     * A deployment descriptor that IDEA doesn't know. Both the path to the file
     * in the project, and the path to the file in the web archive, are
     * required.
     *
     * @param file path to the file in the project.
     * @param archivePath path the file is desployed to in the web archive.
     */
    public void addDescriptorUnknown(File file, String archivePath)
    {
        // name and description both derived by number of unknowns.
        String name;
        String description = "Additional Resource";
        name = unknowns == 0 ? "unknown.xml" : "unknown(" + unknowns + ").xml";
        if (unknowns > 0) description = description + "(" + unknowns + ")";
        unknowns++;

        FileUrl url = urlFactory.fileUrl(file);
        moduleProperties.add(new DeploymentDescriptor(name, url, archivePath,
                description));
    }


    public void addRoot(File file, String relative)
    {
        FileUrl url = urlFactory.fileUrl(file);
        moduleProperties.add(new Root(url, relative));
    }

    ////////////////////////////////////////////////////
    // modules


    public void ignoreModule(String moduleName)
    {
        builder().ignoreModule(moduleName).addTo(moduleProperties);
    }


    // default /WEB-INF/classes
    // make File isntead?
    public void copyModuleOutput(String moduleName, String destination)
    {
        builder().copyModuleOutput(moduleName, destination).
                addTo(moduleProperties);
    }


    // default /WEB-INF/lib/[module's name].jar or /[module's name].jar
    public void jarModuleOutput(String moduleName, String destinationJar,
                                boolean linkViaManifest)
    {
        builder().jarModuleOutput(moduleName, destinationJar, linkViaManifest).
                addTo(moduleProperties);
    }

    ////////////////////////////////////////////////////
    // libraries


    public void ignoreModuleLibrary(File file)
    {
        JarUrl jar = urlFactory.jarUrl(file);
        builder().ignoreModuleLibrary(jar).addTo(moduleProperties);
    }


    public void ignoreProjectLibrary(String name)
    {
        builder().ignoreProjectLibrary(name).addTo(moduleProperties);
    }


    public void ignoreGlobalLibrary(String name)
    {
        builder().ignoreGlobalLibrary(name).addTo(moduleProperties);
    }


    public void copyModuleLibrary(File file,
                                  String destinationJar,
                                  boolean linkViaManifest)
    {
        JarUrl jar = urlFactory.jarUrl(file);
        builder().copyModuleLibraryFiles(jar, destinationJar, linkViaManifest).
                addTo(moduleProperties);
    }


    public void copyProjectLibrary(String name,
                                   String destinationDir,
                                   boolean linkViaManifest)
    {
        builder().copyProjectLibraryFiles(name, destinationDir,
                linkViaManifest).addTo(moduleProperties);
    }


    public void copyGlobalLibrary(String name,
                                  String destinationDir,
                                  boolean linkViaManifest)
    {
        builder().copyGlobalLibraryFiles(name, destinationDir, linkViaManifest).
                addTo(moduleProperties);
    }


    private ContainerElementBuilder builder()
    {
        return new ContainerElementBuilder();
    }


    public void addToDom(Element parent)
    {
        // just defer to module properties impl
        moduleProperties.addToDom(parent);
    }


    public String toString()
    {
        return "WebModuleProperties{" +
               "moduleProperties=" + moduleProperties +
               '}';
    }
}
