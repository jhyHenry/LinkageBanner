package com.henry.linkagebanner.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.henry.linkagebanner.R
import com.henry.linkagebanner.loadImg
import com.youth.banner.Banner
import com.youth.banner.loader.ImageLoader
import kotterknife.bindOptionalView

/**
 *@author  jiahongyu
 *@description
 *@date    18-5-14
 */
class LinkageBanner @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private val bgBanner: LinkageBackground? by bindOptionalView(R.id.bgBanner)
    private val srcBanner: Banner? by bindOptionalView(R.id.srcBanner)

    private var isScrolling = false
    private var currentPosition = 0
    private var beforeX: Float = 0f//手指刚触摸时的x坐标
    var isScroll2Left = false
    var isScroll2Right = false

    init {
        initView()
    }

    fun initView() {
        View.inflate(context, R.layout.layout_linkage_banner, this)
    }

    fun setRes(bgRes: List<Any>, srcRes: List<Any>) {
        bgBanner?.setBgRes(bgRes)
        srcBanner?.setImageLoader(BannerImgLoader())
        srcBanner?.setImages(srcRes)
        srcBanner?.start()
        setListener()
    }

    fun getBanner(): Banner {
        return srcBanner!!
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN ->
                //获取起始坐标值
                beforeX = ev.x
            MotionEvent.ACTION_MOVE ->
                if (ev.x < beforeX) { // 向左侧滑动
                    isScroll2Left = true
                    isScroll2Right = false
                } else if (ev.x > beforeX) {// 向右侧滑动
                    isScroll2Right = true
                    isScroll2Left = false
                }
            else -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun setListener() {
        srcBanner?.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (isScrolling) {
                    //根据滑动方向改变背景透明度
                    if (isScroll2Left && positionOffset > 0) {
                        bgBanner?.scroll2Left(positionOffset)
                    } else if (isScroll2Right && positionOffset > 0) {
                        bgBanner?.scroll2Right(positionOffset)
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
                bgBanner?.resetImg(currentPosition)
            }

            override fun onPageScrollStateChanged(state: Int) {
                isScrolling = state == 1
            }
        })
    }

    class BannerImgLoader : ImageLoader() {
        /**
         * 注意：
         * 1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         * 2.返回的图片路径为Object类型，由于不能确定到底使用的那种图片加载器，
         * 传输的到的是什么格式，那么这种就使用Object接收和返回，只需要强转成你传输的类型就行，
         */
        override fun displayImage(context: Context, path: Any, imageView: ImageView) {
            imageView.loadImg(path)
        }
    }
}