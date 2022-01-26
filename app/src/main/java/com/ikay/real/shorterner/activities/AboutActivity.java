package com.ikay.real.shorterner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ikay.real.shorterner.R;

public class AboutActivity extends AppCompatActivity {
    TextView CompanyEmailTextView;
    ImageView imageViewInstagram,imageViewWhatsapp,imageViewFcaebook,imageViewLinkedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        imageViewInstagram =findViewById(R.id.buttonInstagram);
        CompanyEmailTextView =findViewById(R.id.textViewCompanyEmail);
        imageViewWhatsapp =findViewById(R.id.buttonWhatsapp);
        imageViewFcaebook =findViewById(R.id.buttonFacebook);
        imageViewLinkedIn = findViewById(R.id.buttonLinkedIn);
        CompanyEmailTextView.setMovementMethod(LinkMovementMethod.getInstance());
        CompanyEmailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOpenEmailContactIntent(AboutActivity.this);
            }
        });
        imageViewLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getOpenLinkedinContactIntent(AboutActivity.this));
            }
        });
        imageViewFcaebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getOpenFacebookContantIntent(AboutActivity.this));
            }
        });
        imageViewInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getOpenInstagramContantIntent(AboutActivity.this));
            }
        });
        imageViewWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatApp();
            }
        });
    }
    public Intent getOpenInstagramContantIntent(Context context) {

        try {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/ikay_real/"));

        } catch (Exception e) {
            return new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/ikay_real/")
            );
        }

    }
    private void openWhatApp(){
        checkWhatsAppType();
    }
    public Intent getOpenFacebookContantIntent(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/107449548375313"));

        } catch (Exception e) {
            return new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/<ikaydevelopments>")
            );
        }
    }
    public Intent getOpenLinkedinContactIntent(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.linkedin.android", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("linkedin://profile/www.linkedin.com/in/innocent-kumwenda99"));

        } catch (Exception e) {
            return new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("linkedin://profile/www.linkedin.com/in/innocent-kumwenda99")
            );
        }
    }

    public void getOpenWhatsappContantIntent(Context context,String wp) {

        String contact = "+265 880532508"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;

        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo(wp, PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(AboutActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
    public void getOpenEmailContactIntent(Context context) {
        try{
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            //intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"ikaydevelopment@gmail.com"});
            startActivity(Intent.createChooser(intent,"Send email"));
        }catch(Exception e){
            Toast.makeText(AboutActivity.this,"Error",Toast.LENGTH_SHORT).show();
        }
    }
    private void checkWhatsAppType(){
        if(packageExists("com.whatsapp")) {
            Toast.makeText(AboutActivity.this,"whatsapp is there",Toast.LENGTH_SHORT).show();
            getOpenWhatsappContantIntent(AboutActivity.this,"com.whatsapp");
        }if(packageExists("com.whatsapp.w4b")) {
            Toast.makeText(AboutActivity.this,"whatsapp business is there",Toast.LENGTH_SHORT).show();
            getOpenWhatsappContantIntent(AboutActivity.this,"com.whatsapp.w4b");

        }
    }
    public boolean packageExists(String targetPackage){
        PackageManager pm=getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }
}