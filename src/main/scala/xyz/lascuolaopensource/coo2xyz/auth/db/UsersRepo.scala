package xyz.lascuolaopensource.coo2xyz.auth.db

import com.twitter.bijection.Conversion._
import com.twitter.bijection.twitter_util.UtilBijections.twitter2ScalaFuture
import com.twitter.util.{Future => TwitterFuture}
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future => ScalaFuture}


case class User(id: Option[Long], email: String, password: String)

object UsersRepo extends DAO[User] {

	class UsersTable(tag: Tag) extends Table[User](tag, "users") {
		def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
		def email = column[String]("email")
		def password = column[String]("password")

		def * = (id, email, password) <> (User.tupled, User.unapply)
	}

	protected val tableQuery = TableQuery[UsersTable]

	def findByLogin(email: String, password: String): TwitterFuture[Option[User]] = {
		val q = (for {
			u <- tableQuery if u.email === email && u.password === password } yield u
		).result.headOption

		val scalaFuture: ScalaFuture[Option[User]] = db.run(q)
		scalaFuture.as[TwitterFuture[Option[User]]]
	}

}
