// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2019 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.example.facemesh;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.WindowManager.LayoutParams;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesAssets;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.OnPauseListener;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.OnStopListener;
import com.google.appinventor.components.runtime.PermissionResultHandler;
import com.google.appinventor.components.runtime.WebViewer;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.YailList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@DesignerComponent(version = 1,
    category = ComponentCategory.EXTENSION,
    description = "An extension that embeds a facemesh model.",
    iconName = "aiwebres/icon.png",
    nonVisible = true)
@SimpleObject(external = true)
@UsesAssets(fileNames = "tf-converter.min.js, tf-core.min.js, tf-core.min.js, tf-converter.min.js, facemesh.min.js, model.json, group1-shard1of1.bin, index.html, app.js")
@UsesPermissions({Manifest.permission.CAMERA})
public class FaceExtension extends AndroidNonvisibleComponent
    implements OnResumeListener, OnPauseListener, OnStopListener, Deleteable {
  private static final String LOG_TAG = FaceExtension.class.getSimpleName();
  private static final String ERROR_WEBVIEWER_NOT_SET =
      "You must specify a WebViewer using the WebViewer designer property before you can call %1s";
  private static final int ERROR_JSON_PARSE_FAILED = 101;
  private static final String MODEL_URL =
      "https://tfhub.dev/mediapipe/tfjs-model/facemesh/1/default/1/";
  private static final String BACK_CAMERA = "Back";
  private static final String FRONT_CAMERA = "Front";

  private WebView webview = null;
  private final Map<String, YailList> keyPoints = new ConcurrentHashMap<>();
  private String cameraMode = FRONT_CAMERA;
  private boolean initialized = false;
  private boolean enabled = true;
  private boolean showMesh = false;
  private String BackgroundImage = "";
  private int width = 350;
  private int height = 200; 

  /**
   * Creates a new FaceExtension extension.
   *
   * @param form the container that this component will be placed in
   */
  public FaceExtension(Form form) {
    super(form);
    requestHardwareAcceleration(form);
    WebView.setWebContentsDebuggingEnabled(true);
    keyPoints.put("forehead", YailList.makeEmptyList());
    keyPoints.put("leftCheek", YailList.makeEmptyList());
    keyPoints.put("rightCheek", YailList.makeEmptyList());
    keyPoints.put("leftEyebrow", YailList.makeEmptyList());
    keyPoints.put("rightEyebrow", YailList.makeEmptyList());
    keyPoints.put("chin", YailList.makeEmptyList());
    keyPoints.put("leftEyeInnerCorner", YailList.makeEmptyList());
    keyPoints.put("rightEyeInnerCorner", YailList.makeEmptyList());
    keyPoints.put("mouthTop", YailList.makeEmptyList());
    keyPoints.put("mouthBottom", YailList.makeEmptyList());
    keyPoints.put("leftEyeTop", YailList.makeEmptyList());
    keyPoints.put("leftEyeBottom", YailList.makeEmptyList());
    keyPoints.put("rightEyeTop", YailList.makeEmptyList());
    keyPoints.put("rightEyeBottom", YailList.makeEmptyList());
    keyPoints.put("rightEarStart", YailList.makeEmptyList());
    keyPoints.put("leftEarStart", YailList.makeEmptyList());
    keyPoints.put("noseBottom", YailList.makeEmptyList());
    keyPoints.put("rightNoseTop", YailList.makeEmptyList());
    keyPoints.put("leftNoseTop", YailList.makeEmptyList());
    keyPoints.put("allPoints", YailList.makeEmptyList());
    Log.d(LOG_TAG, "Created FaceExtension extension");
  }

  @SuppressLint("SetJavaScriptEnabled")
  private void configureWebView(WebView webview) {
    this.webview = webview;
    webview.getSettings().setJavaScriptEnabled(true);
    webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
    webview.addJavascriptInterface(new AppInventorTFJS(), "FaceExtension");
    webview.setWebViewClient(new WebViewClient() {
      @Override
      public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        Log.d(LOG_TAG, "shouldInterceptRequest called");
        if (url.startsWith(MODEL_URL)) {
          if (url.indexOf("?") >= 0) {
            url = url.substring(0, url.indexOf("?"));
          }
          Log.d(LOG_TAG, "overriding " + url);
          InputStream is;
          try {
            is = form.openAssetForExtension(FaceExtension.this,
                url.substring(MODEL_URL.length()));
            String contentType, charSet;
            if (url.endsWith(".json")) {
              contentType = "application/json";
              charSet = "UTF-8";
            } else {
              contentType = "application/octet-stream";
              charSet = "binary";
            }
            if (SdkLevel.getLevel() >= SdkLevel.LEVEL_LOLLIPOP) {
              Map<String, String> responseHeaders = new HashMap<>();
              responseHeaders.put("Access-Control-Allow-Origin", "*");
              return new WebResourceResponse(contentType, charSet, 200, "OK", responseHeaders, is);
            } else {
              return new WebResourceResponse(contentType, charSet, is);
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        Log.d(LOG_TAG, url);
        return super.shouldInterceptRequest(view, request);
      }
    });
    webview.setWebChromeClient(new WebChromeClient() {
      @Override
      public void onPermissionRequest(final PermissionRequest request) {
        Log.d(LOG_TAG, "onPermissionRequest called");
        String[] requestedResources = request.getResources();
        for (String r : requestedResources) {
          if (r.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
            form.askPermission(permission.CAMERA, new PermissionResultHandler() {
              @Override
              public void HandlePermissionResponse(String permission, boolean granted) {
                if (granted) {
                  request.grant(new String[]{PermissionRequest.RESOURCE_VIDEO_CAPTURE});
                } else {
                  form.dispatchPermissionDeniedEvent(FaceExtension.this, "Enable", permission);
                }
              }
            });
          }
        }
      }
    });
  }

  @SuppressWarnings("squid:S00100")
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT
      + ":com.google.appinventor.components.runtime.WebViewer")
  @SimpleProperty(userVisible = false)
  public void WebViewer(WebViewer webviewer) {
    if (webviewer != null) {
      configureWebView((WebView) webviewer.getView());
      webview.requestLayout();
    }
    try {
      Log.d(LOG_TAG, "isHardwareAccelerated? " + webview.isHardwareAccelerated());
      webview.loadUrl(form.getAssetPathForExtension(this, "index.html"));
    } catch(FileNotFoundException e) {
      Log.e(LOG_TAG, "Unable to load tensorflow", e);
    }
  }

  public void Initialize() {
    if (webview != null) {
      initialized = true;
    }
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "350")
  @SimpleProperty
  public void Width(int w) {
    width = w;
  }

  @SimpleProperty
  public int Width() {
    return width;
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "200")
  @SimpleProperty
  public void Height(int h) {
    height = h;
  }

  @SimpleProperty
  public int Height() {
    return height;
  }

  @SimpleProperty(description = "Position of forehead")
  public YailList Forehead() {
    return keyPoints.get("forehead");
  }

 @SimpleProperty(description = "Position of nose bottom")
 public YailList NoseBottom() {
   return keyPoints.get("noseBottom");
 }

 @SimpleProperty(description = "Position of chin")
  public YailList Chin() {
    return keyPoints.get("chin");
  }

 @SimpleProperty(description = "Position of left cheek")
  public YailList LeftCheek() {
    return keyPoints.get("leftCheek");
  }

 @SimpleProperty(description = "Position of right cheek")
  public YailList RightCheek() {
    return keyPoints.get("rightCheek");
  }

  @SimpleProperty(description = "Position of left cheek")
  public YailList LeftEyebrow() {
    return keyPoints.get("leftEyebrow");
  }

 @SimpleProperty(description = "Position of right cheek")
  public YailList RightEyebrow() {
    return keyPoints.get("rightEyebrow");
  }

  @SimpleProperty(description = "Position of left eye inner corner")
  public YailList LeftEyeInnerCorner() {
    return keyPoints.get("leftEyeInnerCorner");
  }

  @SimpleProperty(description = "Position of right eye inner corner")
  public YailList RightEyeInnerCorner() {
    return keyPoints.get("rightEyeInnerCorner");
  }

  @SimpleProperty(description = "Position of mouth top")
  public YailList MouthTop() {
    return keyPoints.get("mouthTop");
  }

  @SimpleProperty(description = "Position of mouth bottom")
  public YailList MouthBottom() {
    return keyPoints.get("mouthBottom");
  }

  @SimpleProperty(description = "Position of mouth bottom")
  public YailList LeftEyeTop() {
    return keyPoints.get("leftEyeTop");
  }

  @SimpleProperty(description = "Position of mouth bottom")
  public YailList LeftEyeBottom() {
    return keyPoints.get("leftEyeBottom");
  }

  @SimpleProperty(description = "Position of mouth bottom")
  public YailList RightEyeTop() {
    return keyPoints.get("rightEyeTop");
  }

  @SimpleProperty(description = "Position of mouth bottom")
  public YailList RightEyeBottom() {
    return keyPoints.get("rightEyeBottom");
  }

  @SimpleProperty(description = "Position of mouth bottom")
  public YailList RightForehead() {
    return keyPoints.get("rightEarStart");
  }

  @SimpleProperty(description = "Position of mouth bottom")
  public YailList LeftForehead() {
    return keyPoints.get("leftEarStart");
  }

  @SimpleProperty(description = "Position of mouth bottom")
  public YailList RightNose() {
    return keyPoints.get("rightNoseTop");
  }

  @SimpleProperty(description = "Position of mouth bottom")
  public YailList LeftNose() {
    return keyPoints.get("leftNoseTop");
  }

  @SimpleProperty(description = "width of face")
  public double FaceWidth() {
    return ((double) RightCheek().getObject(0) - (double) LeftCheek().getObject(0));
  }

  @SimpleProperty(description = "width of small ear")
  public double CheekToNoseDistance() {
    return (double) LeftNose().getObject(0) - (double) LeftCheek().getObject(0);
  }

  @SimpleProperty(description = "height from eyebrow to upper lip")
  public double EyeToMouthHeight() {
    return (double) MouthTop().getObject(1) - (double) Forehead().getObject(1);
  }

  @SimpleProperty(description = "Position of mouth bottom")
  public YailList AllPoints() {
    return keyPoints.get("allPoints");
  }

  @SimpleProperty(description = "Background Image.")
  public String BackgroundImage() {
    return BackgroundImage;
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "True")
  @SimpleProperty
  public void Enabled(boolean enabled) {
    this.enabled = enabled;
    if (initialized) {
      assertWebView("Enabled");
      if (showMesh) {
        webview.evaluateJavascript("turnMeshOn();", null);
      } else {
        webview.evaluateJavascript("turnMeshOff();", null);
      }
      webview.evaluateJavascript(enabled ? "startVideo();" : "stopVideo();", null);
    }
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
  @SimpleProperty
  public void ShowMesh(boolean show) {
    this.showMesh = show;
    if (initialized) {
      if (show) {
        webview.evaluateJavascript("turnMeshOn();", null);
      } else {
        webview.evaluateJavascript("turnMeshOff();", null);
      }
    }
  }

  @SimpleProperty(description = "Enables or disables the model.")
  public boolean Enabled() {
    return enabled;
  }

  @SuppressWarnings("squid:S00100")
  @SimpleEvent(description = "Event indicating that the model is ready.")
  public void ModelReady() {
    EventDispatcher.dispatchEvent(this, "ModelReady");
  }

  @SuppressWarnings("squid:S00100")
  @SimpleEvent(description = "Event indicating that an error has occurred.")
  public void Error(int errorCode, String errorMessage) {
    EventDispatcher.dispatchEvent(this, "Error", errorCode, errorMessage);
  }

  @SuppressWarnings("squid:S00100")
  @SimpleEvent(description = "Event indicating that model successfully got a result.")
  public void FaceUpdated() {
    EventDispatcher.dispatchEvent(this, "FaceUpdated");
  }

  @SuppressWarnings("squid:S00100")
  @SimpleEvent(description = "Event indicating that a new video frame is ready. ")
  public void VideoUpdated() {
    EventDispatcher.dispatchEvent(this, "VideoUpdated");
  }

  @SimpleProperty(description = "Configures FaceExtension to use the front or " +
      "back camera on the device.")
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
      editorArgs = {BACK_CAMERA, FRONT_CAMERA}, defaultValue = FRONT_CAMERA)
  public void UseCamera(String mode) {
    if (BACK_CAMERA.equals(mode) || FRONT_CAMERA.equals(mode)) {
      cameraMode = mode;
      if (initialized) {
        boolean frontFacing = mode.equals(FRONT_CAMERA);
        // webview.evaluateJavascript("heightFromJava =" + height + ";", null);
        // webview.evaluateJavascript("widthFromJava =" + width + ";", null);
        if (showMesh) {
          webview.evaluateJavascript("turnMeshOn();", null);
        } else {
          webview.evaluateJavascript("turnMeshOff();", null);
        }
        webview.evaluateJavascript("setCameraFacingMode(" + frontFacing + ");", null);
      }
    } else {
      form.dispatchErrorOccurredEvent(this, "UseCamera", ErrorMessages.ERROR_EXTENSION_ERROR,
          1, LOG_TAG, "Invalid camera selection. Must be either 'Front' or 'Back'.");
    }
  }

  @SimpleProperty
  public String UseCamera() {
    return cameraMode;
  }

  private static void requestHardwareAcceleration(Activity activity) {
    activity.getWindow().setFlags(LayoutParams.FLAG_HARDWARE_ACCELERATED,
        LayoutParams.FLAG_HARDWARE_ACCELERATED);
  }

  @SuppressWarnings("SameParameterValue")
  private void assertWebView(String method) {
    if (webview == null) {
      throw new IllegalStateException(String.format(ERROR_WEBVIEWER_NOT_SET, method));
    }
  }

  @Override
  public void onDelete() {
    if (initialized && webview != null) {
      webview.evaluateJavascript("teardown();", null);
      webview = null;
    }
  }

  @Override
  public void onPause() {
    if (initialized && webview != null) {
      webview.evaluateJavascript("stopVideo();", null);
    }
  }

  @Override
  public void onResume() {
    if (initialized && enabled && webview != null) {
      webview.evaluateJavascript("startVideo();", null);
    }
  }

  @Override
  public void onStop() {
    if (initialized && webview != null) {
      webview.evaluateJavascript("teardown();", null);
      webview = null;
    }
  }

  private class AppInventorTFJS {
    @JavascriptInterface
    public void ready() {
      form.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          ModelReady();
          if (enabled) {
            UseCamera(cameraMode);
          }
        }
      });
    }

    @JavascriptInterface
    public void reportImage(final String dataUrl) {
      Log.d(LOG_TAG, "reportImage "  + dataUrl);
      if (dataUrl != null) {
        BackgroundImage = dataUrl.substring(dataUrl.indexOf(",") + 1);
        form.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            VideoUpdated();
          }
        });
      }
    }

    @JavascriptInterface
    public String reportWidth() {
      return String.valueOf(width);
    }

    @JavascriptInterface
    public String reportHeight() {
      return String.valueOf(height);
    }

  private static final int y_offset = -20;
  private static final double y_multiplier = 1.0/620.0;
  private static final double x_multiplier = 1.0/480.0;
  private static final double x_offset = 180.0;
  private static final double x_range = 480.0;


    @JavascriptInterface 
    public void reportResult(final String result) {
      try {
        JSONObject res = new JSONObject(result);
        JSONObject forehead = res.getJSONObject("forehead");
        YailList foreHeadList = YailList.makeList(new Double[]{forehead.getDouble("x") * width * x_multiplier, forehead.getDouble("y") * height * y_multiplier, forehead.getDouble("z")});
        keyPoints.put("forehead", foreHeadList);

        JSONObject chin = res.getJSONObject("chin");
        YailList chinList = YailList.makeList(new Double[]{chin.getDouble("x") * width * x_multiplier, chin.getDouble("y") * height * y_multiplier, chin.getDouble("z")});
        keyPoints.put("chin", chinList); 

        JSONObject leftCheek = res.getJSONObject("leftCheek");
        YailList leftCheekList = YailList.makeList(new Double[]{leftCheek.getDouble("x") * width *  x_multiplier, leftCheek.getDouble("y") * height * y_multiplier, leftCheek.getDouble("z")});
        keyPoints.put("leftCheek", leftCheekList);

        JSONObject rightCheek = res.getJSONObject("rightCheek");
        YailList rightCheekList = YailList.makeList(new Double[]{rightCheek.getDouble("x") * width * x_multiplier, rightCheek.getDouble("y") * height * y_multiplier, rightCheek.getDouble("z")});
        keyPoints.put("rightCheek", rightCheekList);


        JSONObject leftEyebrow = res.getJSONObject("leftEyebrow");
        YailList leftEyebrowList = YailList.makeList(new Double[]{leftEyebrow.getDouble("x") * width *  x_multiplier, leftEyebrow.getDouble("y") * height * y_multiplier, leftEyebrow.getDouble("z")});
        keyPoints.put("leftEyebrow", leftEyebrowList);

        JSONObject rightEyebrow = res.getJSONObject("rightEyebrow");
        YailList rightEyebrowList = YailList.makeList(new Double[]{rightEyebrow.getDouble("x") * width * x_multiplier, rightEyebrow.getDouble("y") * height * y_multiplier, rightEyebrow.getDouble("z")});
        keyPoints.put("rightEyebrow", rightEyebrowList);

        JSONObject leftEyeInnerCorner = res.getJSONObject("leftEyeInnerCorner");
        YailList leftEyeInnerCornerList = YailList.makeList(new Double[]{leftEyeInnerCorner.getDouble("x") * width * x_multiplier, leftEyeInnerCorner.getDouble("y") * height * y_multiplier, leftEyeInnerCorner.getDouble("z")});
        keyPoints.put("leftEyeInnerCorner", leftEyeInnerCornerList);


        JSONObject rightEyeInnerCorner = res.getJSONObject("rightEyeInnerCorner");
        YailList rightEyeInnerCornerList = YailList.makeList(new Double[]{rightEyeInnerCorner.getDouble("x") * width * x_multiplier, rightEyeInnerCorner.getDouble("y") * height * y_multiplier, rightEyeInnerCorner.getDouble("z")});
        keyPoints.put("rightEyeInnerCorner", rightEyeInnerCornerList);

        JSONObject mouthTop = res.getJSONObject("mouthTop");
        YailList mouthTopList = YailList.makeList(new Double[]{mouthTop.getDouble("x") * width * x_multiplier, mouthTop.getDouble("y") * height * y_multiplier, mouthTop.getDouble("z")});
        keyPoints.put("mouthTop", mouthTopList);

        JSONObject mouthBottom = res.getJSONObject("mouthBottom");
        YailList mouthBottomList = YailList.makeList(new Double[]{mouthBottom.getDouble("x") * width * x_multiplier, mouthBottom.getDouble("y") * height * y_multiplier, mouthBottom.getDouble("z")});
        keyPoints.put("mouthBottom", mouthBottomList);

        JSONObject leftEyeTop = res.getJSONObject("leftEyeTop");
        YailList leftEyeTopList = YailList.makeList(new Double[]{leftEyeTop.getDouble("x") * width * x_multiplier, leftEyeTop.getDouble("y") * height * y_multiplier, leftEyeTop.getDouble("z")});
        keyPoints.put("leftEyeTop", leftEyeTopList);

        JSONObject leftEyeBottom = res.getJSONObject("leftEyeBottom");
        YailList leftEyeBottomList = YailList.makeList(new Double[]{leftEyeBottom.getDouble("x") * width * x_multiplier, leftEyeBottom.getDouble("y") * height * y_multiplier, leftEyeBottom.getDouble("z")});
        keyPoints.put("leftEyeBottom", leftEyeBottomList);

        JSONObject rightEyeTop = res.getJSONObject("rightEyeTop");
        YailList rightEyeTopList = YailList.makeList(new Double[]{rightEyeTop.getDouble("x") * width * x_multiplier, rightEyeTop.getDouble("y") * height * y_multiplier, rightEyeTop.getDouble("z")});
        keyPoints.put("rightEyeTop", rightEyeTopList);

        JSONObject rightEyeBottom = res.getJSONObject("rightEyeBottom");
        YailList rightEyeBottomList = YailList.makeList(new Double[]{rightEyeBottom.getDouble("x") * width * x_multiplier, rightEyeBottom.getDouble("y") * height * y_multiplier, rightEyeBottom.getDouble("z")});
        keyPoints.put("rightEyeBottom", rightEyeBottomList);

        JSONObject rightEarStart = res.getJSONObject("rightEarStart");
        YailList rightEarStartList = YailList.makeList(new Double[]{rightEarStart.getDouble("x") * width * x_multiplier, rightEarStart.getDouble("y") * height * y_multiplier, rightEarStart.getDouble("z")});
        keyPoints.put("rightEarStart", rightEarStartList);

        JSONObject leftEarStart = res.getJSONObject("leftEarStart");
        YailList leftEarStartList = YailList.makeList(new Double[]{leftEarStart.getDouble("x") * width * x_multiplier, leftEarStart.getDouble("y") * height * y_multiplier, leftEarStart.getDouble("z")});
        keyPoints.put("leftEarStart", leftEarStartList);

        JSONObject noseBottom = res.getJSONObject("noseBottom");
        YailList noseBottomList = YailList.makeList(new Double[]{noseBottom.getDouble("x") * width * x_multiplier, noseBottom.getDouble("y") * height * y_multiplier, noseBottom.getDouble("z")});
        keyPoints.put("noseBottom", noseBottomList);

        JSONObject rightNoseTop = res.getJSONObject("rightNoseTop");
        YailList rightNoseTopList = YailList.makeList(new Double[]{rightNoseTop.getDouble("x") * width * x_multiplier, rightNoseTop.getDouble("y") * height * y_multiplier, rightNoseTop.getDouble("z")});
        keyPoints.put("rightNoseTop", rightNoseTopList);

        JSONObject leftNoseTop = res.getJSONObject("leftNoseTop");
        YailList leftNoseTopList = YailList.makeList(new Double[]{leftNoseTop.getDouble("x") * width * x_multiplier, leftNoseTop.getDouble("y") * height * y_multiplier, leftNoseTop.getDouble("z")});
        keyPoints.put("leftNoseTop", leftNoseTopList);

        JSONObject allObjects = res.getJSONObject("allPoints");
        ArrayList<Object> listOfObjects = new ArrayList<Object>();
        for (int i = 0; i < 450; i++) {
          JSONObject currObj = allObjects.getJSONObject(String.valueOf(i));
          YailList currObjList = YailList.makeList(new Double[]{currObj.getDouble("x") * width * x_multiplier, currObj.getDouble("y") * height * y_multiplier});
          listOfObjects.add(currObjList);
        }
        keyPoints.put("allPoints", YailList.makeList(listOfObjects));

        form.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            FaceUpdated();
          }
        });

      } catch (final JSONException e) {
        form.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Error(ERROR_JSON_PARSE_FAILED, e.getMessage());
          }
        });
        Log.e(LOG_TAG, "Error parsing JSON from web view", e);
      }
    }

    @JavascriptInterface
    public void error(final int errorCode, final String errorMessage) {
      form.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          Error(errorCode, errorMessage);
        }
      });
    }
  }
}