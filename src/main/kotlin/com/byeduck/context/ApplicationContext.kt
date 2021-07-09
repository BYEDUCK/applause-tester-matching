package com.byeduck.context

import com.byeduck.context.model.Bug
import com.byeduck.context.model.TesterWithDevices

data class ApplicationContext(
    val bugs: List<Bug>,
    val testers: List<TesterWithDevices>
)