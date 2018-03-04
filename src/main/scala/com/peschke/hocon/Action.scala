package com.peschke.hocon

import scala.annotation.tailrec
import cats.Show
import com.typesafe.config.{Config, ConfigFactory}

sealed trait Action
object Action {
  case class Combine(configs: Seq[Config]) extends Action
  case class Query(paths: Seq[String]) extends Action
  case object LoadProperties extends Action
  case object LoadEnvironment extends Action
  case object Print extends Action

  implicit val show: Show[Action] = Show.show {
    case Combine(configs) =>
      configs.mkString(
        "Combine(",
        ",\n        ",
        "\n)")
    case Query(paths) =>
      paths.mkString(
        "Query(",
        ",\n      ",
        "\n)")
    case a @ (LoadProperties | LoadEnvironment | Print) => a.toString
  }

  def interpret(actions: Seq[Action], mode: Render.Mode, print: String => Unit): Unit = {
    @tailrec
    def loop(actions: Seq[Action], config: Config): Unit =
      actions match {
        case Seq() => ()
        case current +: rest => current match {
          case Combine(configs) => loop(rest, Merge(configs).withFallback(config).resolve)
          case Query(queries) =>
            queries.map(config.getValue).map(Render(_, mode)).foreach(print)
            loop(rest, config)
          case LoadProperties =>
            loop(rest, ConfigFactory.systemProperties.withFallback(config))
          case LoadEnvironment =>
            loop(rest, ConfigFactory.systemEnvironment.withFallback(config))
          case Print =>
            print(Render(config, mode))
            loop(rest, config)
        }
      }

    loop(actions, ConfigFactory.empty)
  }
}
