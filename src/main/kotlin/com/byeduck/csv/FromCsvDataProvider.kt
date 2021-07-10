package com.byeduck.csv

import com.byeduck.DataProvider
import com.byeduck.context.FieldNotFoundException
import com.byeduck.context.model.Bug
import com.byeduck.context.model.Device
import com.byeduck.context.model.Tester
import com.byeduck.context.model.TesterDevice
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FromCsvDataProvider(
    private val contextFileNamesProvider: ContextFileNamesProvider,
    private val csvReader: CsvReader
) : DataProvider {

    override fun getBugs(): List<Bug> =
        csvReader.readAll(contextFileNamesProvider.getBugsFileName(), this::bugCsvMapper)

    override fun getDevices(): List<Device> =
        csvReader.readAll(contextFileNamesProvider.getDevicesFileName(), this::deviceCsvMapper)

    override fun getTesters(): List<Tester> =
        csvReader.readAll(contextFileNamesProvider.getTestersFileName(), this::testersMapper)

    override fun getTesterDeviceData(): List<TesterDevice> =
        csvReader.readAll(contextFileNamesProvider.getTesterDeviceFileName(), this::testerDeviceMapper)

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