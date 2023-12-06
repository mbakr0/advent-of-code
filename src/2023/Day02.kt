
val colorMap = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
)
fun main() {
    addDate(2023, 2)
    val list = readLinesFromFile()
    val testList = readLinesTestFile()

    fun test() = testList.sumOf { it.validateGame() }
    fun part1() = list.sumOf { it.validateGame() }
    fun part2() = list.sumOf { it.part2() }


    val map = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"

    println(map)

    println(test())
    println("Part 1: " + part1())
    println("Part 2: " + part2())
}

private fun String.validateGame():Int {
    val (id, game) = this.split(": ")
    return if (game.isValidGame())
        id.substring(5).toInt()
    else 0
}
private fun String.isValidGame(): Boolean {
    return split("; ").all { it.validRound() }
}

private fun String.validRound(): Boolean {
    return split(", ").all { it.isValidColorNum() }
}
private fun String.isValidColorNum(): Boolean {
    val (num,color) = split(" ")
    return  num.toInt() <= colorMap[color]!!
}

private fun String.part2():Long {
    return split(" ",":",";",",")
        .asSequence()
        .filter { it.isNotEmpty() }
        .chunked(2)
        .groupBy { it.last()}
        .map {map ->
            if (map.key.toIntOrNull() != null)
                "Game" to map.key.toInt()
            else
                map.key to map.value.maxOf { it.first().toInt()}
        }
        .productOf {
            if (it.first == "Game")
                1
            else
                it.second.toLong()
        }
}