// Generated by view binder compiler. Do not edit!
package com.example.a28_roomdatabase.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.a28_roomdatabase.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAddPricesBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button addPriceBtnSave;

  @NonNull
  public final EditText addPriceEtBirthday;

  @NonNull
  public final EditText addPriceEtPrice;

  @NonNull
  public final Spinner addPriceSpinner;

  private ActivityAddPricesBinding(@NonNull LinearLayout rootView, @NonNull Button addPriceBtnSave,
      @NonNull EditText addPriceEtBirthday, @NonNull EditText addPriceEtPrice,
      @NonNull Spinner addPriceSpinner) {
    this.rootView = rootView;
    this.addPriceBtnSave = addPriceBtnSave;
    this.addPriceEtBirthday = addPriceEtBirthday;
    this.addPriceEtPrice = addPriceEtPrice;
    this.addPriceSpinner = addPriceSpinner;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAddPricesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAddPricesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_add_prices, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAddPricesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_price_btn_save;
      Button addPriceBtnSave = ViewBindings.findChildViewById(rootView, id);
      if (addPriceBtnSave == null) {
        break missingId;
      }

      id = R.id.add_price_et_birthday;
      EditText addPriceEtBirthday = ViewBindings.findChildViewById(rootView, id);
      if (addPriceEtBirthday == null) {
        break missingId;
      }

      id = R.id.add_price_et_price;
      EditText addPriceEtPrice = ViewBindings.findChildViewById(rootView, id);
      if (addPriceEtPrice == null) {
        break missingId;
      }

      id = R.id.add_price_spinner;
      Spinner addPriceSpinner = ViewBindings.findChildViewById(rootView, id);
      if (addPriceSpinner == null) {
        break missingId;
      }

      return new ActivityAddPricesBinding((LinearLayout) rootView, addPriceBtnSave,
          addPriceEtBirthday, addPriceEtPrice, addPriceSpinner);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
