package ru.otus.spark.datasource.postgres.read

import org.apache.spark.sql.connector.read.{Scan, ScanBuilder}
import org.apache.spark.sql.util.CaseInsensitiveStringMap
import ru.otus.spark.datasource.postgres.ConnectionProperties

class PostgresScanBuilder(options: CaseInsensitiveStringMap)
  extends ScanBuilder {
  override def build(): Scan = new PostgresScan(
    ConnectionProperties(
      options.get("url"),
      options.get("user"),
      options.get("password"),
      options.get("tableName"),
      options.get("partitionSize").toInt
    )
  )
}
