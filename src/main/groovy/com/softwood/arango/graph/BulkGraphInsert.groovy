package com.softwood.arango.graph

import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase
import com.arangodb.ArangoEdgeCollection
import com.arangodb.ArangoGraph
import com.arangodb.ArangoVertexCollection
import com.arangodb.entity.BaseDocument
import com.arangodb.model.VertexCreateOptions

ArangoDB arango = new ArangoDB.Builder().build()
ArangoDatabase db = arango.db("demoGraph")
if (!db.exists()) {
    println "create demoGraph"
    db.create()
}

ArangoGraph graph = db.graph("graph")
if (!graph.exists()) {
    println "create 'graph'"
    db.createGraph("graph", [])
}

List<String> existing = graph.getVertexCollections()
println "existing graph collections are : $existing "

//add collection to graph
graph.addVertexCollection("Organisations")
ArangoVertexCollection orgsColl = graph.vertexCollection("Organisations")

//graph.a.addEdgeCollection("parent")
//graph.edgeCollection("parent")

BaseDocument vertx = new BaseDocument()
vertx.addAttribute('id', 1)
vertx.addAttribute('name', "vodafone")

println "created vertx"
assert orgsColl.name() == 'Organisations'
def rec = orgsColl.insertVertex(vertx, new VertexCreateOptions())


println "inserted vertx ${rec.dump()}"
System.exit(0)

