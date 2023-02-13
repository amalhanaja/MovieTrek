package com.amalhanaja.movietrek.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class YoutubePlayerController : YouTubePlayerCallback, YouTubePlayer {

    internal var player: YouTubePlayer? = null
    internal var hasBeenPlayed = mutableStateOf(false)

    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
        player = youTubePlayer
    }

    override fun addListener(listener: YouTubePlayerListener): Boolean {
        return player?.addListener(listener) ?: false
    }

    override fun cueVideo(videoId: String, startSeconds: Float) {
        player?.cueVideo(videoId, startSeconds)
        hasBeenPlayed.value = true
    }

    override fun loadVideo(videoId: String, startSeconds: Float) {
        player?.loadVideo(videoId, startSeconds)
        hasBeenPlayed.value = true
    }

    override fun mute() {
        player?.mute()
    }

    override fun pause() {
        player?.pause()
    }

    override fun play() {
        hasBeenPlayed.value = true
        player?.play()
    }

    override fun removeListener(listener: YouTubePlayerListener): Boolean {
        return player?.removeListener(listener) ?: false
    }

    override fun seekTo(time: Float) {
        player?.seekTo(time)
    }

    override fun setPlaybackRate(playbackRate: PlayerConstants.PlaybackRate) {
        player?.setPlaybackRate(playbackRate)
    }

    override fun setVolume(volumePercent: Int) {
        player?.setVolume(volumePercent)
    }

    override fun unMute() {
        player?.unMute()
    }
}

@Composable
fun YoutubePlayerComponent(
    videoId: String,
    thumbnailUrl: String,
    modifier: Modifier = Modifier,
    controller: YoutubePlayerController = remember { YoutubePlayerController() },
) {
    if (controller.hasBeenPlayed.value) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                YouTubePlayerView(context).apply {
                    getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                        override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(videoId, 0f)
                        }
                    })
                }
            }
        )
    } else {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .crossfade(true)
                    .data(thumbnailUrl)
                    .build(),
                contentDescription = videoId,
                contentScale = ContentScale.Crop,
                alpha = 0.8f,
            )
            IconButton(onClick = { controller.loadVideo(videoId, 0f) }) {
                Icon(
                    imageVector = Icons.Outlined.PlayCircleFilled,
                    contentDescription = videoId,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { controller.pause() }
    }
}