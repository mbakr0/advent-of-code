
val arr = intArrayOf(0,28,14,18,33,42,31,46,20,48,47,24,23,49,45,19,38,39,11,1,32,25,35,8,17,7,9,4,2,3,34,10)
fun main() {

    addDate(2020,10)

    val list = readFromFile().map { it.toInt() }.toMutableList().apply { add(0)}.sorted()
    val testList = readTestFile().map { it.toInt() }.toMutableList().apply {add(0)}.sorted()

    fun test() = testList.getJolt().product()
    val test2 = arr.sorted().distinct()
    fun part1() = list.getJolt().product()
    fun part2() = list.distinct()



    check(test() == 35)
    check(test2 == 19208UL)

    println("Part 1: " + part1())
    println("Part 2: " + part2())

}

private fun  List<Int>.getJolt(): Pair<Int,Int> {
    var jolt1 = 0
    var jolt3 = 1
    for (i in  1..lastIndex)
        if (this[i] - this[i - 1] == 1)
            jolt1++
        else
            jolt3++

    return Pair(jolt1 , jolt3)
}


fun Pair<Int,Int>.product()= first * second

fun List<Int>.distinct(visited:MutableMap<Int,ULong> = mutableMapOf()): ULong {
    if (size == 1) return 1UL
    var total = 0UL
    if (visited.contains(first()))
        return visited[first()]!!

    for (i in 1..lastIndex)
        if ((this[i] - first()) > 3)
            break
        else
            total+=drop(i).distinct(visited)
    visited[first()] = total
    return visited[first()]!!
}