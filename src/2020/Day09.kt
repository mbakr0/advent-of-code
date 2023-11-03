
fun main() {

    addDate(2020,9)
    val list = readFromFile().map { it.toULong() }
    val testList = readTestFile().map { it.toULong() }


    fun test() = testList.getInvalid(5)

    fun part1() = list.getInvalid(25)
    fun part2() = list.sumOfMaxAndMin(list.contiguousList(part1()))


    check(test() == 127.toULong())

    println("Part 1: " + part1())
    println("Part 2: " + part2())

}

private fun List<ULong>.isNotTarget(target: ULong, first: Int, last: Int): Boolean {
    for (i in first..last)
        for (j in i + 1..last)
            if (this[i] + this[j] == target)
                return false
    return true
}

private fun List<ULong>.getInvalid(preambleLength :Int ): ULong {

    for (i in preambleLength..lastIndex)
        if (isNotTarget(this[i],i-preambleLength,i-1))
            return this[i]
    throw Exception("Invalid number doesn't exist")
}


private fun List<ULong>.contiguousList(target: ULong): IntRange {
    for (i in 0 until lastIndex)
    {
        var index = i + 1
        var sum = this[i]
        while (index < size){
            sum+=this[index]
            if (sum == target)
                return i..index
            if (sum > target)
                break
            index++
        }
    }
    throw Exception("No list exist")
}



private fun List<ULong>.sumOfMaxAndMin(range: IntRange) =
    range.maxOf { this[it] } + range.minOf { this[it] }
