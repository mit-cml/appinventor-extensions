// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2019-2020 MIT, All rights reserved.
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package edu.mit.appinventor.companionextras;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.IOUtils;
import com.google.appinventor.components.runtime.util.NougatUtil;
import com.google.appinventor.components.runtime.util.QUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@UsesPermissions(permissionNames = Manifest.permission.REQUEST_INSTALL_PACKAGES)
@DesignerComponent(version = 1,
    category = ComponentCategory.EXTENSION,
    description = "An extension to provide additional functionality to non-Play Store Companions",
    nonVisible = true)
@SimpleObject(external = true)
public class CompanionExtras extends AndroidNonvisibleComponent {
  private static final String LOG_TAG = CompanionExtras.class.getSimpleName();

  public CompanionExtras(Form form) {
    super(form);
  }

  /**
   * Extra1 -- Extra function to download and install an APK file.
   *
   * @param urlToApk url pointing to the APK to download and install
   */

  @SimpleFunction
  public void Extra1(final String urlToApk) {
    AsynchUtil.runAsynchronously(new Runnable() {
      @Override
      public void run() {
        Uri packageuri = null;
        InputStream instream = null;
        FileOutputStream apkOut = null;
        File apkfile = new File(QUtil.getReplAssetPath(form), "package.apk");
        try {
          URL url = new URL(urlToApk);
          URLConnection conn = url.openConnection();
          instream = new BufferedInputStream(conn.getInputStream());
          apkOut = new FileOutputStream(apkfile);
          byte[] buffer = new byte[32768];
          int len;
          while ((len = instream.read(buffer, 0, 32768)) > 0) {
            apkOut.write(buffer, 0, len);
          }
        } catch (Exception e) {
          Log.e(LOG_TAG, "ERROR_UNABLE_TO_GET", e);
          form.dispatchErrorOccurredEvent(form, "CompanionExtras",
              ErrorMessages.ERROR_WEB_UNABLE_TO_GET, urlToApk);
          return;
        } finally {
          IOUtils.closeQuietly(LOG_TAG, instream);
          IOUtils.closeQuietly(LOG_TAG, apkOut);
        }
        try {
          // Call Package Manager Here
          Log.d(LOG_TAG, "About to Install package from " + urlToApk);
          Intent intent = new Intent(Intent.ACTION_VIEW);
          packageuri = NougatUtil.getPackageUri(form, apkfile);
          intent.setDataAndType(packageuri, "application/vnd.android.package-archive");
          intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
          form.startActivity(intent);
        } catch (ActivityNotFoundException e) {
          Log.e(LOG_TAG, "Unable to install package", e);
          form.dispatchErrorOccurredEvent(form, "CompanionExtras",
              ErrorMessages.ERROR_UNABLE_TO_INSTALL_PACKAGE, packageuri);
        }
      }
    });
  }
}
