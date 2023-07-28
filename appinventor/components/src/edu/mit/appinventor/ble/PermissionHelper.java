// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2023 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package edu.mit.appinventor.ble;

import android.os.Build;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.util.BulkPermissionRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class PermissionHelper {
  private final Form form;
  private final Component source;

  public PermissionHelper(Form form, Component source) {
    this.form = form;
    this.source = source;
  }

  public boolean askForPermission(String permission, String caller, Runnable continuation) {
    return PermissionHelper.askForPermission(permission, form, source, caller, continuation);
  }

  public boolean askForPermission(String[] permissions, String caller, Runnable continuation) {
    return PermissionHelper.askForPermission(permissions, form, source, caller, continuation);
  }

  public static boolean askForPermission(String permission, Form form, Component component,
      String caller, Runnable continuation) {
    return askForPermission(new String[] { permission }, form, component, caller, continuation);
  }

  public static boolean askForPermission(String[] sdk31permissions, Form form, Component component,
      String caller, final Runnable continuation) {
    List<String> permissions = new ArrayList<>();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      Collections.addAll(permissions, sdk31permissions);
    } else {
      permissions.add(ACCESS_FINE_LOCATION);
    }
    boolean ready = true;
    for (String permission : permissions) {
      if (form.isDeniedPermission(permission)) {
        ready = false;
        break;
      }
    }
    if (!ready) {
      form.askPermission(new BulkPermissionRequest(component, caller, permissions.toArray(new String[0])) {
        @Override
        public void onGranted() {
          continuation.run();
        }
      });
    } else {
      continuation.run();
    }
    return !ready;
  }
}
