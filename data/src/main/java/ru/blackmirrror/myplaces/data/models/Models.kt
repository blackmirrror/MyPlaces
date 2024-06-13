package ru.blackmirrror.myplaces.data.models

data class UserResponse (
    val id          : Long,
    val username    : String,
    val email       : String
)

data class UserRequest (
    val username: String? = null,
    val email       : String,
    val password    : String
)

data class FavoriteDto (
    val id          : Long?  = null,
    val mark        : Mark,
    val user        : UserResponse
)

data class SubscribeDto (
    val id          : Long?  = null,
    val user        : UserResponse,
    val subscribe   : UserResponse
)

data class Mark (
    val id          : Long?  = null,
    val latitude    : Double,
    val longitude   : Double,
    val description : String,
    val imageUrl    : String?  = null,
    val likes       : Int = 0,
    val user        : UserResponse?  = null,
    val dateChanges : Long?    = null,
    val dateCreate  : Long?    = null,
    val isFavorite: Boolean = false
)