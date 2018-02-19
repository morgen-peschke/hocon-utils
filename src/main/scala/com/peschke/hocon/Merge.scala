package com.peschke.hocon

import com.typesafe.config.{Config, ConfigFactory}

object Merge {
  def apply(configs: Seq[Config]): Config = configs match {
    case Seq() => ConfigFactory.empty
    case head +: rest => rest.foldLeft(head)(_ withFallback _)
  }
}
