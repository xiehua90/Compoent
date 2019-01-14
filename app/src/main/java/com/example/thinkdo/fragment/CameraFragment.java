package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.thinkdo.compoentdemo.R;
import com.google.zxing.client.android.camera.AutoFocusManager;

import java.io.IOException;


public class CameraFragment extends Fragment implements SurfaceHolder.Callback, Camera.PictureCallback, Camera.ShutterCallback {
    private static final String TAG = "CameraFragment";
    SurfaceView surfaceView;
    private Camera mCamera;
    AutoFocusManager autoFocusManager;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_camera, container, false);
        surfaceView = view.findViewById(R.id.preview_view);
        imageView = view.findViewById(R.id.imageView);
        view.findViewById(R.id.btn_capture).setOnClickListener(v->{
            if (mCamera != null){
                mCamera.takePicture( this, this, this);
            }
        });

        imageView.setRotation(90);
        return view;
    }

    private boolean safeCameraOpen() {
        releaseCameraAndPreview();
        mCamera = Camera.open();

        return mCamera != null;
    }

    @Override
    public void onResume() {
        super.onResume();

        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        SurfaceHolder holder = surfaceView.getHolder();
        holder.removeCallback(this);

        releaseCameraAndPreview();
    }

    private void setCamera(Camera camera) {
        mCamera = camera;

        if (mCamera != null) {
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPreviewSize(1920, 1080);
                parameters.setPictureFormat(ImageFormat.JPEG);
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(surfaceView.getHolder());
                autoFocusManager = new AutoFocusManager(getActivity(), mCamera);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCamera.startPreview();
        }
    }

    private void releaseCameraAndPreview() {
        if (mCamera != null) {
            autoFocusManager.stop();
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        safeCameraOpen();
        setCamera(mCamera);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCameraAndPreview();
    }

    Handler handler = new Handler();

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if (data == null){
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0, data.length, null);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageBitmap(bitmap);

        if (mCamera != null){
            mCamera.startPreview();
        }
        handler.postDelayed(()->
            imageView.setVisibility(View.GONE)
        ,3000);
    }

    @Override
    public void onShutter() {

    }
}
