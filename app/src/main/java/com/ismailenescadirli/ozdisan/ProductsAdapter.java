package com.ismailenescadirli.ozdisan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;

public class ProductsAdapter extends ArrayAdapter<Products> {

    private Context mContext;
    private int mResource;

    public ProductsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Products>objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);
        TextView productTextView = convertView.findViewById(R.id.productId);
        TextView numberOfStock = convertView.findViewById(R.id.stockId);
        TextView productMean = convertView.findViewById(R.id.productDescriptionId);
        ConstraintLayout relativeLayout = convertView.findViewById(R.id.relativeLayout);

        Products productMain = getItem(position);
        String productName = productMain.getProduct();
        String stock = productMain.getStock();
        String productDescription = productMain.getProductDescription();
        String link = productMain.getLink();

        productTextView.setText(productName);
        numberOfStock.setText("Stock : "+ stock);
        productMean.setText(productDescription);

        relativeLayout.setOnClickListener(view -> {
            Intent dialogIntent = new Intent(mContext, ProductsDetailsActivity.class);
            dialogIntent.putExtra("key", link);
            mContext.startActivity(dialogIntent);
        });

        return convertView;
    }
}