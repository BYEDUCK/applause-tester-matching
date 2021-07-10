package com.byeduck

import com.byeduck.context.ApplicationContext
import com.byeduck.context.model.Bug
import com.byeduck.context.model.TesterWithDevices

class TestingMatcher(
    private val applicationContext: ApplicationContext
) {

    fun match(countries: List<String>, devices: List<String>): List<RankedTester> {
        val searchPredicateMapping = SearchCriteriaParser.parse(countries, devices)
        val testers = applicationContext.testers.let(searchPredicateMapping)
        return testers.map { RankedTester(it, count(it, applicationContext.bugs)) }
            .sortedByDescending { it.rank }
    }

    private fun count(testerWithDevices: TesterWithDevices, bugs: List<Bug>): Int =
        bugs.count { testerWithDevices.tester.id == it.testerId && testerWithDevices.devices.any { device -> device.id == it.deviceId } }
}