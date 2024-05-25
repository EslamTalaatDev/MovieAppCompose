package com.bmmovtask.data.remote.api.others

import com.bmmovtask.data.model.CollectionResponse
import com.bmmovtask.data.model.Config
import com.bmmovtask.data.model.DeviceLanguage
import retrofit2.Call

interface TmdbOthersApiHelper {
    fun getConfig(): Call<Config>


    fun getCollection(
        collectionId: Int,
        isoCode: String = DeviceLanguage.default.languageCode
    ): Call<CollectionResponse>

}