package cn.edu.njust.securityguardian.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by fookey on 15-1-31.
 */
public class ImageItemView extends FrameLayout {
    private ImageButton imageButton;
    private TextView textView;

    public ImageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.image_item,this);
        textView=(TextView)findViewById(R.id.image_item_textview);
        imageButton=(ImageButton)findViewById(R.id.image_item_imagebutton);

        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ImageItemView);
        int n=typedArray.getIndexCount();
        for(int i=0;i<n;i++){
            int attr=typedArray.getIndex(i);
            switch (attr){
                case R.styleable.ImageItemView_imageSrc:
                    setImageSrc(typedArray.getResourceId(attr,0));
                    break;
                case R.styleable.ImageItemView_itemtext:
                    setItemText(typedArray.getString(R.styleable.ImageItemView_itemtext));
                    break;
            }

        }
    }
    public void setImageSrc(int resourceID){
        imageButton.setImageBitmap(
                BitmapFactory.decodeResource(getResources(), resourceID
                ));
    }
    public void setItemText(String string){
        textView.setText(string);
    }
    public void setOnClickListener(OnClickListener l){
        imageButton.setOnClickListener(l);
    }
}
