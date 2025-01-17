package com.gaur.motivationtime.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.gaur.motivationtime.controller.AppController;
import com.gaur.motivationtime.model.Quote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuoteData {

    ArrayList<Quote> quoteArralist = new ArrayList<>();

    public void getQuotes(final QuoteAsyncResponse callBack) {

        String url = "https://raw.githubusercontent.com/pdichone/UIUX-Android-Course/master/Quotes.json%20";

       JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
               new Response.Listener<JSONArray>() {
                   @Override
                   public void onResponse(JSONArray response) {

                       for (int i= 0; i<response.length(); i++){
                           try {
                               JSONObject quoteObject = response.getJSONObject(i);

                               Quote quote = new Quote();
                               quote.setQuote(quoteObject.getString("quote"));
                               quote.setAuthor(quoteObject.getString("name"));

                               quoteArralist.add(quote);

                               Log.d("Author", quoteObject.getString("name"));
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }

                       if (null != callBack) callBack.processFinished(quoteArralist);

                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
}
