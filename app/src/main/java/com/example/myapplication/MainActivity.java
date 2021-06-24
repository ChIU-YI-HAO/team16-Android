package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private TextView textViewResult;
    String url;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("網站評分系統");
        setContentView(R.layout.activity_main);
        Button like = findViewById(R.id.button);
        Button dislike = findViewById(R.id.button7);
        Button tolikelist = findViewById(R.id.button1);
        Button todislikelist = findViewById(R.id.button3);
        Button tosearch = findViewById(R.id.button5);
        textViewResult = findViewById(R.id.textView3);
        EditText input =  findViewById(R.id.textInputEditText2);
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ratewebsiteapi.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        tolikelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        todislikelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
            }
        });
        tosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity4.class);
                startActivity(intent);
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = input.getText().toString();
                if (url.matches("")) {
                    Toast.makeText(MainActivity.this, "你沒輸入任何網址", Toast.LENGTH_SHORT).show();
                }
                else if (url.contains(".com")||url.contains(".edu")||url.contains(".org")||url.contains(".net")||url.contains(".gov")||url.contains(".mil")){
                    status="like";
                    createPost(android_id,url,status);
                }
                else{
                    Toast.makeText(MainActivity.this, "你輸入的不是一個網址", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = input.getText().toString();
                if (url.matches("")) {
                    Toast.makeText(MainActivity.this, "你沒輸入任何網址", Toast.LENGTH_SHORT).show();
                }
                else if (url.contains(".com")||url.contains(".edu")||url.contains(".org")||url.contains(".net")||url.contains(".gov")||url.contains(".mil")){
                    status="dislike";
                    createPost(android_id,url,status);
                }
                else{
                    Toast.makeText(MainActivity.this, "你輸入的不是一個網址", Toast.LENGTH_SHORT).show();
                }
            }
        });
        createUser(android_id);
    }
    private  void createUser(String android_id){
        User user = new User(android_id);
        Call<User> call = jsonPlaceHolderApi.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    return;
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    private void createPost(String username,String url,String status) {
        Post post = new Post(username,url,status);
        Call<Post> call = jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                if (status.equals("like")){
                    Toast.makeText(MainActivity.this, "按讚成功", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MainActivity.this, "倒讚成功", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this, "你已經按讚或倒讚過這個網址了", Toast.LENGTH_SHORT).show();
            }
        });
    }
}