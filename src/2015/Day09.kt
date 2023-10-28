class Graph: AdjacencyList<String>() {
    private val _listOfRoutes = mutableMapOf<String,Int>()
    val listOfRoutes : Map<String,Int>
        get() {
            getRoutes()
            return _listOfRoutes
        }

    private fun getRoutes() =
        this.adjacencyMap.forEach { getRoutes(it.key, mutableSetOf(),0)}
    private fun getRoutes(source: Vertex<String>, visited: MutableSet<String>, weight: Int){
        val data = source.data

        if (visited.contains(data)) return
        visited.add(data)

        if (visited.size == adjacencyMap.size)
        {
            _listOfRoutes[visited.toString()] = weight
            return
        }
        adjacencyMap[source]?.forEach {
            getRoutes(it.destination, mutableSetOf<String>().apply { addAll(visited) },weight + it.weight)
        }
    }
}
fun main(){
    addDate(2015,9)

    val testGraph = readTestFile().buildGraph()
    val graph = readFromFile().buildGraph()

    fun test() = testGraph.listOfRoutes.minOf { it.value }
    fun part1() = graph.listOfRoutes.minOf { it.value }
    fun part2() = graph.listOfRoutes.maxOf { it.value }

    check(test() == 605)

    println("Part 1: " + part1())
    println("Part 2: " + part2())
}



private fun List<String>.buildGraph() =
    Graph().apply {
        forEach {
            val line = it.splitToSequence(" to "," = ")
            val (source,destination,weight) = Triple(line.elementAt(0),line.elementAt(1), line.elementAt(2).toInt())
            addUndirectedEdge(source, destination, weight)
        }
    }



