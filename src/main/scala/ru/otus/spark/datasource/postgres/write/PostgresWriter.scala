package ru.otus.spark.datasource.postgres.write

import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.connector.write.{DataWriter, WriterCommitMessage}
import ru.otus.spark.datasource.postgres.{ConnectionProperties}

import java.sql.{Connection, DriverManager, PreparedStatement}

class PostgresWriter(connectionProperties: ConnectionProperties)
  extends DataWriter[InternalRow] {

  val connection: Connection = DriverManager.getConnection(
    connectionProperties.url,
    connectionProperties.user,
    connectionProperties.password
  )
  val statement = "insert into users (user_id) values (?)"
  val preparedStatement: PreparedStatement =
    connection.prepareStatement(statement)

  override def write(record: InternalRow): Unit = {
    val value = record.getLong(0)
    preparedStatement.setLong(1, value)
    preparedStatement.executeUpdate()
  }

  override def commit(): WriterCommitMessage = WriteSucceeded

  override def abort(): Unit = {}

  override def close(): Unit = connection.close()
}