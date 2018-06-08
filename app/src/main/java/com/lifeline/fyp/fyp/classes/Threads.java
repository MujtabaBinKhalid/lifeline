package com.lifeline.fyp.fyp.classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lifeline.fyp.fyp.lifestyle.suggestions.SwipeCardAdapter;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.models.StyleAccessories;
import com.lifeline.fyp.fyp.models.SunGlasses;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.StyleAccessoriesArray;
import com.wenchao.cardstack.CardStack;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mujtaba_khalid on 2/7/2018.
 */

public class Threads extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private String info;
    private Api api; // retorift.
    private String [] seperator; // used to seperator the string (pinfo)
    private Member updatedmember; // object of the callback.
    Call<StyleAccessoriesArray> call2;
    ArrayList<String> card_list;
    CardStack cardstack;
    ProgressDialog p;
    SwipeCardAdapter swipe_card_adapter;
    ArrayList<String>pictures;
    ArrayList<String>categorynames;


    public Threads(Context ctx, String allinfo, SwipeCardAdapter sca){
                this.ctx = ctx;
                this.info = allinfo;
                this.swipe_card_adapter = sca;
                this.p = new ProgressDialog( ctx );
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p.setMessage("Peforming Data");
        p.setIndeterminate(false);
        p.setProgressStyle( ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            Thread.sleep(1000);
            seperator = info.split( ";" );
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.Base_URL)
                    .addConverterFactory( GsonConverterFactory.create())
                    .build();
            api = retrofit.create(Api.class);

            SunGlasses suggestions  = new SunGlasses(seperator[2],seperator[4],seperator[5]);
            call2 = api.sunglassessugestion(suggestions);




            // sep[4] = faceshape; sep[2] = gender


            call2.enqueue(new Callback<StyleAccessoriesArray>() {
                @Override
                public void onResponse(Call<StyleAccessoriesArray> call, Response<StyleAccessoriesArray> response) {
                    Log.d("ronaldo", String.valueOf(response));
                    Log.d("ronaldo", String.valueOf(response.body()));

                    List<StyleAccessories> users = response.body().getData();

                    String[] Stringcategorynames = new String [users.size()];

                    for (int i=0; i< users.size();i++) {
                        Stringcategorynames[i] = users.get( i ).getCategory();
                        Log.d("itni khushi", String.valueOf(Stringcategorynames[i]));
                    }

                    String[] Stringpictures = new String [users.size()];

                    for (int i=0; i< users.size();i++) {
                        Stringpictures[i] = users.get( i ).getPicture();
                        Log.d("itni khushi pictures", String.valueOf(Stringpictures[i]));
                    }

                    int i =0;

                    pictures = new ArrayList<>();
                    for (String pic: Stringpictures){
                        pictures.add(pic);
                        Log.d("itni khushi arraylist", String.valueOf(pic));
                        i++;
                    }

                    categorynames = new ArrayList<>();

                    for (String category: Stringcategorynames){
                        categorynames.add(category);
                        i++;
                    }

                    Log.d("arraylength", String.valueOf( pictures.size() ) );



                    Log.d("INSDIE AL", String.valueOf(pictures.size()));
                    Log.d("IN al", String.valueOf(categorynames.size()));


                    swipe_card_adapter = new SwipeCardAdapter(ctx,0,categorynames,pictures);

                }

                @Override
                public void onFailure(Call<StyleAccessoriesArray> call, Throwable t) {
                    Log.d("errormessageregistrat", t.getMessage() );

                }
            });

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

          return null;

    }


}
