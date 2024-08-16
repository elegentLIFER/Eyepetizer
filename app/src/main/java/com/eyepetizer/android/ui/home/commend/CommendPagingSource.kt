package com.eyepetizer.android.ui.home.commend

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eyepetizer.android.logic.model.HomePageRecommend
import com.eyepetizer.android.logic.network.api.MainPageService

class CommendPagingSource(private val mainPageService: MainPageService) : PagingSource<String, HomePageRecommend.Item>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, HomePageRecommend.Item> {
        return try {
            val page = params.key ?: MainPageService.HOMEPAGE_RECOMMEND_URL
            val repoResponse = mainPageService.getHomePageRecommend(page)
            val repoItems = repoResponse.itemList
            val prevKey = null
            val nextKey = if (repoItems.isNotEmpty() && !repoResponse.nextPageUrl.isNullOrEmpty()) repoResponse.nextPageUrl else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, HomePageRecommend.Item>): String? = null
}