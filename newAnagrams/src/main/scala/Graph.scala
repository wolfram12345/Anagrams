import scala.collection.mutable

/**
  * Created by Andrey on 27.04.2017.
  */
class Graph {
  private var map: Map[String, Map[String, Int]] = Map()
  private def add(currentString: Option[String], nextString: Option[String]): Unit ={
    if(currentString.isDefined){
      val mapValueOption = map.get(currentString.get)
      if(mapValueOption.isDefined){
        val mapValue = mapValueOption.get
        if(nextString.isDefined){
          val countOption = mapValue.get(nextString.get)
          if(countOption.isDefined){
            val count = countOption.get
            map = map + (currentString.get -> (mapValue + (nextString.get -> (count + 1))))
          }
          else{
            map = map + (currentString.get -> (mapValue + (nextString.get -> 1)))
          }
        }
        else{
          map = map + (currentString.get -> Map())
        }
      }
      else{
        if(nextString.isDefined){
          map = map + (currentString.get -> Map(nextString.get -> 1))
        }
        else
          map = map + (currentString.get -> Map())
      }
    }
  }

  private def seek(keyWord: String, lengthNextWord: BigInt): Option[String] ={
    if(lengthNextWord == 0)
      Some("нуль")
    else {
      val currMap = map.get(keyWord)
      val successLengthMap = currMap.get.filter(_._1.length == lengthNextWord)
      if (successLengthMap.nonEmpty) {
        Some(successLengthMap.maxBy(_._2)._1)
      }
      else {
        val keySet = currMap.get.keySet
        var queue = new mutable.Queue[(String, Int)]
        var result = (None: Option[String], 0)
        keySet.foreach(str => queue.enqueue(str -> map.size))
        while (queue.nonEmpty) {
          val queueElem = queue.dequeue
          if (queueElem._2 > 0) {
            val elemKeys = map.get(queueElem._1)
            if (elemKeys.isDefined) {
              val successLengthElem = elemKeys.get.filter(_._1.length == lengthNextWord)
              if (successLengthElem.nonEmpty) {
                val currRes = successLengthElem.maxBy(_._2)
                if (currRes._2 > result._2)
                  result = (Some(currRes._1), currRes._2)
              }
              else {
                elemKeys.get.keySet.foreach(str => queue.enqueue(str -> (queueElem._2 - 1)))
              }
            }
          }
        }
        result._1
      }
    }
  }
  def getMaxProbabilityString(prevWord: Option[String], lengthNextWord: BigInt, beLeftSteps: Int): Option[String] = {
    if(lengthNextWord == 0)
      Some("нуль")
    else {
      if (prevWord.isEmpty) {
        val keySet = map.keySet
        val wordToOut = keySet.foldLeft(None: Option[String]) { (acc: Option[String], currWord: String) => {
          if (currWord.length == lengthNextWord)
            Some(currWord)
          else
            acc
        }
        }
        wordToOut
      }
      else {
        val wordMap = map.get(prevWord.get)
        if (wordMap.isEmpty)
          None
        else {
          seek(prevWord.get, lengthNextWord)
        }
      }
    }
  }

  private def numbers(number: BigInt, list: List[BigInt]): List[BigInt] = {
    val outList = List(number % 10) ++ list
    if(number / 10 > 0)
      numbers(number / 10, outList)
    else
      outList
  }

  def teach(words: Seq[String]): Unit ={
    for(i <- 0 until words.size - 1){
      add(Some(words(i).toUpperCase), Some(words(i + 1).toUpperCase))
    }
  }

  def getText(number: BigInt): String = {
    val listOfNumbers = numbers(number, List())
    if (listOfNumbers.contains(0))
      "Ошибка"
    else {
      val arr = listOfNumbers.foldLeft(List[String]()) { (acc, num) =>
        val nextWord = getMaxProbabilityString(acc.lastOption, num, map.size)
        if (nextWord.isDefined) {
          acc :+ nextWord.get
        }
        else
          List("-")
      }
      if (arr.contains("-"))
        "Ошибка. Нуль."
      else {
        arr.foldLeft("") { (res, value) => res + value + " " }
      }
    }
  }
}
