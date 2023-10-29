fun main() {
    addDate(2020,6)
    val list = readFromFile().parse()
    val testList = readTestFile().parse()

    fun test() = testList.sumOf {
        it.countAnswerAnyone()
    }

    fun part1() = list.sumOf {
        it.countAnswerAnyone()
    }

    fun part2() = list.sumOf {
        it.countAnswerEveryone()
    }


    check(test() == 6)
    println("Part 1: " + part1())
    println("Part 2: " + part2())

}

fun List<String>.countAnswerAnyone () :Int{
    val mutableSet = mutableSetOf<Char>()
    for (i in this)
        mutableSet.addAll(i.asSequence())
    return mutableSet.size
}

fun List<String>.countAnswerEveryone  () :Int{
    val mutableSet = mutableSetOf<Char>()
    mutableSet.addAll(this.first().toSet())
    for (i in this)
        mutableSet.retainAll(i.toSet())
    return mutableSet.size
}

fun List<String>.parse() =
    groupSequence { it.isNotEmpty() }.map {
        it.filter { str -> str.isNotEmpty() }}

