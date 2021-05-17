package com.example.gerald_interview

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MAIN_ACTIVITY"
class MainActivity : AppCompatActivity() {
    private var layoutManager: LinearLayoutManager? = null
    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private lateinit var viewModel: ShowsViewModel
    private var outerAdapter: OuterAdapter? = null

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(ShowsViewModel::class.java)
        layoutManager =LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        outerRecycler.layoutManager = layoutManager
        viewModel.getShows().observe(this, {
            outerAdapter = OuterAdapter(it)
            outerRecycler.adapter = outerAdapter
        })
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        if (Build.VERSION.SDK_INT < 24) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        exoPlayer.player = player
        val mediaItem =
            MediaItem.fromUri("http://c5x8i7c7.ssl.hwcdn.net/hls/lifestyletv/Boating/A%20Sunsail%20Flotilla%20Vacation.mp4")
        player?.setMediaItem(mediaItem)
        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
        player?.prepare()
    }

    private fun hideSystemUI() {
        exoPlayer.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player?.release()
            player = null
        }
    }
}