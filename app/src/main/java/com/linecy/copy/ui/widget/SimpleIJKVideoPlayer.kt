package com.linecy.copy.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.annotation.AttrRes
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.linecy.copy.ui.misc.ijk.IMediaController
import timber.log.Timber
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import java.io.IOException
import java.lang.ref.WeakReference

/**
 * 视频播放器
 * @author by linecy.
 */

class SimpleIJKVideoPlayer : FrameLayout {

  /**
   * int MEDIA_INFO_UNKNOWN = 1;
  int MEDIA_INFO_STARTED_AS_NEXT = 2;
  int MEDIA_INFO_VIDEO_RENDERING_START = 3;
  int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;
  int MEDIA_INFO_BUFFERING_START = 701;
  int MEDIA_INFO_BUFFERING_END = 702;
  int MEDIA_INFO_NETWORK_BANDWIDTH = 703;
  int MEDIA_INFO_BAD_INTERLEAVING = 800;
  int MEDIA_INFO_NOT_SEEKABLE = 801;
  int MEDIA_INFO_METADATA_UPDATE = 802;
  int MEDIA_INFO_TIMED_TEXT_ERROR = 900;
  int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;
  int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;
  int MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001;
  int MEDIA_INFO_AUDIO_RENDERING_START = 10002;
  int MEDIA_INFO_AUDIO_DECODED_START = 10003;
  int MEDIA_INFO_VIDEO_DECODED_START = 10004;
  int MEDIA_INFO_OPEN_INPUT = 10005;
  int MEDIA_INFO_FIND_STREAM_INFO = 10006;
  int MEDIA_INFO_COMPONENT_OPEN = 10007;
  int MEDIA_INFO_VIDEO_SEEK_RENDERING_START = 10008;
  int MEDIA_INFO_AUDIO_SEEK_RENDERING_START = 10009;
  int MEDIA_INFO_MEDIA_ACCURATE_SEEK_COMPLETE = 10100;
  int MEDIA_ERROR_UNKNOWN = 1;
  int MEDIA_ERROR_SERVER_DIED = 100;
  int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;
  int MEDIA_ERROR_IO = -1004;
  int MEDIA_ERROR_MALFORMED = -1007;
  int MEDIA_ERROR_UNSUPPORTED = -1010;
  int MEDIA_ERROR_TIMED_OUT = -110;
   */
  /**
   * 由ijkplayer提供，用于播放视频，需要给他传入一个surfaceView
   */
  private var mMediaPlayer: IMediaPlayer? = null

  //视频控制按钮层
  private var controlView: IMediaController? = null


  //seekBar
  private var seekBar: SeekBar? = null

  //控制按钮层是否显示中
  private var isShowing: Boolean = true

  //当前播放状态
  private var playState = STATE_INIT

  //上一个播放状态，用于拖动后是否播放
  private var preState = STATE_INIT

  private var useHardDecode = false//是否使用硬解码

  /**
   * 视频文件地址
   */
  private var mPath: String? = null

  private var surfaceView: SurfaceView? = null

  private var mContext: Context? = null

  //视频总长度
  val duration: Long
    get() = if (mMediaPlayer != null) {
      mMediaPlayer!!.duration
    } else {
      0
    }

  //当前播放进度
  val currentPosition: Long
    get() = if (mMediaPlayer != null) {
      mMediaPlayer!!.currentPosition
    } else {
      0
    }
  //记录切换、销毁等情况下的播放进度
  var realCurrent: Long = currentPosition

  //记录seekBar是否处于用户拖动状态(not的话少取一次非运算)
  var isNotDragSeekBar = true

  companion object {
    //播放器状态
    const val STATE_ERROR = -1//错误
    const val STATE_INIT = 0//初始
    const val STATE_PREPARED = 1//准备
    const val STATE_START = 2//开始
    const val STATE_PLAY = 3//播放
    const val STATE_BUFFER = 4//缓冲
    const val STATE_PAUSE = 5//暂停
    const val STATE_STOP = 6//停止
    const val STATE_RELEASE = 7//释放
    const val STATE_RESET = 8//重置
    const val STATE_COMPLETION = 9//播放完成


    private const val MSG_SEEK_STATE = 21
    private const val MSG_PLAY_STATE = 22
    private const val MSG_DELAYED_HIDE = 23

    class LoopHandler(simpleIJKVideoPlayer: SimpleIJKVideoPlayer) : Handler(
        Looper.getMainLooper()) {
      private val weakReference: WeakReference<SimpleIJKVideoPlayer> = WeakReference(
          simpleIJKVideoPlayer)

      override fun handleMessage(msg: Message?) {
        val player = weakReference.get()
        if (null != player) {
          when (msg?.what) {
            MSG_SEEK_STATE -> {
              if (player.playState == STATE_PLAY && player.mMediaPlayer?.isPlaying!! && player.isNotDragSeekBar) {
                removeMessages(MSG_SEEK_STATE)
                sendEmptyMessageDelayed(MSG_SEEK_STATE, 1000)
                val current = (player.currentPosition / 1000).toInt()
                player.seekBar?.progress = current
                player.controlView?.onProgressChanged(player.seekBar, current, false,
                    player.isShowing)
              }
            }
            MSG_PLAY_STATE -> {
              if (player.playState == STATE_PLAY && player.isShowing && player.isNotDragSeekBar) {
                //默认显示5秒后自动隐藏,如果处于用户拖动状态，就不处理
                removeMessages(MSG_DELAYED_HIDE)
                sendEmptyMessageDelayed(MSG_DELAYED_HIDE, 5000)
              }
              player.controlView?.onPlayStateChanged(player.playState, player)
            }
            MSG_DELAYED_HIDE -> {
              player.controlView?.onHide()
              player.isShowing = false
            }
          }
        }
      }

    }
  }

  private val loopHandler = LoopHandler(this)

  constructor(context: Context) : super(context) {
    initVideoView(context)
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    initVideoView(context)
  }

  constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context,
      attrs, defStyleAttr) {
    initVideoView(context)
  }

  private fun initVideoView(context: Context) {
    mContext = context

    //获取焦点，不知道有没有必要~。~
    isFocusable = true
  }

  /**
   * 设置视频地址。
   * 根据是否第一次播放视频，做不同的操作。
   *
   * @param path the path of the video.
   */
  fun setupWithUrl(path: String) {
    sendPlayState(STATE_INIT)
    if (TextUtils.isEmpty(mPath)) {
      //如果是第一次播放视频，那就创建一个新的surfaceView
      mPath = path
      createSurfaceView()
    } else {
      //否则就直接load
      mPath = path
      load()
    }
  }

  /**
   * 新建一个surfaceview
   */
  private fun createSurfaceView() {
    //生成一个新的surface view
    surfaceView = SurfaceView(mContext)
    surfaceView!!.holder.addCallback(LmnSurfaceCallback())
    val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER)
    surfaceView!!.layoutParams = layoutParams
    this.addView(surfaceView)
  }

  /**
   * surfaceView的监听器
   */
  private inner class LmnSurfaceCallback : SurfaceHolder.Callback {
    override fun surfaceCreated(holder: SurfaceHolder) {}

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
      realCurrent = currentPosition
      //surfaceview创建成功后，加载视频
      load()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {}
  }

  /**
   * 加载视频
   */
  private fun load() {
    //每次都要重新创建IMediaPlayer
    createPlayer()
    try {
      mMediaPlayer!!.dataSource = mPath
    } catch (e: IOException) {
      e.printStackTrace()
    }

    //给mediaPlayer设置视图
    mMediaPlayer!!.setDisplay(surfaceView!!.holder)

    mMediaPlayer!!.prepareAsync()
  }

  /**
   * 创建一个新的player
   */
  private fun createPlayer() {
    if (mMediaPlayer != null) {
      mMediaPlayer!!.stop()
      mMediaPlayer!!.setDisplay(null)
      mMediaPlayer!!.release()
    }
    val ijkMediaPlayer = IjkMediaPlayer()

    //开启硬解码
    if (useHardDecode) {
      ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1)
    }

    mMediaPlayer = ijkMediaPlayer

    //size改变以后重新设置进度
    mMediaPlayer?.setOnVideoSizeChangedListener { player, _, _, _, _ ->
      player.seekTo(realCurrent)
    }
    //播放完成时调用
    mMediaPlayer?.setOnCompletionListener {
      sendPlayState(STATE_COMPLETION)
    }

    mMediaPlayer?.setOnPreparedListener {
      sendPlayState(STATE_PREPARED)
    }

    mMediaPlayer?.setOnInfoListener { player, p1, p2 ->
      when (p1) {
        701 -> sendPlayState(STATE_BUFFER)//开始缓冲
        702 -> {
          sendPlayState(STATE_PLAY)//缓冲结束
          loopHandler.sendEmptyMessageDelayed(MSG_SEEK_STATE, 1000)

        }
        10002, 10003, 10004 -> {
          sendPlayState(STATE_PLAY)
          loopHandler.sendEmptyMessageDelayed(MSG_SEEK_STATE, 1000)

        }
      }
      Timber.i("-------->>onInfo---p1=$p1------>p2=$p2--------duration:${player?.duration}")
      return@setOnInfoListener false
    }
    mMediaPlayer?.setOnErrorListener { _, p1, p2 ->
      sendPlayState(STATE_ERROR)
      Timber.i("-------->>OnError-----p1:$p1-----p2:$p2")
      return@setOnErrorListener false
    }

    //拖动结束后开始播放
    mMediaPlayer?.setOnSeekCompleteListener {
      when (preState) {
        STATE_PLAY ->
          onStart()
      //else ->
      }
    }
  }

  /**
   * 发送播放状态
   */
  private fun sendPlayState(state: Int) {
    playState = state
    loopHandler.sendEmptyMessage(MSG_PLAY_STATE)
  }


  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    onRelease()
    loopHandler.removeMessages(MSG_SEEK_STATE)
    loopHandler.removeMessages(MSG_PLAY_STATE)
    loopHandler.removeMessages(MSG_DELAYED_HIDE)
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent?): Boolean {
    if (null != controlView) {
      isShowing = if (isShowing) {
        controlView?.onHide()
        false
      } else {
        controlView?.onShow()
        loopHandler.removeMessages(MSG_DELAYED_HIDE)
        loopHandler.sendEmptyMessageDelayed(MSG_DELAYED_HIDE, 5000)
        true
      }
    }
    return super.onTouchEvent(event)
  }


  private fun setSeekBarListener() {
    seekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        controlView?.onProgressChanged(seekBar, progress, fromUser, isShowing)
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {
        if (loopHandler.hasMessages(MSG_DELAYED_HIDE)) {
          //避免拖动时seekBar消失
          loopHandler.removeMessages(MSG_DELAYED_HIDE)
        }
        isNotDragSeekBar = false
        controlView?.onStartTrackingTouch(seekBar)
      }

      override fun onStopTrackingTouch(seekBar: SeekBar?) {
        onSeekTo((seekBar?.progress!! * 1000).toLong())
        isNotDragSeekBar = true
        loopHandler.sendEmptyMessageDelayed(MSG_DELAYED_HIDE, 5000)
        controlView?.onStopTrackingTouch(seekBar)
      }
    })
  }

  /**
   * 添加播放控制器
   */
  fun setUpWithControlView(controlView: IMediaController?) {
    if (controlView == null) {
      throw IllegalArgumentException("Control view must not be null.")
    } else {
      this.controlView = controlView
    }
  }

  /**
   * 添加seekBar
   */
  fun setUpWithSeekBar(seekBar: SeekBar) {
    this.seekBar = seekBar
    setSeekBarListener()
  }


  /**
   * -------======--------- 下面封装了一下控制视频的方法
   */


  /**
   * 硬解码
   */

  fun setHardDecode(useHardDecode: Boolean) {
    this.useHardDecode = useHardDecode
  }

  /**
   * 获取播放状态
   */
  fun getPlayState(): Int {
    return playState
  }

  /**
   * 播放
   */
  fun onStart() {
    mMediaPlayer?.start()
    sendPlayState(STATE_START)

  }

  /**
   * 释放
   */
  fun onRelease() {
    onStop()
    mMediaPlayer?.reset()
    mMediaPlayer?.release()
    mMediaPlayer = null
    sendPlayState(STATE_RELEASE)
  }

  /**
   * 暂停
   */
  fun onPause() {
    mMediaPlayer?.pause()
    sendPlayState(STATE_PAUSE)
  }

  /**
   * 停止
   */
  fun onStop() {
    mMediaPlayer?.stop()
    realCurrent = 0
    sendPlayState(STATE_STOP)
  }

  /**
   * 重置
   */
  fun onReset() {
    mMediaPlayer?.reset()
    realCurrent = 0
    sendPlayState(STATE_RESET)
  }

  /**
   * 拖动
   */
  fun onSeekTo(l: Long) {
    if (playState == STATE_PLAY) {
      mMediaPlayer?.pause()
    }
    preState = playState
    mMediaPlayer?.seekTo(l)
    sendPlayState(STATE_INIT)
  }
}
