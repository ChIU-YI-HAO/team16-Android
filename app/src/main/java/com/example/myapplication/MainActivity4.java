package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity4 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String require,upordown;
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private TextView textView;
    private ProgressBar progressBar;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Button button6 = findViewById(R.id.button6);
        Button search = findViewById(R.id.button8);
        EditText input = findViewById(R.id.textInputEditText1);
        textViewResult = findViewById(R.id.textView5);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ratewebsiteapi.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getrate();
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity4.this, MainActivity.class);
                startActivity(intent);
            }
        });
        textView = (TextView) findViewById(R.id.textView6);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress);
                textView.setText("" + progress + "%");

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        require = input.getText().toString();
                        if (require.matches("")) {
                            Toast.makeText(MainActivity4.this, "inputting nothing", Toast.LENGTH_SHORT).show();
                        } else {
                            upordown=spinner.getSelectedItem().toString();
                            if(upordown.equals("以上")){
                                upordown="up";
                                searchrate(require,progress,upordown);
                            }
                                else{
                                upordown="down";
                                searchrate(require,progress,upordown);
                            }
                        }
                    }
                });
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
    private void searchrate(String req,int progress,String upordown) {
        Call<List<Rate>> call = jsonPlaceHolderApi.searchrate(req,progress,upordown);
        call.enqueue(new Callback<List<Rate>>() {
            @Override
            public void onResponse(Call<List<Rate>> call, Response<List<Rate>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("code" + response.code());
                    return;
                }
                List<Rate> rates =response.body();
                for (Rate rate : rates) {
                    String content = "";
                    content += "ID: " + rate.getId() + "\n";
                    content += "Url: " + rate.getUrl() + "\n";
                    content += "Likecount: " + rate.getLikecount() + "\n";
                    content += "Dislikecount: " + rate.getDislikecount() + "\n";
                    content += "Rate: " + rate.getRate() + "\n\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Rate>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });


    }
    private void getrate() {
        Call<List<Rate>> call = jsonPlaceHolderApi.getRates();
        call.enqueue(new Callback<List<Rate>>() {
            @Override
            public void onResponse(Call<List<Rate>> call, Response<List<Rate>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Rate> rates = response.body();
                for (Rate rate : rates) {
                    String content = "";
                    content += "ID: " + rate.getId() + "\n";
                    content += "url: " + rate.getUrl() + "\n";
                    content += "likecount: " + rate.getLikecount() + "\n";
                    content += "dislikecount: " + rate.getDislikecount() + "\n";
                    content += "Rate: " + rate.getRate() + "\n\n";
                    textViewResult.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<Rate>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}