package com.rosenel.civiladvocacy;

import static android.content.ContentValues.TAG;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;

public class official implements Serializable {
    private int id;
    private String officeName;
    private String name;
    private String phone;
    private String address;
    private String party;
    private String email;
    private String photoURL;
    private String url;
    private String normalizedAddress;

    public String getNormalizedAddress() {
        return normalizedAddress;
    }
    public String getYoutubeID() {
        return youtubeID;
    }
    public String getTwitterID() {
        return twitterID;
    }
    public String getFacebookID() {
        return facebookID;
    }



    //private HashMap<String, String> channels;
    private String facebookID;
    private String youtubeID;
    private String twitterID;


    public static official ToOfficial(JSONObject officialJSON) throws JSONException, ParseException {
        Log.d(TAG, "ToOfficial: " + officialJSON);
        return null;
    }

    public int getId() {
        return id;
    }

    public String getOfficeName() {
        return officeName;
    }


    public String getParty() {
        return party;
    }

    public String getPhone() {
        return phone;
    }

    public String getUrl() {
        return url;
    }

    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }



    public String getPhotoURL() {
        return photoURL;
    }

    public official(int id, String officeName, String name, String address, String party, String phone, String url,
                    String email, String photoURL, String facebookID, String youtubeID, String twitterID, String normalizedAddress) {
        this.id = id;
        this.officeName = officeName;
        this.name = name;
        this.address = address;
        this.party = party;
        this.phone = phone;
        this.url = url;
        this.email = email;
        this.photoURL = photoURL;
        this.facebookID = facebookID;
        this.youtubeID = youtubeID;
        this.twitterID = twitterID;
        this.normalizedAddress = normalizedAddress;
    }
}
