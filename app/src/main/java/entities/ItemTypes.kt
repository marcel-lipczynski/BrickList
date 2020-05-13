package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemTypes(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "Code") val code: String,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "NamePL") val namePl: String?
)