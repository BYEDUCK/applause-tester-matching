package com.byeduck

import com.byeduck.csv.DefaultContextFileNamesProvider

fun main(args: Array<String>) {
    val defaultCountrySearchCriteria = "ALL"
    val defaultDeviceSearchCriteria = "ALL"
    val countrySearchCriteria = args.getOrElse(1) { defaultCountrySearchCriteria }
    val deviceSearchCriteria: String = args.getOrElse(2) { defaultDeviceSearchCriteria }
    val testingMatcher = TestingMatcher(DefaultContextFileNamesProvider())
    val testersWithRanks = testingMatcher.match(countrySearchCriteria, deviceSearchCriteria)
    val result = testersWithRanks.joinToString("\n") { it.toString() }
    print("Results:\n$result")
}