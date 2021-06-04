package com.example.gerald_interview.ui.activities

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.multidex.MultiDex
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gerald_interview.R
import com.example.gerald_interview.data.models.Show
import com.example.gerald_interview.databinding.ActivityMainBinding
import com.example.gerald_interview.ui.adapters.OuterAdapter
import com.example.gerald_interview.ui.viewmodels.ShowsViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


private const val TAG = "MAIN_ACTIVITY"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var layoutManager: LinearLayoutManager? = null
    private var player: SimpleExoPlayer? = null
    private var adsLoader: ImaAdsLoader? = null
    private var outerAdapter: OuterAdapter? = null
    private var adTag: String? = null
    private lateinit var viewModel: ShowsViewModel


    private val onShowClickedListener: (Show) -> Unit = { it ->
        viewModel.setVideoURL(it)
        initializePlayer(Uri.parse(adTag))
    }

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MultiDex.install(this)
        viewModel = ViewModelProviders.of(this).get(ShowsViewModel::class.java)
        adsLoader = ImaAdsLoader.Builder(this).build()
        layoutManager =LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        binding.outerRecycler.layoutManager = layoutManager
        viewModel.getShows().observe(this, {
            adTag = it.adtag
            outerAdapter = OuterAdapter(viewModel.groupShows(it.shows), onShowClickedListener)
            binding.outerRecycler.adapter = outerAdapter
        })
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= 23) {
            initializePlayer()
            binding.exoPlayer.onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT <= 23 || player == null) {
            initializePlayer()
            binding.exoPlayer.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT <= 23) {
            binding.exoPlayer.onPause()
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > 23) {
            binding.exoPlayer.onPause()
            releasePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adsLoader!!.release()
    }

    private fun initializePlayer(adTag: Uri = Uri.parse("")) {
        val dataSourceFactory =
            DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)))
        val mediaSourceFactory: MediaSourceFactory = DefaultMediaSourceFactory(dataSourceFactory)
            .setAdsLoaderProvider { adsLoader }
            .setAdViewProvider(binding.exoPlayer)
        player = SimpleExoPlayer.Builder(this).setMediaSourceFactory(mediaSourceFactory).build()
        binding.exoPlayer.player = player
        adsLoader?.setPlayer(player)
        var contentUri: Uri = Uri.parse("")
        var mediaItem: MediaItem = MediaItem.Builder()
            .setUri(Uri.parse(""))
            .setAdTagUri(Uri.parse("")).build()
        viewModel.getShowURL().observe(this, {
            contentUri = Uri.parse(it)
            mediaItem = MediaItem.Builder()
                .setUri(contentUri)
                .setAdTagUri(adTag).build()
        })

        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = true
    }

    private fun releasePlayer() {
        adsLoader?.setPlayer(null)
        binding.exoPlayer.player = null
        player!!.release()
        player = null
    }
}