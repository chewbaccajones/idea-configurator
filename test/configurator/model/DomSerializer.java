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

package configurator.model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;


public class DomSerializer
{
    public static String toStringUniqueNode(Document doc, String uniqueNode)
    {
        NodeList nodes = doc.getElementsByTagName(uniqueNode);
        assert nodes.getLength() == 1;
        return toString(nodes.item(0));
    }

    public static String toString(Node node)
    {
        OutputStream out = new ByteArrayOutputStream();
        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer serializer;
        try
        {
            serializer = tfactory.newTransformer();
            //Setup indenting to "pretty print"
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(
                    "{ http://xml.apache.org/xslt }indent-amount", "2");

            serializer.transform(new DOMSource(node), new StreamResult(out));
            return out.toString();
        }
        catch (TransformerException e)
        {
            // this is fatal, just dump the stack and throw a runtime exception
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
}
