package com.plugin.commons.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.plugin.R;

public class WebDialog extends Dialog {

    Context context;
    public View view;
    int viewId;
    int webId;
    public WebDialog(Context context,int id,int webId) {
        super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.viewId=id;
        this.webId=webId;
    }
    public WebDialog(Context context){
        super(context);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(viewId);
        view=this.findViewById(this.webId);
    }

}