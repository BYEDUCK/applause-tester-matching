package com.byeduck.context

import com.byeduck.context.model.*
import com.byeduck.csv.ContextFileNamesProvider
import com.byeduck.csv.CsvReader
import com.byeduck.csv.DefaultCsvReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApplicationContextProvider {

    companion object {

        private var applicationContext: ApplicationContext? = null

        fun getContext(): ApplicationContext =
            applicationContext ?: throw IllegalStateException("Application context hasn't been initialized!")

        fun init(contextFileNamesProvider: ContextFileNamesProvider) {
            if (applicationContext != null) {
                return
            }
            val csvReader: CsvReader = DefaultCsvReader()
            val bugs = csvReader.readAll(contextFileNamesProvider.getBugsFileName(), this::bugCsvMapper)
            val devices = csvReader.readAll(contextFileNamesProvider.getDevicesFileName(), this::deviceCsvMapper)
                .associateBy { it.id }
            val testers = csvReader.readAll(contextFileNamesProvider.getTestersFileName(), this::testersMapper)
            val devicesIdsByTesterId =
                csvReader.readAll(contextFileNamesProvider.getTesterDeviceFileName(), this::testerDeviceMapper)
                    .groupBy(TesterDevice::testerId)
                    .mapValues { it.value.map(TesterDevice::deviceId) }
            applicationContext = ApplicationContext(
                bugs,
                testers.map { TesterWithDevices.merge(it, devices, devicesIdsByTesterId) }
            )
        }

        private fun bugCsvMapper(rowData: Map<String, String>): Bug = rowData.let {
            Bug(
                it["bugId"]?.toInt() ?: throw FieldNotFoundException("bugId"),
                it["deviceId"]?.toInt() ?: throw FieldNotFoundException("deviceId"),
                it["testerId"]?.toInt() ?: throw FieldNotFoundException("testerId")
            )
        }

        private fun deviceCsvMapper(rowData: Map<String, String>): Device = rowData.let {
            Device(
                it["deviceId"]?.toInt() ?: throw FieldNotFoundException("deviceId"),
                it["description"] ?: throw FieldNotFoundException("description")
            )
        }

        private fun testersMapper(rowData: Map<String, String>): Tester = rowData.let {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            Tester(
                it["testerId"]?.toInt() ?: throw FieldNotFoundException("testerId"),
                it["firstName"] ?: throw FieldNotFoundException("firstName"),
                it["lastName"] ?: throw FieldNotFoundException("lastName"),
                it["country"] ?: throw FieldNotFoundException("country"),
                LocalDateTime.parse(it["lastLogin"], dateFormatter) ?: throw FieldNotFoundException("lastLogin")
            )
        }

        private fun testerDeviceMapper(rowData: Map<String, String>): TesterDevice = rowData.let {
            TesterDevice(
                it["testerId"]?.toInt() ?: throw FieldNotFoundException("testerId"),
                it["deviceId"]?.toInt() ?: throw FieldNotFoundException("deviceId")
            )
        }
    }
}