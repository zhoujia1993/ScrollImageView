package com.xfzj.lvsad

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import android.widget.AbsListView
import android.widget.ListView
import java.util.*

class ListViewActivity : AppCompatActivity() {
    private val lv: ListView by lazy {
        findViewById<ListView>(R.id.lv)
    }
    private lateinit var adapter: LvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        val datas = mutableListOf<String>()
        for (i in 1..50) {
            datas.add(i.toString() + "\n\n\n\n\n\n" + "end")
        }
        adapter = LvAdapter(this, datas as ArrayList<String>?)
        val imageView = ScrollImageView(this)
        imageView.setImageResource(R.drawable.demo)
        adapter.setImageView(imageView)
        val originHeight = calculateImageHeight()
        lv.adapter = adapter
//        lv.post {
        imageView.initImageSize(Utils.getScreenWidth(this), originHeight)

//        }
        lv.setOnScrollListener(object : AbsListView.OnScrollListener {
            private val recordSp = SparseArray<ItemRecord>(0)
            private var mCurrentfirstVisibleItem = 0
            private val originImageHeight = calculateImageHeight()

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
            }

            override fun onScroll(arg0: AbsListView?, arg1: Int, arg2: Int, arg3: Int) {

                mCurrentfirstVisibleItem = arg1
                val firstView = arg0!!.getChildAt(0)
                if (null != firstView) {
                    var itemRecord: ItemRecord? = recordSp.get(arg1)
                    if (null == itemRecord) {
                        itemRecord = ItemRecord()
                    }
                    itemRecord!!.height = firstView!!.getHeight()
                    itemRecord!!.top = firstView!!.getTop()
                    recordSp.append(arg1, itemRecord)
                    val h = getScrollY()//滚动距离
                    imageView.scrollImage(Math.max(0, Math.min(h, originImageHeight)))
                }
            }

            private fun getScrollY(): Int {
                var height = 0
                for (i in 0 until mCurrentfirstVisibleItem) {
                    val itemRecord = recordSp.get(i)
                    height += itemRecord?.height ?: 0
                }
                var itemRecord: ItemRecord? = recordSp.get(mCurrentfirstVisibleItem)
                if (null == itemRecord) {
                    itemRecord = ItemRecord()
                }
                return height - itemRecord!!.top
            }


            internal inner class ItemRecord {
                var height = 0
                var top = 0
            }


        })
    }

    /**
     * 计算从listview的第一个item到imageview之前的高度，
     * 这个高度就是将图片放大之后的高度，
     * 可以保证滑到imageview之后，图片刚好也滑到底部
     */
    fun calculateImageHeight(): Int {
        var height = 0
        for (i in 0..adapter.count) {
            if (adapter.getItemViewType(i) == 0) {
                break
            }
            val view = adapter.getView(i, null, lv)
            view.measure(0, 0)
            height += view.measuredHeight
        }
        return height
    }
}
