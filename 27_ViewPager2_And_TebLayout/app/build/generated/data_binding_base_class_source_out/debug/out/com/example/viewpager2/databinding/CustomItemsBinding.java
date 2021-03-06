// Generated by view binder compiler. Do not edit!
package com.example.viewpager2.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.viewpager2.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CustomItemsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView customItemsImg;

  @NonNull
  public final TextView customItemsTvName;

  @NonNull
  public final TextView customItemsTvPrice;

  private CustomItemsBinding(@NonNull LinearLayout rootView, @NonNull ImageView customItemsImg,
      @NonNull TextView customItemsTvName, @NonNull TextView customItemsTvPrice) {
    this.rootView = rootView;
    this.customItemsImg = customItemsImg;
    this.customItemsTvName = customItemsTvName;
    this.customItemsTvPrice = customItemsTvPrice;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CustomItemsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CustomItemsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.custom_items, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CustomItemsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.custom_items_img;
      ImageView customItemsImg = ViewBindings.findChildViewById(rootView, id);
      if (customItemsImg == null) {
        break missingId;
      }

      id = R.id.custom_items_tv_name;
      TextView customItemsTvName = ViewBindings.findChildViewById(rootView, id);
      if (customItemsTvName == null) {
        break missingId;
      }

      id = R.id.custom_items_tv_price;
      TextView customItemsTvPrice = ViewBindings.findChildViewById(rootView, id);
      if (customItemsTvPrice == null) {
        break missingId;
      }

      return new CustomItemsBinding((LinearLayout) rootView, customItemsImg, customItemsTvName,
          customItemsTvPrice);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
