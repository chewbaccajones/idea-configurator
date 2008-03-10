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

import java.net.URL;
import java.net.MalformedURLException;


/**
 * Url, must begin with http, must be well-formed according to java.net.URL.
 *
 * @author Justin Tomich
 * @see java.net.URL
 */
public class HttpUrl extends BaseUrl
{
    public HttpUrl(String path)
    {
        // validate path starts with http
        super(path.endsWith("/") ? path : path + "/");
        if (!valid()) throw new RuntimeException("Malformed http url:" + path);
    }


    protected boolean valid()
    {
        try
        {
            new URL(path);
            return path.startsWith("http://");
        }
        catch (MalformedURLException e)
        {
            return false;
        }
    }


    protected String contents()
    {
        return path;
    }


    public String toString()
    {
        return "HttpUrl{" +
               "path='" + path + "'" +
               "}";
    }
}
