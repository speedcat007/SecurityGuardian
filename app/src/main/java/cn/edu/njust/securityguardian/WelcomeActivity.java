package cn.edu.njust.securityguardian;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


/**
 *
 * 欢迎界面
 *
 */
public class WelcomeActivity extends Activity{
    private ImageView imageVeiw =null;
    private Animation animation=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        imageVeiw=(ImageView)findViewById(R.id.welcome_image_view);
        animation = AnimationUtils.loadAnimation(this,R.anim.in_from_alpha);
        animation.setAnimationListener(new NextAcAnimationListener());
        imageVeiw.setAnimation(animation);

    }

    private class  NextAcAnimationListener implements Animation.AnimationListener{

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
                overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //欢迎界面屏蔽BACK键
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }
}
