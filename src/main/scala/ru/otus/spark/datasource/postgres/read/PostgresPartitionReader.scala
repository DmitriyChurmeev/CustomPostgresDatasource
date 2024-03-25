package ru.otus.spark.datasource.postgres.read

import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.connector.read.PartitionReader
import ru.otus.spark.datasource.postgres.ConnectionProperties

import java.sql.DriverManager

class PostgresPartitionReader(connectionProperties: ConnectionProperties, start: Long, end: Long)
  extends PartitionReader[InternalRow] {
  private val connection = DriverManager.getConnection(
    connectionProperties.url,
    connectionProperties.user,
    connectionProperties.password
  )
  private val statement = connection.createStatement()
  private val resultSet = statement.executeQuery(s"select * from ${connectionProperties.tableName} offset $start limit ${end - start}")

  override def next(): Boolean = resultSet.next()

  override def get(): InternalRow = InternalRow(resultSet.getLong(1))

  override def close(): Unit = connection.close()
}
