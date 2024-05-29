package com.teamsparta.abrasax.domain.exception

data class TagNotFoundException(val tag: String) :
    RuntimeException("Tag Not Found with tag  $tag")