package com.rezaalhadar.listsearch.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rezaalhadar.R;
import com.rezaalhadar.listsearch.adapter.RecyclerViewAdapter;
import com.rezaalhadar.listsearch.helper.FilterType;
import com.rezaalhadar.listsearch.helper.GetUserModelData;
import com.rezaalhadar.listsearch.helper.UserModel;

import java.util.ArrayList;

/**
 * Created by sonu on 08/02/17.
 */
public class RecyclerViewFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private Context context;
    private RecyclerViewAdapter adapter;
    private ArrayList<UserModel> arrayList;
    private RadioGroup searchViaRadioGroup, filterByRadioGroup;
    private EditText searchEditText;
    private TextView searchViaLabel, filterByLabel;

    /*  Filter Type to identify the type of Filter  */
    private FilterType filterType;

    /*  boolean variable for Filtering */
    private boolean isSearchWithPrefix = false;

    public RecyclerViewFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        populateRecyclerView(view);
        implementEvents();
    }

    //Bind all Views
    private void findViews(View view) {
        filterType = FilterType.NAME;
        searchViaRadioGroup = view.findViewById(R.id.search_via_radio_group);
        filterByRadioGroup = view.findViewById(R.id.filter_type_radio_group);
        searchEditText = view.findViewById(R.id.search_text);

        searchViaLabel = view.findViewById(R.id.search_via_label);
        filterByLabel = view.findViewById(R.id.filter_by_label);
    }

    //Populate recycler view
    private void populateRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = GetUserModelData.getUserModelData();
        adapter = new RecyclerViewAdapter(context, arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void implementEvents() {
        filterByRadioGroup.setOnCheckedChangeListener(this);
        searchViaRadioGroup.setOnCheckedChangeListener(this);
        searchViaLabel.setOnClickListener(this);
        filterByLabel.setOnClickListener(this);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //On text changed in Edit text start filtering the list
                adapter.filter(filterType, charSequence.toString(), isSearchWithPrefix);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        int pos = radioGroup.indexOfChild(radioGroup.findViewById(checkedId));//get the checked position of radio button
        switch (radioGroup.getId()) {
            case R.id.search_via_radio_group:
                switch (pos) {
                    case 0:
                        filterType = FilterType.NAME;//Change filter type to Name if pos = 0
                        break;
                    case 1:
                        filterType = FilterType.NUMBER;//Change filter type to Number if pos = 1
                        break;
                    case 2:
                        filterType = FilterType.EMAIL;//Change filter type to Email if pos = 2
                        break;
                }
                break;
            case R.id.filter_type_radio_group:
                switch (pos) {
                    case 0:
                        isSearchWithPrefix = false;//Set boolean value to false
                        break;
                    case 1:
                        isSearchWithPrefix = true;//Set boolean value to true
                        break;

                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_via_label:
                //show hide the radio group
                if (searchViaRadioGroup.isShown()) {
                    searchViaLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_dropdown, 0);
                    searchViaRadioGroup.setVisibility(View.GONE);
                } else {
                    searchViaLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_dropdown, 0);
                    searchViaRadioGroup.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.filter_by_label:
                //show hide the radio group
                if (filterByRadioGroup.isShown()) {
                    filterByLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_dropdown, 0);
                    filterByRadioGroup.setVisibility(View.GONE);
                } else {
                    filterByLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_dropdown, 0);
                    filterByRadioGroup.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
