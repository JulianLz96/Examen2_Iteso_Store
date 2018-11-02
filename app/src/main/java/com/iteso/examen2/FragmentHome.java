package com.iteso.examen2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iteso.pdm18_scrollabletabs.R;
import com.iteso.examen2.beans.ItemProduct;
import com.iteso.examen2.database.DataBaseHandler;
import com.iteso.examen2.database.ItemProductControl;
import com.iteso.examen2.tools.Constant;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
    ItemProductControl itemProductControl = new ItemProductControl();
    DataBaseHandler dh = DataBaseHandler.getInstance(getActivity());
    ArrayList<ItemProduct> products;

    RecyclerView recyclerView;

    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("Tech", "onCreateView_home");
        View rootView = inflater.inflate(R.layout.fragment_technology, container, false);
        recyclerView = rootView.findViewById(R.id.fragment_recycler);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        recyclerView.setHasFixedSize(true);
        // Use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        products = new ArrayList<>();
        products = itemProductControl.getItemProductsByCategory(2, dh);
        Log.e("Tech", "onActivityCreated_home: " + products.toString());
        AdapterProduct adapterProduct = new AdapterProduct(Constant.FRAGMENT_HOME, getActivity(), products);
        recyclerView.setAdapter(adapterProduct);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
