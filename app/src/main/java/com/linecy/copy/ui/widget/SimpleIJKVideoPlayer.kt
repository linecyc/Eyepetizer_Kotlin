package com.linecy.copy.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import com.linecy.copy.ui.misc.ijk.IMediaController
import com.linecy.copy.ui.misc.ijk.MediaPlayerService
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.ISurfaceTextureHolder
import tv.danmaku.ijk.media.player.IjkMediaPlayer

/**
 * @author by linecy.
 */

class SimpleIJKVideoPlayer : FrameLayout {


  // all possible internal states
  private val STATE_ERROR = -1
  private val STATE_IDLE = 0
  private val STATE_PREPARING = 1
  private val STATE_PREPARED = 2
  private val STATE_PLAYING = 3
  private val STATE_PAUSED = 4
  private val STATE_PLAYBACK_COMPLETED = 5

  private var ijkMediaPlayer: IMediaPlayer? = null

  private var surfaceView: SurfaceView? = null
  private lateinit var mContext: Context

  private val callback: SurfaceHolder.Callback by lazy { HolderCallback() }
  private var url: String? = null
  private var videoPlayerListener: VideoPlayerListener? = null
  private var currentState: Int = STATE_IDLE
  private var targetState = STATE_IDLE
  private var enableBackgroundPlay: Boolean = false

  private var seekWhenPrepared: Long = 0

  private var controllerView: View? = null

  constructor(@NonNull context: Context) : this(context, null)
  constructor(@NonNull context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)
  constructor(@NonNull context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(
      context, attrs, defStyleAttr) {
    init(context)
  }


  private fun init(context: Context) {
    this.mContext = context
    initBackground()
  }


  private fun initBackground() {
    if (enableBackgroundPlay) {
      MediaPlayerService.intentToStart(context)
      ijkMediaPlayer = MediaPlayerService.getMediaPlayer()
    }
  }

  private fun createPlayer(): IMediaPlayer {
    val ijkMediaPlayer = IjkMediaPlayer()

    //开启硬解码
    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1)

    return ijkMediaPlayer
  }

  private fun loadVideo() {
    if (TextUtils.isEmpty(url) || null == surfaceView || null == surfaceView?.holder) {
      //not ready for playback just yet, will try again later
      return
    }

    release(false)

    ijkMediaPlayer = createPlayer()

    if (null != ijkMediaPlayer && null != videoPlayerListener) {
      ijkMediaPlayer?.setOnPreparedListener(videoPlayerListener)
      ijkMediaPlayer?.setOnVideoSizeChangedListener(videoPlayerListener)
      ijkMediaPlayer?.setOnCompletionListener(videoPlayerListener)
      ijkMediaPlayer?.setOnErrorListener(videoPlayerListener)
      ijkMediaPlayer?.setOnInfoListener(videoPlayerListener)
      ijkMediaPlayer?.setOnBufferingUpdateListener(videoPlayerListener)
      ijkMediaPlayer?.setOnSeekCompleteListener(videoPlayerListener)
      ijkMediaPlayer?.setOnTimedTextListener(videoPlayerListener)

      ijkMediaPlayer?.dataSource = url
    }


    bindSurfaceHolder(surfaceView?.holder)
    ijkMediaPlayer?.prepareAsync()

    currentState = STATE_PREPARED
    attachMediaController()

  }

  private fun createSurfaceView() {

    surfaceView = SurfaceView(mContext)
    surfaceView?.holder?.addCallback(callback)
    val lp = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.WRAP_CONTENT,
        Gravity.CENTER)
    this.addView(surfaceView, lp)
  }


  private fun bindSurfaceHolder(holder: SurfaceHolder?) {
    if (ijkMediaPlayer == null) {
      return
    }
    if (holder == null) {
      ijkMediaPlayer?.setDisplay(null)
      ijkMediaPlayer?.release()
    }

    if (ijkMediaPlayer != null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && ijkMediaPlayer is ISurfaceTextureHolder) {
        val textureHolder = ijkMediaPlayer as ISurfaceTextureHolder
        textureHolder.surfaceTexture = null
      }
      ijkMediaPlayer?.setDisplay(surfaceView?.holder)
    }

  }

  fun releaseWithoutStop() {
    if (ijkMediaPlayer != null)
      ijkMediaPlayer?.setDisplay(null)
  }


  private fun isInPlaybackState(): Boolean {
    return ijkMediaPlayer != null &&
        currentState != STATE_ERROR &&
        currentState != STATE_IDLE &&
        currentState != STATE_PREPARING
  }

  private inner class HolderCallback : SurfaceHolder.Callback {

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
      if (holder != surfaceView?.holder) {
        return
      }

      if (ijkMediaPlayer != null && targetState == STATE_PLAYING) {
        if (seekWhenPrepared != 0L) {
          onSeekTo(seekWhenPrepared)
        }
        onStart()
      }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
      if (holder != surfaceView?.holder) {
        return
      }
      surfaceView?.holder?.removeCallback(callback)
      surfaceView = null
      releaseWithoutStop()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {

      if (ijkMediaPlayer != null) {
        bindSurfaceHolder(holder)
      } else {
        loadVideo()
      }
    }

  }


  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(ev: MotionEvent): Boolean {
    if (isInPlaybackState() && controllerView != null) {
      toggleMediaControlsVisibility()
    }
    return false
  }

  override fun onTrackballEvent(ev: MotionEvent): Boolean {
    if (isInPlaybackState() && controllerView != null) {
      toggleMediaControlsVisibility()
    }
    return false
  }

  /**
   * 在显示的时候判断点击的时候是控制按钮，是的话不隐藏，不是的话才隐藏？
   */
  private fun toggleMediaControlsVisibility() {
    if (View.VISIBLE == controllerView?.visibility) {
      controllerView?.visibility = View.GONE
    } else {
      controllerView?.visibility = View.VISIBLE
    }
  }

  /**
   * Player controller method.
   */


  fun setupWithUrl(url: String?) {

    if (!TextUtils.isEmpty(url)) {
      if (TextUtils.isEmpty(this.url)) {
        this.url = url
        createSurfaceView()
      } else {
        this.url = url
        loadVideo()
      }
      requestLayout()
      invalidate()

    } else {
      throw IllegalArgumentException("url 不能为空")
    }
  }


  fun setupWithControllerView(view: IMediaController) {
    if (view is View) {
      this.controllerView = view
      attachMediaController()
      requestLayout()
      invalidate()
    }
  }

  private fun attachMediaController() {
    if (ijkMediaPlayer != null && controllerView != null) {
      val lp = FrameLayout.LayoutParams(
          FrameLayout.LayoutParams.MATCH_PARENT,
          FrameLayout.LayoutParams.WRAP_CONTENT,
          Gravity.CENTER)
      this.addView(controllerView, lp)
    }
  }


  fun setVideoPlayerListener(l: VideoPlayerListener?) {
    this.videoPlayerListener = l
  }


  fun isPlaying(): Boolean {
    return ijkMediaPlayer != null && ijkMediaPlayer?.isPlaying!!
  }

  fun enterBackground() {
    MediaPlayerService.setMediaPlayer(ijkMediaPlayer)
  }

  fun isBackgroundPlayEnabled(): Boolean {
    return enableBackgroundPlay
  }

  /*
     * release the media player in any state
     */
  fun release(clearTargetState: Boolean) {
    if (ijkMediaPlayer != null) {
      ijkMediaPlayer?.reset()
      ijkMediaPlayer?.release()
      ijkMediaPlayer = null
      currentState = STATE_IDLE
      targetState = STATE_IDLE
      if (clearTargetState) {
        currentState = STATE_IDLE
        targetState = STATE_IDLE
      }
    }
  }

  fun onStart() {
    if (isInPlaybackState()) {
      ijkMediaPlayer?.start()
      currentState = STATE_PLAYING
    }
    targetState = STATE_PLAYING

  }


  fun onStop() {

    if (isInPlaybackState()) {
      ijkMediaPlayer?.start()
      currentState = STATE_PLAYING
    }
    targetState = STATE_PAUSED
  }

  fun stopPlayback() {
    if (ijkMediaPlayer != null) {
      ijkMediaPlayer?.stop()
      ijkMediaPlayer?.release()
      ijkMediaPlayer = null
      if (null != surfaceView && surfaceView?.holder != null)
        currentState = STATE_IDLE
    }
  }


  fun stopBackgroundPlay() {
    MediaPlayerService.setMediaPlayer(null)
  }

  fun release() {
    if (ijkMediaPlayer != null) {
      ijkMediaPlayer?.reset()
      ijkMediaPlayer?.release()
      ijkMediaPlayer = null
    }
  }

  fun onPause() {
    ijkMediaPlayer?.pause()
    targetState = STATE_PAUSED
  }


  fun onReset() {
    ijkMediaPlayer?.reset()
  }


  fun onSeekTo(l: Long) {
    ijkMediaPlayer?.seekTo(l)

  }


  fun getDuration(): Long {
    return if (ijkMediaPlayer != null) {
      ijkMediaPlayer?.duration!!
    } else {
      0
    }
  }

}