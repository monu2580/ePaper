package com.deepesh.epaper.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deepesh.epaper.Model.Product;
import com.deepesh.epaper.R;

import java.util.List;

/**
 * Created by NgocTri on 10/22/2016.
 */

public class GridViewAdapter extends ArrayAdapter<Product> {
    public GridViewAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //v = inflater.inflate(R.layout.grid_item, null);
            v = inflater.inflate(R.layout.grid_item1, null);
        }
        Product product = getItem(position);
        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        //Button img = (Button) v.findViewById(R.id.imageView);
        TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);

        img.setImageResource(product.getImageId());
        //img.setBackgroundResource(product.getImageId());
        txtTitle.setText(product.getTitle());

        return v;
    }
}
