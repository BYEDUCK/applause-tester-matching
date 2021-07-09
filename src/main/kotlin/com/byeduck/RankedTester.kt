package com.byeduck

import com.byeduck.context.model.TesterWithDevices

data class RankedTester(
    val testerWithDevices: TesterWithDevices,
    val rank: Int
) {

    override fun toString(): String {
        return "(id: ${testerWithDevices.tester.id}): ${testerWithDevices.tester.firstName} ${testerWithDevices.tester.lastName} -> $rank"
    }
}
