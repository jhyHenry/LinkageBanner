package com.henry.linkagebanner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.henry.linkagebanner.view.LinkageBanner
import kotterknife.bindOptionalView

class MainActivity : AppCompatActivity() {
    private val linkageBanner: LinkageBanner? by bindOptionalView(R.id.linkageBanner)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //初始化
        val bgRes = listOf(R.drawable.bg1, R.drawable.bg2, R.drawable.bg3)
        val srcRes = listOf(R.drawable.src1, R.drawable.src2, R.drawable.src3)
        linkageBanner?.setRes(bgRes, srcRes)
    }
}
