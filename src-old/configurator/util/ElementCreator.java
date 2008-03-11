package configurator.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * @author Justin Tomich
 */
public class ElementCreator
{
    private final String elementName;
    private final Element parent;


    public ElementCreator(String elementName, Element parent)
    {
        this.elementName = elementName;
        this.parent = parent;
    }


    public Element create()
    {
        Document dom = parent.getOwnerDocument();
        Element element = dom.createElement(elementName);
        parent.appendChild(element);
        return element;
    }
}
