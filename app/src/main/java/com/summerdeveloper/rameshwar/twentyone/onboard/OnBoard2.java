package com.summerdeveloper.rameshwar.twentyone.onboard;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.summerdeveloper.rameshwar.twentyone.R;

public class OnBoard2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle s) {

        View v= inflater.inflate(
                R.layout.onboarding_2,
                container,
                false
        );
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "Redressed.ttf");
        TextView tv21=(TextView)v.findViewById(R.id.tv23);
        tv21.setTypeface(custom_font);
        return v;

    }
}