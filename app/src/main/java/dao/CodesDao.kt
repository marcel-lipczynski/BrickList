package dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import entities.Codes

@Dao
interface CodesDao {

    @Query("SELECT * FROM Codes")
    fun getAllCodes(): List<Codes>

    @Query("SELECT * FROM Codes WHERE id IN (:codesIDs)")
    fun getCodesByIds(codesIDs: IntArray): List<Codes>

    @Query("SELECT * FROM Codes WHERE id = (:codeID)")
    fun getCodeById(codeID: Int): Codes

    @Insert
    fun insertCodes(vararg codes: Codes)

    @Delete
    fun deleteCode(code: Codes)



}