// Generated by view binder compiler. Do not edit!
package com.werot.helloworld.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.werot.helloworld.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ContentSecondBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final FrameLayout fragmentContentSecond;

  private ContentSecondBinding(@NonNull ConstraintLayout rootView,
      @NonNull FrameLayout fragmentContentSecond) {
    this.rootView = rootView;
    this.fragmentContentSecond = fragmentContentSecond;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ContentSecondBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ContentSecondBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.content_second, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ContentSecondBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.fragment_content_second;
      FrameLayout fragmentContentSecond = ViewBindings.findChildViewById(rootView, id);
      if (fragmentContentSecond == null) {
        break missingId;
      }

      return new ContentSecondBinding((ConstraintLayout) rootView, fragmentContentSecond);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
