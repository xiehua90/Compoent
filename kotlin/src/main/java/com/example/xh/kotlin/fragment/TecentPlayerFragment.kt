package com.example.xh.kotlin.fragment

import android.os.Bundle
import android.view.View
import com.example.xh.kotlin.BaseFragement
import com.example.xh.kotlin.R
import com.tencent.liteav.demo.play.SuperPlayerModel
import kotlinx.android.synthetic.main.fragment_video_player.*

/**
 * https://cloud.tencent.com/document/product/881/20213
 * */
class TecentPlayerFragment : BaseFragement() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_video_player
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            val model = SuperPlayerModel()
            model.videoURL = "http://200024424.vod.myqcloud.com/200024424_709ae516bdf811e6ad39991f76a4df69.f20.mp4"
            playerView.playWithMode(model)
        }


    }

    override fun onStop() {
        super.onStop()
        playerView.resetPlayer()

    }
}