package com.teamsparta.abrasax.domain.exception

data class TagNotFoundException(val id: String) :
    RuntimeException("Post Not Found with tag  $id")