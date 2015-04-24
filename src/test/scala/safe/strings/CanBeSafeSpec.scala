package safe.strings

import org.scalacheck.Arbitrary
import org.scalatest.WordSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import shapeless.test.illTyped

class CanBeSafeSpec extends WordSpec with GeneratorDrivenPropertyChecks {

  "ss\"${safe(value)}\"" should {

    "not compile when passed an unsafe type" in {
      val value = new UnsafeType
      illTyped { """ ss"${safe(value)}" """}
    }

    "compile when passed an CanBeSafe safe type" in {
      val value = new CanBeSafeType
      assert(ss"${safe(value)}" == value.toString)
    }

    "compile when passed a subclass of an AllCanBeSafe safe type" in {
      val value = new CanBeSafeChild
      assert(ss"${safe(value)}" == value.toString)
    }

    "compile when passed a Safe type" in {
      val value = new SafeType
      assert(ss"${safe(value)}" == value.toString)
    }

    s"print all AnyVal primitives equal to toString" in {
      forAll { (value: AnyVal) =>
        assert(ss"${safe(value)}" == value.toString)
      }
    }

    s"print all Strings equal to themselves" in {
      forAll { (value: String) =>
        assert(ss"${safe(value)}" == value)
      }
    }

    s"print all Numbers equal to themselves" in {
      forAll { (value: Number) =>
        assert(ss"${safe(value)}" == value.toString)
      }
    }

    // Copied from ImplicitlySafeSpec, but using safe keyword

    "print a single item using the implicit Safe for that type" in {
      val value = new SafeType
      assert(ss"${safe(value)}" == value.toString)
    }

    "print a sequence of items using the implicit Safe for that type in the toString of the collection" in {
      val values = Seq.fill(5)(new SafeType)
      assert(ss"${safe(values)}" == values.toString)
    }

    "print a tuple of items using the implicit Safe for that type and the toString of the tuple" in {
      val values = (new SafeType, new SafeType)
      assert(ss"${safe(values)}" == values.toString)
    }

    "print a subclass of an item that is implicitly AllSafe using the toString of the class" in {
      val value = new SafeChild
      assert(ss"${safe(value)}" == value.toString)
    }

    "print a case class of items that are all implicitly Safe using the toString of the class" in {
      val value = SafeCaseClass(new SafeType, new AnotherSafeType)
      assert(ss"${safe(value)}" == value.toString)
    }

    "print a case class of items that are both implicitly Safe and AllSafe using the toString of the class" in {
      val value = SafeCaseClassWithChild(new SafeType, new AnotherSafeType, new SafeChild)
      assert(ss"${safe(value)}" == value.toString)
    }

    behave like itPrintsCollectionsOf[AnyVal]("AnyVal")

    behave like itPrintsCollectionsOf[String]("String")

    behave like itPrintsCollectionsOf[Number]("Number")

    "print an infinite Stream in finite time; the same as Stream.toString" in {
      def streamFrom(start: Int): Stream[Int] = {
        if (start > 10) {
          val example = streamFrom(1)
          fail(s"Stream is running infinitely. It should have printed: $example")
        }
        start #:: streamFrom(start + 1)
      }
      val stream = streamFrom(1)
      assert(ss"${safe(stream)}" == stream.toString)
    }
  }

  def itPrintsCollectionsOf[T: Arbitrary: CanBeSafe](tpe: String): Unit = {

    s"print all Traversables of $tpe the same as toString" in {
      forAll { (values: Seq[T]) =>
        val traversable = Traversable(values: _*)
        assert(ss"${safe(traversable)}" == traversable.toString)
      }
    }

    s"print all Iterables of $tpe the same as toString" in {
      forAll { (values: Seq[T]) =>
        val iterable = Iterable(values: _*)
        assert(ss"${safe(iterable)}" == iterable.toString)
      }
    }

    s"print all Seqs of $tpe the same as toString" in {
      forAll { (values: Seq[T]) =>
        val seq = Seq(values: _*)
        assert(ss"${safe(values)}" == values.toString)
        assert(ss"${safe(seq)}" == seq.toString)
      }
    }

    s"print all Lists of $tpe the same as toString" in {
      forAll { (values: Seq[T]) =>
        val list = List(values: _*)
        assert(ss"${safe(list)}" == list.toString)
      }
    }

    s"print all Streams of $tpe the same as toString" in {
      forAll { (values: Seq[T]) =>
        val stream = Stream(values: _*)
        assert(ss"${safe(stream)}" == stream.toString)
      }
    }
  }
}
