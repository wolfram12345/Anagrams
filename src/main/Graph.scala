package main

import scala.collection.mutable

/**
  * Created by Andrey on 27.04.2017.
  */
class Graph {
  private var map: Map[String, Map[String, Int]] = Map()
  def add(currentString: Option[String], nextString: Option[String]): Unit ={
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

  private def seek(keyWord: String, lengthNextWord: Int): Option[String] ={
    val currMap = map.get(keyWord)
    val successLengthMap = currMap.get.filter(_._1.length == lengthNextWord)
    if(successLengthMap.nonEmpty){
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
          if(elemKeys.isDefined){
            val successLengthElem = elemKeys.get.filter(_._1.length == lengthNextWord)
            if(successLengthElem.nonEmpty){
              val currRes = successLengthElem.maxBy(_._2)
              if(currRes._2 > result._2)
                result = (Some(currRes._1), currRes._2)
            }
            else{
              elemKeys.get.keySet.foreach(str => queue.enqueue(str -> (queueElem._2 - 1)))
            }
          }
        }
      }
      result._1
    }
  }
  def getMaxProbabilityString(prevWord: Option[String], lengthNextWord: Int, beLeftSteps: Int): Option[String] = {
    if(prevWord.isEmpty){
      val keySet = map.keySet
      val wordToOut = keySet.foldLeft(None: Option[String]){(acc: Option[String], currWord: String) => {
        if(currWord.length == lengthNextWord)
          Some(currWord)
        else
          acc
      }}
      wordToOut
    }
    else{
      val wordMap = map.get(prevWord.get)
      if(wordMap.isEmpty)
        None
      else{
        seek(prevWord.get, lengthNextWord)
      }
    }
  }
}
