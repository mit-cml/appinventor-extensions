// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2018 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package edu.mit.appinventor.ai.personalaudioclassifier;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager.LayoutParams;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesAssets;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.PermissionResultHandler;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.WebViewer;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.YailList;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Component that classifies audio clips using a user trained model from the spectrogram audio classifier.
 * Based heavily on PIC by Danny Tang, and the Look Extension by kevinzhu@mit.edu (Kevin Zhu) and kelseyc@mit.edu (Kelsey Chan)
 *
 * @author nwbhatia@mit.edu (Nikhil Bhatia)
 */

@DesignerComponent(version = 20200904,
    category = ComponentCategory.EXTENSION,
    description = "Component that classifies audio clips using a user trained model from the personal audio classifier",
    iconName = "images/extension.png",
    nonVisible = true)
@SimpleObject(external = true)
@UsesAssets(fileNames = "tfjs-1.5.2.js, recorder1.js, chroma.js, spectrogram.js, personal_audio_classifier.html, personal_audio_classifier1.js, mobilenet_group1-shard1of1, mobilenet_group10-shard1of1, mobilenet_group11-shard1of1, mobilenet_group12-shard1of1, mobilenet_group13-shard1of1, mobilenet_group14-shard1of1, mobilenet_group15-shard1of1, mobilenet_group16-shard1of1, mobilenet_group17-shard1of1, mobilenet_group18-shard1of1, mobilenet_group19-shard1of1, mobilenet_group2-shard1of1, mobilenet_group20-shard1of1, mobilenet_group21-shard1of1, mobilenet_group22-shard1of1, mobilenet_group23-shard1of1, mobilenet_group24-shard1of1, mobilenet_group25-shard1of1, mobilenet_group26-shard1of1, mobilenet_group27-shard1of1, mobilenet_group28-shard1of1, mobilenet_group29-shard1of1, mobilenet_group3-shard1of1, mobilenet_group30-shard1of1, mobilenet_group31-shard1of1, mobilenet_group32-shard1of1, mobilenet_group33-shard1of1, mobilenet_group34-shard1of1, mobilenet_group35-shard1of1, mobilenet_group36-shard1of1, mobilenet_group37-shard1of1, mobilenet_group38-shard1of1, mobilenet_group39-shard1of1, mobilenet_group4-shard1of1, mobilenet_group40-shard1of1, mobilenet_group41-shard1of1, mobilenet_group42-shard1of1, mobilenet_group43-shard1of1, mobilenet_group44-shard1of1, mobilenet_group45-shard1of1, mobilenet_group46-shard1of1, mobilenet_group47-shard1of1, mobilenet_group48-shard1of1, mobilenet_group49-shard1of1, mobilenet_group5-shard1of1, mobilenet_group50-shard1of1, mobilenet_group51-shard1of1, mobilenet_group52-shard1of1, mobilenet_group53-shard1of1, mobilenet_group54-shard1of1, mobilenet_group55-shard1of1, mobilenet_group6-shard1of1, mobilenet_group7-shard1of1, mobilenet_group8-shard1of1, mobilenet_group9-shard1of1, mobilenet_model.json")
@UsesPermissions(permissionNames = "android.permission.INTERNET, android.permission.CAMERA, android.permission.RECORD_AUDIO, android.permission.MODIFY_AUDIO_SETTINGS")
public final class PersonalAudioClassifier extends AndroidNonvisibleComponent implements Component {
  private static final String LOG_TAG = PersonalAudioClassifier.class.getSimpleName();
  private static final int IMAGE_WIDTH = 500;
  private static final int IMAGE_QUALITY = 100;
  private static final String ERROR_WEBVIEWER_NOT_SET =
      "You must specify a WebViewer using the WebViewer designer property before you can call %1s";
  private static final String MODEL_PATH_SUFFIX = ".mdl";

  private static final String TRANSFER_MODEL_PREFIX = "https://appinventor.mit.edu/personal-audio-classifier/transfer/";
  private static final String PERSONAL_MODEL_PREFIX = "https://appinventor.mit.edu/personal-audio-classifier/personal/";

  // other error codes are defined in personal_image_classifier.js
  private static final int ERROR_CLASSIFICATION_NOT_SUPPORTED = -1;
  private static final int ERROR_CLASSIFICATION_FAILED = -2;
  private static final int ERROR_WEBVIEWER_REQUIRED = -7;
  private static final int ERROR_INVALID_MODEL_FILE = -8;
  private static final int ERROR_MODEL_REQUIRED = -9;

  private WebView webview = null;

  private String modelPath = null;

  public PersonalAudioClassifier(final Form form) {
    super(form);
    requestHardwareAcceleration(form);
    WebView.setWebContentsDebuggingEnabled(true);
    Log.d(LOG_TAG, "Created PersonalAudioClassifier component");
  }

  @SuppressLint("SetJavaScriptEnabled")
  private void configureWebView(WebView webview) {
    this.webview = webview;
    webview.getSettings().setJavaScriptEnabled(true);
    webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
    // adds a way to send strings to the javascript
    webview.addJavascriptInterface(new JsObject(), "PersonalAudioClassifier");
    webview.setWebViewClient(new WebViewClient() {
      @Override
      public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        Log.d(LOG_TAG, "shouldInterceptRequest called");
        InputStream file = null;
        String charSet;
        String contentType;

        if (url.endsWith(".json")) {
          contentType = "application/json";
          charSet = "UTF-8";
        } else {
          contentType = "application/octet-stream";
          charSet = "binary";
        }

        try {
          int size = 0;
          if (url.contains(TRANSFER_MODEL_PREFIX)) {
            Log.d(LOG_TAG, "overriding " + url);

            file = form.openAssetForExtension(PersonalAudioClassifier.this, url.substring(TRANSFER_MODEL_PREFIX.length()));
          } else if (url.contains(PERSONAL_MODEL_PREFIX)) {
            Log.d(LOG_TAG, "overriding " + url);

            String fileName = url.substring(PERSONAL_MODEL_PREFIX.length());
            ZipInputStream zipInputStream = new ZipInputStream(MediaUtil.openMedia(form, modelPath));
            ZipEntry zipEntry = null;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
              if (zipEntry.getName().equals(fileName)) {
                int zipEntrySize = (int) zipEntry.getSize();
                byte[] fileBytes = new byte[zipEntrySize];

                Log.d(LOG_TAG, "(length) zipEntrySize: " + zipEntrySize);

                int start = 0;
                int read = 0;
                while ((read = zipInputStream.read(fileBytes, start, zipEntrySize)) > 0) {
                  Log.d(LOG_TAG, "(in loop) start: " + start);
                  Log.d(LOG_TAG, "(in loop) read: " + read);
                  start += read;
                  zipEntrySize -= read;
                }
                Log.d(LOG_TAG, "(end) start: " + start);
                Log.d(LOG_TAG, "(end) read: " + read);

                file = new ByteArrayInputStream(fileBytes);
                size = fileBytes.length;
                break;
              }
            }

            zipInputStream.close();
          }

          if (file != null) {
            if (SdkLevel.getLevel() >= SdkLevel.LEVEL_LOLLIPOP) {
              Map<String, String> responseHeaders = new HashMap<String, String>();
              responseHeaders.put("Access-Control-Allow-Origin", "*");
              responseHeaders.put("Content-Length", "" + size);
              return new WebResourceResponse(contentType, charSet, 200, "OK", responseHeaders, file);
            } else {
              return new WebResourceResponse(contentType, charSet, file);
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
          return super.shouldInterceptRequest(view, url);
        }

        Log.d(LOG_TAG, url);
        return super.shouldInterceptRequest(view, url);
      }
    });
    webview.setWebChromeClient(new WebChromeClient() {
      @Override
      public void onPermissionRequest(PermissionRequest request) {
        Log.d(LOG_TAG, "onPermissionRequest called");

        String[] requestedResources = request.getResources();
        for (String r : requestedResources) {
          Log.d(LOG_TAG, r);
          Log.d(LOG_TAG, PermissionRequest.RESOURCE_AUDIO_CAPTURE);
          Log.d(LOG_TAG, r.equals(PermissionRequest.RESOURCE_AUDIO_CAPTURE) ? "equal" : "not equal");
          if (r.equals(PermissionRequest.RESOURCE_AUDIO_CAPTURE)) {
            request.grant(new String[]{PermissionRequest.RESOURCE_AUDIO_CAPTURE});
            Log.d(LOG_TAG, "Permission granted");
          }
        }
      }

      @Override
      public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        Log.d(LOG_TAG, "(JS) " + message + " -- From line "
            + lineNumber + " of "
            + sourceID);
      }
    });
    WebView.setWebContentsDebuggingEnabled(true);
  }

  public void Initialize() {
    Log.d(LOG_TAG, "webview = " + webview);
    if (webview == null) {
      form.dispatchErrorOccurredEvent(this, "WebViewer",
          ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_WEBVIEWER_REQUIRED, LOG_TAG,
          "You must specify a WebViewer component in the WebViewer property.");
    }

    Log.d(LOG_TAG, "modelPath = " + modelPath);
    if (modelPath == null) {
      form.dispatchErrorOccurredEvent(this, "Model",
          ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_MODEL_REQUIRED, LOG_TAG,
          "You must provide a model file in the Model property");
    }
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT + ":com.google.appinventor.runtime.components.WebViewer")
  @SimpleProperty(userVisible = false)
  public void WebViewer(final WebViewer webviewer) {
    Runnable next = new Runnable() {
      public void run() {
        if (webviewer != null) {
          configureWebView((WebView) webviewer.getView());
          webview.requestLayout();
          try {
            Log.d(LOG_TAG, "isHardwareAccelerated? " + webview.isHardwareAccelerated());
            webview.loadUrl(form.getAssetPathForExtension(PersonalAudioClassifier.this, "personal_audio_classifier.html"));
          } catch (FileNotFoundException e) {
            Log.d(LOG_TAG, e.getMessage());
            e.printStackTrace();
          }
        }
      }
    };
    if (shouldAskForPermission(form)) {
      Log.d(LOG_TAG, "Asking for permissions...");
      askForPermission(this, next);
    } else {
      next.run();
    }
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET,
      defaultValue = "")
  @SimpleProperty(userVisible = false)
  public void Model(String path) {
    Log.d(LOG_TAG, "Personal model path: " + path);

    if (path.endsWith(MODEL_PATH_SUFFIX)) {
      modelPath = path;
    } else {
      form.dispatchErrorOccurredEvent(this, "Model",
          ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_INVALID_MODEL_FILE, LOG_TAG,
          "Invalid model file format: files must be of format " + MODEL_PATH_SUFFIX);
    }
  }

  static boolean shouldAskForPermission(Form form) {
    return form.getApplicationInfo().targetSdkVersion >= 23 &&
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
  }

  void askForPermission(final PersonalAudioClassifier personalAudioClassifier, final Runnable next) {
    form.askPermission(Manifest.permission.RECORD_AUDIO, new PermissionResultHandler() {
      @Override
      public void HandlePermissionResponse(String permission, boolean granted) {
        if (granted) {
          form.askPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS, new PermissionResultHandler() {
            @Override
            public void HandlePermissionResponse(String permission1, boolean granted1) {
              if (granted1 || form instanceof ReplForm) {
                Log.d(LOG_TAG, "Record audio + modify audio both granted...");
                next.run();
              } else {
                personalAudioClassifier.getForm().PermissionDenied(personalAudioClassifier, "WebViewer", permission1);
              }
            }
          });
        } else {
          personalAudioClassifier.getForm().PermissionDenied(personalAudioClassifier, "WebViewer", permission);
        }
      }
    });
  }

  private static String encodeFileToBase64(File file) {
    String base64File = "";
    try (FileInputStream imageInFile = new FileInputStream(file)) {
      // Reading a file from file system
      byte fileData[] = new byte[(int) file.length()];
      imageInFile.read(fileData);
      base64File = Base64.encodeToString(fileData, 0).replace("\n", "");
    } catch (FileNotFoundException e) {
      System.out.println("File not found" + e);
    } catch (IOException ioe) {
      System.out.println("Exception while reading the file " + ioe);
    }
    return base64File;
  }

  @SimpleFunction(description = "Performs classification on the image at the given path and triggers the GotClassification event when classification is finished successfully.")
  public void ClassifySoundData(final String sound) {
    assertWebView("ClassifySoundData");
    Log.d(LOG_TAG, "Entered Classify Sound Data");

    String soundPath = (sound == null) ? "" : sound;
    Log.d(LOG_TAG, "soundPath: " + soundPath);

    File soundFile = new File(soundPath);
    Log.d(LOG_TAG, "soundFile: " + soundFile);

    String encodedSound = encodeFileToBase64(soundFile);
    Log.d(LOG_TAG, "encodedSound: " + encodedSound);

    /* ENCODE AND SEND AUDIO CLIP (JAVA):
     * 1. get sound object from sound path √
     * 2. convert sound object to some sort of encoded string √
     * 3. pass encoded string to getSpectrogram endpoint √
     *
     * DECODE AUDIO CLIP AND CONVERT TO SPECTROGRAM (JAVASCRIPT)
     * 1. convert encoded string to javascript audio blob √
     * 2. pass audio blob through spectrogram.js √
     * 3. convert spectrogram.js output to encoded string √
     * 4. pass encoded string back to Java via reportSpectrogram endpoint √
     *
     * CLASSIFY SPECTROGRAM (JAVA):
     * 1. convert encoded string to image
     * 2. classify image (refer to commented PIC code)
     */

    webview.evaluateJavascript("getSpectrogram(\"" + encodedSound + "\");", null);

    Log.d(LOG_TAG, "encodedSound sent to Javascript!");
  }

  @SimpleEvent(description = "Event indicating that the classifier is ready.")
  public void ClassifierReady() {
    EventDispatcher.dispatchEvent(this, "ClassifierReady");
  }

  @SimpleEvent(description = "Event indicating that classification has finished successfully. Result is of the form [[class1, confidence1], [class2, confidence2], ..., [class10, confidence10]].")
  public void GotClassification(YailList result) {
    Log.d(LOG_TAG, "GOT CLASSIFICATION: " + result);
    EventDispatcher.dispatchEvent(this, "GotClassification", result);
  }

  @SimpleEvent(description = "Event indicating that an error has occurred.")
  public void Error(final int errorCode, final String errorMessage) {
    EventDispatcher.dispatchEvent(this, "Error", errorCode, errorMessage);
  }

  Form getForm() {
    return form;
  }

  private static void requestHardwareAcceleration(Activity activity) {
    activity.getWindow().setFlags(LayoutParams.FLAG_HARDWARE_ACCELERATED, LayoutParams.FLAG_HARDWARE_ACCELERATED);
  }

  private void assertWebView(String method) {
    if (webview == null) {
      throw new RuntimeException(String.format(ERROR_WEBVIEWER_NOT_SET, method));
    }
  }

  private class JsObject {
    @JavascriptInterface
    public void ready() {
      Log.d(LOG_TAG, "Entered ready");
      form.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          ClassifierReady();
        }
      });
    }

    @JavascriptInterface
    public void reportImage(final String dataURL) {
      Log.d(LOG_TAG, "Entered reportImage: " + dataURL);
      //how do I get this image in ClassifySoundData
    }

    @JavascriptInterface
    public void reportResult(final String result) {
      Log.d(LOG_TAG, "Entered reportResult: " + result);
      try {
        Log.d(LOG_TAG, "Entered try of reportResult");
        JSONArray list = new JSONArray(result);
        YailList intermediateList = YailList.makeList(JsonUtil.getListFromJsonArray(list));
        final List resultList = new ArrayList();
        for (int i = 0; i < intermediateList.size(); i++) {
          resultList.add(YailList.makeList((List) intermediateList.getObject(i)));
        }
        form.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            GotClassification(YailList.makeList(resultList));
          }
        });
      } catch (JSONException e) {
        Log.d(LOG_TAG, "Entered catch of reportResult");
        e.printStackTrace();
        Error(ERROR_CLASSIFICATION_FAILED, e.toString());
      }
    }

    @JavascriptInterface
    public void error(final int errorCode, final String errorMessage) {
      Log.d(LOG_TAG, "Entered error: " + errorCode);
      form.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          Error(errorCode, errorMessage);
        }
      });
    }
  }
}
