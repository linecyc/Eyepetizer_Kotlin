package com.linecy.copy.ui.detail

import android.arch.lifecycle.ViewModel
import android.content.pm.ActivityInfo
import android.databinding.ViewDataBinding
import android.graphics.Point
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.SystemClock
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import com.linecy.copy.R
import com.linecy.copy.navigation.Navigator
import com.linecy.copy.ui.BaseActivity
import com.linecy.copy.ui.detail.adapter.VideoDetailAdapter
import com.linecy.copy.ui.misc.ijk.IMediaController
import com.linecy.copy.ui.widget.SimpleIJKVideoPlayer
import com.linecy.copy.utils.BindingUIUtil
import com.linecy.copy.utils.TimeUtils
import com.linecy.eyepetizer.data.model.ItemList
import kotlinx.android.synthetic.main.activity_video_detail.ivPlayLoading
import kotlinx.android.synthetic.main.activity_video_detail.layoutPlayControl
import kotlinx.android.synthetic.main.activity_video_detail.recyclerView
import kotlinx.android.synthetic.main.activity_video_detail.videoPlayer
import kotlinx.android.synthetic.main.layout_play_control.groupFull
import kotlinx.android.synthetic.main.layout_play_control.groupMin
import kotlinx.android.synthetic.main.layout_play_control.ivFull
import kotlinx.android.synthetic.main.layout_play_control.ivLock
import kotlinx.android.synthetic.main.layout_play_control.ivMin
import kotlinx.android.synthetic.main.layout_play_control.ivMore
import kotlinx.android.synthetic.main.layout_play_control.ivPlayPause
import kotlinx.android.synthetic.main.layout_play_control.seekBar
import kotlinx.android.synthetic.main.layout_play_control.tvCurrent
import kotlinx.android.synthetic.main.layout_play_control.tvDrag
import kotlinx.android.synthetic.main.layout_play_control.tvDuration
import tv.danmaku.ijk.media.player.IjkMediaPlayer

/**
 * @author by linecy.
 */
class VideoDetailActivity : BaseActivity<ViewDataBinding, ViewModel>(), IMediaController {

  private var lastClickTime = 0L
  private val DEFAULT_TIME = 400

  override fun layoutResId(): Int {
    return R.layout.activity_video_detail
  }

  override fun onInitView(savedInstanceState: Bundle?) {

    hideToolBar()
    layoutPlayControl.setOnClickListener(this)

    val bundle = intent.extras
    if (bundle != null) {
      val itemList = bundle.getParcelable<ItemList>(Navigator.EXTRA_DATA)

      recyclerView.layoutManager = LinearLayoutManager(this)
      BindingUIUtil.setBackground(recyclerView, itemList.data?.cover?.blurred)
      val adapter = VideoDetailAdapter(this, itemList)
      recyclerView.adapter = adapter

      val data = ArrayList<ItemList>()
      for (i in 0 until 10) {
        data.add(itemList)
      }
      adapter.refreshData(data)

      val url = itemList.data?.playUrl
      videoPlayer.setUpWithControlView(this)
      videoPlayer.setOnClickListener(this)
      layoutPlayControl.setOnClickListener(this)
      videoPlayer.setUpWithSeekBar(seekBar)
      IjkMediaPlayer.loadLibrariesOnce(null)
      IjkMediaPlayer.native_profileBegin("libijkplayer.so")
      setClickListener(ivMin, ivFull, ivPlayPause, ivMore, ivLock)

      if (!TextUtils.isEmpty(url)) {
        videoPlayer.setupWithUrl(url!!)
      }
    }

  }

  /**
   * 批量设置监听
   */
  private fun setClickListener(vararg views: View) {
    views.forEach {
      it.setOnClickListener(this)
    }
  }

  override fun onClick(p0: View?) {
    super.onClick(p0)
    when (p0?.id) {
      R.id.ivFull -> {
        if (!ivLock.isSelected) {
          setFullScreen()
        }
      }
      R.id.ivMin -> {
        if (ivMin.isSelected) {
          if (!ivLock.isSelected) {
            setFullScreen()
          }
        } else {
          finish()
        }
      }
      R.id.ivPlayPause -> {
        if (!ivLock.isSelected) {
          if (ivPlayPause.isSelected) {
            ivPlayPause.isSelected = false
            videoPlayer.onPause()
          } else {
            ivPlayPause.isSelected = true
            videoPlayer.onStart()
          }
        }
      }
      R.id.ivLock -> {
        ivLock.isSelected = !ivLock.isSelected
      }

      R.id.videoPlayer, R.id.layoutPlayControl -> {
        val current = SystemClock.elapsedRealtime()
        if (current - lastClickTime > DEFAULT_TIME) {
          lastClickTime = current
        } else if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
          setFullScreen()
        }
      }
    }
  }

  override fun onHide() {
    layoutPlayControl.visibility = View.GONE
  }

  override fun onShow() {
    layoutPlayControl.visibility = View.VISIBLE
  }

  override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean,
      isShowing: Boolean) {
    if (fromUser) {
      val s = TimeUtils.formatTime(progress)
      if (!TextUtils.isEmpty(s))
        if (tvDrag.visibility != View.VISIBLE) {
          tvDrag.visibility = View.VISIBLE
        }
      tvDrag.text = s
    } else {
      if (isShowing) {
        tvCurrent.text = TimeUtils.formatTime(seekBar?.progress!!)
      }
    }
  }

  override fun onStartTrackingTouch(seekBar: SeekBar?) {
    //ignore
  }

  override fun onStopTrackingTouch(seekBar: SeekBar?) {
    if (tvDrag.visibility == View.VISIBLE) {
      tvDrag.visibility = View.GONE
    }
  }

  override fun onPlayStateChanged(state: Int, player: SimpleIJKVideoPlayer?) {
    when (state) {
      SimpleIJKVideoPlayer.STATE_BUFFER -> {
        loadLoading(true)
        ivPlayPause.isSelected = false
      }
      SimpleIJKVideoPlayer.STATE_PREPARED -> {
        loadLoading(false)
        ivPlayPause.isSelected = true
      }
      SimpleIJKVideoPlayer.STATE_START, SimpleIJKVideoPlayer.STATE_PLAY -> {
        loadLoading(false)
        ivPlayPause.isSelected = true

        seekBar.max = (player?.duration!! / 1000).toInt()
        tvDuration.text = TimeUtils.formatTime(seekBar.max)
      }
      SimpleIJKVideoPlayer.STATE_COMPLETION -> {
        //修复刚好在控制层隐藏时播放结束进度没有更新的问题
        tvCurrent.text = tvDuration.text
        loadLoading(false)
        ivPlayPause.isSelected = false
      }
      else -> {
        loadLoading(false)
        ivPlayPause.isSelected = false
      }
    }
  }

  private fun loadLoading(isLoading: Boolean) {
    val animation: AnimationDrawable = ivPlayLoading.drawable as AnimationDrawable
    if (isLoading) {
      if (ivPlayLoading.visibility != View.VISIBLE) {
        ivPlayLoading.visibility = View.VISIBLE
        animation.start()
      }
    } else {
      if (ivPlayLoading.visibility != View.GONE) {
        animation.stop()
        ivPlayLoading.visibility = View.GONE
      }
    }
  }

  override fun onBackPressed() {
    //全屏时返回，切换为竖屏,同时解锁
    if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
      ivLock.isSelected = false
      setFullScreen()
    } else {
      super.onBackPressed()
    }
  }

  override fun onStop() {
    super.onStop()
    videoPlayer.onStop()
    IjkMediaPlayer.native_profileEnd()
  }

  /**
   * 横竖屏切换
   */
  private fun setFullScreen() {
    ivMin.isSelected = !ivMin.isSelected
    if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
      requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
      window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
      val display = windowManager.defaultDisplay
      val size = Point()
      display.getSize(size)
      videoPlayer.layoutParams = ConstraintLayout.LayoutParams(size.x,
          (size.x * 9 / 16f).toInt())
      recyclerView.visibility = View.VISIBLE
      groupFull.visibility = View.GONE
      groupMin.visibility = View.VISIBLE
    } else {
      requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
      window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
      videoPlayer.layoutParams = ConstraintLayout.LayoutParams(
          ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
      recyclerView.visibility = View.GONE
      groupFull.visibility = View.VISIBLE
      groupMin.visibility = View.GONE
    }
  }
}