import org.scalatra.ScalatraServlet

import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
  * Created by Andrey on 09.05.2017.
  */
class Controller extends ScalatraServlet{
  val graph = new Graph
  val pathToText : String = "C:\\Users\\Andrey\\YandexDisk\\text.txt"
  val source = Source.fromFile(pathToText, "UTF-8")
  val text = source.mkString
  source.close()
  graph.teach(text.toUpperCase.split("[^a-zA-Zа-яА-Я0-9]+"))

  get("/"){
    contentType="text/html; charset = utf-8"
    val first = params.get("number")
    if(first.isDefined){
      Try{
        BigInt(first.get)
      } match {
        case Success(num) => Source.fromResource("standart.html").mkString.replace("$result$", graph.getText(num))
        case Failure(ex) => Source.fromResource("standart.html").mkString.replace("$result$", "ошибка")
      }
    }
    else {
      Source.fromResource("standart.html").mkString.replace("$result$", "")
    }
  }
}
