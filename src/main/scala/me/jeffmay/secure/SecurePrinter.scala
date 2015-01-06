package me.jeffmay.secure

import play.api.libs.functional.ContravariantFunctor

import scala.annotation.implicitNotFound
import scala.language.{higherKinds, implicitConversions}

/**
 * A typeclass of securely printable values.
 *
 * Only primitive values and values that are marked as securely printable by default.
 *
 * You can extend new types as securely printable by adding an implicit printable to the
 * companion object of the class.
 *
 * {{{
 *   class NewType
 *   object NewType {
 *     implicit lazy val printable = SecurelyPrintable.usingToString[NewType]
 *   }
 * }}}
 */
@implicitNotFound("No implicit SecurePrinter found for type ${T}. " +
  "Implement or import an implicit SecurePrinter[${T}]."
)
trait SecurePrinter[-T] {

  def write(value: T): String
}

object SecurePrinter {

  def of[T: SecurePrinter]: SecurePrinter[T] = implicitly

  def using[T](asString: T => String): SecurePrinter[T] = new SecurePrinter[T] {
    override def write(value: T): String = asString(value)
  }

  def usingToString[T <: AnyRef]: SecurePrinter[T] = new SecurePrinter[T] {
    override def write(value: T): String = value.toString
  }

  implicit object SecurePrinterString extends SecurePrinter[String] {
    override def write(value: String): String = value
  }

  implicit object SecurePrinterAnyVal extends SecurePrinter[AnyVal] {
    override def write(value: AnyVal): String = value.toString
  }

  /**
   * Prints an option with the value inside.
   */
  implicit def OptionSecurePrinter[T](implicit printer: SecurePrinter[T]): SecurePrinter[Option[T]] = {
    new SecurePrinter[Option[T]] {
      override def write(value: Option[T]): String = value.map(printer.write).toString
    }
  }

  /**
   * Creates a printer of a traversable, using the underlying toString method after
   * each element has been securely printed.
   */
  implicit def TraversableSecurePrinter[T](implicit printer: SecurePrinter[T]): SecurePrinter[Traversable[T]] = {
    new SecurePrinter[Traversable[T]] {
      override def write(value: Traversable[T]): String = {
        value.map(printer.write).toString
      }
    }
  }

  /**
   * Creates a printer of a map, using the underlying toString method after
   * each element has been securely printed.
   */
  implicit def MapSecurePrinter[T](implicit printer: SecurePrinter[T]): SecurePrinter[Map[String, T]] = {
    new SecurePrinter[Map[String, T]] {
      override def write(value: Map[String, T]): String = {
        value.mapValues(printer.write).toString
      }
    }
  }

  /**
   * [[SecurePrinter]] is a Play functional [[ContravariantFunctor]].
   *
   * This allows the syntax `val printerB: SecurePrinter[B] = printerA.contramap(f: B => A)` when
   * [[play.api.libs.functional.syntax]] is imported.
   */
  implicit object SecurePrinterContravariantFunctor extends ContravariantFunctor[SecurePrinter] {
    override def contramap[A, B](printer: SecurePrinter[A], f: (B) => A): SecurePrinter[B] = {
      new SecurePrinter[B] {
        override def write(value: B): String = printer.write(f(value))
      }
    }
  }

}
