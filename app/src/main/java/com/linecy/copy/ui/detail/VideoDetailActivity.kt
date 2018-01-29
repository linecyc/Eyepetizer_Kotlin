package com.linecy.copy.ui.detail

import android.arch.lifecycle.ViewModel
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.linecy.copy.R
import com.linecy.copy.navigation.Navigator
import com.linecy.copy.ui.BaseActivity
import com.linecy.copy.ui.detail.adapter.VideoDetailAdapter
import com.linecy.copy.ui.widget.VideoPlayerListener
import com.linecy.copy.utils.BindingUIUtil
import com.linecy.eyepetizer.data.model.ItemList
import kotlinx.android.synthetic.main.activity_video_detail.recyclerView
import kotlinx.android.synthetic.main.activity_video_detail.videoPlayer
import timber.log.Timber
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import tv.danmaku.ijk.media.player.IjkTimedText

/**
 * @author by linecy.
 */
class VideoDetailActivity : BaseActivity<ViewDataBinding, ViewModel>() {


  private var mBackPressed = false

  override fun layoutResId(): Int {
    return R.layout.activity_video_detail
  }

  override fun onInitView(savedInstanceState: Bundle?) {

    hideToolBar()
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
      videoPlayer.setVideoPlayerListener(VideoListener())
      IjkMediaPlayer.loadLibrariesOnce(null)
      IjkMediaPlayer.native_profileBegin("libijkplayer.so")
      videoPlayer.setupWithUrl(url)
      //videoPlayer.setupWithControllerView(layoutInflater.inflate(R.layout.layout_video_cover, null))

    }

  }

  override fun onBackPressed() {
    mBackPressed = true

    super.onBackPressed()
  }

  override fun onStop() {
    super.onStop()
    if (mBackPressed || !videoPlayer.isBackgroundPlayEnabled()) {
      videoPlayer.stopPlayback()
      videoPlayer.release(true)
      videoPlayer.stopBackgroundPlay()
    } else {
      videoPlayer.enterBackground()
    }
    IjkMediaPlayer.native_profileEnd()
  }

  inner class VideoListener : VideoPlayerListener() {
    override fun onTimedText(p0: IMediaPlayer?, p1: IjkTimedText?) {
      Timber.i("-------->>onTimedText")
    }

    override fun onSeekComplete(p0: IMediaPlayer?) {
      Timber.i("-------->>onSeekComplete")
    }

    override fun onInfo(p0: IMediaPlayer?, p1: Int, p2: Int): Boolean {
      Timber.i("-------->>onInfo")

      return false
    }

    override fun onVideoSizeChanged(p0: IMediaPlayer?, p1: Int, p2: Int, p3: Int, p4: Int) {
      Timber.i("-------->>onVideoSizeChanged")

    }

    override fun onBufferingUpdate(p0: IMediaPlayer?, p1: Int) {
      Timber.i("-------->>onBufferingUpdate")

    }

    override fun onPrepared(p0: IMediaPlayer?) {
      Timber.i("-------->>onPrepared")

    }

    override fun onCompletion(p0: IMediaPlayer?) {
      Timber.i("-------->>onCompletion")

      p0?.seekTo(0)
      p0?.start()
    }

    override fun onError(p0: IMediaPlayer?, p1: Int, p2: Int): Boolean {
      Timber.i("-------->>onError")

      return false
    }

  }

}