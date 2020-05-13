package dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import entities.Colors

@Dao
interface ColorsDao {

    @Query("SELECT * FROM Colors")
    fun getAllColors(): List<Colors>

    @Query("SELECT * FROM Colors WHERE id IN (:colorsIDs)")
    fun getColorsByIds(colorsIDs: IntArray): List<Colors>

    @Query("SELECT * FROM Colors WHERE id = (:colorID)")
    fun getColorById(colorID: Int): Colors

    @Insert
    fun insertColors(vararg colors: Colors)

    @Delete
    fun deleteColor(color: Colors)


}