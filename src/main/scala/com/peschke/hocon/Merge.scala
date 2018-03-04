package com.peschke.hocon

import scala.annotation.tailrec
import com.typesafe.config.{Config, ConfigFactory}

object Merge {
  @tailrec
  def apply(configs: Seq[Config]): Config = configs match {
    case Seq() => ConfigFactory.empty
    case Seq(single) => single
    case rest :+ c2 :+ c1 => apply(rest :+ c1.withFallback(c2).resolve)
  }
}
