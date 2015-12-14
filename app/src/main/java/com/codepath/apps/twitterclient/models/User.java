package com.codepath.apps.twitterclient.models;

import org.json.JSONObject;

public class User {

    private long uid;
    private String name;
    private String screenName;
    private String profileImageUrl;

    public long getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

	public static User getUser(JSONObject jsonUser) {
		User user = new User();
		user.name = jsonUser.optString("name");
		user.uid = jsonUser.optLong("id", -1);
		user.screenName = jsonUser.optString("screen_name");
		user.profileImageUrl = jsonUser.optString("profile_image_url");
		if(user.name == null || user.uid == -1) {
			return null;
		}
		return user;
	}

}
