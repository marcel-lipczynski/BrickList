package dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import entities.ItemTypes


@Dao
interface ItemTypesDao {

    @Query("SELECT * FROM ItemTypes")
    fun getAllItemTypes(): List<ItemTypes>

    @Query("SELECT * FROM ItemTypes WHERE id = (:itemTypeId)")
    fun getItemTypeById(itemTypeId: Int): List<ItemTypes>

    @Insert
    fun insertItemType(vararg itemType: ItemTypes)

    @Delete
    fun deleteItemType(itemType: ItemTypes)
}