package com.example.locusreminder.display;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.locusreminder.R;

public class HelpFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        final TextView mItemDescription = (TextView) view.findViewById(R.id.item_description);
        final ImageView mDescriptionImg=(ImageView) view.findViewById(R.id.item_description_img);
        final LinearLayout layout_one=(LinearLayout)view.findViewById(R.id.item_description_layout);
        final TextView mItemDescription1 = (TextView) view.findViewById(R.id.item_description1);
        final ImageView mDescriptionImg1=(ImageView) view.findViewById(R.id.item_description_img1);
        final LinearLayout layout_one1=(LinearLayout)view.findViewById(R.id.item_description_layout1);
        final TextView mItemDescription3 = (TextView) view.findViewById(R.id.item_description3);
        final ImageView mDescriptionImg3=(ImageView) view.findViewById(R.id.item_description_img3);
        final LinearLayout layout_one3=(LinearLayout)view.findViewById(R.id.item_description_layout3);
        final TextView mItemDescription4 = (TextView) view.findViewById(R.id.item_description4);
        final ImageView mDescriptionImg4=(ImageView) view.findViewById(R.id.item_description_img4);
        final LinearLayout layout_one4=(LinearLayout)view.findViewById(R.id.item_description_layout4);
        final TextView mItemDescription5 = (TextView) view.findViewById(R.id.item_description5);
        final ImageView mDescriptionImg5=(ImageView) view.findViewById(R.id.item_description_img5);
        final LinearLayout layout_one5=(LinearLayout)view.findViewById(R.id.item_description_layout5);
        layout_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemDescription.getVisibility() == View.INVISIBLE) {
                    // it's collapsed - expand it
                    mItemDescription.setVisibility(View.VISIBLE);
                    mDescriptionImg.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                } else {
                    // it's expanded - collapse it
                    mItemDescription.setVisibility(View.INVISIBLE);
                    mDescriptionImg.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }

                ObjectAnimator animation = ObjectAnimator.ofInt(mItemDescription, "maxLines", mItemDescription.getMaxLines());
                animation.setDuration(200).start();

            }
        });
        layout_one1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemDescription1.getVisibility() == View.INVISIBLE) {
                    // it's collapsed - expand it
                    mItemDescription1.setVisibility(View.VISIBLE);
                    mDescriptionImg1.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                } else {
                    // it's expanded - collapse it
                    mItemDescription1.setVisibility(View.INVISIBLE);
                    mDescriptionImg1.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }

                ObjectAnimator animation = ObjectAnimator.ofInt(mItemDescription1, "maxLines", mItemDescription1.getMaxLines());
                animation.setDuration(200).start();

            }
        });
        layout_one3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemDescription3.getVisibility() == View.INVISIBLE) {
                    // it's collapsed - expand it
                    mItemDescription3.setVisibility(View.VISIBLE);
                    mDescriptionImg3.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                } else {
                    // it's expanded - collapse it
                    mItemDescription3.setVisibility(View.INVISIBLE);
                    mDescriptionImg3.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }

                ObjectAnimator animation = ObjectAnimator.ofInt(mItemDescription3, "maxLines", mItemDescription3.getMaxLines());
                animation.setDuration(200).start();

            }
        });
        layout_one4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemDescription4.getVisibility() == View.INVISIBLE) {
                    // it's collapsed - expand it
                    mItemDescription4.setVisibility(View.VISIBLE);
                    mDescriptionImg4.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                } else {
                    // it's expanded - collapse it
                    mItemDescription4.setVisibility(View.INVISIBLE);
                    mDescriptionImg4.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }

                ObjectAnimator animation = ObjectAnimator.ofInt(mItemDescription4, "maxLines", mItemDescription4.getMaxLines());
                animation.setDuration(200).start();

            }
        });
        layout_one5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemDescription5.getVisibility() == View.INVISIBLE) {
                    // it's collapsed - expand it
                    mItemDescription5.setVisibility(View.VISIBLE);
                    mDescriptionImg5.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                } else {
                    // it's expanded - collapse it
                    mItemDescription5.setVisibility(View.INVISIBLE);
                    mDescriptionImg5.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }

                ObjectAnimator animation = ObjectAnimator.ofInt(mItemDescription5, "maxLines", mItemDescription5.getMaxLines());
                animation.setDuration(200).start();

            }
        });

        FloatingActionButton floatingActionButton = ((ViewReminders) getActivity()).getFloatingActionButton();
        floatingActionButton.hide();

        return view;
    }
}