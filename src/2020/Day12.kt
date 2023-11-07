import kotlin.math.*
enum class Direction(private val degree: Int) {
    NORTH(0), EAST(90), SOUTH(180), WEST(270);
    fun getDirectionByDegree(degree: Int) = entries.first { it.degree == degree }
}
abstract class Point{
    abstract fun forward(int: Int)
    abstract fun rotateOrUpdateDirection(i: Int)

    var degree = 0 ; var east = 0 ; var west = 0 ; var north = 0 ; var south = 0

    private fun parse(str: String) = Pair(str.first(),str.substring(1).toInt())

    fun finalDestination(ship: Ship) = abs(ship.east - ship.west) + abs(ship.north - ship.south)

    fun step(str:String){
        val (action,value) = parse(str)
        when(action) {
            'N' -> north += value
            'S' -> south += value
            'E' -> east += value
            'W' -> west += value
            'L' -> rotateOrUpdateDirection(-value)
            'R' -> rotateOrUpdateDirection(value)
            'F' -> forward(value)
        }
    }
}

class Ship : Point()
{
    private var direction = Direction.EAST
    init {
        degree = 90
    }
    override fun forward(int: Int) = when(direction){
        Direction.NORTH -> north += int
        Direction.EAST -> east += int
        Direction.SOUTH -> south += int
        Direction.WEST -> west += int
    }

    override fun rotateOrUpdateDirection(i: Int) {
        degree = (degree + 360 + i) % 360
        direction = direction.getDirectionByDegree(degree)
    }
}


class WayPoint (val ship:Ship): Point(){
    init {
        east = 10
        north = 1
    }
    override fun forward(int: Int) {
        ship.east  += int * east
        ship.west  += int * west
        ship.north += int * north
        ship.south += int * south
    }

    override fun rotateOrUpdateDirection(i: Int) {
        degree = (i + 360) % 360
        rotateByDegree(degree)
    }
    private fun rotateByDegree(degree: Int) {
        when(degree){
            0 -> return
            90 -> rotateCallback(0)
            180 -> rotateCallback(90)
            270 -> rotateCallback(180)
            else -> throw Exception("rotation not available")
        }
    }
    private fun rotateCallback(degree: Int){
        rotatePoint()
        rotateByDegree(degree)
    }
    private fun rotatePoint(){
        val (east,south,west,north) = listOf(north,east,south,west)
        this.north = north ; this.east = east ;this.south = south ; this.west = west
    }
}

fun main() {

    addDate(2020,12)

    val list = readFromFile()
    val testList = readTestFile()

    fun test() = testList.move(Ship()).let { it.finalDestination(it) }

    fun part1() = list.move(Ship()).let { it.finalDestination(it) }
    fun part2() = list.move(WayPoint(Ship())).let { it.finalDestination(it.ship) }

    check(test() == 25)

    println("Part 1: " + part1())
    println("Part 2: " + part2())



}

fun List<String>.move(wayPoint: WayPoint): WayPoint {
    for (i in this)
        wayPoint.step(i)
    return wayPoint
}

fun List<String>.move(ship: Ship): Ship {
    for (i in this)
        ship.step(i)
    return ship
}





//
//fun rotate(x:Int,y:Int,degree:Int){
//    val radian = degreeToRadians(degree)
//    val newX = x * cos(radian) - (y * sin(radian))
//    val newY = x * sin(radian) + (y * cos(radian))
//
//}
//fun degreeToRadians (degree: Int) = degree * (PI / 180)
//
//





