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

import java.io.File;


/**
 * @author Justin Tomich
 */
public interface DeploymentComponent
{
    void addDescriptor(String name, File file, String version);


    void addDescriptorUnknown(File file, String archivePath);


    void addRoot(File file, String relative);


    void ignoreModule(String moduleName);


    void copyModuleOutput(String moduleName, String destination);


    void jarModuleOutput(String moduleName, String destinationJar,
                         boolean linkViaManifest);


    void ignoreModuleLibrary(File file);


    void ignoreProjectLibrary(String name);


    void ignoreGlobalLibrary(String name);


    void copyModuleLibrary(File file,
                           String destinationJar,
                           boolean linkViaManifest);


    void copyProjectLibrary(String name,
                            String destinationDir,
                            boolean linkViaManifest);


    void copyGlobalLibrary(String name,
                           String destinationDir,
                           boolean linkViaManifest);
}
