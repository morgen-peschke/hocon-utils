package com.peschke.hocon

import com.typesafe.config.{Config, ConfigValue, ConfigRenderOptions}
import enumeratum.{EnumEntry, Enum}

object Render {
  sealed abstract class Mode(val options: ConfigRenderOptions)
      extends EnumEntry with EnumEntry.Hyphencase

  object Mode extends Enum[Mode] {
    case object ConciseJson extends Mode(
      ConfigRenderOptions.concise.setJson(true))

    case object PrettyJson extends Mode(
      ConciseJson.options.setFormatted(true))

    case object ConciseHOCON extends Mode(
      ConfigRenderOptions
        .concise
        .setJson(false))

    case object PrettyHOCON extends Mode(
      ConfigRenderOptions
        .concise
        .setJson(false)
        .setComments(true)
        .setFormatted(true))

    override val values: scala.collection.immutable.IndexedSeq[Mode] = findValues

    implicit val read: scopt.Read[Mode] = scopt.Read.reads(Mode.withNameInsensitive)
  }

  def apply(config: Config, mode: Mode): String =
    apply(config.root, mode)

  def apply(configValue: ConfigValue, mode: Mode): String =
    configValue.render(mode.options)
}
