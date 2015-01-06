package com.plugin.commons.view;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.model.DialogObj;

 
/**
 * 自定义dialog
 * 推荐好友
 * @author zhanggh
 *
 */
public class DialogRecmdSelect {
	DingLog log = new DingLog(DialogRecmdSelect.class);
	private PickDialogcallback pickDialogcallback;
	private Dialog dialog;
	private Context context;
	private List<DialogObj> list;
	private DialogObj selectItem;
	private ListView lv_list;
	private Button btn_cancel;
	private ImageView im_weibo;
	private ImageView im_qq;
	private ImageView im_weixin;
	public DialogRecmdSelect(Context con,PickDialogcallback pickDialogcallback,List<DialogObj> items,DialogObj selectItem) {
		this.context = con;
		dialog = new Dialog(context, R.style.dialog);
		this.pickDialogcallback = pickDialogcallback;
		dialog.setContentView(R.layout.dialog_recommend);
		dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		dialog.getWindow().setWindowAnimations(R.style.dialog_up);
		dialog.getWindow().setGravity(Gravity.BOTTOM);
		this.list = items;
		this.selectItem = selectItem;
		initUI();
	}
	private void initUI(){
		im_weibo=(ImageView) dialog.findViewById(R.id.im_weibo);
		im_weibo.setImageResource(ComApp.getInstance().appStyle.setting_rec_weibo_selector);
		im_qq=(ImageView) dialog.findViewById(R.id.im_qq);
		im_qq.setImageResource(ComApp.getInstance().appStyle.setting_rec_qq_selector);
		im_weixin=(ImageView) dialog.findViewById(R.id.im_weixin);
		im_weixin.setImageResource(ComApp.getInstance().appStyle.setting_rec_weixin_selector);
		btn_cancel=(Button) dialog.findViewById(R.id.btn_cancel);
		
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}
	
	/**
	 * 回调
	 * @author vinci
	 * @modifylog
	 */
	public interface PickDialogcallback {
		/**
		 * 回调函数
		 * @author vinci
		 * @modifylog   
		 */
		public void onItemSelect(DialogObj selectItem);
	}

	public void show() {
		dialog.show();
	}

	public void hide() {
		dialog.hide();
	}

	public void dismiss() {
		dialog.dismiss();
	}
	

	public Dialog getDialog() {
		return dialog;
	}
	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}
	public DialogObj getSelectItem() {
		return selectItem;
	}
	public void setSelectItem(DialogObj selectItem) {
		this.selectItem = selectItem;
	}
	
}
