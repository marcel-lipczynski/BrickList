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

    @Query("SELECT * FROM InventoriesParts WHERE InventoryID =:inventoryPartId ORDER BY (QuantityInSet - QuantityInStore) DESC, ColorID")
    fun getAllPartsForInventoryOrdered(inventoryPartId: Int): List<InventoriesParts>

    @Query("SELECT * FROM InventoriesParts WHERE InventoryID = :inventoryPartId")
    fun getInventoryPartById(inventoryPartId: Int): InventoriesParts


    @Query("UPDATE InventoriesParts SET QuantityInStore = :newQuantity WHERE id = :id")
    fun updateQuantityInStoreById(newQuantity: Int, id: Int): Int


    @Insert
    fun insertInventoryParts(vararg inventoryParts: InventoriesParts)

    @Delete
    fun deleteInvetoryPart(inventoryPart: InventoriesParts)

}