package com.byeduck.csv

@FunctionalInterface
interface CsvRowMapper<T> {
    fun map(rowData: Map<String, String>): T
}