package com.takemehome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.takemehome.adapter.ConfirmContactsAdapter;

/**
 * Created by ruitzei on 12/9/16.
 */

public class GroupNameFragment extends Fragment {

    private static final String TAG = GroupNameFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private EditText editText;
    private Button button;

    public static GroupNameFragment newInstance() {
        
        Bundle args = new Bundle();
        
        GroupNameFragment fragment = new GroupNameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_name, null);

        recyclerView = (RecyclerView) view.findViewById(R.id.groupname_recycler);
        editText = (EditText) view.findViewById(R.id.group_name);
        button = (Button) view.findViewById(R.id.group_create);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setHasOptionsMenu(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new ConfirmContactsAdapter(((TakeMeHomeApp)getActivity().getApplication()).getContactFavs()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "group created");
                ((TakeMeHomeApp)getActivity().getApplication()).setGroupName(editText.getText().toString());
                Toast.makeText(getActivity(), "Group created", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }
}
