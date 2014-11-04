package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.FeatureInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class NavigationFragment extends Fragment implements OnClickListener {
	private Button movieBtn, tvBtn, musicBtn;
	private MovieFragment movieFragment;
	private TVFragment TVFragment;
	private MusicFragment musicFragment;
	private List<Button> mList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mList = new ArrayList<Button>();
		View view = inflater.inflate(R.layout.navigation_fragment, null);
		movieBtn = (Button) view.findViewById(R.id.movieBtn);
		tvBtn = (Button) view.findViewById(R.id.tvBtn);
		musicBtn = (Button) view.findViewById(R.id.musicBtn);
		mList.add(movieBtn);
		mList.add(tvBtn);
		mList.add(musicBtn);
		movieBtn.setOnClickListener(this);
		tvBtn.setOnClickListener(this);
		musicBtn.setOnClickListener(this);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		movieFragment = new MovieFragment();
		TVFragment = new TVFragment();
		musicFragment = new MusicFragment();
		transaction.add(R.id.container, movieFragment);
		transaction.commit();
		return view;
	}

	@Override
	public void onClick(View v) {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		switch (v.getId()) {
		case R.id.movieBtn:
			transaction.replace(R.id.container, movieFragment);
			selectedBtn(0);
			break;
		case R.id.tvBtn:
			transaction.replace(R.id.container, TVFragment);
			selectedBtn(1);
			break;
		case R.id.musicBtn:
			transaction.replace(R.id.container, musicFragment);
			selectedBtn(2);
			break;
		}
		transaction.commit();
	}

	// 单击不同按钮颜色变化
	private void selectedBtn(int position) {
		for (int i = 0; i < mList.size(); i++) {
			if (position == i) {
				mList.get(i).setBackgroundColor(Color.BLUE);
			} else {
				mList.get(i).setBackgroundColor(Color.GRAY);
			}
		}
	}
}
