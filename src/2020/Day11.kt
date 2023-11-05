
fun main() {

    addDate(2020,11)

    val list = readFromFile()
    val testList = readTestFile()

    val grid = createGrid('0',list.size,list.first().length).fillGrid(list)
    val testGrid = createGrid('0',testList.size,testList.first().length).fillGrid(testList)


    fun test() = testGrid.noSeatsChangeStateNum(1)

    fun part1() = grid.noSeatsChangeStateNum(1)
    fun part2() = grid.noSeatsChangeStateNum(2)

    check(test() == 37)

    println("Part 1: " + part1())
    println("Part 2: " + part2())

}


private fun Array<Array<Char>>.noSeatsChangeStateNum(part: Int) : Int{
    var prev: Int
    var current = 0
    var arr = this
    do {
        arr = arr.fillSeat(part)
        prev = current
        current = arr.countOccupiedSeats()
    }
    while (prev != current)

    return current
}


private fun Array<Array<Char>>.countOccupiedSeats() = sumOf { it.count { char-> char == '#' } }


private fun Array<Array<Char>>.fillSeat(part: Int): Array<Array<Char>>{
    val occupiedLimit = if (part == 1) 3 else 4
    val array = Array(size){this[it].copyOf()}
    for ((row,arr) in withIndex())
        for ((col,char) in arr.withIndex())
        {
            val occupiedSeats = if (part == 1) checkAdjacentOccupiedSeats(row,col)
            else numOccupiedSeat(row,col)
            if (char == 'L'&& occupiedSeats == 0)
                array[row][col] = '#'
            else if (char == '#' && occupiedSeats > occupiedLimit)
                array[row][col] = 'L'
        }
    return array
}


private fun Array<Array<Char>>.checkAdjacentOccupiedSeats (row:Int,col:Int) =
    checkOccupiedSeat(row-1,col-1) +
            checkOccupiedSeat(row-1,col) +
            checkOccupiedSeat(row-1,col+1) +
            checkOccupiedSeat(row,col-1) +
            checkOccupiedSeat(row,col+1) +
            checkOccupiedSeat(row+1,col-1) +
            checkOccupiedSeat(row+1,col) +
            checkOccupiedSeat(row+1,col+1)

private fun Array<Array<Char>>.checkOccupiedSeat(row:Int,col:Int) : Int{
    val rowBound = row in indices
    val colBound= col in first().indices
    if (rowBound && colBound && this[row][col] == '#')
        return 1
    return 0
}


private fun List<Char>.findUtilOccupied():Int{
    for (i in this)
        if (i == '#')
            return 1
        else if (i == 'L')
            return 0
    return 0
}

private fun Array<Array<Char>>.numOccupiedSeat(row:Int,col:Int): Int {
    var numSeats = 0

    val list = mutableListOf<List<Char>>().apply {
        addAll(getXAxis(row,col))
        addAll(getYAxis(row,col))
        addAll(getLeftDiagonal(row,col))
        addAll(getRightDiagonal(row,col))
    }
    list.forEach {
        numSeats+=it.findUtilOccupied()
    }

    return numSeats

}


fun Array<Array<Char>>.getXAxis(row: Int,col: Int):List<List<Char>> =
    listOf(
        (col-1 downTo 0).map { this[row][it]},
        (col+1 ..first().lastIndex ).map { this[row][it] }
    )
fun Array<Array<Char>>.getYAxis(row: Int,col: Int):List<List<Char>> =
    listOf(
        (row-1 downTo 0).map { this[it][col]},
        (row+1 ..lastIndex ).map { this[it][col] }
    )

fun Array<Array<Char>>.getLeftDiagonal(row: Int,col: Int):List<List<Char>>{
    val up = mutableListOf<Char>()
    for (i in 1 .. lastIndex)
    {
        if ((row - i) < 0 || (col - i) < 0)
            break
        up.add(this[row - i][col - i])
    }
    val down = mutableListOf<Char>()
    for (i in 1 .. lastIndex)
    {
        if ((row + i) > lastIndex || (col + i) > first().lastIndex)
            break
        down.add(this[row + i][col + i])
    }

    return listOf(up,down)
}

fun Array<Array<Char>>.getRightDiagonal(row: Int,col: Int):List<List<Char>>{
    val up = mutableListOf<Char>()
    for (i in 1 .. lastIndex)
    {
        if ((row - i) < 0 || (col + i) > first().lastIndex)
            break
        up.add(this[row - i][col + i])
    }
    val down = mutableListOf<Char>()
    for (i in 1 .. lastIndex)
    {
        if ((row + i) > lastIndex || (col - i) < 0)
            break
        down.add(this[row + i][col - i])
    }

    return listOf(up,down)
}





/**
 * numOccupiedSeat optimized
 * */
//
//private fun Array<Array<Char>>.numOccupiedSeat(row:Int,col:Int): Int {
//    var numSeats = 0
//
//    (1..size).any {i->
//        val num = checkDirectionOccupiedSeat(row-i,col-i)
//        numSeats+= if (num != -1) num else 0
//        num != -1
//    }
//
//    (1..size).any {i->
//        val num = checkDirectionOccupiedSeat(row-i,col)
//        numSeats+= if (num != -1) num else 0
//        num != -1
//    }
//    (1..size).any {i->
//        val num = checkDirectionOccupiedSeat(row-i,col+i)
//        numSeats+= if (num != -1) num else 0
//        num != -1
//    }
//    (1..size).any {i->
//        val num = checkDirectionOccupiedSeat(row,col-i)
//        numSeats+= if (num != -1) num else 0
//        num != -1
//    }
//    (1..size).any {i->
//        val num = checkDirectionOccupiedSeat(row,col+i)
//        numSeats+= if (num != -1) num else 0
//        num != -1
//    }
//    (1..size).any {i->
//        val num = checkDirectionOccupiedSeat(row+i,col-i)
//        numSeats+= if (num != -1) num else 0
//        num != -1
//    }
//    (1..size).any {i->
//        val num = checkDirectionOccupiedSeat(row+i,col)
//        numSeats+= if (num != -1) num else 0
//        num != -1
//    }
//    (1..size).any {i->
//        val num = checkDirectionOccupiedSeat(row+i,col+i)
//        numSeats+= if (num != -1) num else 0
//        num != -1
//    }
//
//    return  numSeats
//
//}
//
//
//private fun Array<Array<Char>>.checkDirectionOccupiedSeat(row:Int,col:Int): Int {
//    val rowBound = row in indices
//    val colBound= col in first().indices
//    if (!rowBound || !colBound || this[row][col] =='L')
//        return 0
//    if (this[row][col] == '#')
//        return 1
//    return -1
//}
