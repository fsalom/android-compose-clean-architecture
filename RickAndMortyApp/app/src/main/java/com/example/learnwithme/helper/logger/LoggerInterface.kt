package com.example.learnwithme.helper.logger

import android.util.Log
import okhttp3.Request
import okhttp3.Response

interface LoggerInterface {
    fun log(message: String)
    fun log(request: Request, response: Response, duration: Double)
}