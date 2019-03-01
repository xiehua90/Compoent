package com.example.xh.kotlin.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.hardware.Camera
import android.hardware.Camera.Parameters.PREVIEW_FPS_MIN_INDEX
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED
import android.media.MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.frag_camera.*
import java.text.SimpleDateFormat
import java.util.*


class CameraFragment : Fragment(), SurfaceHolder.Callback {
    var mCamera: Camera? = null
    var permissionList = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    var hasSurface = false
    var outPath: String = ""
    private var mMediaRecorder: MediaRecorder? = null
    private val handler: Handler by lazy {
        Handler()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val gridview = object :GridView{
            override fun measureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                super.measureChildren(widthMeasureSpec, heightMeasureSpec)
            }

            override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
                super.onLayout(changed, l, t, r, b)
            }

            override fun layoutChildren() {
                super.layoutChildren()
            }

        }

        requestCameraPermissions()
        mMediaRecorder?.setOnInfoListener { mr: MediaRecorder, what: Int, extra: Int ->
            if (what == MEDIA_RECORDER_INFO_MAX_DURATION_REACHED || what == MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED) {
                Toast.makeText(activity!!, "$what $extra $mr", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recordBtn.setOnClickListener {
            if (it is Button) {
                if (it.text == "录制") {
                    recordVideo()
                    it.text = "结束"
                    playBtn.visibility = View.INVISIBLE
                } else {
                    mMediaRecorder?.stop()
                    it.text = "录制"
                    handler.postDelayed({
                        tipTextView.visibility = View.INVISIBLE
                    }, 3000)
                    playBtn.visibility = View.VISIBLE
                }
            }
        }

        playBtn.setOnClickListener {
            playVideo()
        }
    }

    override fun onResume() {
        super.onResume()
        if (hasSurface) {
            openCamera()
        } else {
            surfaceView.holder.addCallback(this)
        }
    }

    override fun onPause() {
        super.onPause()

        releaseCameraAndPreview()
        if (!hasSurface) {
            surfaceView.holder.removeCallback(this)
        }
    }

    override fun onStop() {
        super.onStop()
        mMediaRecorder?.release()
        media.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        media.release()
    }

    private fun releaseCameraAndPreview() {
        mCamera?.stopPreview()
        mCamera?.release()
        mCamera = null
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        hasSurface = surfaceView.holder.isCreating
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        hasSurface = false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        hasSurface = true
        openCamera()
    }

    private fun openCamera() {
        releaseCameraAndPreview()
        mCamera = Camera.open()
        if (mCamera != null) {
            Log.d("TAG", "openCamera() $mCamera ${surfaceView.holder.isCreating}")

            val parameters = mCamera?.parameters
            parameters?.setPreviewSize(1920, 1080)
            parameters?.pictureFormat = ImageFormat.JPEG
            mCamera?.setDisplayOrientation(90)

            mCamera?.setPreviewDisplay(surfaceView.holder)
            mCamera?.startPreview()
        }
    }


    private fun recordVideo() {
        mMediaRecorder = MediaRecorder()
        mMediaRecorder?.reset()
        mCamera?.unlock()

        mMediaRecorder?.setCamera(mCamera)

        mMediaRecorder?.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
        mMediaRecorder?.setVideoSource(MediaRecorder.VideoSource.CAMERA)
        mMediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)

        mMediaRecorder?.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
        mMediaRecorder?.setVideoEncodingBitRate(2 * 1024 * 1024)

//        mMediaRecorder?.setVideoFrameRate(20)
        mMediaRecorder?.setVideoSize(1920, 1080)

        mMediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mMediaRecorder?.setAudioEncodingBitRate(128 * 1024)

        outPath = getRandomVideoName()
        mMediaRecorder?.setOutputFile(outPath)
        mMediaRecorder?.setPreviewDisplay(surfaceView.holder.surface)

        mMediaRecorder?.prepare()
        mMediaRecorder?.start()

        tipTextView.text = outPath
        tipTextView.visibility = View.VISIBLE
    }


    private fun getVideoMinFrameRate() {
        val list = mCamera?.parameters?.supportedPreviewFpsRange?.get(PREVIEW_FPS_MIN_INDEX)!!

        for (i in list) {
            Log.d("TAG", "$i")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (PERMISSIONS_REQUEST == requestCode) {
            for ((index, value) in permissions.withIndex()) {
                if (value in permissionList) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        requestCameraPermissions()
                    }
                }
            }
        }
    }

    private fun requestCameraPermissions() {
        for (permission in permissionList) {
            val result = ContextCompat.checkSelfPermission(activity!!, permission)

            if (result != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissionList, PERMISSIONS_REQUEST)
            }
        }
    }

    private fun getRandomVideoName(): String {
        val name = "autel-${SimpleDateFormat("yyyymmddHHmmss", Locale.CHINA).format(Date())}.mp4"
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
        return "$dir/$name"
    }

    val media = MediaPlayer()

    private fun playVideo() {
        if (!media.isPlaying) {
            media.reset()
            surfaceView2.visibility = View.VISIBLE
            media.setOnCompletionListener {
                surfaceView2.visibility = View.INVISIBLE
            }
            media.setDataSource(outPath)
            media.setDisplay(surfaceView2.holder)
            media.prepare()
            media.start()
        } else {
            media.stop()
        }
    }

    companion object {
        const val PERMISSIONS_REQUEST = 0x100
    }

}