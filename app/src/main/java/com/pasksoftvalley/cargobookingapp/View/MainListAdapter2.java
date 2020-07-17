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

import java.util.List;

public class MainListAdapter2 extends ArrayAdapter<OrderDetailsModel> {

    Context context;
    List<OrderDetailsModel> arrayListEmployee;
    public MainListAdapter2(@NonNull Context context, List<OrderDetailsModel> arrayListEmployee) {
        super(context, R.layout.custum_list_view_sec,arrayListEmployee);
        this.context = context;
        this.arrayListEmployee = arrayListEmployee;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_list_view_sec,null,true);
        TextView tvID = view.findViewById(R.id.txt_id1);
        TextView tvName = view.findViewById(R.id.txt_name1);
        TextView bloodgroup = view.findViewById(R.id.bloodgroup1);
        tvID.setText(arrayListEmployee.get(position).getFirstName());
        tvName.setText(arrayListEmployee.get(position).getLastName());
        bloodgroup.setText(arrayListEmployee.get(position).getBloodGroup());
        return view;
    }
}
