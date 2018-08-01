package com.lmoumou.downlistview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lmoumou.lib_downlistview.DownListView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val dataList: MutableList<String> by lazy { mutableListOf<String>() }
    private val adapter: TestAdapter by lazy {
        TestAdapter(this, dataList) {
            itemClick(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (m in 0 until 20) {
            dataList.add("item$m")
        }
        mDownListView.setBindListener(object : DownListView.BindListener {
            override fun bindAdapter(recycler: RecyclerView) {
                recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                //recycler.layoutManager = GridLayoutManager(this@MainActivity, 2)
                recycler.adapter = adapter
            }
        })


    }

    private fun itemClick(content: String) {
        mDownListView.textStr = content
        mDownListView.closePopWindow()
    }
}
