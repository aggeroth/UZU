package com.example.uzuswipe;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ScanFragment extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		if(container == null){
			return null;
		}
		Activity activity = getActivity();
		View view = (RelativeLayout)inflater.inflate(R.layout.fragment_scan, container, false);
		
		final ImageView iView = (ImageView)view.findViewById(R.id.imageButton1);
		
		final Animation an = AnimationUtils.loadAnimation(activity, R.anim.spin);
		
		ImageButton imgBtn = (ImageButton)view.findViewById(R.id.imageButton1);
		
		imgBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				iView.startAnimation(an);
			}
		});
		
		return view;
	}
}
