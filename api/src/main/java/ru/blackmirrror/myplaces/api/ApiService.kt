package ru.blackmirrror.myplaces.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.blackmirrror.myplaces.api.models.FavoriteDto
import ru.blackmirrror.myplaces.api.models.MarkDto
import ru.blackmirrror.myplaces.api.models.SubscribeDto
import ru.blackmirrror.myplaces.api.models.UserRequestDto
import ru.blackmirrror.myplaces.api.models.UserResponseDto

interface ApiService {

    @POST("users/register")
    suspend fun registerUser(@Body userRequest: UserRequestDto): Response<UserResponseDto>

    @POST("users/login")
    suspend fun loginUser(@Body userRequest: UserRequestDto): Response<UserResponseDto>

    @GET("marks")
    suspend fun getAllMarks(): Response<List<MarkDto>>

    @POST("marks")
    suspend fun createMark(@Body mark: MarkDto): Response<MarkDto>

    @PUT("marks/{id}")
    suspend fun updateMark(@Path("id") id: Long, @Body mark: MarkDto): Response<MarkDto>

    @DELETE("marks/{id}")
    suspend fun deleteMark(@Path("id") id: Long): Response<Unit>

    @GET("marks/favorite/{id}")
    suspend fun getAllFavoriteMarksByUserId(@Path("id") id: Long): Response<List<MarkDto>>

    @POST("marks/favorite")
    suspend fun createFavorite(@Body favorite: FavoriteDto): Response<FavoriteDto>

    @DELETE("marks/favorite/{id}")
    suspend fun deleteFavorite(@Path("id") id: Long): Response<Unit>

    @GET("subscribes/{id}")
    suspend fun getAllSubscribesByUserId(@Path("id") id: Long): Response<List<UserResponseDto>>

    @POST("subscribes")
    suspend fun createSubscribe(@Body subscribe: SubscribeDto): Response<SubscribeDto>

    @DELETE("subscribes/{id}")
    suspend fun deleteSubscribe(@Path("id") id: Long): Response<Unit>
}