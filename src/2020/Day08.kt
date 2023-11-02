import kotlin.math.max

fun main() {

    addDate(2020,8)
    val list = readFromFile()
    val testList = readTestFile()

    fun test() = testList.accumulator()

    fun part1() = list.accumulator()
    fun part2() = list.changeOperation()
    check(test() == 5)

    println(test())
    println("Part 1: " + part1())
    println("Part 2: " + part2())
}

private fun List<String>.changeOperation(index: Int = 0,visited:MutableSet<Int> = mutableSetOf(),hasChanged: Boolean = false) : Int {
    if(visited.contains(index))
        return Int.MIN_VALUE

    if (index > lastIndex)
        return 0

    visited.add(index)

    val newVisited = mutableSetOf<Int>().apply {addAll(visited)}
    val (operation,num) = get(index).parse()
    when(operation){
        "nop"->
        {
            val route1 = changeOperation(index + 1, newVisited,hasChanged)
            val route2 = if (!hasChanged) changeOperation(index + num, newVisited,true) else Int.MIN_VALUE
            return max(route1,route2)
        }


        "jmp"-> {
            val route1 = changeOperation(index + num, newVisited,hasChanged)
            val route2 = if (!hasChanged) changeOperation(index + 1, newVisited,true) else Int.MIN_VALUE
            return max(route1,route2)
        }
        else-> {
            return num + changeOperation(index + 1, newVisited,hasChanged)

        }
    }
}


private fun List<String>.accumulator(): Int {
    var index = 0
    var acc = 0
    val set = mutableSetOf<Int>()
    while (!set.contains(index))
    {
        set.add(index)
        val (operation,num) = this[index].parse()
        when(operation){
            "nop"-> index++
            "jmp"-> index += num
            else-> {
                acc += num
                index++
            }
        }
    }

    return acc
}
private fun String.parse() =
    substring(0,3) to substring(4).toInt()

