package xmlMappings

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import java.lang.reflect.Constructor


data class ItemXML(
    @JacksonXmlProperty(localName = "ITEMTYPE") val ITEMTYPE: String,
    @JacksonXmlProperty(localName = "ITEMID") val ITEMID: String,
    @JacksonXmlProperty(localName = "QTY") val QTY: Int,
    @JacksonXmlProperty(localName = "COLOR") val COLOR: Int,
    @JacksonXmlProperty(localName = "EXTRA") val EXTRA: String,
    @JacksonXmlProperty(localName = "ALTERNATE") val ALTERNATE: String,
    @JacksonXmlProperty(localName = "MATCHID") val MATCHID: Int,
    @JacksonXmlProperty(localName = "COUNTERPART") val COUNTERPART: String
)