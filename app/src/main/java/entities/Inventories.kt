package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Inventories(
    @PrimaryKey(autoGenerate = true)  val id: Int = 1,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "Active") val active: Int,
    @ColumnInfo(name = "LastAccessed") val lastAccessed: Int
)