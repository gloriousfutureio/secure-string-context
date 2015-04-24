package safe.strings

import scala.language.{higherKinds, implicitConversions}

/**
 * Provides implicit conversion from collections of [[SafeToString]] instances into [[SafeToString]] collections.
 */
trait SafeCollections[S[T] <: SafeToString[T]] {
  self: SafeBuilders[S] =>

  /**
   * Marks any option of a Safe value as safe.
   *
   * After the element has been safely converted to a string,
   * it uses the Option.toString method.
   */
  implicit def SafeOption[T](implicit safe: S[T]): S[Option[T]] = {
    using(_.map(safe.stringify).toString)
  }

  /**
   * Marks any Traversable of Safe values as Safe.
   *
   * After each element has been safely converted to strings,
   * it uses the underlying toString method on the collection.
   */
  implicit def SafeTraversable[T, C[A] <: Traversable[A]](implicit safe: S[T]): S[C[T]] = {
    using((v: C[T]) => v.map(safe.stringify).toString())
  }

  /**
   * Marks any Map of Safe key-value pairs as Safe.
   *
   * After each element has been safely converted to strings,
   * it uses the underlying toString method on the collection.
   */
  implicit def SafeMap[K, V](implicit safeK: S[K], safeV: S[V]): S[Map[K, V]] = {
    using(
      _.map {
        case (k, v) => (safeK stringify k, safeV stringify v)
      }.toString()
    )
  }
}
