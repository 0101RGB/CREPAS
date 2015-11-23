package com.rgb0101.crepas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by noirCynical on 2015. 11. 21..
 */
public class MainFragment extends Fragment {
    private View wholeView= null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wholeView = inflater.inflate(R.layout.fragment_main, null);
        return wholeView;
    }
    @Override
    public void onResume(){
        super.onResume();
        makeView();
    }
    @Override
    public void onPause(){
        super.onPause();
    }

    private void makeView(){
        ((Button)wholeView.findViewById(R.id.btnFragmentMainFindMusic)).setOnClickListener(click);
        ((Button)wholeView.findViewById(R.id.btnFragmentMainStartCREPAS)).setOnClickListener(click);
    }

    public void setSelectedFile(String filename){ ((TextView)wholeView.findViewById(R.id.textFragmentMainSelected)).setText(filename); }

    View.OnClickListener click= new View.OnClickListener(){
        @Override
        public void onClick(View v){
            int id= v.getId();

            if(id == R.id.btnFragmentMainFindMusic) ((MainActivity)getActivity()).moveFragment(Constants.FINDMUSIC);
            else if(id == R.id.btnFragmentMainStartCREPAS){
                ((MainActivity)getActivity()).finishCREPAS(CREPASProcessing.crepasFromPath(((MainActivity)getActivity()).getFromMusic(), "file:///android_asset"));
            }
        }
    };
}
