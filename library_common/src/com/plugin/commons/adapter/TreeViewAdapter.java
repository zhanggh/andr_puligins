package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.SettingFqaModel;
import com.plugin.commons.service.DisClsTestService;
import com.plugin.commons.widget.TreeView;
import com.plugin.commons.widget.TreeView.IphoneTreeHeaderAdapter;


 

@SuppressLint("UseSparseArrays")
public class TreeViewAdapter extends BaseExpandableListAdapter
implements IphoneTreeHeaderAdapter {
	private LayoutInflater mInflater;
	private TreeView treeView;
	
	private HashMap<Integer, Integer> groupStatusMap;
	private String[] groups = { "如何成为智慧浑南会员？", "如何成为智慧浑南会员？", "如何成为智慧浑南会员？", "其他问题？" };
	private String[][] children = {
		{ "01" },
		{ "02"},
		{ "03"},
		{ "04"} };
	
	public TreeViewAdapter(LayoutInflater mInflater, TreeView treeView) {
		this.mInflater=mInflater;
		this.treeView=treeView;
		groupStatusMap = new HashMap<Integer, Integer>();
		DisClsTestService service= new DisClsTestService();
		RspResultModel rsp = service.getFqaList();
		List<SettingFqaModel> list = rsp.getFqa_list();
		if(list!=null&&list.size()>0){
//			List<HelpInfo> helps=list.getHelps();
//			groups=new String[helps.size()];
			children=new String[list.size()][1];
			for(int a=0;a<list.size();a++){
//				groups[a]=list.get(a).getContent();
				children[a][0]=list.get(a).getContent();
			}
		}
	}
	
	public Object getChild(int groupPosition, int childPosition) {
		return children[groupPosition][childPosition];
	}
	
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	public int getChildrenCount(int groupPosition) {
		return children[groupPosition].length;
	}
	
	public Object getGroup(int groupPosition) {
		return groups[groupPosition];
	}
	
	public int getGroupCount() {
		return groups.length;
	}
	
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	public boolean hasStableIds() {
	return true;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
		boolean isLastChild, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	if (convertView == null) {
		convertView = mInflater.inflate(R.layout.item_setting_fqa, null);
	}
	TextView state = (TextView) convertView
		.findViewById(R.id.tv_fqa_content);
	 
		state.setText(getChild(groupPosition,0).toString());
//	}else if(1==groupPosition){
//		state.setText("当买到检测卡后，请在检测卡指定的官网下载该app应用，并注册用户，登陆app,然后申请采样器，本公司将在收到申请后，尽快将采样器寄送到申请人指定的收件地址，收件人拿到采样器后，进行采样器登记，并收集样本，寄送会检测中心");
//	}else if(2==groupPosition){
//		state.setText("用户将采样器寄送会检测中心的5个工作日后，登陆本app进行检测结果查询，可以查询到该检测卡对应的检查结果");
//	}else if(3==groupPosition){
//		state.setText("如果发现收到的采样器登记失败，则需与本公司运营人员联系，寻求人工处理");
//	}
	return convertView;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
		View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	if (convertView == null) {
		convertView = mInflater.inflate(R.layout.list_group_view, null);
	}
	TextView groupName = (TextView) convertView
			.findViewById(R.id.group_name);
	groupName.setText(groups[groupPosition]);
	
	ImageView indicator = (ImageView) convertView
			.findViewById(R.id.group_indicator);
	TextView onlineNum = (TextView) convertView
			.findViewById(R.id.online_count);
	onlineNum.setText(getChildrenCount(groupPosition) + "/"
			+ getChildrenCount(groupPosition));
	if (isExpanded) {
		indicator.setImageResource(R.drawable.title_active_ico);
	} else {
		indicator.setImageResource(R.drawable.title_normal_ico);
	}
		return convertView;
	}
	
	@Override
	public int getTreeHeaderState(int groupPosition, int childPosition) {
		final int childCount = getChildrenCount(groupPosition);
		if (childPosition == childCount - 1) {
			return PINNED_HEADER_PUSHED_UP;
		} else if (childPosition == -1
				&& !treeView.isGroupExpanded(groupPosition)) {
			return PINNED_HEADER_GONE;
		} else {
			return PINNED_HEADER_VISIBLE;
		}
	}
	
	@Override
	public void configureTreeHeader(View header, int groupPosition,
		int childPosition, int alpha) {
	// TODO Auto-generated method stub
	((TextView) header.findViewById(R.id.group_name))
			.setText(groups[groupPosition]);
//	((TextView) header.findViewById(R.id.online_count))
//			.setText(getChildrenCount(groupPosition) + "/"
//					+ getChildrenCount(groupPosition));
	((TextView) header.findViewById(R.id.online_count)).setText("");
	}
	
	@Override
	public void onHeadViewClick(int groupPosition, int status) {
	// TODO Auto-generated method stub
	groupStatusMap.put(groupPosition, status);
	}
	
	@Override
	public int getHeadViewClickStatus(int groupPosition) {
		if (groupStatusMap.containsKey(groupPosition)) {
			return groupStatusMap.get(groupPosition);
		} else {
			return 0;
		}
	}

}