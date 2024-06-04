package ru.blackmirrror.myplaces.api.models

import kotlinx.serialization.SerialName

data class UserResponseDto (
    @SerialName("id"          ) var id          : Long,
    @SerialName("username"    ) var username    : String
)

data class UserRequestDto (
    @SerialName("username"    ) var username    : String,
    @SerialName("password"    ) var password    : String
)

data class MarkDto (
    @SerialName("id"          ) var id          : Long?  = null,
    @SerialName("latitude"    ) var latitude    : Double,
    @SerialName("longitude"   ) var longitude   : Double,
    @SerialName("description" ) var description : String,
    @SerialName("imageUrl"    ) var imageUrl    : String?  = null,
    @SerialName("likes"       ) var likes       : Int = 0,
    @SerialName("user"        ) var user        : UserResponseDto?  = null,
    @SerialName("dateChanges" ) var dateChanges : Long?    = null,
    @SerialName("dateCreate"  ) var dateCreate  : Long?    = null
) {
    fun toMarlLocal(isFavorite: Boolean = false): MarkLocal {
        return MarkLocal(
            id = id,
            latitude = latitude,
            longitude = longitude,
            description = description,
            imageUrl = imageUrl,
            likes = likes,
            user = user,
            dateChanges = dateChanges,
            dateCreate = dateCreate,
            isFavorite = isFavorite
        )
    }
}

data class FavoriteDto (
    @SerialName("id"          ) var id          : Long?  = null,
    @SerialName("mark"        ) var mark        : MarkDto,
    @SerialName("user"        ) var user        : UserResponseDto,
)

data class SubscribeDto (
    @SerialName("id"          ) var id          : Long?  = null,
    @SerialName("user"        ) var user        : UserResponseDto,
    @SerialName("subscribe"   ) var subscribe   : UserResponseDto,
)

data class MarkLocal (
    @SerialName("id"          ) var id          : Long?  = null,
    @SerialName("latitude"    ) var latitude    : Double,
    @SerialName("longitude"   ) var longitude   : Double,
    @SerialName("description" ) var description : String,
    @SerialName("imageUrl"    ) var imageUrl    : String?  = null,
    @SerialName("likes"       ) var likes       : Int = 0,
    @SerialName("user"        ) var user        : UserResponseDto?  = null,
    @SerialName("dateChanges" ) var dateChanges : Long?    = null,
    @SerialName("dateCreate"  ) var dateCreate  : Long?    = null,
    var isFavorite: Boolean = false
) {
    fun toMarkDto(): MarkDto {
        return MarkDto(
            id = id,
            latitude = latitude,
            longitude = longitude,
            description = description,
            imageUrl = imageUrl,
            likes = likes,
            user = user,
            dateChanges = dateChanges,
            dateCreate = dateCreate
        )
    }
}