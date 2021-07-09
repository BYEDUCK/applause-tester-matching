package com.byeduck.csv

interface ContextFileNamesProvider {
    fun getBugsFileName(): String
    fun getDevicesFileName(): String
    fun getTesterDeviceFileName(): String
    fun getTestersFileName(): String
}