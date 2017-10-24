package com.cardee.owner_home.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.owner_home.view.adapter.CarListAdapter;

public class OwnerCarsFragment extends Fragment {

    private CarListAdapter mAdapter;
    private RecyclerView mCarsListView;

    public static Fragment newInstance() {
        return new OwnerCarsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CarListAdapter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_cars, container, false);
        mCarsListView = rootView.findViewById(R.id.owner_cars_list);
        initCarList(mCarsListView);
        return rootView;
    }

    private void initCarList(RecyclerView listView) {
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listView.setItemAnimator(new DefaultItemAnimator());
    }
}
