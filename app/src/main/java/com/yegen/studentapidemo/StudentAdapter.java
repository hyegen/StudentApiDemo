package com.yegen.studentapidemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private Context context;
    private int resource;

    public StudentAdapter(@NonNull Context context, int pResource, @NonNull ArrayList<Student> pObjects) {
        super(context, pResource, pObjects);
        this.context = context;
        this.resource=pResource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).NAME;
        String surname= getItem(position).SURNAME;

        if (convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.student_adapter,parent,false);
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView nameTextView = convertView.findViewById(R.id.studentName_Textview);
        TextView surnameTextView = convertView.findViewById(R.id.studentDetail_TextView);

        nameTextView.setText(name);
        surnameTextView.setText(surname);

        return convertView;
    }
}
