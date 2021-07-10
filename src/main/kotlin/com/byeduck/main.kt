package com.byeduck

import com.byeduck.SearchCriteriaParser.Companion.WILD_CARD
import com.byeduck.context.ApplicationContextInitializer
import com.byeduck.csv.DefaultContextFileNamesProvider
import com.byeduck.csv.DefaultCsvReader
import com.byeduck.csv.FromCsvDataProvider
import java.io.BufferedReader
import java.io.InputStreamReader

const val EXIT_INPUT = "exit"

fun main(args: Array<String>) {
    println("Welcome to Testing Matcher.")
    println("Initializing...")
    val csvDataProvider = FromCsvDataProvider(DefaultContextFileNamesProvider(), DefaultCsvReader())
    val context = ApplicationContextInitializer.init(csvDataProvider)
    val testingMatcher = TestingMatcher(context)
    println("Done.")
    println("Type \"exit\" (without quotes) in order to exit the app")
    println()
    BufferedReader(InputStreamReader(System.`in`)).use { consoleReader ->
        val availableCountries = context.testers.map { it.tester.country }.distinct()
        val availableDevices = context.testers.flatMap { it.devices }.associate { it.id to it.description }
        println("Available countries:\n$availableCountries")
        println("Available devices:\n${
            availableDevices.toList().sortedBy { it.first }.joinToString("\n") { "${it.first} -> ${it.second}" }
        }")
        println("\n")
        while (true) {
            println("Please enter search criteria separated with comas:")
            print("Countries (default: $WILD_CARD): ")
            var input = consoleReader.readLine()
            if (EXIT_INPUT == input) {
                break
            }
            val searchCountries = input.sanitizeInputCriteria().split(",")
            println()
            print("Devices (default: $WILD_CARD)(you can also input device's ids from the list): ")
            input = consoleReader.readLine()
            if (EXIT_INPUT == input) {
                break
            }
            val searchDevices = input.sanitizeInputCriteria().split(",").map {
                if (it.all(Character::isDigit)) availableDevices.getOrDefault(it.toInt(), "") else it
            }
            println("Executing matching for the following criteria: $searchCountries | $searchDevices")
            val testersRanking = testingMatcher.match(searchCountries, searchDevices)
            val result = testersRanking.joinToString("\n") { it.toString() }
            println("Results:\n$result")
            println()
        }
    }
}

fun String?.sanitizeInputCriteria() = this
    ?.replace('_', ' ')
    ?.removeSurrounding("\"")
    ?.removeSurrounding("'")
    ?.trim().let {
        it?.ifEmpty { WILD_CARD }
    } ?: WILD_CARD