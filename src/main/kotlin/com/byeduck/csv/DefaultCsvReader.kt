package com.byeduck.csv

import java.util.*

class DefaultCsvReader(
    private val delimiter: String = ","
) : CsvReader {
    override fun <T> readAll(fileName: String, rowMapper: (rowData: Map<String, String>) -> T): List<T> {
        val reader = this.javaClass.classLoader.getResource(fileName)?.openStream()?.bufferedReader()
            ?: throw IllegalStateException("Cannot read file $fileName")
        val headers = reader.readLine().split(delimiter).map(this::fixString)
        val parsed = LinkedList<Map<String, String>>()
        reader.useLines { lines ->
            lines.forEach {
                if (it.isNotBlank()) {
                    val rowData = it.split(delimiter).map(this::fixString)
                    parsed.add(rowData.mapIndexed { i, r -> headers[i] to r }.toMap())
                }
            }
        }
        return parsed.map(rowMapper)
    }

    private fun fixString(s: String): String = s.let {
        val strRegex = Regex("^\"(.*)\"$")
        val match = strRegex.matchEntire(it)
        match?.groups?.get(1)?.value ?: it
    }

}