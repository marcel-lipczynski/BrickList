package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Parts(
    @PrimaryKey(autoGenerate = true)  val id: Int,
    @ColumnInfo(name = "TypeID") val typeID: Int,
    @ColumnInfo(name = "Code") val code: String,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "NamePL") val namePl: String?,
    @ColumnInfo(name = "CategoryID") val categoryID: Int
)