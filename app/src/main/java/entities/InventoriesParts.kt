package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InventoriesParts(
    @PrimaryKey(autoGenerate = true)  val id: Int = 0,
    @ColumnInfo(name = "InventoryID") val inventoryID: Int,
    @ColumnInfo(name = "TypeID") val typeID: Int,
    @ColumnInfo(name = "ItemID") val itemID: String,
    @ColumnInfo(name = "QuantityInSet") val quantitiyInSet: Int,
    @ColumnInfo(name = "QuantityInStore") val quantitiyInStore: Int,
    @ColumnInfo(name = "ColorID") val colorID: Int,
    @ColumnInfo(name = "Extra") val extra: String
)
