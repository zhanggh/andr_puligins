package com.plugin.commons.view;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.adapter.NewListItemCache;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.model.DialogObj;

/**
 * 自定义dialog
 * @author vinci
 *
 */
public class DialogOptionSelect {
	DingLog log = new DingLog(DialogOptionSelect.class);
	private PickDialogcallback pickDialogcallback;
	private Dialog dialog;
	private Context context;
	private List<DialogObj> list;
	private DialogObj selectItem;
	private ListView lv_list;
	private Button btn_cancel;
	OptionListAdapter mAdapter;
	public DialogOptionSelect(Context con,PickDialogcallback pickDialogcallback,List<DialogObj> items,DialogObj selectItem) {
		this.context = con;
		dialog = new Dialog(context, R.style.dialog);
		this.pickDialogcallback = pickDialogcallback;
		dialog.setContentView(R.layout.dialog_option_select);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialog.getWindow().setWindowAnimations(R.style.dialog_up);
		dialog.getWindow().setGravity(Gravity.BOTTOM);
		this.list = items;
		this.selectItem = selectItem;
		initUI();
	}
	private void initUI(){
		lv_list = (ListView) dialog.findViewById(R.id.lv_list);
		btn_cancel = (Button)dialog.findViewById(R.id.btn_cancel);
		mAdapter = new OptionListAdapter(context,list);
		lv_list.setAdapter(mAdapter);
		lv_list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(pickDialogcallback!=null){
					pickDialogcallback.onItemSelect(list.get(arg2));
				}
				dialog.dismiss();
			}
			
		});
		
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
	
	private class OptionListAdapter extends BaseAdapter {
		
		private Context context;
		private List<DialogObj> dataList;
		private Map<String,View> viewMap = new HashMap<String,View>();
		
		public OptionListAdapter(Context context, List<DialogObj> data){
			this.context = context;
			this.dataList = data;
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {		
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final DialogObj item = dataList.get(position);
			View rowView = viewMap.get(item.getCode());
			final NewListItemCache viewCache;
	        if (rowView == null) {
	                rowView = LayoutInflater.from(context).inflate(R.layout.item_option, null);
	                viewCache = new NewListItemCache(rowView,null,context,item.getCode());
	                rowView.setTag(viewCache);
	        } else {
	                viewCache = (NewListItemCache) rowView.getTag();
	        }
	        
	        ImageView iv_image=(ImageView) rowView.findViewById(R.id.iv_image);
	        iv_image.setImageResource(ComApp.getInstance().appStyle.btn_comsure_selector);
	        
	        viewCache.getTv_title().setText(item.getName());
	        if(selectItem!=null&&selectItem.getCode().equals(item.getCode())){
	        	viewCache.getIv_image().setVisibility(View.VISIBLE);
	        }
	        else{
	        	viewCache.getIv_image().setVisibility(View.INVISIBLE);
	        }
	        return rowView;
		}

		public Context getContext() {
			return context;
		}

		public void setContext(Context context) {
			this.context = context;
		}
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
