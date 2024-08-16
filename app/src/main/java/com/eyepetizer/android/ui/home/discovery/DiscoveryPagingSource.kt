package com.eyepetizer.android.ui.home.discovery

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eyepetizer.android.logic.model.Discovery
import com.eyepetizer.android.logic.network.api.MainPageService

class DiscoveryPagingSource(private val mainPageService: MainPageService) : PagingSource<String, Discovery.Item>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Discovery.Item> {
        return try {
            val page = params.key ?: MainPageService.DISCOVERY_URL
            val repoResponse = mainPageService.getDiscovery(page)
            val repoItems = repoResponse.itemList
            val prevKey = null
            val nextKey = if (repoItems.isNotEmpty() && !repoResponse.nextPageUrl.isNullOrEmpty()) repoResponse.nextPageUrl else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Discovery.Item>): String? = null
}