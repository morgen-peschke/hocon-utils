package com.peschke.hocon

import cats.Show
import cats.syntax.show._
import monocle.macros.GenLens
import monocle.macros.syntax.lens._
import com.typesafe.config.ConfigFactory
import java.io.File
import scopt._

case class CliOpts(
  actions: Vector[Action] = CliOpts.DefaultActions,
  renderMode: Render.Mode = CliOpts.DefaultRenderMode)

object CliOpts {
  val DefaultActions: Vector[Action] = Vector.empty
  val DefaultRenderMode: Render.Mode = Render.Mode.ConciseJson

  implicit val show: Show[CliOpts] = Show.show { cliOpts =>
    s"""|CliOpts(
        |  renderMode = ${cliOpts.renderMode.entryName},
        |  actions = Vector(\n""".stripMargin +
      cliOpts.actions.map(_.show).mkString(",\n").split("\n").map("  " + _).mkString("\n") + "\n)"
  }

  def parse(args: Seq[String]): Option[CliOpts] =
    parser
      .parse(args, CliOpts())
      .map { cliOpts =>
        cliOpts.actions.lastOption.fold(cliOpts) {
          case Action.Print | Action.Query(_) => cliOpts
          case _ => addAction(Action.Print)(cliOpts)
        }
      }

  def addAction(a: Action) = GenLens[CliOpts](_.actions).modify(_ :+ a)

  val parser: OptionParser[CliOpts] =
    new OptionParser[CliOpts]("hocon-utils") {
      opt[Seq[File]]('c', "config")
        .required
        .unbounded
        .valueName("<file>")
        .text("Add additional config files. Later files take priority over files earlier on the command line")
        .action { (cs, opts) =>
          addAction(Action.Combine(cs.map(ConfigFactory.parseFileAnySyntax(_))))(opts)
        }

      opt[Seq[String]]('q', "query")
        .unbounded
        .valueName("<path>")
        .text("Query and print the config at path, resolved using the configs previously passed.")
        .action { (ps, opts) =>
          addAction(Action.Query(ps))(opts)
        }

      opt[Unit]('p', "load-properties")
        .unbounded
        .text("Load system properties, priority is set by the flag position on the command line.")
        .action((_, opts) => addAction(Action.LoadProperties)(opts))

      opt[Unit]('e', "load-env")
        .unbounded
        .text("Load environment variables, priority is set by the flag position on the command line.")
        .action((_, opts) => addAction(Action.LoadEnvironment)(opts))

      note("")

      opt[Unit]('s', "show")
        .unbounded
        .text("Print the current state of aggregated configs")
        .action((_, opts) => addAction(Action.Print)(opts))

      opt[Render.Mode]('f', "format")
        .optional
        .text(s"Format used to display configs, defaults to ${DefaultRenderMode.entryName} one of: " +
          Render.Mode.values.map(_.entryName).mkString(" ") + " - this is a global setting.")
        .action((m, opts) => opts.lens(_.renderMode).set(m))

      note("")
      help("help")

      override def showUsageOnError = true
    }
}

object Cli extends App {
  CliOpts.parse(args).foreach {
    case CliOpts(actions, renderMode) => Action.interpret(actions, renderMode, println(_))
  }
}
