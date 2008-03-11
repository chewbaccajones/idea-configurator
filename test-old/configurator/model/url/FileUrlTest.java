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

package configurator.model.url;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.w3c.dom.Element;


/**
 * @author Justin Tomich
 */
public class FileUrlTest extends TestCase
{
    public void testAddToDom()
    {
        String path = "foo";

        Element setting = EasyMock.createMock(Element.class);
        setting.setAttribute("url", "file://" + path);
        EasyMock.replay(setting);

        FileUrl fu = new FileUrl(path);
        fu.setAttribute(setting);
        
        EasyMock.verify(setting);
    }

    
}