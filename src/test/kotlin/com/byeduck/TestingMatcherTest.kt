package com.byeduck

import com.byeduck.context.ApplicationContextInitializer
import com.byeduck.csv.ContextFileNamesProvider
import com.byeduck.csv.DefaultCsvReader
import com.byeduck.csv.FromCsvDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TestingMatcherTest {

    @Test
    internal fun test() {
        val fileNamesProvider = object : ContextFileNamesProvider {
            override fun getBugsFileName(): String = "test_bugs.csv"

            override fun getDevicesFileName(): String = "test_devices.csv"

            override fun getTesterDeviceFileName(): String = "test_tester_device.csv"

            override fun getTestersFileName(): String = "test_testers.csv"
        }
        val csvDataProvider = FromCsvDataProvider(fileNamesProvider, DefaultCsvReader())
        val context = ApplicationContextInitializer.init(csvDataProvider)
        val testingMatcher = TestingMatcher(context)
        val result = testingMatcher.match(listOf("PL"), listOf("iPhone 4S", "iPhone 5"))
        assertThat(result)
            .hasSize(2)
            .allMatch { rankedTester -> rankedTester.testerWithDevices.tester.country == "PL" }
            .allMatch { rankedTester -> rankedTester.testerWithDevices.devices.any { it.description == "iPhone 4S" || it.description == "iPhone 5" } }
            .element(0)
            .matches { rankedTester -> rankedTester.testerWithDevices.tester.id == 2 && rankedTester.rank == 5 }
    }
}