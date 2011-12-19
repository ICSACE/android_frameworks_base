/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.test.hwui;

import android.app.Activity;
<<<<<<< HEAD
import android.graphics.Bitmap;
=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
<<<<<<< HEAD
import android.os.Environment;
import android.view.Gravity;
import android.view.Surface;
=======
import android.view.Gravity;
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

<<<<<<< HEAD
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
import java.io.IOException;

@SuppressWarnings({"UnusedDeclaration"})
public class TextureViewActivity extends Activity implements TextureView.SurfaceTextureListener {
    private Camera mCamera;
    private TextureView mTextureView;
    private FrameLayout mContent;
    private Matrix mMatrix = new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContent = new FrameLayout(this);

        mTextureView = new TextureView(this);
        mTextureView.setSurfaceTextureListener(this);
<<<<<<< HEAD
        mTextureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = mTextureView.getBitmap(800, 800);
                BufferedOutputStream out = null;
                try {
                    File dump = new File(Environment.getExternalStorageDirectory(), "out.png");
                    out = new BufferedOutputStream(new FileOutputStream(dump));
                    b.compress(Bitmap.CompressFormat.PNG, 100, out);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e

        Button button = new Button(this);
        button.setText("Remove/Add");
        button.setOnClickListener(new View.OnClickListener() {
            private boolean mAdded = true;

            @Override
            public void onClick(View v) {
                if (mAdded) {
                    mContent.removeView(mTextureView);
                } else {
                    mContent.addView(mTextureView);
                }
                mAdded = !mAdded;
            }
        });

        mContent.addView(mTextureView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));
        mContent.addView(button, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM));
        setContentView(mContent);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamera = Camera.open();
<<<<<<< HEAD
        mCamera.setDisplayOrientation(getCameraOrientation());

=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        mTextureView.setLayoutParams(new FrameLayout.LayoutParams(
                previewSize.width, previewSize.height, Gravity.CENTER));

        try {
            mCamera.setPreviewTexture(surface);
        } catch (IOException t) {
            android.util.Log.e("TextureView", "Cannot set preview texture target!", t);
        }

        mCamera.startPreview();
    }

<<<<<<< HEAD
    private int getCameraOrientation() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) break;
        }
        
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        return (info.orientation - degrees + 360) % 360;
    }

=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored, the Camera does all the work for us
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Ignored
    }
}
