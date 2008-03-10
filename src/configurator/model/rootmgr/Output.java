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

import configurator.model.url.FileUrl;
import configurator.model.url.Url;


/**
 * Describes output destination for SourceContent. In other words, your
 * "clssses" dir for production (not test classes. Child of Component.
 * <p/>
 *
 * <![CDATA[
 *
 * <output url="file://$MODULE_DIR$/build/classes"/>
 *
 * ]]>
 * 
 * @author Justin Tomich
 */
public class Output extends BaseOutput
{
    Output(Url url)
    {
        this.url = url;
    }


    Output(FileUrl url)
    {
        this.url = url;
    }


    public Output(String path)
    {
        this.url = new FileUrl(path);
    }


    protected String name()
    {
        return "output";
    }


    public String toString()
    {
        return "Output{" +
               "url=" + url +
               "}";
    }
}
