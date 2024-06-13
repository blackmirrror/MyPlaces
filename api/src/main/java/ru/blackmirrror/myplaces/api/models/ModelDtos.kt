package ru.blackmirrror.myplaces.api.models

import kotlinx.serialization.SerialName

data class UserResponseDto (
    @SerialName("id"          ) val id          : Long,
    @SerialName("username"    ) val username    : String,
    @SerialName("email"       ) val email       : String
)

data class UserRequestDto (
    @SerialName("username"    ) val username    : String? = null,
    @SerialName("email"       ) val email       : String,
    @SerialName("password"    ) val password    : String
)

data class MarkDto (
    @SerialName("id"          ) val id          : Long?  = null,
    @SerialName("latitude"    ) val latitude    : Double,
    @SerialName("longitude"   ) val longitude   : Double,
    @SerialName("description" ) val description : String,
    @SerialName("imageUrl"    ) val imageUrl    : String?  = null,
    @SerialName("likes"       ) val likes       : Int = 0,
    @SerialName("user"        ) val user        : UserResponseDto?  = null,
    @SerialName("dateChanges" ) val dateChanges : Long?    = null,
    @SerialName("dateCreate"  ) val dateCreate  : Long?    = null
)

data class FavoriteDto (
    @SerialName("id"          ) val id          : Long?  = null,
    @SerialName("mark"        ) val mark        : MarkDto,
    @SerialName("user"        ) val user        : UserResponseDto,
)

data class SubscribeDto (
    @SerialName("id"          ) val id          : Long?  = null,
    @SerialName("user"        ) val user        : UserResponseDto,
    @SerialName("subscribe"   ) val subscribe   : UserResponseDto,
)