package com.byeduck.context

import com.byeduck.DataProvider
import com.byeduck.context.model.TesterDevice
import com.byeduck.context.model.TesterWithDevices

class ApplicationContextInitializer {

    companion object {

        fun init(dataProvider: DataProvider): ApplicationContext {
            val bugs = dataProvider.getBugs()
            val devices = dataProvider.getDevices().associateBy { it.id }
            val testers = dataProvider.getTesters()
            val devicesIdsByTesterId = dataProvider.getTesterDeviceData()
                .groupBy(TesterDevice::testerId)
                .mapValues { it.value.map(TesterDevice::deviceId) }
            return ApplicationContext(
                bugs,
                testers.map { TesterWithDevices.merge(it, devices, devicesIdsByTesterId) }
            )
        }
    }
}