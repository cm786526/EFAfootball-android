package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

import com.example.cnm.efafootball.R;

/**
 * Created by cnm on 2016/11/5.
 */

public class Fragment_mine extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container,false);
        return view;
    }
}
