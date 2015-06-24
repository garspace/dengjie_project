package com.cloudstime.viewpager_slider;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2015/6/12.
 */
public class ViewPagerAdapter extends PagerAdapter {
   List<View> viewList;
    Context context;

    public ViewPagerAdapter(List<View> viewList, Context context) {
        this.viewList = viewList;
        this.context = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(viewList.get(position%(viewList.size())));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        container.addView(viewList.get(position % (viewList.size())));
        ImageView imageView = (ImageView) viewList.get(position%(viewList.size()));
        imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(context,""+position,Toast.LENGTH_LONG).show();
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
