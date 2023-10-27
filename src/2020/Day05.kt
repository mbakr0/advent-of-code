fun main() {

    addDate(2020, 5)

    val testList = readTestFile()
    val list = readFromFile()

    fun test() = testList.maxOf { it.getId() }
    fun part1() = list.maxOf { it.getId() }
    fun part2() = list.map { it.getId() }.sorted().findMissingSeat()

    check(test() == 567)
    println("Part 1: " + part1())
    println("Part 2: " + part2())


}

private fun List<Int>.findMissingSeat(): Int {
    for (i in 1 until lastIndex)
        if ((get(i) + 1) != get(i + 1))
            return get(i) + 1
    return 0
}

private fun String.getId(): Int {
    val row = getRow(this)
    val col = getCol(this)
    return row * 8 + col
}

fun getRow(str: String) = getRowOrCol(str.substring(0, 7), 'F', h = 128)
fun getCol(str: String) = getRowOrCol(str.substring(7), 'L', h = 8)

fun getRowOrCol(str: String, char: Char, l: Int = 0, h: Int): Int {
    if (str.isEmpty()) return l

    val mid = (l + h) / 2

    return if (str[0] == char)
        getRowOrCol(str.substring(1), char, l, mid)
    else
        getRowOrCol(str.substring(1), char, mid, h)

}












