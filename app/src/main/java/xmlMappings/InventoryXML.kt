package xmlMappings

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "INVENTORY")
data class InventoryXML(
    @JacksonXmlProperty(localName = "ITEM")
    @JacksonXmlElementWrapper(useWrapping = false)
    val item: List<ItemXML>
)