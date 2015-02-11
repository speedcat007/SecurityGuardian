package cn.edu.njust.securityguardian.ui;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by fookey on 15-2-11.
 * 沿Y坐标轴在两角度之间旋转的Animation
 * 为了加强效果，这个Animation也增加了Z轴上的转换(为0则无Z轴转换)
 */
public class Rotate3dAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera camera;

    public Rotate3dAnimation(float fromDegrees,float toDegrees,
                             float centerX,float centerY,float depthZ,boolean reverse){
        mFromDegrees=fromDegrees;
        mToDegrees=toDegrees;
        mCenterX=centerX;
        mCenterY=centerY;
        mDepthZ=depthZ;
        mReverse=reverse;
    }
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        camera=new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float degrees=mFromDegrees+(mToDegrees-mFromDegrees)*interpolatedTime;
        Matrix matrix=t.getMatrix();
        camera.save();
        if(mReverse){
            camera.translate(0.0f,0.0f,mDepthZ*interpolatedTime);
        }else{
            camera.translate(0.0f,0.0f,mDepthZ*(1.0f-interpolatedTime));
        }
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-mCenterX,-mCenterY);
        matrix.postTranslate(mCenterX,mCenterY);
    }
}
