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
public class f4 extends Fragment {
    static final boolean GRID_LAYOUT = false;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Object> mContentItems = new ArrayList<>();

    public static f4 newInstance() {
        return new f4();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
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
        //mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        mAdapter = new TestRecyclerViewAdapter4(mContentItems);

        //mAdapter = new RecyclerViewMaterialAdapter();
        mRecyclerView.setAdapter(mAdapter);
        if(mContentItems!=null) mContentItems.clear();
                   mContentItems.add(new Object("Insignia 2k16","For those seeking thrills and challenges, along with a unique forum for expressing their avant-garde opinions, Insignia is a manifestation of their most ambitious dreams. Insignia is a literature festival, spanning two days. The events being held can be classified into three categories, namely, Quizzing, Debating and Writing, interspersed with a gamut of enjoyable recreational activities. The quizzes will pose a considerable challenge to those who wish to display their mental prowess. Their minds will be titillated by the impassioned debates on myriad of societal issues. And the story tellers shall grip their pens tighter when their creativity rises to its zenith. This literary festival aspires to ascend to the pinnacles of quality, and attract more individuals to bring their unique persona to the smorgasbord of minds.",
                           "ALPHAZ: ","Alphaz, the literary team of NIT Delhi is the core organization team of the fest. Alphaz is the debating and literary society of National Institute of Technology, Delhi. It was founded with the primary objective of creating a dimension of unorthodox learning. The events, conducted by Alphaz, train its participants to think clearly, argue coherently, and speak forcefully on any topic. Alphaz has continually endeavoured to provide our college with an unparalleled source for the oratorical and literary improvement. Yet the society has never been simply a student organization, and its influence and bonds of friendship formed survives long after the culmination of a student’s life. Alphaz faces the future confident in its traditions but cognizant of the need for innovation.",R.drawable.image2));

        }
}

