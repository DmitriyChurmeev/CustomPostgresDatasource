package ru.otus.spark.datasource.postgres.read

import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.connector.read.{InputPartition, PartitionReader, PartitionReaderFactory}
import ru.otus.spark.datasource.postgres.ConnectionProperties

class PostgresPartitionReaderFactory(connectionProperties: ConnectionProperties)
  extends PartitionReaderFactory {

  override def createReader(partition: InputPartition): PartitionReader[InternalRow] = new PostgresPartitionReader(
    connectionProperties, partition.asInstanceOf[PostgresPartition].start, partition.asInstanceOf[PostgresPartition].end
  )

}
