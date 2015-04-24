package safe.strings

import org.scalacheck.{Gen, Arbitrary}
import org.scalacheck.Arbitrary._
import org.scalatest.WordSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class RawSpec extends WordSpec with GeneratorDrivenPropertyChecks {

  implicit val arbAny: Arbitrary[Any] = Arbitrary(Gen.oneOf(
    arbAnyVal,
    arbString,
    arbException,
    arbContainer[Set, AnyVal],
    arbContainer[Stream, AnyVal],
    Gen.const(new UnsafeType),
    Gen.const(new SafeCaseClass(new SafeType, new AnotherSafeType))
  ))

  "ss\"${raw(value)}\"" should {

    "print all values same as toString" in {

      forAll() { (value: Any) =>
        assert(ss"${raw(value)}" == value.toString)
      }
    }
  }
}
