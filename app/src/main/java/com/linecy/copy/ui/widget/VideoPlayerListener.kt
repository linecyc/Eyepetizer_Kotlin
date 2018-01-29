package com.linecy.copy.ui.widget

import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IMediaPlayer.OnTimedTextListener

/**
 * @author by linecy
 */
abstract class VideoPlayerListener : IMediaPlayer.OnBufferingUpdateListener,
    IMediaPlayer.OnCompletionListener, IMediaPlayer.OnPreparedListener,
    IMediaPlayer.OnInfoListener, IMediaPlayer.OnVideoSizeChangedListener,
    IMediaPlayer.OnErrorListener, IMediaPlayer.OnSeekCompleteListener, OnTimedTextListener
