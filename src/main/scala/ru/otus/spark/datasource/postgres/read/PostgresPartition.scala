package ru.otus.spark.datasource.postgres.read

import org.apache.spark.sql.connector.read.InputPartition

class PostgresPartition(val start: Long, val end: Long)  extends InputPartition {


}
