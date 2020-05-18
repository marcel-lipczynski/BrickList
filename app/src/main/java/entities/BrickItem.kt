package entities

data class BrickItem(
    val id: Int,
    val imagePath: String,
    val brickQuantity: Int,
    var actualBrickQuantity: Int,
    val brickColor: String,
    val brickName: String?
)