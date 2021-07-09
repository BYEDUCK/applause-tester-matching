package com.byeduck

import com.byeduck.context.model.Device
import com.byeduck.context.model.TesterWithDevices

class SearchCriteriaParser {

    companion object {

        private const val WILD_CARD = "ALL"

        fun parse(
            countrySearchCriteria: String,
            deviceSearchCriteria: String
        ): (testers: List<TesterWithDevices>) -> List<TesterWithDevices> {
            val countries = countrySearchCriteria.split(',')
            val devices = deviceSearchCriteria.split(',')
            return { testers ->
                val countryMatchingTesters =
                    if (countries.containWildCard()) testers else testers.filter { it.isCountryMatching(countries) }
                val isDevicesWildCard = devices.containWildCard()
                countryMatchingTesters.map {
                    TesterWithDevices(
                        it.tester,
                        if (isDevicesWildCard) it.devices else it.devices.filter { device ->
                            device.isDeviceMatching(devices)
                        }
                    )
                }.filter { it.devices.isNotEmpty() }
            }
        }

        private fun List<String>.containWildCard(): Boolean = this.contains(WILD_CARD)

        private fun Device.isDeviceMatching(devices: List<String>): Boolean =
            devices.isEmpty() || devices.any { it == this.description }

        private fun TesterWithDevices.isCountryMatching(countries: List<String>): Boolean =
            countries.isEmpty() || countries.find { it == this.tester.country } != null

    }
}