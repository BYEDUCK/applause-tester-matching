package com.byeduck.context

data class TesterWithDevices(
    val tester: Tester,
    val devices: List<Device>
) {
    companion object {
        fun merge(
            tester: Tester,
            devices: Map<Int, Device>,
            deviceIdsByTesterId: Map<Int, List<Int>>
        ): TesterWithDevices =
            deviceIdsByTesterId.getOrDefault(tester.id, emptyList())
                .map { devices[it] ?: throw IllegalArgumentException("Device with id $it not found") }
                .let { TesterWithDevices(tester, it) }
    }
}
