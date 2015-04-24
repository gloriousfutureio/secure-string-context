package safe.strings

import scala.annotation.implicitNotFound
import scala.language.{higherKinds, implicitConversions}

/**
 * The interface of all 4 subtypes of [[Safe]], [[AllSafe]], [[CanBeSafe]], and [[AllCanBeSafe]].
 *
 * For more on how these 4 markers are used, please refer to the README.md
 *
 * This trait defines the implementation of these interfaces and provides a handle for common
 * generic traits.
 */
sealed trait SafeToString[-T] {

  /**
   * Safely convert the given value to a String.
   */
  def stringify(value: T): String
}

/**
 * Marks a type as safe to use as [[SafeString]]s.
 *
 * You can allow new types to be safe to view by adding an implicit [[Safe]] instance to the
 * companion object of the class.
 *
 * {{{
 *   class SafeType
 *   object SafeType {
 *     implicit val safe = Safe.usingToString[SafeType]
 *   }
 * }}}
 *
 * You can also safely print case classes with all of the arguments safely printed by using:
 *
 * {{{
 *   case class SafeCaseClass(safeType: SafeType, allSafeType: AllSafeType)
 *   object SafeCaseClass {
 *     implicit val safe: Safe[SafeCaseClass] = Safe.usingUnapply(SafeCaseClass.unapply)
 *   }
 * }}}
 *
 * This will look up implicit [[Safe]] instances for the arguments provided.
 */
@implicitNotFound("No implicit Safe[${T}] in scope. To fix this, you can either:\n" +
  "A) If the value is a primitive, string, or a CanBeSafe value, and you are using 'ss', use the 'safe' method.\n" +
  "B) Import or define an implicit Safe[${T}] that will convert all values to be safe to view\n" +
  "C) Import or define an implicit CanBeSafe[${T}] and explicitly use 'safe' method where appropriate")
trait Safe[T] extends CanBeSafe[T]

object Safe
  extends SafeBuilders[Safe]
  with SafeCollections[Safe]
  with SafeTuples[Safe] {

  /**
   * Summon a [[Safe]] or [[AllSafe]] instance from the implicit world.
   */
  def of[T: Safe]: Safe[T] = implicitly

  override def using[T](asString: T => String): Safe[T] = new Safe[T] {
    override def stringify(value: T): String = asString(value)
  }

  /**
   * Converts [[AllSafe]] instances to [[Safe]] since any subclass of an AllSafe type parameter are
   * also considered [[Safe]].
   */
  implicit def SafeSubclasses[T](implicit safe: AllSafe[T]): Safe[T] = new Safe[T] {
    override def stringify(value: T): String = safe stringify value
  }
}

/**
 * A contravariant typeclass, so that all subclasses of T also have a [[Safe]] defined implicitly.
 *
 * This allows you to mark a parent class as [[AllSafe]], and all subclasses will be treated as [[Safe]].
 *
 * {{{
 *   class ParentType
 *   object ParentType {
 *     implicit val safe = AllSafe.usingToString[ParentType]
 *   }
 *   class ChildType extends ParentType  // now implied Safe instance
 * }}}
 *
 * @note be careful when using this. Adding an unsafe value to a subclass will allow values to
 *       leak through via the naive implementation of stringify (ie. `.toString`).
 */
@implicitNotFound("No implicit AllSafe[${T}] in scope. " +
  "Define or import or implement an AllSafe instance for this type " +
  "(only if all subclasses are implicitly Safe).")
trait AllSafe[-T] extends AllCanBeSafe[T]

object AllSafe extends SafeBuilders[AllSafe] {

  /**
   * Summon an [[AllSafe]] instance from the implicit world.
   */
  def of[T: AllSafe]: AllSafe[T] = implicitly

  override def using[T](asString: T => String): AllSafe[T] = new AllSafe[T] {
    override def stringify(value: T): String = asString(value)
  }
}

/**
 * Marks a type as safe to use as [[SafeString]]s only when used in conjunction with an explicit
 * call to [[safe]].
 *
 * You can allow new types to be safe to view explicitly.
 *
 * {{{
 *   class ExplicitlySafeType
 *   object ExplicitlySafeType {
 *     implicit val safe = ExplicitlySafe.usingToString[ExplicitlySafeType]
 *   }
 * }}}
 */
@implicitNotFound("No implicit CanBeSafe[${T}] in scope. To fix this, you can either:\n" +
  "A) Use the 'raw' method to bypass this check and print the value as a string\n" +
  "B) Import or define an implicit ExplicitlySafe[${T}] instance to allow only explicit serialization" +
  " using the 'safe' method\n" +
  "C) Import or define an implicit Safe[${T}] and (optionally) remove the call to the 'safe' method.")
trait CanBeSafe[T] extends SafeToString[T]

object CanBeSafe
  extends SafeBuilders[CanBeSafe]
  with SafeCollections[CanBeSafe]
  with SafeTuples[CanBeSafe] {

  /**
   * Summon any [[SafeToString]] instance from the implicit world as an [[CanBeSafe]] instance.
   */
  def of[T](implicit safe: SafeToString[T]): CanBeSafe[T] = safe match {
    case explicitly: CanBeSafe[T] => explicitly
    case _ => using(safe.stringify)
  }

  override def using[T](asString: T => String): CanBeSafe[T] = new CanBeSafe[T] {
    override def stringify(value: T): String = asString(value)
  }

  // Strings are safe to use with the 'safe' method

  implicit object StringCanBeSafe extends CanBeSafe[String] {
    override def stringify(value: String): String = value
  }

  /**
   * Any subclass of an [[AllCanBeSafe]] is also [[CanBeSafe]].
   */
  implicit def SafeSubclasses[T](implicit safe: AllCanBeSafe[T]): CanBeSafe[T] = using(safe.stringify)
}

@implicitNotFound("No implicit AllCanBeSafe[${T}] in scope. " +
  "Define or import or implement an AllCanBeSafe instance for this type " +
  "(only if all subclasses should be marked CanBeSafe).")
trait AllCanBeSafe[-T] extends SafeToString[T]

object AllCanBeSafe extends SafeBuilders[AllCanBeSafe] {

  override def using[T](asString: T => String): AllCanBeSafe[T] = new AllCanBeSafe[T] {
    override def stringify(value: T): String = asString(value)
  }

  // All subtypes of AnyVal are safe to use with the 'safe' method

  implicit object AllAnyValCanBeSafe extends AllCanBeSafe[AnyVal] {
    override def stringify(value: AnyVal): String = value.toString
  }

  implicit object AllNumberCanBeSafe extends AllCanBeSafe[Number] {
    override def stringify(value: Number): String = value.toString
  }
}
