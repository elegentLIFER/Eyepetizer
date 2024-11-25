package com.eyepetizer.android.logic.network

import com.eyepetizer.android.logic.network.api.MainPageService
import com.eyepetizer.android.logic.network.api.VideoService

/**
 * 管理所有网络请求。
 */
class EyepetizerNetwork {

    var mainPageService = ServiceCreator.create(MainPageService::class.java)
        private set

    private val videoService = ServiceCreator.create(VideoService::class.java)

    suspend fun fetchHotSearch() = mainPageService.getHotSearch()

    suspend fun fetchVideoBeanForClient(videoId: Long) = videoService.getVideoBeanForClient(videoId)

    suspend fun fetchVideoRelated(videoId: Long) = videoService.getVideoRelated(videoId)

    suspend fun fetchVideoReplies(url: String) = videoService.getVideoReplies(url)

    companion object {

        @Volatile
        private var INSTANCE: EyepetizerNetwork? = null

        fun getInstance(): EyepetizerNetwork = INSTANCE ?: synchronized(this) {
            INSTANCE ?: EyepetizerNetwork()
        }
    }
}