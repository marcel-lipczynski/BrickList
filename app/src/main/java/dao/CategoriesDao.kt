package dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import entities.Categories

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM Categories")
    fun getAllCategories(): List<Categories>

    @Query("SELECT * FROM Categories WHERE id IN (:categoriesIDs)")
    fun getCategoriesByIds(categoriesIDs: IntArray): List<Categories>

    @Query("SELECT * FROM Categories WHERE id = (:categoryID)")
    fun getCategoryById(categoryID: Int): Categories

    @Insert
    fun insertCategories(vararg categories: Categories)

    @Delete
    fun deleteCategory(category: Categories)



}