package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity
data class Codes(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "ItemID") val itemID: Int,
    @ColumnInfo(name = "ColorID") val colorID: Int?,
    @ColumnInfo(name = "Code") val code: Int?,
    @ColumnInfo(name = "Image") val image: Blob?
)