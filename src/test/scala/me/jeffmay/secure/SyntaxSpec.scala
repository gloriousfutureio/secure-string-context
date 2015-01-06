package me.jeffmay.secure

import me.jeffmay.secure.syntax._
import org.scalatest.FunSpec
import shapeless.test.illTyped

class SyntaxSpec extends FunSpec {

  import me.jeffmay.secure.SyntaxSpec._

  describe("using the secure string interpolator") {

    it("should not compile if type is not a member of the printable typeclass") {
      illTyped { """ secure"$CustomType" """}
    }

    it("should print a single item if the type has an implicit printable") {
      val value = new PrintableType
      assert(secure"$value" == value.toString)
    }

    it("should print a sequence of items if the type has an implicit printable") {
      val values = Seq.fill(5)(new PrintableType)
      assert(secure"$values" == values.toString)
    }
  }
}

object SyntaxSpec {

  class CustomType
  object CustomType extends CustomType

  class PrintableType
  object PrintableType {
    implicit lazy val printable: SecurePrinter[PrintableType] = SecurePrinter.usingToString
  }
}
