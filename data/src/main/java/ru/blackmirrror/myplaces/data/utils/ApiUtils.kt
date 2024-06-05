package ru.blackmirrror.myplaces.data.utils

import retrofit2.Response
import ru.blackmirrror.myplaces.data.models.Conflict
import ru.blackmirrror.myplaces.data.models.NoContent
import ru.blackmirrror.myplaces.data.models.NotFound
import ru.blackmirrror.myplaces.data.models.OtherError
import ru.blackmirrror.myplaces.data.models.ResultState
import ru.blackmirrror.myplaces.data.models.ServerError

object ApiUtils {
    fun <T> handleResponse(response: Response<T>): ResultState<T> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null)
                ResultState.Success(body)
            else
                ResultState.Error(NoContent)
        } else if (response.code() == 404)
            return ResultState.Error(NotFound)
        else if (response.code() == 409)
            return ResultState.Error(Conflict)
        else if (response.code() >= 500)
            ResultState.Error(ServerError)
        else
            ResultState.Error(OtherError)
    }
}