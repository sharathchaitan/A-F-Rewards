package com.example.aberchrombie.shar.recylerViewAdapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aberchrombie.shar.R;
import com.example.aberchrombie.shar.retrofit.ViewModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductCardRecylerViewAdapter extends RecyclerView.Adapter<ProductCardRecylerViewAdapter.ViewHolder> {

    private ArrayList<ViewModel> viewModels;
    private Context context;
    private SharedPreferences sharedPreferences;

    public ProductCardRecylerViewAdapter(Context context, ArrayList<ViewModel> viewModels) {
        this.viewModels = viewModels;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Key", Context.MODE_PRIVATE);

    }

    @Override
    public ProductCardRecylerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_view_layout, parent, false);
        return new ProductCardRecylerViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ProductCardRecylerViewAdapter.ViewHolder holder, int position) {

        ViewModel viewModel = viewModels.get(position);
        holder.title.setText(viewModel.getTitle());
        Picasso.with(context).load(viewModel.getBackGroundImage()).into(holder.imageViewUrl);

        if (viewModel.getTopDescription() == null) {
            holder.topDescription.setVisibility(View.GONE);
        } else {
            holder.topDescription.setText(viewModel.getTopDescription());
        }

        if (viewModel.getBottomDescription() == null) {
            holder.bottomDescription.setVisibility(View.GONE);
        } else {
            holder.bottomDescription.setText(viewModel.getBottomDescription());
        }

        if (viewModel.getPromoMessage() == null) {
            holder.promoMessage.setVisibility(View.GONE);
        } else {
            holder.promoMessage.setText(viewModel.getPromoMessage());
        }

        if (viewModel.getContent() == null) {
            holder.shopWomen.setVisibility(View.GONE);
            holder.shopMen.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < viewModel.getContent().length(); i++) {

                if (viewModel.getContent().length() == 1) {
                    try {
                        JSONObject jsonObject = viewModel.getContent().getJSONObject(i);
                        if (jsonObject.has("target")) {
                            holder.shopMen.setText(jsonObject.get("title").toString());
                        }
                        holder.shopWomen.setVisibility(View.GONE);
                        if (jsonObject.has("target")) {
                            sharedPreferences.edit().putString("url", jsonObject.getString("target")).apply();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonObject = viewModel.getContent().getJSONObject(i);
                        if (jsonObject.has("target")) {
                            if (i == 0) {
                                holder.shopMen.setText(jsonObject.get("title").toString());
                            } else {
                                holder.shopWomen.setText(jsonObject.get("title").toString());
                            }
                        }
                        holder.shopWomen.setVisibility(View.GONE);
                        if (jsonObject.has("target")) {
                            sharedPreferences.edit().putString("url", jsonObject.getString("target")).apply();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return viewModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, topDescription, promoMessage, bottomDescription;
        private ImageView imageViewUrl;
        private Button shopMen, shopWomen;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_view_title);
            imageViewUrl = itemView.findViewById(R.id.image_view);
            topDescription = itemView.findViewById(R.id.text_view_description);
            bottomDescription = itemView.findViewById(R.id.tv_bottom_description);
            promoMessage = itemView.findViewById(R.id.text_view_promo_message);
            shopMen = itemView.findViewById(R.id.bt_shop_men);
            shopWomen = itemView.findViewById(R.id.bt_shop_women);
        }
    }
}