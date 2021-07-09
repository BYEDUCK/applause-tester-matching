package com.byeduck

import com.byeduck.csv.DefaultContextFileNamesProvider

// mvn package exec:java -Dexec.args="JP,GP iPhone_4,iPhone_4S"
fun main(args: Array<String>) {
    val defaultCountrySearchCriteria = "ALL"
    val defaultDeviceSearchCriteria = "ALL"
    val criteria = args[0].split(" ")
    val countrySearchCriteria = criteria.getOrElse(0) { defaultCountrySearchCriteria }.sanitizeInputCriteria()
    val deviceSearchCriteria: String = criteria.getOrElse(1) { defaultDeviceSearchCriteria }.sanitizeInputCriteria()
    println("Executing matching for the following criteria: $countrySearchCriteria | $deviceSearchCriteria")
    val testingMatcher = TestingMatcher(DefaultContextFileNamesProvider())
    val testersWithRanks = testingMatcher.match(countrySearchCriteria, deviceSearchCriteria)
    val result = testersWithRanks.joinToString("\n") { it.toString() }
    println("Results:\n$result")
}

fun String.sanitizeInputCriteria() = this
    .replace('_', ' ')
    .removeSurrounding("\"")
    .removeSurrounding("'")
    .trim()