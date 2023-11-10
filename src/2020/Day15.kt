
fun main() {
    val list = mutableListOf(1,0,16,5,17,4)
    val testList = listOf(
        mutableListOf(0,3,6),
        mutableListOf(1,3,2),
        mutableListOf(2,1,3),
        mutableListOf(1,2,3),
        mutableListOf(2,3,1),
        mutableListOf(3,2,1),
        mutableListOf(3,1,2)
    )
    val testAnswer = listOf(436,1,10,27,78,438,1836)

    fun test() = testList.count()

    fun part1() = list.mostRecentNum(2020)
    fun part2() = list.mostRecentNum(30000000)

    check(checkList(2020,testList,testAnswer))

    println("Part 1: " + part1())
    println("Part 2: " + part2())

}

fun checkList(num: Int,l1:List<MutableList<Int>>,l2:List<Int>):Boolean{
    for (i in l2.indices)
        if (l1[i].mostRecentNum(num) != l2[i])
            return false
    return true
}

fun MutableList<Int>.mostRecentNum(num:Int): Int {
    val map = mutableMapOf<Int,MutableList<Int>>()
    for ((i,v) in withIndex()) {
        map[v]?.add(v) ?: map.put(v,mutableListOf(i))
    }
    var recentNum = last()

    for (i in size until  num)
    {
        val list = map[recentNum]!!
        while (list.size > 2)
            list.removeFirst()
        recentNum = if (list.size == 1) {
            map[0]?.add(i) ?: map.put(0, mutableListOf(i))
            0
        } else {
            val value = list.last() - list.first()
            map[value]?.add(i) ?: map.put(value, mutableListOf(i))
            value
        }

    }
    return recentNum
}
