open class AdjacencyList<T : Any> {
    protected data class Vertex<T>(val index: Int, val data: T)
    private data class Edge<T>(val source: Vertex<T>, val destination: Vertex<T>, val weight: Int)

    private val adjacencyMap = mutableMapOf<Vertex<T>, ArrayList<Edge<T>>>()
    val size get() = adjacencyMap.size
    private val _listOfRoutes = mutableMapOf<String,Int>()
    val listOfRoutes : Map<String,Int>
        get() {
            getRoutes()
            return _listOfRoutes
        }

    private fun getVertex(data: T): Vertex<T> {
        adjacencyMap.keys.forEach {
            if (it.data == data)
                return it
        }
        return newVertex(data)
    }
    private fun newVertex(data: T): Vertex<T> {
        val vertex = Vertex(adjacencyMap.count(), data)
        adjacencyMap[vertex] = arrayListOf()
        return vertex
    }


    private fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Int){
        val edge = Edge(source, destination, weight)
        adjacencyMap[source]?.add(edge)
    }

    fun addUndirectedEdge(source: T, destination: T, weight: Int) {
        val s = getVertex(source)
        val d = getVertex(destination)
        addDirectedEdge(s,d,weight)
        addDirectedEdge(d,s,weight)
    }



    override fun toString(): String {
        val maxLength = adjacencyMap.keys.maxOf { (it.data as String).length }
        return buildString {
            adjacencyMap.forEach { (vertex, edges) ->
                val edgeString = edges.joinToString(separator = " | "){
                    String.format("%-${maxLength}s",it.destination.data.toString()) + " =>  " + String.format("%-6s",it.weight.toString())
                }
                append(String.format("%-${maxLength}s %-6s","${vertex.data}"," -> ") + "[$edgeString]\n")
            }
        }
    }

    private operator fun get(source: T): ArrayList<Edge<T>>{
        val s = getVertex(source)
        val array = ArrayList<Edge<T>>()
        adjacencyMap[s]?.forEach {
            array.add(it)
        }
        return array
    }
}