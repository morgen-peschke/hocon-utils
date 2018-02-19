package com.peschke.hocon

import com.typesafe.config.{Config, ConfigFactory}

trait ConfigHelpers {
  def config(contents: String): Config =
    ConfigFactory.parseString(contents)
}
