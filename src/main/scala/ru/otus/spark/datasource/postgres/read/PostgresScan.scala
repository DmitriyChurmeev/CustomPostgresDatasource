package ru.otus.spark.datasource.postgres.read

import org.apache.spark.sql.connector.read.{Batch, InputPartition, PartitionReaderFactory, Scan}
import org.apache.spark.sql.types.StructType
import ru.otus.spark.datasource.postgres.{ConnectionProperties, PostgresTable}

import java.sql.DriverManager

class PostgresScan(connectionProperties: ConnectionProperties)
  extends Scan
    with Batch {

  private val connection = DriverManager.getConnection(
    connectionProperties.url, connectionProperties.user, connectionProperties.password
  )

  override def readSchema(): StructType = PostgresTable.schema

  override def toBatch: Batch = this

  override def planInputPartitions(): Array[InputPartition] = {
    val partitions = new Array[InputPartition](connectionProperties.partitionSize)
    val countOfRows = getCountRows(connectionProperties)
    val partitionsNumber = countOfRows / connectionProperties.partitionSize


    for (i <- 0 until connectionProperties.partitionSize) {
      val start: Long = partitionsNumber * i
      val end: Long = if (i==connectionProperties.partitionSize-1) countOfRows else start + partitionsNumber
      partitions(i) = new PostgresPartition(start, end)
    }

    return partitions
  }
  def getCountRows(connectionProperties: ConnectionProperties): Long = {

     val statement = connection.createStatement()
     val query = s"select count(*) from ${connectionProperties.tableName}"
     val resultSet = statement.executeQuery(query)

    if (resultSet.next()) {
      return resultSet.getLong(1)
    }

    return 0
  }


  override def createReaderFactory(): PartitionReaderFactory =
    new PostgresPartitionReaderFactory(connectionProperties)
}

