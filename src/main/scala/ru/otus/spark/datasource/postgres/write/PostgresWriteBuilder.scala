package ru.otus.spark.datasource.postgres.write

import org.apache.spark.sql.connector.write.{BatchWrite, WriteBuilder}
import org.apache.spark.sql.util.CaseInsensitiveStringMap
import ru.otus.spark.datasource.postgres.ConnectionProperties

class PostgresWriteBuilder(options: CaseInsensitiveStringMap)
  extends WriteBuilder {
  override def buildForBatch(): BatchWrite = new PostgresBatchWrite(
    ConnectionProperties(
      options.get("url"),
      options.get("user"),
      options.get("password"),
      options.get("tableName"),
      options.get("partitionSize").toInt
    )
  )
}
