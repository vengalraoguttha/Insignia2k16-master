package com.example.surya.insignia2k16.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.surya.insignia2k16.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by havihavish on 04-07-2016.
 */
public class f3 extends Fragment {
    static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 100;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Object> mContentItems = new ArrayList<>();

    public static f3 newInstance() {
        return new f3();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager;

        if (GRID_LAYOUT) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            layoutManager = new LinearLayoutManager(getActivity());
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        //Use this now
       // mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        mAdapter = new TestRecyclerViewAdapter3(mContentItems);

        //mAdapter = new RecyclerViewMaterialAdapter();
        mRecyclerView.setAdapter(mAdapter);
        if(mContentItems!=null) mContentItems.clear();
        {
            /*for (int i = 0; i < ITEM_COUNT; ++i) {
                mContentItems.add(new Object());
            }*/
            mContentItems.add(new Object("What is insignia?","It is Alphaz Fest"));
            mContentItems.add(new Object("Who is the Director of NIT Delhi?","Sharma is the director of nit delhi"));
            mContentItems.add(new Object("How are You?","I am Fine but I am missing something"));
            mContentItems.add(new Object("What is Your team count?","Our team is of 4,Surya,Havish,Vengal,Chandan"));
            mContentItems.add(new Object("Where is Nit Delhi?","Nit Delhi is in Narela,Delhi"));
            mContentItems.add(new Object("Youtube","co sponsor"));
            mAdapter.notifyDataSetChanged();
        }
    }
}
