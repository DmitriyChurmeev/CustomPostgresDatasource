package ru.otus.spark.datasource.postgres.write

import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.connector.write.{DataWriter, DataWriterFactory}
import ru.otus.spark.datasource.postgres.{ConnectionProperties}

class PostgresDataWriterFactory(connectionProperties: ConnectionProperties)
  extends DataWriterFactory {
  override def createWriter(
                             partitionId: Int,
                             taskId: Long
                           ): DataWriter[InternalRow] =
    new PostgresWriter(connectionProperties)
}
