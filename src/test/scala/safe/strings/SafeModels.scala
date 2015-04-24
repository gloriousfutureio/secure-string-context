package safe.strings

class UnsafeType

class SafeType
object SafeType {
  implicit val safe: Safe[SafeType] = Safe.usingToString
}

class AnotherSafeType
object AnotherSafeType {
  implicit val safe: Safe[AnotherSafeType] = Safe.usingToString
}

sealed trait SafeParent

object SafeParent {
  implicit val allSafe: AllSafe[SafeParent] = AllSafe.usingToString
}

class SafeChild extends SafeParent

case class SafeCaseClass(safe1: SafeType, safe2: AnotherSafeType)
object SafeCaseClass {
  implicit val safe: Safe[SafeCaseClass] = Safe.usingUnapply(SafeCaseClass.unapply)
}

case class SafeCaseClassWithChild(safe1: SafeType, safe2: AnotherSafeType, child: SafeChild)
object SafeCaseClassWithChild {
  implicit val safe: Safe[SafeCaseClassWithChild] = Safe.usingUnapply(SafeCaseClassWithChild.unapply)
}

class CanBeSafeType
object CanBeSafeType {
  implicit val safe: CanBeSafe[CanBeSafeType] = CanBeSafe.usingToString
}

class CanBeSafeParent
object CanBeSafeParent {
  implicit val safe: AllCanBeSafe[CanBeSafeParent] = AllCanBeSafe.usingToString
}

class CanBeSafeChild extends CanBeSafeParent