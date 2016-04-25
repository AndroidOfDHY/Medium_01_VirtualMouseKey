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

		/* ȡ��Gallery���Ե�Index id */
		mGalleryItemBackground = a.getResourceId(
				R.styleable.Gallery_android_galleryItemBackground, 0);

		/* �ö����styleable�����ܹ�����ʹ�� */
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
			view = new ImageView(context); // ���½�һ��
		}

		// ����������Ѿ����ڵ� convertView ��

		// �����������Դ������߳����������� �� Ҳ���Լ����ڴ��ʹ�� �� ������ Gallery ����

		// ���кܶ� item ��ʱ��

		// �趨��ʾͼƬ

		view.setImageResource(images[position]);

		// �趨ÿ��ͼƬ����ʾ��С

		view.setLayoutParams(new Gallery.LayoutParams(100, 100));

		// view.setScaleType(ImageView.ScaleType.FIT_XY); // �����ά��ͼƬԭʼ��С

		// �趨ͼƬ���ţ���������涨�Ĵ�С�н�����ʾ�����Ҿ���

		view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		
		view.setBackgroundResource(mGalleryItemBackground);

		return view;
	}

}
