
class BagGraph:AdjacencyList<String>() {
    fun numOfBags(bag:String = "shiny gold") = numOfParent(bag) - 1

    private fun numOfParent(bag:String, visited : MutableSet<String> = mutableSetOf()):Int
    {
        if (visited.contains(bag))
            return 0
        visited.add(bag)

        var size = 0

        this[bag].forEach {
            size+= numOfParent(it.destination.data,visited)
        }

        return size + 1
    }
    fun numOfChildren(bag:String = "shiny gold", visited : MutableSet<String> = mutableSetOf()):Int
    {

        var size = 0
        this[bag].forEach {
            size+= it.weight + it.weight * numOfChildren(it.destination.data,visited)
        }
        return size
    }

    fun addEdge(source: String,destination:String,weight:Int){
        addEdge(source,destination ,weight,false)
    }
    fun parse(str:String) = Pair(
        Regex("""\w+ \w+""").find(str)?.value?:"",
        Regex("""(\d+) (\w+ \w+)""").findAll(str)
            .map { matchResult-> matchResult.groupValues.filterIndexed{index, _ -> index != 0 } }
            .toList()
    )
}

fun main() {

    addDate(2020,7)
    val list = readFromFile()
    val testList1 = readTestFile().buildNodeToParentGraph()


    fun test() = testList1.numOfBags()

    fun part1() = list.buildNodeToParentGraph().numOfBags()
    fun part2() = list.buildNodeToChildrenGraph().numOfChildren()

    check(test() == 4)

    println("Part 1: " + part1())
    println("Part 2: " + part2())


}

private fun List<String>.buildNodeToParentGraph() =
    BagGraph().apply {
        for(i in this@buildNodeToParentGraph)
        {
            val (mainBag,childBag) = parse(i)
            for (j in childBag)
                addEdge(j[1],mainBag,j[0].toInt())
        }

    }

private fun List<String>.buildNodeToChildrenGraph() =
    BagGraph().apply {
        for(i in this@buildNodeToChildrenGraph)
        {
            val (mainBag,childBag) = parse(i)
            for (j in childBag)
                addEdge(mainBag,j[1],j[0].toInt())
        }
    }
