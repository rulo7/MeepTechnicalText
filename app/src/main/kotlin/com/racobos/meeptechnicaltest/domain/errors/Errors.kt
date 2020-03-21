package com.racobos.meeptechnicaltest.domain.errors

class NetworkError(message: String) : BaseError(message)
class ParseError(message: String) : BaseError(message)
open class BaseError(override val message: String) : Throwable(message)