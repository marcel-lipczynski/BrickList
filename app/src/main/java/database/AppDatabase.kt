package database

import androidx.room.Database
import androidx.room.RoomDatabase
import dao.*
import entities.*

@Database(
    entities = [Categories::class, Codes::class, Colors::class, Inventories::class, InventoriesParts::class, ItemTypes::class, Parts::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun codesDao(): CodesDao
    abstract fun colorsDao(): ColorsDao
    abstract fun inventoriesDao(): InventoriesDao
    abstract fun inventoriesPartsDao(): InventoriesPartsDao
    abstract fun itemTypeDao(): ItemTypesDao
    abstract fun partsDao(): PartsDao
}