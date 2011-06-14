
package org.bangbang.song.andorid.widget.coverflow;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.view.animation.Transformation;

public class SpecularUtil {

    private static Camera mCamera = new Camera();

    public static Bitmap transformImageBitmap(Bitmap bitmap, final Transformation t,
            final float rotationAngle) {
        mCamera.save();
        final Matrix imageMatrix = t.getMatrix();

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        // height = 320;
        // width = 190;

        mCamera.rotateY(rotationAngle);
        mCamera.getMatrix(imageMatrix);
        imageMatrix.preTranslate(-(width / 2.0f), -(height / 2.0f));
        imageMatrix.postTranslate((width / 2.0f), (height / 2.0f));
        mCamera.restore();

        Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, width,
                height, imageMatrix, false);

        return dstbmp;
    }

    public static Bitmap createReflectedImage(Bitmap originalImage) {
        final int reflectionGap = 4;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                           height / 2, width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                           (height + height / 3), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(originalImage, 0, 0, null);

        Paint gapPaint = new Paint();
        gapPaint.setColor(Color.TRANSPARENT);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                           originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
                                              + reflectionGap, 0x70ffffff, 0x00ffffff,
                           TileMode.MIRROR);

        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                           + reflectionGap, paint);

        return bitmapWithReflection;
    }
}
