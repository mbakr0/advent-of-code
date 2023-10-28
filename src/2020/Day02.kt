fun main() {

    addDate(2020, 2)

    val testList = readTestFile()
    val list = readFromFile()

    fun test() = testList.count { validateBoundary(it) }

    fun part1() = list.count { validateBoundary(it) }
    fun part2() = list.count { validatePosition(it) }

    check(test() == 2)
    println("Part 1: " + part1())
    println("Part 2: " + part2())
}

fun validateBoundary(str: String): Boolean {
    val (low,high,char,password) = str.parse()
    val numChar = password.count { it == char.first() }
    return (numChar in low.toInt() .. high.toInt())
}
fun validatePosition(str: String): Boolean {
    val (pos1,pos2,char,password) = str.parse()
    val (c,c1,c2) = Triple(char.first(),password[pos1.toInt() - 1] , password[pos2.toInt() - 1])
    return ((c1 != c2) && (c == c1 || c == c2))
}
private fun String.parse() = splitToSequence("-"," ").toList()





