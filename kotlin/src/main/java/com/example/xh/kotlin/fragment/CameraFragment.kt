package com.example.xh.kotlin.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.CamcorderProfile.QUALITY_720P
import android.media.CamcorderProfile.QUALITY_HIGH_SPEED_720P
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.frag_camera.*
import java.text.SimpleDateFormat
import java.util.*


class CameraFragment : Fragment(), SurfaceHolder.Callback {
    var mCamera: Camera? = null
    var mCameraPermission = PackageManager.PERMISSION_DENIED
    var hasSurface = false
    var mMediaRecorder: MediaRecorder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestCameraPermissions()
    }

    private fun requestCameraPermissions() {
        mCameraPermission = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA)

        if (mCameraPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_CAMERA)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (PERMISSIONS_REQUEST_CAMERA == requestCode) {

            for ((index, value) in permissions.withIndex()) {
                if (Manifest.permission.CAMERA == value) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        requestCameraPermissions()
                    } else {
                        Log.d("TAG", "onRequestPermissionsResult: $value $grantResults[index]")
                    }
//                    else if (hasSurface) {
//                        openCamera()
//                    }
                }
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
                } else {
                    mMediaRecorder?.stop()
                    it.text = "录制"
                }
            }
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

        surfaceView.holder.removeCallback(this)
        releaseCameraAndPreview()
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

        val profile = CamcorderProfile.get(QUALITY_720P)
        profile.fileFormat = MediaRecorder.OutputFormat.MPEG_4
        profile.audioCodec = MediaRecorder.AudioEncoder.AAC
        profile.videoCodec = MediaRecorder.VideoEncoder.H264
        profile.videoBitRate = QUALITY_HIGH_SPEED_720P
        profile.audioBitRate = QUALITY_HIGH_SPEED_720P

        mMediaRecorder?.setProfile(profile)
        mMediaRecorder?.setOutputFile(getRandomVideoName())
        mMediaRecorder?.setPreviewDisplay(surfaceView.holder.surface)


        mMediaRecorder?.prepare()
        mMediaRecorder?.start()

    }


    private fun getRandomVideoName(): String {
        val name = "autel-${SimpleDateFormat("yyyymmddHHmmss", Locale.CHINA).format(Date())}.mp4"
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
        return "$dir/$name"
    }


    companion object {
        const val PERMISSIONS_REQUEST_CAMERA = 0X100
    }

}