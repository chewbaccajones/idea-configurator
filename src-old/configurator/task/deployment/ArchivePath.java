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

package configurator.task.deployment;

import configurator.util.Validator;


/**
 * @author Justin Tomich
 */
public class ArchivePath
{
    protected static final String ROOT_DIR = "/";
    protected static final String LIB_DIR = "/WEB-INF/lib/";
    protected static final String CLASSES_DIR = "/WEB-INF/classes/";


    /**
     * Return the path to the jar in the archive. It archivePath is supplied,
     * that path will be returned. Otherwise, a path will be constructed using
     * default destination directories.
     *
     * @param archivePath path to jar, or null.
     * @param linkViaManifest default dir varies if linked in manifest
     * @param jarName name of jar, may not be null
     * @return path to jar in archive
     */
    public String jarPath(String archivePath, boolean linkViaManifest,
                          String jarName)
    {
        // if this happens, programmer error - should already be validated
        if (Validator.isEmpty(jarName)) runtime("null jarname!");

        if (Validator.isNotEmpty(archivePath)) return archivePath;
        if (linkViaManifest) return ROOT_DIR + jarName;
        return LIB_DIR + jarName;
    }


    /**
     * Return the path to the lib dir in the archive. If archiveDir is not null,
     * that path will be returned. Otherwise a default will be returned.
     *
     * @param archiveDir optional path to the lib dir in the archive.
     * @param linkViaManifest default dir varies if linked in manifest
     * @return path to lib directory
     */
    public String libDirPath(String archiveDir, boolean linkViaManifest)
    {
        if (Validator.isNotEmpty(archiveDir)) return archiveDir;
        if (linkViaManifest) return ROOT_DIR;
        return LIB_DIR;
    }


    /**
     * Return the path to the classes dir in the archive. If archiveDir is not
     * null, that path will be returned. Otherwise a default will be returned.
     *
     * @param archiveDir target dir in archive, optional.
     * @return path to classes dir in archive
     */
    public String classesDirPath(String archiveDir)
    {
        if (Validator.isNotEmpty(archiveDir)) return archiveDir;
        return CLASSES_DIR;
    }


    /**
     * Build a path to the jar in the given archiveDir. If archiveDir is null, a
     * default path will be generated.
     *
     * @param archiveDir target dir in archive, null is accepted.
     * @param linkViaManifest default dir varies if linked in manifest
     * @param jarName name of jar file, required
     * @return path to jar in the archive
     */
    public String archiveDirJarPath(String archiveDir, boolean linkViaManifest,
                                    String jarName)
    {
        String archivePath = completeArchivePath(archiveDir, jarName);
        return jarPath(archivePath, linkViaManifest, jarName);
    }


    /**
     * If not null, give me archiveDir + "/" + jarName. Else null.
     *
     * @param archiveDir destination directory (of jar) in archive
     * @param jarName name of jar to go in archiveDir
     * @return if not null, archiveDir "/" + jarName, else null.
     */
    String completeArchivePath(String archiveDir, String jarName)
    {
        // if this happens, programmer error - should already be validated
        if (Validator.isEmpty(jarName)) runtime("null jarname!");

        if (archiveDir == null) return null;
        archiveDir = archiveDir.endsWith("/") ? archiveDir : archiveDir + "/";
        if (Validator.isNotEmpty(archiveDir)) return archiveDir + jarName;
        return null;
    }


    private void runtime(String message)
    {
        message = "Programmer error, please report to " +
                  "http://code.google.com/p/idea-configurator. " + message;
        throw new RuntimeException(message);
    }
}
