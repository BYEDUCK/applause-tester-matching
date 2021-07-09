package com.byeduck.csv

interface CsvReader {
    fun <T> readAll(fileName: String, rowMapper: (rowData: Map<String, String>) -> T): List<T>
}