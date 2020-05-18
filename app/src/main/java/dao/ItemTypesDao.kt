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

    @Query("SELECT * FROM ItemTypes WHERE id = :itemTypeId")
    fun getItemTypeById(itemTypeId: Int): List<ItemTypes>

    @Query("SELECT id FROM ItemTypes WHERE Code =:code")
    fun getItemTypeByCode(code: String): Int

    @Query("SELECT Code FROM ItemTypes WHERE id = :typeId")
    fun getItemTypeByTypeId(typeId: Int): String

    @Insert
    fun insertItemType(vararg itemType: ItemTypes)

    @Delete
    fun deleteItemType(itemType: ItemTypes)
}