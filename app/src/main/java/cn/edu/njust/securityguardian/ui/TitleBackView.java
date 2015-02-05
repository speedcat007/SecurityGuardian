package cn.edu.njust.securityguardian.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.edu.njust.securityguardian.R;

/**
 * Created by fookey on 15-1-31.
 */
public class TitleBackView extends FrameLayout {
    private Button backButton;
    private TextView titleText;

    public TitleBackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_back, this);
        titleText = (TextView) findViewById(R.id.tv_title_text);
        backButton = (Button) findViewById(R.id.btn_back);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });

        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.TitleBackView);
        int n=typedArray.getIndexCount();
        for(int i=0;i<n;i++){
            if(typedArray.getIndex(i)==R.styleable.TitleBackView_titleText)
                setTitleText(typedArray.getString(R.styleable.TitleBackView_titleText));
        }
    }

    public void setTitleText(String text) {
        this.titleText.setText(text);
    }

    public void setLeftButtonText(String text) {
        backButton.setText(text);
    }

    public void setBackButtonListener(OnClickListener l){
        backButton.setOnClickListener(l);
    }
}

