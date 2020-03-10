package com.aizidev.themovies.util

private const val BASE_PROTOCOL = "https://"
private const val BASE_PROTOCOL_IMG = "http://"
private const val BASE_IP =  "api.themoviedb.org"
private const val BASE_IP_IMG =  "image.tmdb.org"
const val BASE_URL = "$BASE_PROTOCOL$BASE_IP"
const val BASE_URL_IMG = "$BASE_PROTOCOL_IMG$BASE_IP_IMG/t/p/w185/"

// PARAM INTENT MIDDLE
const val INTENT_CODE_FROM_ACADEMY_SEARCH = 1
const val INTENT_CODE_FROM_ACADEMY_CATEGORY = 2

// LOGIN PARAMETER SAVE (For need in application)
const val KEY_PREFF_AUTHENTICATION = "AUTHENTICATION"
const val KEY_PREFF_TOKEN = "a31a792105e2afceefc645005dc7569d"

// REQUEST CODE ACTIVITY RESULT
const val REQUEST_CODE_FROM_PRIVATE_INFO = 1
const val RESULT_CODE_FROM_PRIVATE_INFO = "DATE_BORN"
const val REQUEST_CODE_FROM_REGISTER = 1
const val RESULT_CODE_FROM_REGISTER = "COUNTRY_CODE"
