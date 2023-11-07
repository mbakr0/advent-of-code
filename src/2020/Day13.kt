
fun main() {

    addDate(2020,13)

    val list = readFromFile()
    val testList = readTestFile()

    fun test() = testList.getIdMinutes()

    fun part1() = list.getIdMinutes()
    fun part2() = list.mapToULong().getEarliestTimestamp()

    check(test() == 295UL)

    println("Part 1: " + part1())
    println("Part 2: " + part2())

}

private fun List<String>.getIdMinutes(): ULong {
    val  timestamp = first().toULong()
    val busIds = mapToULong().filterNot { it == 1UL }

    val minId = busIds.minBy {
        reminder(it,timestamp)
    }
    return minId * reminder(minId,timestamp)
}

private fun reminder(i1:ULong,i2: ULong) =
    i1- i2 % i1

private fun List<String>.mapToULong() =
    last().split(",").map { if (it == "x") 1UL else it.toULong() }

private fun List<ULong>.getEarliestTimestamp():ULong {
    var timestamp = 0UL
    var step = first()
    for (i in 1..lastIndex){
        val value = get(i)
        while ((timestamp + i.toULong() ) % value != 0UL) {
            timestamp+=step
        }
        step*=value
    }
    return timestamp
}


