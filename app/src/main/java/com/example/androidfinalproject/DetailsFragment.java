package com.example.androidfinalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

/**
 *  This class handles adding content passed to it by the
 *  favourites list to be displayed in a fragment.
 *
 * @author Byron, Alycia
 */

public class DetailsFragment extends Fragment {

    private Bundle dataFromActivity;
    private Button btnHD;
    private TextView title;
    private TextView date;
    private ImageView image;
    private Bitmap nasaPic;
    String filepath;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get bundles
        dataFromActivity = getArguments();

        View result = inflater.inflate(R.layout.fragment_details, container, false);

        title = result.findViewById(R.id.tv_Title);
        title.setText(dataFromActivity.getString("title"));

        date = result.findViewById(R.id.tv_Date);
        date.setText(dataFromActivity.getString("date"));

        image = result.findViewById(R.id.iv_Nasa);
        filepath = dataFromActivity.getString("filePath");

        try {
            // set Image View to saved file.
            FileInputStream inputStream = getActivity().openFileInput(filepath);
            nasaPic = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        image.setImageBitmap(nasaPic);

        // open HD link in browser
        btnHD = result.findViewById(R.id.btnHD);
        btnHD.setOnClickListener(click -> {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataFromActivity
                    .getString("hdUrl")));
            startActivity(browserIntent);

        });

        // Inflate the layout for this fragment
        return result;
    }

}