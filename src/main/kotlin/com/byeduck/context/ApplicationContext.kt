package com.byeduck.context

data class ApplicationContext(
    val bugs: List<Bug>,
    val testers: List<TesterWithDevices>
)