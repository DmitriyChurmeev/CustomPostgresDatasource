package ru.otus.spark.datasource.postgres

case class ConnectionProperties(
                                 url: String,
                                 user: String,
                                 password: String,
                                 tableName: String,
                                 partitionSize: Integer
                               )
