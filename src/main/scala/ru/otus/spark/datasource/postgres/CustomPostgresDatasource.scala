package ru.otus.spark.datasource.postgres

import org.apache.spark.sql.connector.catalog.{Table, TableProvider}
import org.apache.spark.sql.connector.expressions.Transform
import org.apache.spark.sql.types._
import org.apache.spark.sql.util.CaseInsensitiveStringMap

import java.util

class CustomPostgresDatasource extends TableProvider {
  override def inferSchema(options: CaseInsensitiveStringMap): StructType =
    PostgresTable.schema

  override def getTable(
                         schema: StructType,
                         partitioning: Array[Transform],
                         properties: util.Map[String, String]
                       ): Table = new PostgresTable(
    properties.get("tableName")
  )
}




















