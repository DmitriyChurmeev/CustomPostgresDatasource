package ru.otus.spark.datasource.postgres.write

import org.apache.spark.sql.connector.write.{BatchWrite, DataWriterFactory, PhysicalWriteInfo, WriterCommitMessage}
import ru.otus.spark.datasource.postgres.{ConnectionProperties}

class PostgresBatchWrite(connectionProperties: ConnectionProperties)
  extends BatchWrite {
  override def createBatchWriterFactory(
                                         physicalWriteInfo: PhysicalWriteInfo
                                       ): DataWriterFactory =
    new PostgresDataWriterFactory(connectionProperties)

  override def commit(
                       writerCommitMessages: Array[WriterCommitMessage]
                     ): Unit = {}

  override def abort(
                      writerCommitMessages: Array[WriterCommitMessage]
                    ): Unit = {}
}
