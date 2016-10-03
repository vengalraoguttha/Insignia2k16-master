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
 * Created by hp on 01-07-2016.
 */
public class f2 extends Fragment {
    static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 100;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Object> mContentItems = new ArrayList<>();

    public static f2 newInstance() {
        return new f2();
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

        mAdapter = new TestRecyclerViewAdapter2(mContentItems);

        //mAdapter = new RecyclerViewMaterialAdapter();
        mRecyclerView.setAdapter(mAdapter);
        if(mContentItems!=null) mContentItems.clear();
        {
            /*for (int i = 0; i < ITEM_COUNT; ++i) {
                mContentItems.add(new Object());
            }*/
            mContentItems.add(new Object("Google","Title sponsor",R.drawable.image2));
            mContentItems.add(new Object("Youtube","co sponsor",R.drawable.image2));
            mContentItems.add(new Object("Facebook","Shirt sponsor",R.drawable.image2));
            mContentItems.add(new Object("Hungry House","Title sponsor",R.drawable.image2));
            mContentItems.add(new Object("Pizza Hut","Title sponsor",R.drawable.image2));
            mContentItems.add(new Object("KFC","Food sponsor",R.drawable.image2));
            mContentItems.add(new Object("Hungry House","Title sponsor",R.drawable.image2));
            mContentItems.add(new Object("Hungry House","Title sponsor",R.drawable.image2));
            mContentItems.add(new Object("Hungry House","Title sponsor",R.drawable.image2));
            mContentItems.add(new Object("Hungry House","Title sponsor",R.drawable.image2));
            mContentItems.add(new Object("Hungry House","Title sponsor",R.drawable.image2));
            mAdapter.notifyDataSetChanged();
        }
    }
}
