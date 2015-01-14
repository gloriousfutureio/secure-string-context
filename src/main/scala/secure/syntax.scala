package me.jeffmay.secure

import scala.language.implicitConversions

object syntax {

  implicit class SecureStringContext(val sc: StringContext) extends AnyVal {

    def secure(args: PrintableWrapper*): String = {
      // concatenate all the arguments as strings using the original StringContext
      sc.s(args.map(_.asString): _*)
    }
  }

  implicit def toPrintableWrapper[T: SecurePrinter](value: T): PrintableWrapper =
    new PrintableWrapperImpl(SecurePrinter.of[T].write(value))

  sealed trait PrintableWrapper extends NotNull {
    def asString: String
  }

  private case class PrintableWrapperImpl(asString: String) extends PrintableWrapper

}
