package com.cardee.inbox.alert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;

public class AlertListFragment extends Fragment {

    public static AlertListFragment newInstance() {
        Bundle args = new Bundle();
        AlertListFragment fragment = new AlertListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inbox_alerts, container, false);
        return root;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }
}
