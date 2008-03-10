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

import static configurator.model.deployment.ContainerElement.*;
import static configurator.model.deployment.Attribute.*;
import configurator.model.url.JarUrl;


/**
 * @author Justin Tomich
 */
public class ContainerElementBuilder
{
    private ContainerElement e;


    public ContainerElementBuilder()
    {
    }

    /////////////////////////////////////////////////////
    // modules


    public ContainerElementBuilder ignoreModule(String name)
    {
        moduleContainer(name, DO_NOT_PACKAGE, NA_VALUE);
        return this;
    }


    public ContainerElementBuilder copyModuleOutput(String moduleName,
                                                    String destination)
    {
        moduleContainer(moduleName, COPY_MODULE_OUTPUT, destination);
        return this;
    }


    // default /WEB-INF/lib/[module's name].jar or /[module's name].jar
    public ContainerElementBuilder jarModuleOutput(String moduleName,
                                                   String destinationJar,
                                                   boolean linkViaManifest)
    {
        Attribute method = libraryMethod(linkViaManifest);
        moduleContainer(moduleName, method, destinationJar);
        return this;
    }

    /////////////////////////////////////////////////////
    // module libraries


    public ContainerElementBuilder ignoreModuleLibrary(JarUrl jar)
    {
        create(Type.library, Level.module).
                attribute(DO_NOT_PACKAGE).
                attribute(NA_URI).
                jarUrl(jar);
        return this;
    }


    public ContainerElementBuilder copyModuleLibraryFiles
            (JarUrl sourceJar, String destinationJar, boolean linkViaManifest)
    {
        Attribute copy = linkViaManifest ?
                         LINK_IN_MANIFEST_AND_COPY_LIBRARY_FILES :
                         COPY_LIBRARY_FILES;
        create(Type.library, Level.module).
                attribute(copy).
                attribute(attributeURI(destinationJar)).
                jarUrl(sourceJar);
        return this;
    }

    /////////////////////////////////////////////////////
    // project | global libraries


    public ContainerElementBuilder ignoreProjectLibrary(String name)
    {
        ignoreNamedLib(name, Level.project);
        return this;
    }


    public ContainerElementBuilder ignoreGlobalLibrary(String name)
    {
        ignoreNamedLib(name, Level.application);
        return this;
    }


    public ContainerElementBuilder copyProjectLibraryFiles
            (String libraryName, String destinationDir, boolean linkViaManifest)
    {
        namedLib(Level.project, libraryName, linkViaManifest,
                destinationDir);
        return this;
    }


    public ContainerElementBuilder copyGlobalLibraryFiles
            (String libraryName, String destinationDir, boolean linkViaManifest)
    {
        namedLib(Level.application, libraryName, linkViaManifest,
                destinationDir);
        return this;
    }

    ////////////////////////////////////////////////////
    // generic assembly methods below here


    public ContainerElementBuilder create(Type type, String name)
    {
        e = new ContainerElement(type, name);
        return this;
    }


    public ContainerElementBuilder create(Type type, Level level)
    {
        e = new ContainerElement(type, level);
        return this;
    }


    public ContainerElementBuilder create(Type type, Level level, String name)
    {
        e = new ContainerElement(type, level, name);
        return this;
    }


    public ContainerElementBuilder attribute(Attribute a)
    {
        e.addAttribute(a);
        return this;
    }


    public ContainerElementBuilder jarUrl(JarUrl jar)
    {
        e.setUrl(new Url(jar));
        return this;
    }


    public ContainerElementBuilder addContextRoot(String value)
    {
        e.addAttribute(new Attribute(Name.contextRoot, value));
        return this;
    }


    public void addTo(ModuleProperties properties)
    {
        properties.add(e);
        e = null;
    }

    ////////////////////////////////////////////////////////////
    // private methods below here


    private void namedLib(Level level, String libraryName,
                          boolean linkViaManifest,
                          String destinationDir)
    {
        create(Type.library, level, libraryName).
                attribute(libraryMethod(linkViaManifest)).
                attribute(attributeURI(destinationDir));
    }


    private void ignoreNamedLib(String name, Level level)
    {
        if (level == Level.project || level == Level.application)
        {
            throw new RuntimeException("level must be project or application");
        }
        create(Type.library, level, name).
                attribute(DO_NOT_PACKAGE).
                attribute(NA_URI);
    }


    private void moduleContainer(String name, Attribute method, String uri)
    {
        create(Type.module, name).
                attribute(method).
                attribute(Attribute.attributeURI(uri));
    }


    private Attribute libraryMethod(boolean linkViaManifest)
    {
        return linkViaManifest ?
               LINK_IN_MANIFEST_AND_COPY_LIBRARY_FILES :
               COPY_LIBRARY_FILES;
    }


}
