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

package configurator.util;

import org.apache.tools.ant.BuildException;


/**
 * @author Justin Tomich
 */
public class Validator
{
    public static boolean isNotIn(String field, String... allowed)
    {
        return !isIn(field, allowed);
    }


    public static boolean isNotInIgnoreCase(String field, String... allowed)
    {
        return !isInIgnoreCase(field, allowed);
    }


    public static boolean isIn(String field, String... allowed)
    {
        for (String s : allowed)
        {
            if (s.equals(field)) return true;
        }
        return false;
    }


    public static boolean isInIgnoreCase(String field, String... allowed)
    {
        for (String s : allowed)
        {
            if (s.equalsIgnoreCase(field)) return true;
        }
        return false;
    }


    public static boolean isEmpty(Object... fields)
    {
        for (Object field : fields)
        {
            if (isNotEmpty(field)) return false;
        }
        return true;
    }


    public static boolean isNotEmpty(Object... fields)
    {
        for (Object field : fields)
        {
            if (field == null) return false;
            if ("".equals(field)) return false;
        }
        return true;
    }


    /**
     * If field is empty, throw a BuildException.
     *
     * @param field field to be validated
     * @param fieldName name of field being validated, used in exception
     * @throws BuildException if validation fails
     */
    public static void notEmpty(Object field, String fieldName)
    {
        if (isEmpty(field)) huck(fieldName + " is required.");
    }


    public static BuildException huck(String msg)
    {
        throw new BuildException(msg);
    }
}
