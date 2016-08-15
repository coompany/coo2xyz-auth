package xyz.lascuolaopensource.coo2xyz.auth.db

import com.twitter.inject.Logging
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


trait DAO[E] extends Logging {

	protected val tableQuery: TableQuery[_ <: Table[E]]

	protected val db = Database.forConfig("pgdb")

	def createSchema() = {
		db.run(tableQuery.schema.create) onComplete {
			case Success(_) => info(s"Created schema for ${getClass.getSimpleName}")
			case Failure(x) => error(s"Failed creating schema for ${getClass.getSimpleName}\n${x.getMessage}")
		}
	}

	def dropSchema() = {
		db.run(tableQuery.schema.drop) onComplete {
			case Success(_) => info(s"Dropped schema for ${getClass.getSimpleName}")
			case Failure(x) => error(s"Failed dropping schema for ${getClass.getSimpleName}\n${x.getMessage}")
		}
	}

	def createSchemaSQL: String = tableQuery.schema.create.statements.mkString("\n")

	def dropSchemaSQL: String = tableQuery.schema.drop.statements.mkString("\n")

}
