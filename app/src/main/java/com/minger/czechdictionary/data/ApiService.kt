package com.minger.czechdictionary.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("entries/cs/{word}")
    suspend fun getWord(
        @Path("word") word: String,
        @Query("translations") includeTranslation: Boolean = true,
    ): WordResponse
}