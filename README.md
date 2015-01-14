Secure String Context
=====================

A Scala library for pre-processing certain types of values during string interpolation to ensure all values can be
printed securely.

Usage
=====

It's as simple as:

```scala
import me.jeffmay.secure.syntax._

val name = "Jeff"
println(secure"Hello my name is $name!")
```

This will print `Hello my name is Jeff!`

Note that this is identical to the `s` string interpolation, however, it will not work on custom types.

```scala
val user = User(name = "Jeff", password = "12345")  // Hey, it's easy to remember
println(secure"User created successfully: $user")
                                          ^ compile error
```

In order to use this type of value, you must define an implicit instance of `SecurePrinter[User]`. Typically,
it is best to put this on the companion object so that you never have to import it.

```scala
case class User(name: String, password: String)

object User {
  implicit val secure: SecurePrinter[User] = new SecurePrinter[User] {
    override def write(value: User): String = {
      value.map(password = encrypt(value.password)).toString
    }
  }
}
```

And voila! Now when printing an instance of `User` in a secure string, the `SecurePrinter[User]` will be used to
ensure that the password is encrypted before being printed to a log or something.
