fun main() {
    addDate(2020, 3)
    val fill = ' '
    val list = readFromFile()
    val testList = readTestFile()

    val charArray = createGrid(fill, list.size, list.first().length)
    val testArray = createGrid(fill, testList.size, testList.first().length)
    charArray.fillGrid(list)
    testArray.fillGrid(testList)

    fun test() = testArray.listOfAllTrees(listOf(3 to 1)).first()

    fun part1() = charArray.listOfAllTrees(listOf(3 to 1)).first()
    fun part2() = charArray.listOfAllTrees(
        listOf(
            1 to 1,
            3 to 1,
            5 to 1,
            7 to 1,
            1 to 2,
        )
    ).product()



    check(test() == 7U)
    println("Part 1: " + part1())
    println("Part 2: " + part2())

}


fun Array<Array<Char>>.listOfAllTrees(list: List<Pair<Int, Int>>) = list.map { entry ->
    numOfTrees(entry.first, entry.second)
}


fun Array<Array<Char>>.numOfTrees(right: Int, down: Int): UInt {
    var treeNum = 0
    var (row, col, end) = Triple(0, 0, this.lastIndex)

    while (row < end) {
        col = (col + right) % this.first().size
        row += down
        if (row > end) break
        if (this[row][col] == '#') treeNum++
    }
    return treeNum.toUInt()
}


