package com.xilehang.ceshi_gallery;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity implements OnItemSelectedListener,
		ViewFactory {

	private ImageSwitcher mSwitcher;

	private Integer[] mThumbIds = { R.drawable.red, R.drawable.yellow,
			R.drawable.blue};

	private Integer[] mImageIds = { R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
		mSwitcher.setFactory(this);
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));
		Button butright = (Button) findViewById(R.id.butright);
		Button butleft = (Button) findViewById(R.id.butleft);

		g = (Gallery) findViewById(R.id.gallery);
		//g.getSelectedItemPosition();
		ImageAdapter adapter = new ImageAdapter(this);
		g.setAdapter(adapter);
		g.setSelection(adapter.getCount()/2-2);//改变最开始的position的数值
		g.setOnItemSelectedListener(this);
		 Timer timer = new Timer();
	     timer.schedule(task, 3000, 3000);
		butleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (g.getSelectedItemPosition() == 0) {
					g.setSelection(mThumbIds.length - 1);
					mSwitcher.setImageResource(mImageIds.length - 1);
				} else if (g.getSelectedItemPosition() == 1) {
					g.setSelection(0);
					mSwitcher.setImageResource(0);

				} else {
				/*	System.out.println("我的" + g.getSelectedItemPosition());
					g.setSelection((g.getSelectedItemPosition() - 1)
							% mThumbIds.length);
					mSwitcher.setImageResource(mImageIds[(g
							.getSelectedItemPosition() - 1) % mImageIds.length]);
					*/Message message = new Message();
		            message.what = 2;
		            index--;
		            handler.sendMessage(message);
				}
			}
		});
		butright.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			     Message message = new Message();
		            message.what = 2;
		            index++;
		            handler.sendMessage(message);
				/*g.setSelection(mypos%mThumbIds.length);
				mSwitcher.setImageResource(mypos%mImageIds.length);*/

			}
		});
	}

	private int index = 0;
	private Gallery g;
	/**
     * 定时器，实现自动播放
     */
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 2;
            index = g.getSelectedItemPosition();
            index++;
            handler.sendMessage(message);
        }
    };
 
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case 2:
            	if(index<=1){
            	index=Integer.MAX_VALUE/2;
            	}
                g.setSelection(index);
                mSwitcher.setImageResource(index);
                break;

            default:
                break;
            }
        }
    };

	private int mypos;


	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			//return mThumbIds.length;
			return Integer.MAX_VALUE;//替换的
		}

		public Object getItem(int position) {
			
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);
			System.out.println("我的位置输入"+position);
			i.setImageResource(mThumbIds[position%mThumbIds.length]);
			i.setAdjustViewBounds(true);
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			i.setBackgroundColor(Color.TRANSPARENT);
			// i.setBackgroundResource(R.drawable.picturefrom);
			return i;
		}

		private Context mContext;
	}

	@Override
	public void onItemSelected(AdapterView<?> adapter, View v, int position,
			long id) {
		 mypos = position;
		mSwitcher.setImageResource(mImageIds[position%mThumbIds.length]);
		Toast.makeText(this, "你好啊"+position, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF0000f0);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return i;
	}
}
