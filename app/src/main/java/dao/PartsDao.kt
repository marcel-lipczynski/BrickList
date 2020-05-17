package dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import entities.Parts

@Dao
interface PartsDao {

    @Query("SELECT * FROM Parts")
    fun getAllParts(): List<Parts>

    @Query("SELECT * FROM Parts WHERE id IN (:partIds)")
    fun getPartsByIds(partIds: IntArray): List<Parts>

    @Query("SELECT Name FROM Parts WHERE Code = :code")
    fun getPartNameByPartCode(code: String): String

    @Query("SELECT * FROM Parts WHERE id = :partId")
    fun getPartById(partId: Int): Parts

    @Insert
    fun insertParts(vararg parts: Parts)

    @Delete
    fun deletePart(part: Parts)



}