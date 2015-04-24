package safe.strings.play

import play.api.libs.functional.ContravariantFunctor
import safe.strings.{AllSafe, CanBeSafe, Safe}

package object functional {

  /**
   * [[CanBeSafe]] is a Play functional [[ContravariantFunctor]].
   *
   * This allows the syntax `val printerB: SecurePrinter[B] = printerA.contramap(f: B => A)` when
   * [[play.api.libs.functional.syntax]] is imported.
   */
  implicit object CanBeSafeContravariantFunctor extends ContravariantFunctor[CanBeSafe] {
    override def contramap[A, B](safe: CanBeSafe[A], f: B => A): CanBeSafe[B] = {
      new CanBeSafe[B] {
        override def stringify(value: B): String = safe.stringify(f(value))
      }
    }
  }

  /**
   * [[Safe]] is a Play functional [[ContravariantFunctor]].
   *
   * This allows the syntax `val printerB: SecurePrinter[B] = printerA.contramap(f: B => A)` when
   * [[play.api.libs.functional.syntax]] is imported.
   */
  implicit object SafeContravariantFunctor extends ContravariantFunctor[Safe] {
    override def contramap[A, B](safe: Safe[A], f: B => A): Safe[B] = {
      new Safe[B] {
        override def stringify(value: B): String = safe.stringify(f(value))
      }
    }
  }

  /**
   * [[Safe]] is a Play functional [[ContravariantFunctor]].
   *
   * This allows the syntax `val printerB: SecurePrinter[B] = printerA.contramap(f: B => A)` when
   * [[play.api.libs.functional.syntax]] is imported.
   */
  implicit object AllSafeContravariantFunctor extends ContravariantFunctor[AllSafe] {
    override def contramap[A, B](safe: AllSafe[A], f: B => A): AllSafe[B] = {
      new AllSafe[B] {
        override def stringify(value: B): String = safe.stringify(f(value))
      }
    }
  }
}
