private val requiredFields = arrayOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
private val eyeColor = hashSetOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
fun main() {
    addDate(2020, 4)

    val testList = readTestFile()
    val list = readFromFile()

    fun test() = testList.getAllPassport().passportWithAllFields().count()
    fun part1() = list.getAllPassport().passportWithAllFields().count()
    fun part2() = list.getAllPassport().passportWithAllFields().checkDataValidation().count()

    check(test() == 2)
    println("Part 1: " + part1())
    println("Part 2: " + part2())

}
private fun List<String>.checkDataValidation() =
    filter { validate(it) }


private fun validate(passport: String): Boolean {
    return getFormattedData(passport).all { checkData(it) }

}
private fun getFormattedData(str: String) =
    str.split(" ").map {
        val (a, b) = it.split(":")
        Pair(a, b)
    }
private fun checkData(element: Pair<String, String>) =
    when (element.first) {
        "byr" -> validRange(element.second, 1920..2002)
        "iyr" -> validRange(element.second, 2010..2020)
        "eyr" -> validRange(element.second, 2020..2030)
        "hgt" -> validHgt(element.second)
        "hcl" -> validHcl(element.second)
        "ecl" -> eyeColor.contains(element.second)
        "pid" -> element.second.length == 9
        "cid" -> true
        else -> false
    }
private fun validRange(str: String, range: IntRange) = str.toInt() in range
private fun validHcl(hColor: String): Boolean {
    if (hColor.first() != '#') return false
    val str = hColor.substring(1)
    for (i in str)
        if (i !in '0'..'9' && i !in 'a'..'f')
            return false
    return true
}
private fun validHgt(height: String): Boolean {
    if (height.last() == 'n') {
        val h = height.split("in").first()
        return validRange(h,59..76)
    }
    val h = height.split("cm").first()
    return validRange(h,150..193)
}
private fun List<String>.passportWithAllFields() = filter { passport -> requiredFields.all { passport.contains(it) } }
private fun List<String>.getAllPassport() = joinToString(" ").split("  ")





