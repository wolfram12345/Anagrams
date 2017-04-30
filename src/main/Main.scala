package main

/**
  * Created by Andrey on 07.04.2017.
  */
object Main extends App{
  var graph = new Graph
  graph.add(Some("a"), Some("b"))
  graph.add(Some("b"), Some("c"))
  graph.add(Some("c"), None)
  graph.add(Some("b"), Some("p"))
  graph.add(Some("b"), Some("p"))
  println("Hello, world!")
}
