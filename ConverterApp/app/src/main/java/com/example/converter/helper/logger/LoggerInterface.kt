package com.example.converter.helper.logger

import okhttp3.Request
import okhttp3.Response

interface LoggerInterface {
    fun log(message: String)
    fun log(request: Request, response: Response, duration: Double)
}