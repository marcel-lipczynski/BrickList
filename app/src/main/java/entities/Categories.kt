package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Categories(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "Code") val code: Int,
    @ColumnInfo (name = "Name") val name: String,
    @ColumnInfo(name = "NamePL") val namePL: String?
)