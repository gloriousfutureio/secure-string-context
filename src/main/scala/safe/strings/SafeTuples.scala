package safe.strings

import scala.language.higherKinds

/**
 * Provides implicit conversion from [[SafeToString]] instances into [[SafeToString]] tuples of all sizes.
 */
trait SafeTuples[S[T] <: SafeToString[T]] {
  self: SafeBuilders[S] =>

  private[this] def stringifyTuple(values: Array[String]): String = values.mkString("(", ",", ")")

  implicit def SafeTuple2[A1: S, A2: S]: S[(A1, A2)] = {
    using { value =>
      stringifyTuple(Array(
        string(value._1), string(value._2)
      ))
    }
  }

  implicit def SafeTuple3[A1: S, A2: S, A3: S]: S[(A1, A2, A3)] = {
    using { value =>
      stringifyTuple(Array(
        string(value._1), string(value._2), string(value._3)
      ))
    }
  }

  implicit def SafeTuple4[A1: S, A2: S, A3: S, A4: S]: S[(A1, A2, A3, A4)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1), string(value._2), string(value._3), string(value._4)
        )
      )
    }
  }

  implicit def SafeTuple5[A1: S, A2: S, A3: S, A4: S, A5: S]: S[(A1, A2, A3, A4, A5)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1), string(value._2), string(value._3), string(value._4),
          string(value._5)
        )
      )
    }
  }

  implicit def SafeTuple6[A1: S, A2: S, A3: S, A4: S, A5: S, A6: S]: S[(A1, A2, A3, A4, A5, A6)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1), string(value._2), string(value._3), string(value._4),
          string(value._5), string(value._6)
        )
      )
    }
  }

  implicit def SafeTuple7[A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S]: S[(A1, A2, A3, A4, A5, A6, A7)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1), string(value._2), string(value._3), string(value._4),
          string(value._5), string(value._6), string(value._7)
        )
      )
    }
  }

  implicit def SafeTuple8[A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S]: S[(A1, A2, A3, A4, A5, A6, A7, A8)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1), string(value._2), string(value._3), string(value._4),
          string(value._5), string(value._6), string(value._7), string(value._8)
        )
      )
    }
  }

  implicit def SafeTuple9[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1), string(value._2), string(value._3), string(value._4),
          string(value._5), string(value._6), string(value._7), string(value._8),
          string(value._9)
        )
      )
    }
  }

  implicit def SafeTuple10[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1), string(value._2), string(value._3), string(value._4),
          string(value._5), string(value._6), string(value._7), string(value._8),
          string(value._9), string(value._10)
        )
      )
    }
  }

  implicit def SafeTuple11[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S, A11: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1), string(value._2),  string(value._3), string(value._4),
          string(value._5), string(value._6),  string(value._7), string(value._8),
          string(value._9), string(value._10), string(value._11)
        )
      )
    }
  }

  implicit def SafeTuple12[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S, A11: S,
      A12: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1), string(value._2),  string(value._3),  string(value._4),
          string(value._5), string(value._6),  string(value._7),  string(value._8),
          string(value._9), string(value._10), string(value._11), string(value._12)
        )
      )
    }
  }

  implicit def SafeTuple13[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S, A11: S,
      A12: S, A13: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1), string(value._2),  string(value._3),  string(value._4),
          string(value._5), string(value._6),  string(value._7),  string(value._8),
          string(value._9), string(value._10), string(value._11), string(value._12),
          string(value._13)
        )
      )
    }
  }

  implicit def SafeTuple14[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S, A11: S,
      A12: S, A13: S, A14: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1),  string(value._2),  string(value._3),  string(value._4),
          string(value._5),  string(value._6),  string(value._7),  string(value._8),
          string(value._9),  string(value._10), string(value._11), string(value._12),
          string(value._13), string(value._14)
        )
      )
    }
  }

  implicit def SafeTuple15[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S, A11: S,
      A12: S, A13: S, A14: S, A15: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1),  string(value._2),  string(value._3),  string(value._4),
          string(value._5),  string(value._6),  string(value._7),  string(value._8),
          string(value._9),  string(value._10), string(value._11), string(value._12),
          string(value._13), string(value._14), string(value._15)
        )
      )
    }
  }

  implicit def SafeTuple16[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S, A11: S,
      A12: S, A13: S, A14: S, A15: S, A16: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1),  string(value._2),  string(value._3),  string(value._4),
          string(value._5),  string(value._6),  string(value._7),  string(value._8),
          string(value._9),  string(value._10), string(value._11), string(value._12),
          string(value._13), string(value._14), string(value._15), string(value._16)
        )
      )
    }
  }

  implicit def SafeTuple17[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S, A11: S,
      A12: S, A13: S, A14: S, A15: S, A16: S, A17: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1),  string(value._2),  string(value._3),  string(value._4),
          string(value._5),  string(value._6),  string(value._7),  string(value._8),
          string(value._9),  string(value._10), string(value._11), string(value._12),
          string(value._13), string(value._14), string(value._15), string(value._16),
          string(value._17)
        )
      )
    }
  }

  implicit def SafeTuple18[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S, A11: S,
      A12: S, A13: S, A14: S, A15: S, A16: S, A17: S, A18: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1),  string(value._2),  string(value._3),  string(value._4),
          string(value._5),  string(value._6),  string(value._7),  string(value._8),
          string(value._9),  string(value._10), string(value._11), string(value._12),
          string(value._13), string(value._14), string(value._15), string(value._16),
          string(value._17), string(value._18)
        )
      )
    }
  }

  implicit def SafeTuple19[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S, A11: S,
      A12: S, A13: S, A14: S, A15: S, A16: S, A17: S, A18: S, A19: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1),  string(value._2),  string(value._3),  string(value._4),
          string(value._5),  string(value._6),  string(value._7),  string(value._8),
          string(value._9),  string(value._10), string(value._11), string(value._12),
          string(value._13), string(value._14), string(value._15), string(value._16),
          string(value._17), string(value._18), string(value._19)
        )
      )
    }
  }

  implicit def SafeTuple20[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S, A11: S,
      A12: S, A13: S, A14: S, A15: S, A16: S, A17: S, A18: S, A19: S, A20: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1),  string(value._2),  string(value._3),  string(value._4),
          string(value._5),  string(value._6),  string(value._7),  string(value._8),
          string(value._9),  string(value._10), string(value._11), string(value._12),
          string(value._13), string(value._14), string(value._15), string(value._16),
          string(value._17), string(value._18), string(value._19), string(value._20)
        )
      )
    }
  }

  implicit def SafeTuple21[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S, A11: S,
      A12: S, A13: S, A14: S, A15: S, A16: S, A17: S, A18: S, A19: S, A20: S, A21: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1),  string(value._2),  string(value._3),  string(value._4),
          string(value._5),  string(value._6),  string(value._7),  string(value._8),
          string(value._9),  string(value._10), string(value._11), string(value._12),
          string(value._13), string(value._14), string(value._15), string(value._16),
          string(value._17), string(value._18), string(value._19), string(value._20),
          string(value._21)
        )
      )
    }
  }

  implicit def SafeTuple22[
      A1: S, A2: S, A3: S, A4: S, A5: S, A6: S, A7: S, A8: S, A9: S, A10: S, A11: S,
      A12: S, A13: S, A14: S, A15: S, A16: S, A17: S, A18: S, A19: S, A20: S, A21: S, A22: S
      ]: S[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22)] = {
    using { value =>
      stringifyTuple(
        Array(
          string(value._1),  string(value._2),  string(value._3),  string(value._4),
          string(value._5),  string(value._6),  string(value._7),  string(value._8),
          string(value._9),  string(value._10), string(value._11), string(value._12),
          string(value._13), string(value._14), string(value._15), string(value._16),
          string(value._17), string(value._18), string(value._19), string(value._20),
          string(value._21), string(value._22)
        )
      )
    }
  }
}
