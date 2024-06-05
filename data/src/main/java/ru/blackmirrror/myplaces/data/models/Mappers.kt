package ru.blackmirrror.myplaces.data.models

import ru.blackmirrror.myplaces.api.models.MarkDto
import ru.blackmirrror.myplaces.api.models.UserRequestDto
import ru.blackmirrror.myplaces.api.models.UserResponseDto

internal fun MarkDto.toMark(isFavorite: Boolean = false): Mark {
    return Mark(
        id = id,
        latitude = latitude,
        longitude = longitude,
        description = description,
        imageUrl = imageUrl,
        likes = likes,
        user = user?.toUserResponse(),
        dateChanges = dateChanges,
        dateCreate = dateCreate,
        isFavorite = isFavorite
    )
}

internal fun Mark.toMarkDto(): MarkDto {
    return MarkDto(
        id = id,
        latitude = latitude,
        longitude = longitude,
        description = description,
        imageUrl = imageUrl,
        likes = likes,
        user = user?.toUserResponseDto(),
        dateChanges = dateChanges,
        dateCreate = dateCreate
    )
}

internal fun UserResponseDto.toUserResponse(): UserResponse {
    return UserResponse(
        id = id,
        username = username
    )
}

internal fun UserResponse.toUserResponseDto(): UserResponseDto {
    return UserResponseDto(
        id = id,
        username = username
    )
}

internal fun UserRequestDto.toUserRequest(): UserRequest {
    return UserRequest(
        username = username,
        password = password
    )
}

internal fun UserRequest.toUserRequestDto(): UserRequestDto {
    return UserRequestDto(
        username = username,
        password = password
    )
}
