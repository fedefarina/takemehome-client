package com.takemehome.adapter;

/**
 * Created by ruitzei on 12/17/16.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.takemehome.R;
import com.takemehome.model.Contact;
import com.takemehome.widget.RoundedImageView;

/**
 * Contains a Contact List Item
 */
public class ContactViewHolder extends RecyclerView.ViewHolder {
    private RoundedImageView mImage;
    private TextView mLabel;
    private Contact mBoundContact; // Can be null

    public ContactViewHolder(final View itemView) {
        super(itemView);
        mImage = (RoundedImageView) itemView.findViewById(R.id.rounded_iv_profile);
        mLabel = (TextView) itemView.findViewById(R.id.tv_label);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBoundContact != null) {
                    Toast.makeText(
                            itemView.getContext(),
                            "Hi, I'm " + mBoundContact.getName(),
                            Toast.LENGTH_SHORT).show();

                    Log.d("asd", mBoundContact.getNumber());
                }
            }
        });
    }

    public void bind(Contact contact) {
        mBoundContact = contact;
        mLabel.setText(contact.getName());
        Picasso.with(itemView.getContext())
                .load(contact.getImage())
                .placeholder(R.mipmap.ic_user)
                .error(R.mipmap.ic_user)
                .into(mImage);
    }
}
