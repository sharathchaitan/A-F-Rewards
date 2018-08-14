package com.example.aberchrombie.shar.retrofit;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.aberchrombie.shar.recylerViewAdapter.ProductCardRecylerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by saiabhinaypidugu on 1/24/18.
 */

public class ProductAsynchronous extends AsyncTask<String, String, ArrayList<ViewModel>> {
    int coiunt = 0;
    private Context context;
    RecyclerView recyclerView;

    public ProductAsynchronous(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected ArrayList<ViewModel> doInBackground(String... strings) {
        Gson gson = new Gson();
        //convert java object to JSON format
        String json = gson.toJson(loadJSONFromAsset());
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(loadJSONFromAsset());
        Log.d("tag", jsonObject.get("results") + "");
        ArrayList<ViewModel> viewModels = new ArrayList<>();
        try {
            JSONObject productCardJsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray productCardJsonArray = productCardJsonObject.getJSONArray("results");
            ViewModel viewModel;

            for (int i = 0; i < productCardJsonArray.length(); i++) {
                JSONObject prductCard = productCardJsonArray.getJSONObject(i);
                viewModel = new ViewModel();
                viewModel.setTitle(prductCard.get("title").toString());
                viewModel.setBackGroundImage(prductCard.get("backgroundImage").toString());
                if (prductCard.has("promoMessage")) {
                    viewModel.setPromoMessage(prductCard.get("promoMessage").toString());
                }
                if (prductCard.has("topDescription")) {
                    viewModel.setTopDescription(prductCard.get("topDescription").toString());
                }
                if (prductCard.has("bottomDescription")) {
                    viewModel.setBottomDescription(prductCard.get("bottomDescription").toString());
                }

                if (prductCard.has("content")) {
                    viewModel.setContent(prductCard.getJSONArray("content"));
                }

                viewModels.add(viewModel);
            }
        } catch (JSONException e) {
        }
        return viewModels;
    }


    @Override
    protected void onPostExecute(ArrayList<ViewModel> result) {
        ProductCardRecylerViewAdapter productCardRecylerViewAdapter = new ProductCardRecylerViewAdapter(context, result);
        recyclerView.setAdapter(productCardRecylerViewAdapter);
        productCardRecylerViewAdapter.notifyDataSetChanged();
    }

    public String loadJSONFromAsset() {
        InputStream is = null;
        String json = null;
        try {
            is = context.getAssets().open("result.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
