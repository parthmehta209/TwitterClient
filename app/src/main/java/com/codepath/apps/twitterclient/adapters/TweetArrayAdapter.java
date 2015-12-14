package com.codepath.apps.twitterclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by parth.mehta on 12/12/15.
 */
public class TweetArrayAdapter  extends ArrayAdapter<Tweet>{

    public static class ViewHolder {
        ImageView ivProfileImage;
        TextView tvUserName;
        TextView tvTweetBody;
        TextView tvTwitterHandle;
        TextView tvTweetTime;
    }

    public TweetArrayAdapter(Context context, List<Tweet> tweets) {
        super(context,0,tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tweet tweet = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
            viewHolder.ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvTweetBody = (TextView)convertView.findViewById(R.id.tvTweetBody);
            viewHolder.tvTwitterHandle = (TextView)convertView.findViewById(R.id.tvTwitterHandle);
            viewHolder.tvTweetTime = (TextView)convertView.findViewById(R.id.tvTweetTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
        viewHolder.tvTweetBody.setText(tweet.getBody());
        viewHolder.tvUserName.setText(tweet.getUser().getName());
        viewHolder.tvTwitterHandle.setText("@" + tweet.getUser().getScreenName());
        viewHolder.tvTweetTime.setText(tweet.getCreatedAt());
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);

        return convertView;
    }
}
