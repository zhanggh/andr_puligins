package com.plugin.commons.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.FuncUtil;


public class CustomDialog extends Dialog {  
    private static int default_width = 290; //默认宽度
    private static int default_height = 340;//默认高度
    String photo=null;
    ImageView im_show;
    public CustomDialog(Context context, int layout, int style,String photo) { 
        this(context, default_width, default_height, layout, style,photo); 
    }

    public CustomDialog(Context context, int width, int height, int layout, int style,String photo) {
        super(context, style); 
        //set content
        setContentView(layout);
        //set window params
        im_show= (ImageView) this.findViewById(R.id.im_show);
        if(!FuncUtil.isEmpty(photo)){
        	 ComApp.getInstance().getFinalBitmap().display(im_show,photo);
        }
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //set width,height by density and gravity
        float density = getDensity(context);
        params.width = (int) (width*density);
        params.height = (int) (height*density);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }
    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
       return dm.density;
    }
}