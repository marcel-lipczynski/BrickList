package dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import entities.Inventories

@Dao
interface InventoriesDao {

    @Query("SELECT * FROM Inventories")
    fun getAllInventories(): List<Inventories>

    @Query("SELECT * FROM Inventories WHERE id IN(:inventoriesIDs)")
    fun getInventoriesByIds(inventoriesIDs: IntArray): List<Inventories>

    @Query("SELECT * FROM Inventories WHERE id = :inventoryID")
    fun getInventoryById(inventoryID: Int): Inventories

    @Query("SELECT * FROM Inventories WHERE name = :inventoryName")
    fun getInventoryByName(inventoryName: String): Inventories

    @Query("UPDATE Inventories SET active =:activeValue WHERE id = :id")
    fun updateInventoryActiveValue(activeValue: Int, id: Int): Int

    @Insert
    fun insertInventories(vararg inventories: Inventories)

    @Delete
    fun deleteInventory(inventory: Inventories)

}