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

package configurator.task.build;

import configurator.model.build.BuildPropertiesComponent;
import configurator.task.java.JavaTask;

import java.io.File;


/**
 * Contains most of the 'build' configuration data for the ant tasks. Some
 * module types add on "validation", so BuildPropertiesSubtask provides two
 * subtasks that can be encapsulated by the enclosing module task.
 *
 * @author Justin Tomich
 */
public class BuildTask
{
    private final JavaTask java;

    private File explodedDir;
    private boolean explode;
    private File warFile;
    private boolean buildJar;
    private boolean syncExplodedDir;
    private boolean buildOnFrameDeactivation;
    private boolean copyOutsideExplodedDir;


    public BuildTask(JavaTask java)
    {
        this.java = java;
    }

    /////////////////////////////////////////////////////////
    // encompassing module should proxy these two methods...


    public War createWar()
    {
        return new War();
    }


    public Exploded createExploded()
    {
        return new Exploded();
    }

    /////////////////////////////////////////////////////////

    // <war file="whatever/big.war" buildOnDeactivation="false"
    // allowFilesOutsideExploded="false"/>

    public class War // inner class so we can express as <war> in ant task
    {
        public War()
        {
            buildJar = true; // if constructed, build it yo
        }


        public void setFile(File file)
        {
            warFile = file;
        }


        public void setBuildOnFrameDeactivation(boolean build)
        {
            buildOnFrameDeactivation = build;
        }


        public void setAllowFilesOutsideExploded(boolean allowOutside)
        {
            copyOutsideExplodedDir = allowOutside;
        }
    }


    // <exploded dir="exploded" excluded="true" synchronize="true"/>
    public class Exploded
    {
        public Exploded()
        {
            explode = true; // true by default if constructed
        }


        public void setDir(File dir)
        {
            explodedDir = dir;
        }


        public void setExcluded(boolean excluded)
        {
            java.setExcludeExploded(excluded);
        }


        public void setSynchronize(boolean sync)
        {
            syncExplodedDir = sync;
        }
    }


    /**
     * Inject properties gathered in BuildTask into the BuildPropertiesComponent
     * given here. The component does all the actual "work".
     *
     * @param component inject configuration information into component.
     */
    public void configure(BuildPropertiesComponent component)
    {
        component.setExplodeEnabled(explode);
        component.setExplodedDir(explodedDir.getAbsolutePath());
        component.setSyncExplodedDir(syncExplodedDir);
        component.setJarEnabled(buildJar);
        component.setJarFile(warFile.getAbsolutePath());
        component.setBuildModuleOnFrameDeactivation(buildOnFrameDeactivation);
        component.setBuildExternalDependencies(copyOutsideExplodedDir);
    }


    public String toString()
    {
        return "BuildTask{" +
               "explodedDir=" + explodedDir +
               ", explode=" + explode +
               ", warFile=" + warFile +
               ", buildJar=" + buildJar +
               ", syncExplodedDir=" + syncExplodedDir +
               ", buildOnFrameDeactivation=" + buildOnFrameDeactivation +
               ", copyOutsideExplodedDir=" + copyOutsideExplodedDir +
               '}';
    }
}
