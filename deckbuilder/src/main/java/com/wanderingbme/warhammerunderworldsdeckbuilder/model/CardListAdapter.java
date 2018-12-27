package com.wanderingbme.warhammerunderworldsdeckbuilder.model;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanderingbme.warhammerunderworldsdeckbuilder.R;

import java.util.List;

/**
 * Created by JT on 3/22/2018.
 */

public class CardListAdapter extends ArrayAdapter<Card> {

    private int layout;
    private boolean deletable;
    private List<Card> cards;

    public CardListAdapter(@NonNull Context context, int resource, @NonNull List<Card> objects) {
        this(context, resource, objects, false);
    }

    public CardListAdapter(@NonNull Context context, int resource, @NonNull List<Card> objects, boolean deletable) {
        super(context, resource, objects);
        layout = resource;
        this.deletable = deletable;
        this.cards = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        ViewHolder mainViewHolder;
        View.OnClickListener viewListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(parent.getContext());
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                ImageView image = new ImageView(parent.getContext());
                String fileToUse = "c" + getItem(position).getId();
                int imageResource = parent.getResources().getIdentifier(fileToUse, "drawable", getContext().getPackageName());
                image.setImageResource(imageResource);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(image);
                dialog.show();
            }
        };
        View.OnClickListener removeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cards.remove(position);
                notifyDataSetChanged();
            }
        };
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.card_name);
            viewHolder.view = convertView.findViewById(R.id.view_card);
            convertView.setTag(viewHolder);
            viewHolder.title.setText(getItem(position).getTitle());
            viewHolder.view.setOnClickListener(viewListener);
            if (deletable) {
                LinearLayout row = convertView.findViewById(R.id.row_layout);
                ImageView remove = new ImageView(parent.getContext());
                remove.setImageResource(R.drawable.remove);
                remove.setOnClickListener(removeListener);
                viewHolder.removeView = remove;
                row.addView(remove);
            }
        } else {
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.title.setText(getItem(position).getTitle());
            mainViewHolder.view.setOnClickListener(viewListener);
            if (deletable) {
                mainViewHolder.removeView.setOnClickListener(removeListener);
            }
        }

        return convertView;
    }

    private class ViewHolder {
        TextView title;
        ImageView view;
        ImageView removeView;
    }
}

