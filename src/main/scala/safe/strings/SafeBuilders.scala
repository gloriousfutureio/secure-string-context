package safe.strings

import scala.language.higherKinds
import scala.reflect.{ClassTag, classTag}

/**
 * Provides a basic interface for the companion objects of the [[SafeToString]] types.
 *
 * @note this doesn't include the common `def of[T]: S[T]` method, since this is different
 *       based on the subclass, and you wouldn't need the method for any generic code anyhow.
 */
trait SafeBuilders[S[T] <: SafeToString[T]] {

  def string[T](value: T)(implicit safe: S[T]): String = safe stringify value

  def using[T](asString: T => String): S[T]

  def usingToString[T]: S[T] = using((_: T).toString)

  def usingUnapply[T: ClassTag, Tuple <: Product](unapply: T => Option[Tuple])(implicit safe: S[Tuple]): S[T] = {
    val clsName = classTag[T].runtimeClass.getSimpleName
    val tupled = Function.unlift(unapply)
    using { value =>
      s"$clsName${string(tupled(value))}"
    }
  }
}
