package com.example.patrick.aspro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.patrick.aspro.models.User_pro;

import java.util.ArrayList;

/**
 * Created by Patrick on 26/08/2017.
 */

public class ArrayAdapterContact extends android.widget.ArrayAdapter<User_pro>{
    private final Context context;
    private final ArrayList<User_pro> elements;

    public ArrayAdapterContact(@NonNull Context context, ArrayList<User_pro> elements) {
        super(context, R.layout.listview_searchpro, elements);
        this.context = context;
        this.elements = elements;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_searchpro,parent,false);

        TextView namePro = (TextView) rowView.findViewById(R.id.proListElement_name);
        TextView profession = (TextView) rowView.findViewById(R.id.proListElement_profession);
        TextView email = (TextView) rowView.findViewById(R.id.proListElement_email);

        namePro.setText(elements.get(position).getName());
        profession.setText(elements.get(position).getProfession());
        email.setText(elements.get(position).getEmail());

        return rowView;
    }
}


