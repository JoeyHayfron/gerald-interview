package com.example.gerald_interview

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.multidex.MultiDex
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gerald_interview.databinding.ActivityMainBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaItem.AdsConfiguration
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


private const val TAG = "MAIN_ACTIVITY"
class MainActivity : AppCompatActivity(), InnerAdapter.OnShowListener {

    private lateinit var binding: ActivityMainBinding
    private var layoutManager: LinearLayoutManager? = null
    private var player: SimpleExoPlayer? = null
    private var adsLoader: ImaAdsLoader? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private lateinit var viewModel: ShowsViewModel
    private var outerAdapter: OuterAdapter? = null

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MultiDex.install(this)
        adsLoader = ImaAdsLoader.Builder(this).build()
        viewModel = ViewModelProviders.of(this).get(ShowsViewModel::class.java)
        layoutManager =LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        binding.outerRecycler.layoutManager = layoutManager
        viewModel.getShows().observe(this, {
            outerAdapter = OuterAdapter(it)
            binding.outerRecycler.adapter = outerAdapter
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
        val dataSourceFactory =
            DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)))
        val mediaSourceFactory: MediaSourceFactory = DefaultMediaSourceFactory(dataSourceFactory)
            .setAdsLoaderProvider { unusedAdTagUri: AdsConfiguration? -> adsLoader }
            .setAdViewProvider(binding.exoPlayer)
        player = SimpleExoPlayer.Builder(this).setMediaSourceFactory(mediaSourceFactory).build()
        binding.exoPlayer.player = player
        adsLoader?.setPlayer(player)
        val adTagUri: Uri = Uri.parse(getString(R.string.ad_tag_url))
        val contentUri: Uri = Uri.parse(getString(R.string.content_url))
        val mediaItem =
            MediaItem.Builder()
                .setUri(contentUri)
                .setAdTagUri(adTagUri).build()
        player?.setMediaItem(mediaItem)
        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
        player?.prepare()
    }

    private fun hideSystemUI() {
        binding.exoPlayer.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
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

    override fun onShowClicked(show: Show) {

    }
}