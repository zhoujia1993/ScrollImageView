package com.xfzj.lvsad

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.btnlv) {
            startActivity(Intent(this, ListViewActivity::class.java))
        }else if (v?.id == R.id.btnrv) {
            startActivity(Intent(this, RecyclerViewActivity::class.java))
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btnlv).setOnClickListener(this)
        findViewById<Button>(R.id.btnrv).setOnClickListener(this)

    }
}
