package com.cloudstime.viewpager_slider;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity {
    int imgId[]={R.drawable.viewpager_img01,R.drawable.viewpager_img02,R.drawable.viewpager_img03};
    int dotId[]={R.id.dot1,R.id.dot2,R.id.dot3};
    Date start,endDate;
    ViewPager viewPager;
    List<View> viewList;
    ImageView dot[]=new ImageView[3];
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            handler.sendEmptyMessageDelayed(0,3000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        dot[0]=(ImageView)findViewById(R.id.dot1);
        dot[1]=(ImageView)findViewById(R.id.dot2);
        dot[2]=(ImageView)findViewById(R.id.dot3);
        initImg();
        Log.v("Listview", String.valueOf(viewList.size()));
        viewPager.setAdapter(new ViewPagerAdapter(viewList, this));
        viewPager.setCurrentItem(viewList.size() * 300);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int k = i % viewList.size();
                for (int j = 0; j < imgId.length; j++) {
                    if (k == j) {
                        dot[j].setBackgroundResource(R.drawable.dot_focus);
                    } else {
                        dot[j].setBackgroundResource(R.drawable.dot_unfocus);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
       /* viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        handler.removeMessages(0);
                        Log.v("action_down", "DOWN");
                        Toast.makeText(MainActivity.this,"action_down excected!",Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.sendEmptyMessage(0);
                        Log.v("action_up","UP");
                        break;
                }
                return true;
            }
        });*/
        handler.sendEmptyMessageDelayed(0,3000);
        //cycleThread();
     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                while (isLoop) {
                    handler.sendEmptyMessage(0);
                    SystemClock.sleep(3000);
                }
            }
        }).start();*/
    }


    private void initImg() {
        viewList = new ArrayList<View>();
        for (int i=0;i<imgId.length;i++){
            dot[i]=(ImageView)findViewById(dotId[i]);   //找到小圆点图片Id

            ImageView imageView=new ImageView(this);    //设置ViewPager背景图片
            imageView.setBackgroundResource(imgId[i]);
            viewList.add(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class ViewPagerAdapter extends PagerAdapter {
        List<View> viewList;
        Context context;
        ViewGroup parent;

        public ViewPagerAdapter(List<View> viewList, Context context) {
            this.viewList = viewList;
            this.context = context;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           // container.removeView(viewList.get(position%viewList.size()));     //不要在destroy里remove

        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            parent = (ViewGroup) viewList.get(position%viewList.size()).getParent();
            if (parent != null) {
                parent.removeView(viewList.get(position%viewList.size()));
            }
            container.addView(viewList.get(position%viewList.size()));
           ImageView imageView = (ImageView) viewList.get(position%viewList.size());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   handler.removeMessages(0);
                       Toast.makeText(context,"clicked"+position,Toast.LENGTH_LONG).show();
                }
            });

            imageView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                          start=new Date(System.currentTimeMillis());
                            handler.removeMessages(0);
                            Log.v("action_down", "DOWN");
                            break;
                        case MotionEvent.ACTION_UP:
                            endDate =new Date(System.currentTimeMillis());
                            long internal = endDate.getTime() - start.getTime();
                            Log.v("internal ",internal+"");
                            if (internal<180) {
                                Toast.makeText(MainActivity.this, "action_up excected!", Toast.LENGTH_SHORT).show();
                            }
                                handler.sendEmptyMessageDelayed(0,3000);

                            break;
                        case MotionEvent.ACTION_MOVE:
                           // handler.removeMessages(0);
                            //  SystemClock.sleep(2000);
                            // Toast.makeText(MainActivity.this, "action_move excected!", Toast.LENGTH_SHORT).show();
                            //  SystemClock.sleep(2000);
                            handler.removeMessages(0);
                           handler.sendEmptyMessageDelayed(0, 4000);
                            break;
                    }
                    return true;
                }
            });
            return imageView;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view==o;
        }
    }

}

