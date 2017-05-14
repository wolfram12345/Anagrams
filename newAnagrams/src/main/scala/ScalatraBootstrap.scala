import javax.servlet.ServletContext

import org.scalatra.LifeCycle

import scala.io.Source

/**
  * Created by Andrey on 09.05.2017.
  */
class ScalatraBootstrap extends LifeCycle{
  override def init(context: ServletContext){
//    val graph = new Graph
//    val pathToText : String = "C:\\Users\\Andrey\\YandexDisk\\text.txt"
//    val source = Source.fromFile(pathToText, "UTF-8")
//    val text = source.mkString
//    source.close()
//    graph.teach(text.toUpperCase.split("[^a-zA-Zа-яА-Я0-9]+"))
    context.mount(new Controller, "")
  }
}
