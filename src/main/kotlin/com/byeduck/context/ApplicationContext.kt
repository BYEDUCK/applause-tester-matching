package com.byeduck.context

data class ApplicationContext(
    val bugs: List<Bug>,
    val devices: List<Device>,
    val testers: List<Tester>,
    val testerDevices: List<TesterDevice>
)