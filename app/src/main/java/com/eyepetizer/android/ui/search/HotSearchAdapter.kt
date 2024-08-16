package com.eyepetizer.android.ui.search

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eyepetizer.android.BuildConfig
import com.eyepetizer.android.Const
import com.eyepetizer.android.R
import com.eyepetizer.android.extension.gone
import com.eyepetizer.android.extension.inflate
import com.eyepetizer.android.extension.showToast
import com.eyepetizer.android.util.GlobalUtil

/**
 * 热搜关键词Adapter
 */
class HotSearchAdapter(val fragment: SearchFragment, var dataList: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = dataList.size + 1

    override fun getItemViewType(position: Int) = when (position) {
        0 -> Const.ItemViewType.CUSTOM_HEADER
        else -> HOT_SEARCH_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        Const.ItemViewType.CUSTOM_HEADER -> HeaderViewHolder(R.layout.item_search_header.inflate(parent))
        else -> HotSearchViewHolder(R.layout.item_search.inflate(parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.tvTitle.text = GlobalUtil.getString(R.string.hot_keywords)
            }
            is HotSearchViewHolder -> {
                val item = dataList[position - 1]
                holder.tvKeywords.text = item
                holder.itemView.setOnClickListener {
                    "${item},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
                }
            }
            else -> {
                holder.itemView.gone()
                if (BuildConfig.DEBUG) "${TAG}:${Const.Toast.BIND_VIEWHOLDER_TYPE_WARN}\n${holder}".showToast()
            }
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    }

    class HotSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvKeywords = view.findViewById<TextView>(R.id.tvKeywords)
    }

    companion object {
        const val TAG = "HotSearchAdapter"
        const val HOT_SEARCH_TYPE = Const.ItemViewType.MAX
    }
}