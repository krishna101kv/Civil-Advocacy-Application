package com.rosenel.civiladvocacy;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class individualActivity extends AppCompatActivity {


    TextView paaddresstv;
    ImageView officialIV;
    TextView officialTVP;
    TextView officeTVP;

    TextView partyTVP;
    ImageView facebookButton;
    ImageView youtubeButton;
    ImageView twitterButton;
    View identity;

    ImageView addressLabelTV;
    TextView addressLinkTV;

    ImageView phoneLabelTV;
    TextView phoneLinkTV;

    ImageView emailLabelTV;
    TextView emailLinkTV;

    ImageView webLabelTV;
    TextView webLinkTV;

    ImageView partyLogoIV;

    private com.rosenel.civiladvocacy.official official;
    private String partyURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        paaddresstv = findViewById(R.id.PAAddressTV);
        officialIV = findViewById(R.id.officialIV);
        officialTVP = findViewById(R.id.officialTVP);
        officeTVP = findViewById(R.id.officeTVP);
        partyTVP = findViewById(R.id.partyTVP);

        facebookButton = findViewById(R.id.facebookBtnV);
        youtubeButton = findViewById(R.id.youtubeBtnV);
        twitterButton = findViewById(R.id.twitterBtnV);

        addressLabelTV = findViewById(R.id.addressLabelTV);
        addressLinkTV = findViewById(R.id.addressLinkTV);

        phoneLabelTV = findViewById(R.id.phoneLabelTV);
        phoneLinkTV = findViewById(R.id.phoneLinkTV);

        emailLabelTV = findViewById(R.id.emailLabelTV);
        emailLinkTV = findViewById(R.id.emailLinkTV);

        webLabelTV = findViewById(R.id.webLabelTV);
        webLinkTV = findViewById(R.id.webLinkTV);

        partyLogoIV = findViewById(R.id.partyLogoIV);

        if (getIntent().hasExtra("OFFICIAL")) {
            official = (com.rosenel.civiladvocacy.official) getIntent().getSerializableExtra("OFFICIAL");
            populateAPI();
        }

    }



    private void errorAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg);
        builder.setTitle("No App Found");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setOfficialIV(com.rosenel.civiladvocacy.official official) {

        if (official.getPhotoURL() != null) {
            Glide.with(this)
                    .load(official.getPhotoURL())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            return false;
                        }
                    })
                    .error(R.drawable.brokenimage)
                    .into(officialIV);
        } else {
            officialIV.setImageResource(R.drawable.missing);
        }

    }
    public void click_Photo(View v) {
        if (official.getPhotoURL() == null) {
            return;
        }
        Intent intent = new Intent(this, pictureActivity.class);
        intent.putExtra("OFFICIAL", official);
        startActivity(intent);
    }



    public void click_Facebook(View v) {
        String user = official.getFacebookID();
        String FACEBOOK_URL = "https://www.facebook.com/" + user;

        Intent intent;

        if (checkInstalledPackage("com.facebook.katana")) {
            String urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToUse));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL));
        }

        // Check if there is an app that can handle fb or https intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            errorAlert("No Application found that handles ACTION_VIEW (fb/https) intents");
        }

    }



    public void click_Twitter(View v) {
        String user = official.getTwitterID();
        String twitterAppUrl = "twitter://user?screen_name=" + user;
        String twitterWebUrl = "https://twitter.com/" + user;

        Intent intent;
        if (checkInstalledPackage("com.twitter.android")) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterAppUrl));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterWebUrl));
        }

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            errorAlert("No Application found that handles ACTION_VIEW (twitter/https) intents");
        }
    }

    public boolean checkInstalledPackage(String packageName) {
        try {
            return getPackageManager().getApplicationInfo(packageName, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void click_Youtube(View v) {
        String name = official.getYoutubeID();
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + name)));
        }
    }


    public void click_Map(View v) {
        String address = official.getAddress();

        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));

        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);

        // Check if there is an app that can handle geo intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            errorAlert("No Application found that handles ACTION_VIEW (geo) intents");
        }
    }

    public void click_Logo(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(partyURL));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            errorAlert("No Application found that handles ACTION_VIEW (https) intents");
        }
    }


    private void populateAPI() {
        officialTVP.setText(official.getName());
        officeTVP.setText(official.getOfficeName());
        partyTVP.setText(String.format("(%s)", official.getParty()));
        identity = findViewById(R.id.personCL);
        if (official.getParty().contains("Demo")) {
            identity.setBackgroundColor(getColor(R.color.democract_color));
            partyLogoIV.setImageResource(R.drawable.dem_logo);
            partyURL = "https://democrats.org";
            addressLinkTV.setTextColor(Color.parseColor("#00EFEF"));
            //set logo
        }
        if (official.getParty().contains("Repub")) {
            identity.setBackgroundColor(getColor(R.color.republic_color));
            partyLogoIV.setImageResource(R.drawable.rep_logo);
            partyURL = "https://www.gop.com";
            addressLinkTV.setTextColor(Color.parseColor("#00EFEF"));
            //set logo
        }
        if (official.getParty().contains("Non")) {
            identity.setBackgroundColor(Color.parseColor("#121212"));
            //change text color
        }
        paaddresstv.setText(official.getNormalizedAddress());
        setOfficialIV(official);

        if (official.getTwitterID() != null) {
            Log.d(TAG, "populateAPI: TWITTER " + official.getTwitterID());
            twitterButton.setImageResource(R.drawable.twitter);
        } else {
            twitterButton.setVisibility(View.GONE);
        }

        if (official.getFacebookID() != null) {
            //Toast.makeText(this, "Facebook", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "populateAPI: FACEBOOK " + official.getFacebookID());
            facebookButton.setImageResource(R.drawable.facebook);
        } else {
            facebookButton.setVisibility(View.GONE);
        }

        if (official.getYoutubeID() != null) {
            //Toast.makeText(this, "Facebook", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "populateAPI: YOUTUBE " + official.getYoutubeID());
            youtubeButton.setImageResource(R.drawable.youtube);
        } else {
            youtubeButton.setVisibility(View.GONE);
        }

        if (official.getAddress() != null) {
            String mystring = official.getAddress();
            SpannableString content = new SpannableString(mystring);
            content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
            addressLinkTV.setText(content);
            //addressLinkTV.setText(official.getAddress());
            Linkify.addLinks(addressLinkTV, Linkify.ALL);
        } else {
            addressLabelTV.setVisibility(View.GONE);
            addressLinkTV.setVisibility(View.GONE);
        }

        if (official.getPhone() != null) {
            phoneLinkTV.setText(official.getPhone());
        } else {
            phoneLabelTV.setVisibility(View.GONE);
            phoneLinkTV.setVisibility(View.GONE);
        }

        if (official.getEmail() != null) {
            emailLinkTV.setText(official.getEmail());
        } else {
            emailLabelTV.setVisibility(View.GONE);
            emailLinkTV.setVisibility(View.GONE);
        }

        if (official.getUrl() != null) {
            webLinkTV.setText(official.getUrl());
        } else {
            webLabelTV.setVisibility(View.GONE);
            webLabelTV.setVisibility(View.GONE);
        }

    }




}