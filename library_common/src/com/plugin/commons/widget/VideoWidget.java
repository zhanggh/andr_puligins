
package com.plugin.commons.widget;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;


public class VideoWidget extends Activity implements OnInfoListener, OnBufferingUpdateListener {
  public static final String PARAM_URL = "URL";
  public static final String PARAM_TITLE = "TITLE";
  //private String path = "http://221.180.149.181/file/static/webfiles/20141031/1846096380.flv";
  private String path = "";
  private Uri uri;
  private VideoView mVideoView;
  private ProgressBar pb;
  private TextView downloadRateView, loadRateView;
  Button btn_left;

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    if (!LibsChecker.checkVitamioLibs(this))
      return;
    setContentView(R.layout.activity_videobuffer);
    mVideoView = (VideoView) findViewById(R.id.buffer);
    pb = (ProgressBar) findViewById(R.id.probar);
    btn_left = (Button)findViewById(R.id.btn_left);
    downloadRateView = (TextView) findViewById(R.id.download_rate);
    loadRateView = (TextView) findViewById(R.id.load_rate);
    if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAM_URL)) {
   	 	path =(String)getIntent().getExtras().get(PARAM_URL);
	}
    if(!FuncUtil.isEmpty(path)){
   	 	uri = Uri.parse(path);
        mVideoView.setVideoURI(uri);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnBufferingUpdateListener(this);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
          @Override
          public void onPrepared(MediaPlayer mediaPlayer) {
            // optional need Vitamio 4.0
            mediaPlayer.setPlaybackSpeed(1.0f);
          }
        });
      }
    else{
    	DialogUtil.showToast(this, "loadingfail");
    	finish();
    }
    btn_left.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();
			overridePendingTransition(R.anim.fade,
					R.anim.fade_out);
		}
	});

  }

  @Override
  public boolean onInfo(MediaPlayer mp, int what, int extra) {
    switch (what) {
    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
      if (mVideoView.isPlaying()) {
        mVideoView.pause();
        pb.setVisibility(View.VISIBLE);
        downloadRateView.setText("");
        loadRateView.setText("");
        downloadRateView.setVisibility(View.VISIBLE);
        loadRateView.setVisibility(View.VISIBLE);

      }
      break;
    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
      mVideoView.start();
      pb.setVisibility(View.GONE);
      downloadRateView.setVisibility(View.GONE);
      loadRateView.setVisibility(View.GONE);
      break;
    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
      downloadRateView.setText("" + extra + "kb/s" + "  ");
      break;
    }
    return true;
  }

  @Override
  public void onBufferingUpdate(MediaPlayer mp, int percent) {
    loadRateView.setText(percent + "%");
  }
  
  	@Override
 	public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_BACK&& event.getRepeatCount() == 0) {
         	finish();
         	overridePendingTransition(R.anim.fade,
 					R.anim.fade_out);
             return false;
          }
          return super.onKeyDown(keyCode, event);
      }

}
