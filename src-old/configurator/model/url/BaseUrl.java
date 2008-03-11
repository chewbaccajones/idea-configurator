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

import org.w3c.dom.Element;


/**
 * @author Justin Tomich
 */
public abstract class BaseUrl implements Url
{
    protected final String path;


    public BaseUrl(String path)
    {
        this.path = path;
    }


    public boolean matches(String pattern)
    {
        return path.matches(pattern);
    }


    public void setAttribute(Element e)
    {
        e.setAttribute("url", contents());
    }


    public void setAttribute(Element e, String name)
    {
        e.setAttribute(name, contents());
    }


    public void setText(Element e)
    {
        e.setTextContent(contents());
    }


    /**
     * The full contents of the url as a String. Includes the path, plus
     * whatever other crap wraps it.
     *
     * @return full contents of the url as a String
     */
    protected abstract String contents();


    /**
     * @param candidatePath
     * @return true if candidate path is contained in the url.
     * @see String#contains(CharSequence)
     */
    public boolean contains(String candidatePath)
    {
        return path.contains(candidatePath);
    }
}
