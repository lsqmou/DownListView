package com.lmoumou.downlistview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_popup_content.view.*

/**
 * Author: Lmoumou
 * Date: 2018-07-25 10:14
 * 文件名: TestAdapter
 * 描述:
 */
class TestAdapter(context: Context, private val dataList: List<String>,
                  private val itemClick: (content: String) -> Unit) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {
    private val mInflater: LayoutInflater by lazy { LayoutInflater.from(context) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(mInflater.inflate(R.layout.item_popup_content, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.itemView.mTextView.text = dataList[position]
        holder.itemView.setOnClickListener {
            itemClick(dataList[position])
        }
    }

    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}