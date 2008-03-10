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

/**
 * Specify the jdk for a module. Two implementations exist: specify a jdk, or
 * inherit the jdk setting from the project.
 * <p/>
 * <p/>
 * <ordered type="jdk" jdkName="1.5" /> OR <ordered type="inheritedJdk" />
 *
 * @author Justin Tomich
 */
public abstract class Jdk implements OrderEntry
{

}
