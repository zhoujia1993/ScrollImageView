package com.xfzj.lvsad

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import java.util.*

class RecyclerViewActivity : AppCompatActivity() {
    private val rv: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.recyclerView)
    }

    private lateinit var adapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        val datas = mutableListOf<String>()
        for (i in 1..50) {
            datas.add(i.toString() + "\n\n\n\n\n\n" + "end")
        }
        adapter = RvAdapter(this, datas as ArrayList<String>?)
        val imageView = ScrollImageView(this)
        imageView.setImageResource(R.drawable.demo)
        adapter.setImageView(imageView)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        var originHeight = calculateImageHeight()

        rv.adapter = adapter
        imageView.initImageSize(Utils.getScreenWidth(this), originHeight)
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val recordSp = SparseArray<ItemRecord>(0)
            private var mCurrentfirstVisibleItem = 0
            private val originImageHeight = calculateImageHeight()
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val linearLayoutManager = rv.layoutManager as LinearLayoutManager
                mCurrentfirstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
                val firstView = recyclerView!!.getChildAt(0)
                if (firstView != null) {
                    var itemRecord: ItemRecord? = recordSp.get(mCurrentfirstVisibleItem)
                    if (itemRecord == null) {
                        itemRecord = ItemRecord()
                    }
                    itemRecord!!.height = firstView.height
                    itemRecord!!.top = firstView.top
                    recordSp.put(mCurrentfirstVisibleItem, itemRecord)
                    val h = getScrollY()//滚动距离
                    Log.i("aaa", "h=$h")
                    imageView.scrollImage(Math.max(0, Math.min(h, originImageHeight)))
                }

            }

            private fun getScrollY(): Int {
                var height = 0
                for (i in 0 until mCurrentfirstVisibleItem) {
                    var itemRecord: ItemRecord? = recordSp.get(i)
                    height += itemRecord?.height ?: 0
                }
                var itemRecord = recordSp.get(mCurrentfirstVisibleItem)
                if (null == itemRecord) {
                    itemRecord = ItemRecord()
                }
                return height - itemRecord.top

            }

            internal inner class ItemRecord {
                var height = 0
                var top = 0
            }
        }
        )


    }

    /**
     * 计算从listview的第一个item到imageview之前的高度，
     * 这个高度就是将图片放大之后的高度，
     * 可以保证滑到imageview之后，图片刚好也滑到底部
     */
    fun calculateImageHeight(): Int {
        var height = 0
        for (i in 0 until adapter.itemCount) {
            if (adapter.getItemViewType(i) == 0) {
                break
            }
            val viewholder = adapter.onCreateViewHolder(rv, adapter.getItemViewType(i))
            adapter.onBindViewHolder(viewholder, i)

            val view = viewholder.itemView
            view.measure(0, 0)
            height += view.measuredHeight

        }
        return height
    }
}
