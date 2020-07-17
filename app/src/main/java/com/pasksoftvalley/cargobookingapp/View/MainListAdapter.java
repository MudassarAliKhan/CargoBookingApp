package com.pasksoftvalley.cargobookingapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pasksoftvalley.cargobookingapp.Model.OrderDetailsModel;
import com.pasksoftvalley.cargobookingapp.Presenter.OrderDetails;
import com.pasksoftvalley.cargobookingapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainListAdapter extends ArrayAdapter<OrderDetailsModel> {

        Context context;
        List<OrderDetailsModel> arrayListEmployee;
public MainListAdapter(@NonNull Context context, ArrayList<OrderDetailsModel> arrayListEmployee) {
        super(context, R.layout.custom_list_item,arrayListEmployee);
        this.context = context;
        this.arrayListEmployee = arrayListEmployee;
        }
@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,null,true);
        TextView tvID = view.findViewById(R.id.txt_id);
        TextView tvName = view.findViewById(R.id.txt_name);
        TextView bloodgroup = view.findViewById(R.id.bloodgroup);
        tvID.setText(arrayListEmployee.get(position).getFirstName());
        tvName.setText(arrayListEmployee.get(position).getLastName());
        bloodgroup.setText(arrayListEmployee.get(position).getBloodGroup());
        return view;
        }
        }