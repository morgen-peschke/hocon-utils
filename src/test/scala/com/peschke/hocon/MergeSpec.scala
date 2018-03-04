package com.peschke.hocon

import com.typesafe.config.ConfigFactory
import org.scalatest.{WordSpec, MustMatchers}

class MergeSpec extends WordSpec with MustMatchers with ConfigHelpers {
  "Merge" when {
    "passed an empty sequence" should {
      "return an empty config" in {
        Merge(Seq.empty) mustBe ConfigFactory.empty
      }
    }

    "passed a single config" should {
      "return an equivalent config" in {
        val input = config {
          """|a: 1
             |b {
             |  a: "2"
             |  b: true
             |  c: ${a}
             |}""".stripMargin
        }
        Merge(Seq(input)) mustBe input
      }
    }

    "passed multiple configs" should {
      "return a merged config" in {
        val input = Seq(
          config("b.c: ${a}"),
          config("b.b: true"),
          config("b.a: \"2\""),
          config("a: 1"))

        val merged = config {
          """|a: 1
             |b {
             |  a: "2"
             |  b: true
             |  c: 1
             |}""".stripMargin
        }
        Merge(input) mustBe merged
      }

      "resolve references correctly" in {
        val input = Seq(
          config("b.c: ${a}"),
          config("b.a: \"2\""),
          config("b.b: true"),
          config("a: 1"))

        Merge(input) mustBe config {
          """|a: 1
             |b {
             |  a: "2"
             |  b: true
             |  c: 1
             |}""".stripMargin
        }
      }

      "prefer later values" in {
        val input = Seq(
          config("a: 1"),
          config("b.a: true"),
          config("a: 2")
        )
        Merge(input) mustBe config {
          """|a: 2
             |b.a: true""".stripMargin
        }
      }
    }
  }
}
