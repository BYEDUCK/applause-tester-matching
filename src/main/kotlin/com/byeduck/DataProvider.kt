package com.byeduck

import com.byeduck.context.model.Bug
import com.byeduck.context.model.Device
import com.byeduck.context.model.Tester
import com.byeduck.context.model.TesterDevice

interface DataProvider {
    fun getBugs(): List<Bug>
    fun getDevices(): List<Device>
    fun getTesters(): List<Tester>
    fun getTesterDeviceData(): List<TesterDevice>
}