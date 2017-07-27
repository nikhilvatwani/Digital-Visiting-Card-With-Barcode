package com.phpmysql.navigationdrawer;


import android.content.Context;
import android.content.SharedPreferences;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {
    private DrawerLayout mDrawerLayout;
    public static ActionBarDrawerToggle mActionBarDrawerToggle;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private final static String PREF_KEY = "testpref";
    private final static String KEY = "test";
    private RecyclerView recyclerView;
    private VivzAdapter vivzAdapter;
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readPrefrences(getActivity(),PREF_KEY,"false"));
        if(savedInstanceState!=null){
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.drawerList);
        vivzAdapter = new VivzAdapter(getActivity(),getData());
        recyclerView.setAdapter(vivzAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    public static List<Information> getData(){
        List<Information> data = new ArrayList<>();
        int[] icons = {R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
        String[] title = {"First Page","Second Page","Third Page","Fourth Page"};
        for(int i=0;i<icons.length&&i<title.length;i++){
            Information current = new Information();
            current.iconId = icons[i];
            current.title = title[i];
            data.add(current);
        }
        return data;
    }

    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(),KEY,mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if(!mUserLearnedDrawer&&!mFromSavedInstanceState){
            mDrawerLayout.openDrawer(getActivity().findViewById(R.id.fragment_navigation_drawer));
        }
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                Log.d("Here :","inside");
                mActionBarDrawerToggle.syncState();
            }
        });

    }
    public static void saveToPreferences(Context context,String PreferenceName, String PreferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PreferenceName, PreferenceValue);
        editor.apply();
    }
    public static String readPrefrences(Context context, String PreferenceName, String DefaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_KEY,Context.MODE_PRIVATE);
        return sharedPreferences.getString(PreferenceName,DefaultValue);
    }
}
