package com.example.praktika1;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    private TestButton myButton;
    private EditText text1;
    private EditText text2;
    private EditText text3;
    private EditText text4;
    private TextView result;
    private SensorManager sensorManager;
    private Acceler acceler;
    private Button ActivityButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_actions,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_setting){

        }
        else {
            return super.onOptionsItemSelected(item);
        }

        return false;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //acceler = new Acceler(sensorManager);
        setContentView(R.layout.activity_main);
        initViews();




    }

    private void initViews() { // инициализация переменных

        text1 = findViewById(R.id.Text_Price);
        text2 = findViewById(R.id.Text_Interest_rate);
        text3 = findViewById(R.id.Text_First_contribution);
        text4 = findViewById(R.id.Text_Credit_term);
        result = findViewById(R.id.result);
        myButton = new TestButton(this, R.id.button);

        ActivityButton = findViewById(R.id.activity2);


        ActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,SecondActivity.class);
                i.putExtra("Result",result.getText());
                i.putExtra("key", "myKey");
                startActivity(i);
            }
        });

    }






    class TestButton implements View.OnClickListener {
        private Button button;


        TestButton(Activity activity, int id){
            button = findViewById(id);
            button.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) { // обработка нажатия кнопки

            new Load().execute();


        }
    }

    //объект потока
        private class Load extends AsyncTask<Void, Integer, Void> {
        String Res;
        ProgressBar progressBar;
        private String Result() { // расчет ежемесячный платежей

            Double s = Double.valueOf(text1.getText().toString());
            Double m = Double.valueOf(text4.getText().toString());
            Double k = Double.valueOf(text3.getText().toString());
            Double p = Double.valueOf(text2.getText().toString())/(100*12);
            //Double x = s*/(p/(1+p)-m-1);
            Double y = (s-k)/m + s*p;
            DecimalFormat decimalFormat = new DecimalFormat( "#.###" );
            try {

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return decimalFormat.format(y); //результат ежемесячных платежей
        }

        private boolean Check(){ // Проверка заполнены ли строки

            try {

                Double t1 = Double.valueOf(text1.getText().toString());
                Double t2 = Double.valueOf(text2.getText().toString());
                Double t3 = Double.valueOf(text3.getText().toString());
                Double t4 = Double.valueOf(text4.getText().toString());

                if(t1 > -1 && t2 > -1 && t3 > -1 && t4 > -1){
                    return true; // строка заполнина
                }

            }catch (NumberFormatException e){
                System.err.println("Неправельный формат строки");
            }

            return false; // строка не заполнина

        }

        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected Void doInBackground(Void... voids) { //получение результата

            if(Check()){
                Res = Result();
            }



            return null;
        }

        protected void onPostExecute(Void image) {
            progressBar.setVisibility(View.INVISIBLE);
            if(!Check()){
                    Toast.makeText(MainActivity.this, "Вы ввели не все данные", Toast.LENGTH_SHORT).show();
            }
            result.setText(Res);
        }


    }



}






