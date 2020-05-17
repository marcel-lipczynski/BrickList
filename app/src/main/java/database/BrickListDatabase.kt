package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dao.*
import entities.*

@Database(
    entities = [Categories::class, Codes::class, Colors::class, Inventories::class, InventoriesParts::class, ItemTypes::class, Parts::class],
    version = 1, exportSchema = false
)
abstract class BrickListDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun codesDao(): CodesDao
    abstract fun colorsDao(): ColorsDao
    abstract fun inventoriesDao(): InventoriesDao
    abstract fun inventoriesPartsDao(): InventoriesPartsDao
    abstract fun itemTypeDao(): ItemTypesDao
    abstract fun partsDao(): PartsDao

    companion object{
        @Volatile
        private var INSTANCE: BrickListDatabase? = null

        fun getDatabase(context: Context): BrickListDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        BrickListDatabase::class.java,
                        "BrickListDatabase"
                    ).createFromAsset("BrickList.db").build()
                INSTANCE = instance
                return instance
            }
        }
    }

}