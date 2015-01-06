package com.plugin.commons.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import android.os.Handler;
import android.os.Message;

import com.zq.types.StBaseType;

public class SituoHttpAjax {

	private static ExecutorService ajaxExecutor = Executors.newFixedThreadPool(5,new ThreadFactory() {
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			// 设置线程的优先级别，让线程先后顺序执行（级别越高，抢到cpu执行的时间越多）
			t.setPriority(Thread.NORM_PRIORITY - 1);
			return t;
		}
	});
	
	
	public static void ajax(SituoAjaxCallBack callBack) {
		ajaxExecutor.submit(new SituoAjaxTask(callBack));
	}
	
	static class SituoAjaxTask implements Runnable{
		final private SituoAjaxCallBack mCallBack;
		
		final private Handler mHandler= new Handler(){
			public void handleMessage(Message msg) {
				mCallBack.callBack((StBaseType)msg.obj);
			}
		};
		
		public SituoAjaxTask(SituoAjaxCallBack callBack) {
			this.mCallBack = callBack;
		}

		public void run() {
			Message msg = new Message();
			msg.obj = mCallBack.requestApi();
			mHandler.sendMessage(msg);
		}
	}
	

	public interface SituoAjaxCallBack {
		/**
		 * 后台线程请求
		 * @return
		 */
		public StBaseType requestApi();
		
		
		/**
		 * 主界面线程，执行操作
		 * @param baseType
		 */
		public void callBack( StBaseType baseType);
		
		

	}
}
