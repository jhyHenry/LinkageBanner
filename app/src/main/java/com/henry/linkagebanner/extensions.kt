package com.henry.linkagebanner

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 *@author  henry
 *@description 扩展函数
 *@date    2018/5/26
 */

fun ImageView.loadImg(url: Any) {
    Glide.with(context).load(url).into(this)
}