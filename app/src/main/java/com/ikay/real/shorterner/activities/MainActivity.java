package com.ikay.real.shorterner.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import  androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ikay.real.shorterner.R;
import com.ikay.real.shorterner.models.ResponseBody;
import com.ikay.real.shorterner.retrofitutilities.ApiInterface;
import com.ikay.real.shorterner.retrofitutilities.RetrofitApiClient;
import com.ikay.real.shorterner.sysutilities.SystemInfo;
import com.tt.whorlviewlibrary.WhorlView;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    Button CopyLinkButton;
    Button PasteLinkButton;
    Button GenerateButton;
    Button CancelButtonOnCopy;
    Button CancelButtonOnPaste;
    EditText GeneratedLinkEditText;
    EditText PasteLinkEditText;
    Drawable drawableBackground;
    ConstraintLayout ConstraintLayoutMain;
    TextView ErrorTextInURLTextView,HostNameTextView;
    Retrofit retrofit;
    Context context;
    WhorlView whorlViewProgressView;
    CircleImageView circleImageView;
    BodyData bodyData;
    FloatingActionButton floatingActionButtonShareLink;
    Toolbar toolbar;

    boolean IsCancelButtonPasteVisible = true;
    //ClipboardManager clipboardManager =(android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    //URLUtil. check if url is valid
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CopyLinkButton = findViewById(R.id.buttonCopy);
        PasteLinkButton = findViewById(R.id.buttonPaste);
        GenerateButton =findViewById(R.id.GenerateButton);
        GeneratedLinkEditText =findViewById(R.id.editTextGeneratedLink);
        PasteLinkEditText= findViewById(R.id.editTextPasteLink);
        ConstraintLayoutMain = findViewById(R.id.constraintLayoutMain);
        ErrorTextInURLTextView =findViewById(R.id.textViewErrorInURL);
        CancelButtonOnCopy = findViewById(R.id.buttonCancelCopyText);
        CancelButtonOnPaste = findViewById(R.id.buttonCancelPasteText);
        whorlViewProgressView = this.findViewById(R.id.whorl2ProgressViewGenerate);
        HostNameTextView =findViewById(R.id.textViewUrlHostName);
        floatingActionButtonShareLink = findViewById(R.id.floatingActionButtonShareLink);
        circleImageView = (CircleImageView) findViewById(R.id.favIconCircularImageView);
        toolbar=findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        context =MainActivity.this;
        CopyLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(GeneratedLinkEditText.getText().toString().equals("")){
                   ErrorTextInURLTextView.setTextColor(getResources().getColor(R.color.error_red));
                   ErrorTextInURLTextView.setText("Please generate link");
               }else {
                   copyGeneratedLink(GeneratedLinkEditText.getText().toString());
                   displayInformation("Copied");
                   //CancelButtonOnPaste.setVisibility(View.VISIBLE);
               }
            }
        });
        PasteLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasteLinkEditText.setText(pasteLink());
                PasteLinkButton.setVisibility(View.VISIBLE);

            }
        });

        PasteLinkEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PasteLinkEditText.setBackground(getResources().getDrawable(R.drawable.edit_textbg));
                ErrorTextInURLTextView.setText("");
                if(PasteLinkEditText.getText().toString().length()==0){
                        CancelButtonOnPaste.setVisibility(View.GONE);
                }else {
                    CancelButtonOnPaste.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        GenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                ErrorTextInURLTextView.setTextColor(getResources().getColor(R.color.error_red));
                if(PasteLinkEditText.getText().toString().equals("")){
                    ErrorTextInURLTextView.setText("Field is empty");
                    PasteLinkEditText.setBackground(getResources().getDrawable(R.drawable.error_in_url_round_icon));
                    Snackbar.make(ConstraintLayoutMain,"eeeee",Snackbar.LENGTH_SHORT).setBackgroundTint(7).setBackgroundTint(getResources().getColor(R.color.error_red));
                    //kd.make
                }else {
                        url=PasteLinkEditText.getText().toString();
                        if(URLUtil.isValidUrl(url)){
                            //popupAlert(getResources().getString(R.string.alert_no_internet_title),getResources().getString(R.string.alert_no_internet_connection_information));
                            if(GeneratedLinkEditText.getText().length()>0){
                                GeneratedLinkEditText.setText("");
                                CancelButtonOnCopy.setVisibility(View.GONE);
                            }
                            whorlViewProgressView.setVisibility(View.VISIBLE);
                            whorlViewProgressView.start();
                            floatingActionButtonShareLink.setVisibility(View.GONE);
                            HostNameTextView.setText("");
                            sendLinkRequest(url);
                        }else {
                            ErrorTextInURLTextView.setText("Please insert/paste a valid URL"+" example ("+"http://www.bing.com/news/america"+")");
                        }
                }
            }
        });
        CancelButtonOnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelButtonOnPaste.setVisibility(View.GONE);
                PasteLinkEditText.setText("");
            }
        });
        CancelButtonOnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneratedLinkEditText.setText("");
                CancelButtonOnCopy.setVisibility(View.GONE);
            }
        });
        floatingActionButtonShareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLink(bodyData.getLink());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id =item.getItemId();
        if(id==R.id.about){
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("deprecation")
    private void copyGeneratedLink(String text){
        android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = ClipData.newPlainText("generated_url",text);
        clipboardManager.setPrimaryClip(clip);
    }
    @SuppressWarnings("deprecation")
    private String pasteLink(){
        String url=null;
        android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        try{
            url=clipboardManager.getText().toString();
            return url;

        }catch (NullPointerException NE){
            displayInformation("You haven't copied the URL");
            return url;
        }

    }
    private void sendLinkRequest(String url){
        Call<ResponseBody> call = RetrofitApiClient.getApiClient().create(ApiInterface.class).sendLinkRequest(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.body().getData().isEmpty()){
                    //displayInformation(response.body().getData());
                    GeneratedLinkEditText.setText(response.body().getData());
                    HostNameTextView.setText(response.body().getHostName());
                    CancelButtonOnCopy.setVisibility(View.VISIBLE);
                    floatingActionButtonShareLink.setVisibility(View.VISIBLE);
                    bodyData= new BodyData(response.body().getData());

                    //PROGRES VIEW
                    getHostIcon("http://"+response.body().getHostName());

                    whorlViewProgressView.stop();
                    whorlViewProgressView.setVisibility(View.GONE);
                }else {
                    displayInformation("There was an error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String title =getResources().getString(R.string.alert_no_internet_title);
                String information =getResources().getString(R.string.alert_no_internet_connection_information);
                whorlViewProgressView.stop();
                whorlViewProgressView.setVisibility(View.GONE);
                popupAlert(title,information);
            }
        });
    }

    private void removeCancelButton(Button CancelButton){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) CancelButton.getLayoutParams();
        final int REMOVE_BUTTON=0,MAX_COPY_PASTE_BUTTON_SIZE=88,MAX_CANCEL_BUTTON_SIZE=16;
        int PASTE_BUTTON_SIZE=layoutParams.width;
        if (CancelButtonOnPaste.getVisibility()==View.VISIBLE){
            CancelButton.setVisibility(View.GONE);
        }
    }
    private void getHostIcon(String url){
        url=url+"/favicon.ico";
        //Glide.with(this).load("https://www.baeldung.com/favicon.ico").;

        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        circleImageView.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        placeholder = getResources().getDrawable(R.drawable.url_logo);
                        circleImageView.setImageDrawable(placeholder);
                    }
                });
    }
    private void displayInformation(String information){
        Toast.makeText(MainActivity.this,information,Toast.LENGTH_SHORT).show();
    }
    private void shareLink(String link){
        Intent sendLinkIntent = new Intent();
        sendLinkIntent.setAction(Intent.ACTION_SEND);
        sendLinkIntent.putExtra(Intent.EXTRA_TEXT,link);
        sendLinkIntent.setType("text/plain");
        Intent shareLink= Intent.createChooser(sendLinkIntent,null);
        startActivity(shareLink);
    }
    private void popupAlert(String title,String information){
        AlertDialog.Builder myAlertBuilder = new
                AlertDialog.Builder(MainActivity.this);
        myAlertBuilder.setTitle(title);
        myAlertBuilder.setMessage(information);
        myAlertBuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        myAlertBuilder.show();
    }

}