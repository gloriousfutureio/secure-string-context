package safe

import scala.language.implicitConversions

package object strings {

  /**
   * Builds a [[StringContext]] interpolator that only accepts [[SafeString]]s.
   */
  implicit class SafeStringContext(val sc: StringContext) extends AnyVal {

    /**
     * Safely stringify the given arguments.
     *
     * @note if you know a value's .toString method to be safe in all cases, you can use
     *       the [[safe]] method in the interpolated string explicitly.
     *
     * @param args the [[SafeString]]s to inject into the interpolated string context.
     */
    def ss(args: SafeString*): SafeString = {
      // concatenate all the arguments as strings using the original StringContext
      new SafeStringImpl(sc.s(args.map(_.asString): _*))
    }

    /**
     * More descriptive alias for [[ss]].
     */
    def safe(args: SafeString*): SafeString = ss(args: _*)
  }

  /**
   * A magnet trait for values that can be safely converted to Strings for visible output.
   */
  sealed trait SafeString extends Any {
    def asString: String
  }

  private class SafeStringImpl(val asString: String) extends AnyVal with SafeString {
    override def toString: String = asString
  }

  /**
   * Allow the value to be converted to a string, regardless of if it is safe.
   *
   * @note this is an alternative to marking this as explicitly safe, however you don't get the
   *       benefit of knowing whether the caller knows that this value (and all values to be printed)
   *       are safe, since no conversions are applied to any of the values shown in the string.
   *
   * @param value the value to convert to a string
   */
  def raw(value: Any): SafeString = new SafeStringImpl(value.toString)

  /**
   * Explicitly mark a value as safe and provide the means to print it.
   *
   * @param value the value that will be safely converted to a string using the implicit converter
   */
  def safe[T](value: T)(implicit safe: CanBeSafe[T]): SafeString =
    new SafeStringImpl(safe stringify value)

  /**
   * Convert any value that belongs to the [[Safe]] typeclass to a [[SafeString]].
   *
   * @param value the value that will be safely converted to a string
   */
  implicit def implicitlySafe[T](value: T)(implicit safe: Safe[T]): SafeString =
    new SafeStringImpl(safe stringify value)

}