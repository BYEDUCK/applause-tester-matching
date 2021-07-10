package com.byeduck

import com.byeduck.context.ApplicationContext
import com.byeduck.context.model.TesterWithDevices

class TestingMatcher(
    private val applicationContext: ApplicationContext
) {

    fun match(countries: List<String>, devices: List<String>): List<RankedTester> {
        val searchPredicateMapping = SearchCriteriaParser.parse(countries, devices)
        val testers = applicationContext.testers.let(searchPredicateMapping)
        return testers.map { RankedTester(it, rank(it)) }
            .sortedByDescending { it.rank }
    }

    private fun rank(testerWithDevices: TesterWithDevices): Int =
        applicationContext.bugs.count { bug ->
            testerWithDevices.tester.id == bug.testerId && testerWithDevices.devices.any { device -> device.id == bug.deviceId }
        }
}