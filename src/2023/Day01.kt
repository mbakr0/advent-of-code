package adventofcode
val numArray = arrayOf(
    "one","two","three","four","five","six","seven","eight","nine"
)
fun main() {
    addDate(2023, 1)
    val list = readLinesFromFile()
    val testList = readLinesTestFile()

    fun test() = testList.sumOf { it.firstCalibration() }
    fun part1() = list.sumOf { it.firstCalibration() }
    fun part2() = list.sumOf { it.secondCalibration() }

    (3..5).map {

    }
    check(test() == 142)
    println("Part 1: " + part1())
    println("Part 2: " + part2())
}

fun String.firstCalibration():Int{
    return "${this.first{it.isDigit()}}${this.last {it.isDigit()}}".toInt()
}
fun String.secondCalibration():Int{
    val list = mutableListOf<Int>()
    for ((i,c) in this.withIndex()) {
        if (c.isDigit())
            list.add(c.digitToInt())
        for ((w,word) in numArray.withIndex())
            if (this.substring(i).startsWith(word))
                list.add(w+1)
    }
    return "${list.first()}${list.last()}".toInt()
}