package com.format.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.format.activity.R;

public class GalleryAdapter extends BaseAdapter {

	private Context context;
	int mGalleryItemBackground;

	private int[] images = { R.drawable.lession1, R.drawable.lession2,
			R.drawable.lession3, R.drawable.lession4,R.drawable.lession5,R.drawable.lession6,R.drawable.lession7 };

	public GalleryAdapter(Context ctx) {
		this.context = ctx;
		TypedArray a = context.obtainStyledAttributes(R.styleable.Gallery);

		/* 取得Gallery属性的Index id */
		mGalleryItemBackground = a.getResourceId(
				R.styleable.Gallery_android_galleryItemBackground, 0);

		/* 让对象的styleable属性能够反复使用 */
		a.recycle();
	}

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		return images[position];
	}

	@Override
	public long getItemId(int position) {
		return images[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView view = (ImageView) convertView;

		if (view == null) {
			view = new ImageView(context); // 就新建一个
		}

		// 否则就是用已经存在的 convertView 。

		// 上面做法可以大幅度提高程序运行性能 ， 也可以减少内存的使用 ， 尤其在 Gallery 对象

		// 中有很多 item 的时候

		// 设定显示图片

		view.setImageResource(images[position]);

		// 设定每个图片的显示大小

		view.setLayoutParams(new Gallery.LayoutParams(100, 100));

		// view.setScaleType(ImageView.ScaleType.FIT_XY); // 这个是维持图片原始大小

		// 设定图片缩放：即在上面规定的大小中进行显示，并且居中

		view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		
		view.setBackgroundResource(mGalleryItemBackground);

		return view;
	}

}
