fun main() {
    val testSolution = listOf("1221","11", "21", "1211", "111221", "312211")
    val testList = listOf("211","1", "11", "21", "1211", "111221")
    val list = "1113122113"

    fun test() = testList.map { it.convert()  }

    fun part1() = list.repeat(40)
    fun part2() = list.repeat(50)

    check(checkList(test(),testSolution))
    println("Part 1: " + part1())
    println("Part 2: " + part2())

}



fun checkList(arr1: List<String>, arr2: List<String>) :Boolean {
    for (i in arr1.indices)
        if (arr1[i] != arr2[i])
            return false
    return true
}

fun String.repeat(num:Int):Int
{
    var str = this
    for (i in 1..num)
        str = str.convert2()
    return str.length

}

fun String.convert():String {
    var count = 0
    var c = first()
    val stringBuilder = StringBuilder()
    for (i in indices)
    {
        if (c == get(i))
        {
            count++
        }
        else
        {
            stringBuilder.append("$count$c")
            c = get(i)
            count = 1
        }
    }
    stringBuilder.append("$count$c")
    return stringBuilder.toString()
}


private fun String.convert2(): String {
    var mapIndex = 0
    var c = first()
    return groupBy {
        if (c == it)
            return@groupBy mapIndex
        c=it
        ++mapIndex
    }.map {
        "${it.value.size}${it.value.first()}"
    }.joinToString("")
}