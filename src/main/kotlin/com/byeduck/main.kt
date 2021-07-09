package com.byeduck

import com.byeduck.context.ApplicationContextProvider

fun main(args: Array<String>) {
    ApplicationContextProvider.init()
    val context = ApplicationContextProvider.getContext()
    print(context)
}