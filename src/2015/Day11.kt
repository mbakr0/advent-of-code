import java.lang.StringBuilder as StringBuilder

const val passwordLength = 8
val charSet = setOf('i' ,'o' ,'l')

fun main() {

    //addDate(2015,11)

    val list = "cqjxjnds"
    val testList = listOf("abcdefgh","ghijklmn")
    val testAnswer = listOf("abcdffaa","ghjaabcc")

    println("a".repeat(5))


    fun test() = checkTest(testList,testAnswer)

    fun part1() = list.incrementUntilValid()
    fun part2() = part1().incrementUntilValid()

    check(test())
    println("Part 1: " + part1())
    println("Part 2: " + part2())


}

fun checkTest(l1: List<String>,l2: List<String>): Boolean {
    for (i in l1.indices)
        if (l1[i].incrementUntilValid() != l2[i])
            return false
    return true
}
fun String.incrementUntilValid() : String {
    var str = this.incrementPassword()

    while (!checkPassword(str))
    {
        if (!str.checkCharSets())
            str = str.replaceCharSets()
        str = str.incrementPassword()
    }
    return str
}

fun String.incrementPassword():String{
    return if (last() == 'z') substring(0 until lastIndex).incrementPassword() + 'a'
    else substring(0 until lastIndex) + last().inc()
}

fun checkPassword(str: String) : Boolean {
    val b1 = str.checkPairs()
    val b2 = str.checkStraight()
    val b3 = str.checkCharSets()
    return b1&&b2&&b3

}

fun String.checkPairs():Boolean{
    val set = mutableSetOf<Int>()
    for (i in 1..lastIndex)
        if (this[i-1] == this[i])
            set.addAll(listOf(i-1,i))
    return set.size >= 4
}

fun String.checkStraight() : Boolean {
    for (i in 1..< lastIndex)
    {
        val (prev,curr,next) = Triple(this[i-1].inc(),this[i],this[i+1].dec())
        if (prev == curr && curr == next)
            return true
    }
    return false
}

fun String.checkCharSets() : Boolean {
    for (i in this)
        if (charSet.contains(i))
            return false
    return true
}
/**
 * adding replaceCharSets() function for optimization
 **/
fun String.replaceCharSets():String {
    val str = StringBuilder()
    for (c in this)
        if (charSet.contains(c))
        {
            str.append(c.inc())
            break
        }
        else
            str.append(c)
    str.append("a".repeat(passwordLength - str.length))

    return str.toString()
}
