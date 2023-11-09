import java.lang.Long.toBinaryString as longToBinary

fun main() {

    addDate(2020,14)
    val list = readFromFile()
    val testList = readTestFile()

    fun test() = testList.sumOfMemoryValues(mutableMapOf())

    fun part1() = list.sumOfMemoryValues(mutableMapOf())
    fun part2() = list.sumOfMem(mutableMapOf())

    check(test() == 165L)
    println("Part 1: " + part1())
    println("Part 2: " + part2())


}

private fun List<String>.sumOfMemoryValues(mem : MutableMap<Long,Long>): Long {
    var mask = ""
    for (i in this)
        if (i.contains("mask"))
            mask = i.getMask()
        else {
            val (memAddress,value) = i.getMemory()
            mem[memAddress] = addMaskToValue(mask,longToBits(value))
        }
    return mem.values.sum()
}

fun List<String>.sumOfMem(mem : MutableMap<Long,Long>): Long {
    var mask = ""
    for (i in this)
        if (i.contains("mask"))
            mask = i.getMask()
        else {
            val (memAddress,value) = i.getMemory()
            val m = addMaskToMemory(mask,longToBits(memAddress))
            val l = getListOfPossibleMemoryAddress(m)
            l.forEach {
                mem[it.bitsToLong()] = value
            }
        }
    return mem.values.sum()
}


private fun addMaskToValue(mask:String, value:String) = buildString {
    append(mask)
    for (i in value.indices)
        if (this[i] == 'X')
            this[i] = value[i]
}.bitsToLong()

private fun addMaskToMemory(mask:String, memory:String) = buildString {
    append(mask)
    for (i in memory.indices)
        if (this[i] == '0')
            this[i] = memory[i]
}

private fun getListOfPossibleMemoryAddress(str: String):MutableList<String> {
    if (str.isEmpty() || !str.contains("X")) return mutableListOf(str)
    val i = str.indexOf("X")
    val s = str.substring(0,i)
    val poss0 = "0" + str.substring(i + 1)
    val poss1 = "1" + str.substring(i + 1)

    val l0 = getListOfPossibleMemoryAddress(poss0)
    val l1 = getListOfPossibleMemoryAddress(poss1)

    for (j in 0..l0.lastIndex)
        l0[j] = s + l0[j]

    for (j in 0..l1.lastIndex)
        l1[j] = s + l1[j]

    return mutableListOf<String>().apply {  addAll(l0) ; addAll(l1) }
}
private fun longToBits(num: Long) = longToBinary(num).formatTo36Bit()
private fun String.formatTo36Bit() = String.format("%36s",this).replace(" ","0")
private fun String.bitsToLong() = toLong(2)
private fun String.getMask() = Regex(""".{36}$""").find(this)?.value.toString()
private fun String.getMemory() = Regex("""\d+""").findAll(this).map { it.value.toLong() }.toList()