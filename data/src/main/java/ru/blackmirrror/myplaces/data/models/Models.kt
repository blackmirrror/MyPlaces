package ru.blackmirrror.myplaces.data.models

data class UserResponse (
    var id          : Long,
    var username    : String
)

data class UserRequest (
    var username    : String,
    var password    : String
)

data class FavoriteDto (
    var id          : Long?  = null,
    var mark        : Mark,
    var user        : UserResponse,
)

data class SubscribeDto (
    var id          : Long?  = null,
    var user        : UserResponse,
    var subscribe   : UserResponse,
)

data class Mark (
    var id          : Long?  = null,
    var latitude    : Double,
    var longitude   : Double,
    var description : String,
    var imageUrl    : String?  = null,
    var likes       : Int = 0,
    var user        : UserResponse?  = null,
    var dateChanges : Long?    = null,
    var dateCreate  : Long?    = null,
    var isFavorite: Boolean = false
)