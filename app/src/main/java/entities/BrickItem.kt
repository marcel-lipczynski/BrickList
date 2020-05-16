package entities

data class BrickItem(
    val imagePath: String,
    val brickQuantity: Int,
    val actualBrickQuantity: Int,
    val brickCode: String,
    val brickName: String
)