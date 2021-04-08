package com.udacity.project4.utils

/**
 * Author: Ankush
 * On: 05 April 2021
 */

/**
 * A sealed class that encapsulates successful outcome with a value of type [T]
 * or a failure with message
 */

sealed class ResultData<out T> {
    object DoNothing : ResultData<Nothing>()
    object Loading : ResultData<Nothing>()
    data class Success<out T>(val data: T? = null) : ResultData<T>()
    data class Failed(val message: String? = null) : ResultData<Nothing>()
}