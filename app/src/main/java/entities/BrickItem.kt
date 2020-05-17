package entities

data class BrickItem(
    val imagePath: String,
    val brickQuantity: Int,
    var actualBrickQuantity: Int,
    val brickColor: String,
    val brickName: String?
)