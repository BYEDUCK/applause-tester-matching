package com.byeduck

import com.byeduck.context.ApplicationContextProvider
import com.byeduck.context.model.Bug
import com.byeduck.context.model.TesterWithDevices
import com.byeduck.csv.ContextFileNamesProvider

class TestingMatcher(
    private val contextFileNamesProvider: ContextFileNamesProvider
) {

    fun match(countrySearchCriteria: String, deviceSearchCriteria: String): List<RankedTester> {
        ApplicationContextProvider.init(contextFileNamesProvider)
        val context = ApplicationContextProvider.getContext()
        val searchPredicateMapping = SearchCriteriaParser.parse(countrySearchCriteria, deviceSearchCriteria)
        val testers = context.testers.let(searchPredicateMapping)
        return testers.map { RankedTester(it, count(it, context.bugs)) }
            .sortedByDescending { it.rank }
    }

    private fun count(testerWithDevices: TesterWithDevices, bugs: List<Bug>): Int =
        bugs.count { testerWithDevices.tester.id == it.testerId && testerWithDevices.devices.any { device -> device.id == it.deviceId } }
}