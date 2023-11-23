package com.example.learnwithme.data.manager.network

sealed class RetrofitError : Exception() {
    object EmptyBody : RetrofitError()
    class HttpException(val description: String) : RetrofitError()
    class Network(val description: String) : RetrofitError()
    class Timeout(val description: String) : RetrofitError()
    class Unknown(val description: String) : RetrofitError()
}

sealed class HttpCodeError : Exception() {
    object ServerNoContent : HttpCodeError() //204
    object BadRequest : HttpCodeError() //400
    object Unauthorized : HttpCodeError() //401
    object Forbidden : HttpCodeError()  //403
    object NotFound : HttpCodeError() //404
    object NotAcceptable : HttpCodeError() //406
    object Timeout : HttpCodeError() //408
    object ServerConflict : HttpCodeError() //409
    object InternalServerError : HttpCodeError() //500
}