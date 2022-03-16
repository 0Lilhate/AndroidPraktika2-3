package com.example.praktika1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class SecondActivity extends AppCompatActivity {
    String result;
    TextView textView;
    Button next, back;
    ImageView imageView;
    String url = "https://cache.desktopnexus.com/thumbseg/1549/1549071-bigthumbnail.jpg";


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        init();

        Bundle arguments = getIntent().getExtras();
        this.result = arguments.get("Result").toString();

        textView.setText("Результат выполнения программы: " + result);

        ClickButton(next);
        ClickButton(back);
    }



    private void init() {
        textView = findViewById(R.id.directory);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        imageView = findViewById(R.id.imageView);
    }

    public void ClickButton(Button button) {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.back) {
                    Intent i = new Intent();
                    i.putExtra("Result", result);
                    setResult(RESULT_OK, i);
                    finish();
                } else if (view.getId() == R.id.next) {
                   new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(url);
                }

            }
        });

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Ошибка передачи изображения", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) { bmImage.setImageBitmap(result); }

    }



}
