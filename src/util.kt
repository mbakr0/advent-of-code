import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse



private const val cookie = "// TODO: add cookie"
private const val baseURL = "https://adventofcode.com"


private object Date {
    var year = 2015
    var day = 1
    fun addDate(year:Int,day:Int){
        Date.year = year
        Date.day= day
    }
}

private fun fileDirectory() = Date.year.toString()
private fun fileName() = "Day${if (Date.day > 9) "" else "0"}${Date.day}"
private fun testFileName () = fileName() + "_test"
private const val root = "src"

fun readFromFile(): List<String> {
    if (fileNotExist())
        makeFile()
    return read()
}

private fun read(fileName: String = fileName()) = File("$root/${fileDirectory()}", fileName)
    .readLines()

private fun makeFile(text:String = readStringFromUrl(),fileName:String = fileName()) {
    createDir()
    write(text,fileName)
}

private fun write(text:String = readStringFromUrl(),fileName:String = fileName()) = File("$root/${fileDirectory()}", fileName)
    .writeText(text)



fun addDate(year:Int,day:Int){
    if (year !in (2015..2022) || day !in (1..25)) throw Exception("Year or day not exist in advent of code")
    Date.addDate(year,day)
}

fun readTestFile(replaceTestFile:Boolean = false): List<String> {
    if (replaceTestFile) writeTestFile()
    if (fileNotExist(testFileName()))
        makeTestFile()
    return read(testFileName())
}

private fun makeTestFile() {
    createDir()
    writeTestFile()
}

private fun writeTestFile(){
    val testInput = getFromClipboard()
    if (testInput != "")
        write(testInput,testFileName())
}
/**
 * clipboard
**/
private fun getFromClipboard():String {
    return Toolkit.getDefaultToolkit().systemClipboard.getString()
}
private fun Clipboard.getString () = if (haveString())getData(DataFlavor.stringFlavor) as String else ""
private fun Clipboard.haveString() = isDataFlavorAvailable(DataFlavor.stringFlavor)

/**
 * Url Request
 **/

private fun readStringFromUrl() = HttpClient
    .newHttpClient()
    .send(makeRequest(), HttpResponse.BodyHandlers.ofString())
    .body()
    .trim()


private fun makeRequest()  = HttpRequest
    .newBuilder(URI("$baseURL/${Date.year}/day/${Date.day}/input"))
    .header("Cookie", cookie)
    .GET()
    .build()

/**
 * file Logic
 * **/
private fun createDir() = File(root, fileDirectory())
    .mkdir()

private fun fileNotExist(fileName: String = fileName()) = !File("$root/${fileDirectory()}", fileName)
    .isFile


inline fun <reified T> createGrid(fill:T, size:Int, predicate: (Int) -> T = {fill},) = createGrid(fill,size,size,predicate)
inline fun <reified T> createGrid(fill: T,row:Int,col:Int,predicate: (Int) -> T = {fill}) = Array(row){ Array(col){predicate(it)} }

inline fun <reified T> Array<Array<T>>.fillGrid(list:List<String>){
    for((row,line) in list.withIndex())
        for ((col,char) in line.withIndex())
            this[row][col] = char as T
}




inline fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()

    for (item in this) {
        list.add(item)
        if (predicate(item))
            break
    }
    return list
}

fun downloadAllFiles(yearRange: IntRange = (2015..2022), dayRange: IntRange = (1..25)){
    for (year in yearRange)
        for (day in dayRange)
        {
            Date.year = year
            Date.day = day
            makeFile()
            println("years $year : Day $day Downloaded")
        }

}

fun Iterable<UInt>.product() = reduce { acc, i ->  acc * i}
