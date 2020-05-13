package dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import entities.InventoriesParts

@Dao
interface InventoriesPartsDao {

    @Query("SELECT * FROM InventoriesParts WHERE InventoryID=(:inventoryID)")
    fun getAllPartsForInventory(inventoryID: Int): List<InventoriesParts>

    @Query("SELECT * FROM InventoriesParts WHERE id=(:inventoryPartId)")
    fun getInventoryPartById(inventoryPartId: Int): InventoriesParts

    @Insert
    fun insertInventoryParts(vararg inventoryParts: InventoriesParts)

    @Delete
    fun deleteInvetoryPart(inventoryPart: InventoriesParts)

}