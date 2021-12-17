package com.example.javaclicker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.javaclicker.MainActivity;
import com.example.javaclicker.R;

import java.util.Observable;
import java.util.Observer;

public class FactoryFragment extends Fragment {
    MainActivity activity;
    ProgressBar oProd;
    ProgressBar oCon;
    ProgressBar gProd;
    ProgressBar gCon;
    View view;

    public FactoryFragment(Activity activity){
        this.activity = (MainActivity) activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_factory, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        this.view = view;
        this.oProd =  (ProgressBar) view.findViewById(R.id.oProd);
        this.oCon =  view.findViewById(R.id.oCon);
        this.gProd =  view.findViewById(R.id.gProd);
        this.gCon =  view.findViewById(R.id.gCon);
    }
    public void update(int oiProd,int gaProd){
        if(view != null){
            if(oiProd != 0 && gaProd != 0){
                if(oiProd >= gaProd){
                    int diff = (gaProd / oiProd) * 100;
                    oProd.setProgress(100,true);
                    oCon.setProgress(diff,true);
                }
                else{
                    int diff = (oiProd / gaProd ) * 100;
                    oProd.setProgress(diff,true);
                    oCon.setProgress(100,true);
                }
                gProd.setProgress(100,true);
                gCon.setProgress(0,true);
            }else{
                oProd.setProgress(100,true);
                oCon.setProgress(0, true);
            }
        }
    }
}
