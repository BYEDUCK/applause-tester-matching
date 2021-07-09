package com.byeduck.csv

class DefaultContextFileNamesProvider : ContextFileNamesProvider {
    override fun getBugsFileName(): String = "bugs.csv"

    override fun getDevicesFileName(): String = "devices.csv"

    override fun getTesterDeviceFileName(): String = "tester_device.csv"

    override fun getTestersFileName(): String = "testers.csv"
}