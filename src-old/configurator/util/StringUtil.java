package configurator.util;

/**
 * User: tomichj Date: Jul 11, 2007 Time: 6:32:51 PM
 */
public class StringUtil
{
    /**
     * Protect against null Strings.
     * 
     * @param value String to denull
     * @return value or "" if value is null
     */
    public static String denull(String value)
    {
        if (value == null) return "";
        return value;
    }
}
