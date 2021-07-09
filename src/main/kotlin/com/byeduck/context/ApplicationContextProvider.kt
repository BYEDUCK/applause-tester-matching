package com.byeduck.context

import com.byeduck.BUGS_FILE_NAME
import com.byeduck.DEVICES_FILE_NAME
import com.byeduck.TESTERS_FILE_NAME
import com.byeduck.TESTER_DEVICE_FILE_NAME
import com.byeduck.csv.CsvReader
import com.byeduck.csv.DefaultCsvReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApplicationContextProvider {

    companion object {

        private var applicationContext: ApplicationContext? = null

        fun getContext(): ApplicationContext =
            applicationContext ?: throw IllegalStateException("Application context hasn't been initialized!")

        fun init() {
            val csvReader: CsvReader = DefaultCsvReader()
            val bugs = csvReader.readAll(BUGS_FILE_NAME) {
                Bug(
                    it["bugId"]?.toInt() ?: throw FieldNotFoundException("bugId"),
                    it["deviceId"]?.toInt() ?: throw FieldNotFoundException("deviceId"),
                    it["testerId"]?.toInt() ?: throw FieldNotFoundException("testerId")
                )
            }
            val devices = csvReader.readAll(DEVICES_FILE_NAME) {
                Device(
                    it["deviceId"]?.toInt() ?: throw FieldNotFoundException("deviceId"),
                    it["description"] ?: throw FieldNotFoundException("description")
                )
            }.associateBy { it.id }
            val testers = csvReader.readAll(TESTERS_FILE_NAME) {
                val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                Tester(
                    it["testerId"]?.toInt() ?: throw FieldNotFoundException("testerId"),
                    it["firstName"] ?: throw FieldNotFoundException("firstName"),
                    it["lastName"] ?: throw FieldNotFoundException("lastName"),
                    it["country"] ?: throw FieldNotFoundException("country"),
                    LocalDateTime.parse(it["lastLogin"], dateFormatter) ?: throw FieldNotFoundException("lastLogin")
                )
            }
            val testerDevices = csvReader.readAll(TESTER_DEVICE_FILE_NAME) {
                TesterDevice(
                    it["testerId"]?.toInt() ?: throw FieldNotFoundException("testerId"),
                    it["deviceId"]?.toInt() ?: throw FieldNotFoundException("deviceId")
                )
            }
            val devicesIdsByTesterId = testerDevices
                .groupBy(TesterDevice::testerId)
                .mapValues { it.value.map(TesterDevice::deviceId) }
            applicationContext = ApplicationContext(
                bugs,
                testers.map { TesterWithDevices.merge(it, devices, devicesIdsByTesterId) }
            )
        }
    }
}