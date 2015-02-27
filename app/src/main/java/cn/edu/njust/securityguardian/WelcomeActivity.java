package cn.edu.njust.securityguardian;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


/**
 *
 * 欢迎界面
 *
 */
public class WelcomeActivity extends Activity{
    private ImageView imageVeiw =null;
    private Animation animation=null;
    private TextView tv_welcome_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        imageVeiw=(ImageView)findViewById(R.id.welcome_image_view);
        animation = AnimationUtils.loadAnimation(this,R.anim.in_from_alpha);
        animation.setAnimationListener(new NextAcAnimationListener());
        imageVeiw.setAnimation(animation);

        tv_welcome_go=(TextView)findViewById(R.id.tv_welcome_go);
        tv_welcome_go.setClickable(true);
        tv_welcome_go.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
                overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
        });

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
