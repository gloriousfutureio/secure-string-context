package me.jeffmay.secure

import me.jeffmay.secure.syntax._
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks

class SecureStringContextSpec extends FlatSpec with PropertyChecks {

  it should "print all primitives as a normal toString" in {
    forAll { (value: AnyVal) =>
      assert(secure"$value" == value.toString)
    }
  }

  it should "print an Traversable of AnyVal the same as toString" in {
    forAll { (values: Seq[AnyVal]) =>
      val traversable = Traversable(values: _*)
      assert(secure"$traversable" == traversable.toString)
    }
  }

  it should "print an Iterable of AnyVal the same as toString" in {
    forAll { (values: Seq[AnyVal]) =>
      val iterable = Iterable(values: _*)
      assert(secure"$iterable" == iterable.toString)
    }
  }

  it should "print a Seq of AnyVal the same as toString" in {
    forAll { (values: Seq[AnyVal]) =>
      val seq = Seq(values: _*)
      assert(secure"$values" == values.toString)
      assert(secure"$seq" == seq.toString)
    }
  }

  it should "print a List of AnyVal the same as toString" in {
    forAll { (values: Seq[AnyVal]) =>
      val list = List(values: _*)
      assert(secure"$list" == list.toString)
    }
  }

  it should "print a Stream of AnyVal the same as toString" in {
    forAll { (values: Seq[AnyVal]) =>
      val stream = Stream(values: _*)
      assert(secure"$stream" == stream.toString)
    }
  }

  it should "print an infinite Stream in finite time the same as toString" in {
    def streamFrom(start: Int): Stream[Int] = {
      if (start > 10) {
        val example = streamFrom(1)
        fail(s"Stream is running infinitely. It should have printed: $example")
      }
      start #:: streamFrom(start + 1)
    }
    val stream = streamFrom(1)
    assert(secure"$stream" == stream.toString)
  }
}
