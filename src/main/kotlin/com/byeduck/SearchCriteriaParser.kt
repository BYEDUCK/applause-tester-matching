package com.byeduck

import com.byeduck.context.model.Device
import com.byeduck.context.model.TesterWithDevices

class SearchCriteriaParser {

    companion object {

        const val WILD_CARD = "ALL"

        fun parse(
            searchCountries: List<String>,
            searchDevices: List<String>
        ): (testers: List<TesterWithDevices>) -> List<TesterWithDevices> {
            return { testers ->
                val countryMatchingTesters =
                    if (searchCountries.containWildCard()) testers else testers.filter {
                        it.isCountryMatching(
                            searchCountries
                        )
                    }
                val isDevicesWildCard = searchDevices.containWildCard()
                countryMatchingTesters.map {
                    TesterWithDevices(
                        it.tester,
                        if (isDevicesWildCard) it.devices else it.devices.filter { device ->
                            device.isDeviceMatching(searchDevices)
                        }
                    )
                }.filter { it.devices.isNotEmpty() }
            }
        }

        private fun List<String>.containWildCard(): Boolean = this.contains(WILD_CARD)

        private fun Device.isDeviceMatching(searchDevices: List<String>): Boolean =
            searchDevices.isEmpty() || searchDevices.any { it == this.description }

        private fun TesterWithDevices.isCountryMatching(searchCountries: List<String>): Boolean =
            searchCountries.isEmpty() || searchCountries.find { it == this.tester.country } != null

    }
}