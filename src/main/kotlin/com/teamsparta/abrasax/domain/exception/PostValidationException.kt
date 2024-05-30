package com.teamsparta.abrasax.domain.exception

open class PostValidationException(message: String) : RuntimeException(message)
class InvalidTitleException(message: String) : RuntimeException(message)
class InvalidContentException(message: String) : RuntimeException(message)
class InvalidTagSizeException(message: String) : RuntimeException(message)
class InvalidTagLengthException(message: String) : RuntimeException(message)
class InvalidDuplicateTagException(message: String) : RuntimeException(message)