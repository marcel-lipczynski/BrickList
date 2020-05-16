package util

import org.w3c.dom.Document
import org.w3c.dom.Element
import xmlMappings.ItemXML
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class XmlParser {

    companion object {
        fun writeXml(itemsList: List<ItemXML>, file: File) {

            val docBuilder: DocumentBuilder =
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val doc: Document = docBuilder.newDocument()
            val transformer: Transformer = TransformerFactory.newInstance().newTransformer()

            val rootElement: Element = doc.createElement("INVENTORY")

            for (item in itemsList) {
                val itemElement: Element = doc.createElement("ITEM")

                val itemType: Element = doc.createElement("ITEMTYPE")
                val itemId: Element = doc.createElement("ITEMID")
                val qty: Element = doc.createElement("QTY")
                val color: Element = doc.createElement("COLOR")
                val extra: Element = doc.createElement("EXTRA")
                val alternate: Element = doc.createElement("ALTERNATE")
                val matchId: Element = doc.createElement("MATCHID")
                val counterpart: Element = doc.createElement("COUNTERPART")

                itemType.appendChild(doc.createTextNode(item.itemType))
                itemId.appendChild(doc.createTextNode(item.itemId))
                qty.appendChild(doc.createTextNode(item.qty.toString()))
                color.appendChild(doc.createTextNode(item.color.toString()))
                extra.appendChild(doc.createTextNode(item.extra))
                alternate.appendChild(doc.createTextNode(item.alternate))
                matchId.appendChild(doc.createTextNode(item.matchId.toString()))
                counterpart.appendChild(doc.createTextNode(item.counterpart))

                itemElement.appendChild(itemType)
                itemElement.appendChild(itemId)
                itemElement.appendChild(qty)
                itemElement.appendChild(color)
                itemElement.appendChild(extra)
                itemElement.appendChild(alternate)
                itemElement.appendChild(matchId)
                itemElement.appendChild(counterpart)

                rootElement.appendChild(itemElement)
            }

            doc.appendChild(rootElement)

            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")

            transformer.transform(DOMSource(doc), StreamResult(file))

        }
    }


}