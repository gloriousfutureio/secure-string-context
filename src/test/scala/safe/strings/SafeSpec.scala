package safe.strings

import org.scalatest.WordSpec
import shapeless.test.illTyped

class SafeSpec extends WordSpec {

  "ss\"$value\"" should {

    "not compile when passed an unsafe type" in {
      val value = new UnsafeType
      illTyped { """ ss"$value" """}
    }

    "print a single item using the implicit Safe for that type" in {
      val value = new SafeType
      assert(ss"$value" == value.toString)
    }

    "print a sequence of items using the implicit Safe for that type in the toString of the collection" in {
      val values = Seq.fill(5)(new SafeType)
      assert(ss"$values" == values.toString)
    }

    "print a tuple of items using the implicit Safe for that type and the toString of the tuple" in {
      val values = (new SafeType, new SafeType)
      assert(ss"$values" == values.toString)
    }

    "print a subclass of an item that is implicitly AllSafe using the toString of the class" in {
      val value = new SafeChild
      assert(ss"$value" == value.toString)
    }

    "print a case class of items that are all implicitly Safe using the toString of the class" in {
      val value = SafeCaseClass(new SafeType, new AnotherSafeType)
      assert(ss"$value" == value.toString)
    }

    "print a case class of items that are both implicitly Safe and AllSafe using the toString of the class" in {
      val value = SafeCaseClassWithChild(new SafeType, new AnotherSafeType, new SafeChild)
      assert(ss"$value" == value.toString)
    }
  }
}
