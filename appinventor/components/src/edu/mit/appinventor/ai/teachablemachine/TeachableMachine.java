// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2018 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package edu.mit.appinventor.ai.teachablemachine;

import android.Manifest;
import android.Manifest.permission;
import android.webkit.PermissionRequest;
import com.google.appinventor.components.runtime.PermissionResultHandler;

// For writing variable in a file
import java.io.IOException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager.LayoutParams;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
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
import com.google.appinventor.components.runtime.OnClearListener;
import com.google.appinventor.components.runtime.OnPauseListener;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.WebViewer;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
// error in this import
import com.google.appinventor.components.runtime.util.YailDictionary;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.util.Collections;



import org.json.JSONArray;
import org.json.JSONException;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Component that classifies images using a user trained model from the image classification explorer.
 * Based heavily on the Look Extension by kevinzhu@mit.edu (Kevin Zhu) and kelseyc@mit.edu (Kelsey Chan)
 *
 * @author data1013@mit.edu (Danny Tang)
 */

// Common for every extension
@DesignerComponent(version = 1,
        category = ComponentCategory.EXTENSION,
        description = "Component that classifies images using a user trained model from the image " +
                "classification explorer. You must provide a WebViewer component in the Teachable Machine Extension " +
                "component's WebViewer property in order for classification to work.",
        iconName = "aiwebres/tm.png",
        nonVisible = true)
@SimpleObject(external = true)
// Defining the assets
@UsesAssets(fileNames = "teachable_machine.html, teachable_machine.js")
@UsesPermissions({Manifest.permission.CAMERA})

//@UsesPermissions(permissionNames = "android.permission.INTERNET, android.permission.CAMERA")
public final class TeachableMachine extends AndroidNonvisibleComponent
        implements Component, OnPauseListener, OnResumeListener, OnClearListener {

    private static final String LOG_TAG = TeachableMachine.class.getSimpleName();
    private static final int IMAGE_WIDTH = 500;
    private static final int IMAGE_QUALITY = 100;
    private static final String MODE_VIDEO = "Video";
    private static final String MODE_IMAGE = "Image";
    private static final String ERROR_WEBVIEWER_NOT_SET =
            "You must specify a WebViewer using the WebViewer designer property before you can call %1s";

    // other error codes are defined in teachable_machine.js
    private static final int ERROR_CLASSIFICATION_NOT_SUPPORTED = -1;
    private static final int ERROR_CLASSIFICATION_FAILED = -2;
    private static final int ERROR_CANNOT_TOGGLE_CAMERA_IN_IMAGE_MODE = -3;
    private static final int ERROR_CANNOT_CLASSIFY_IMAGE_IN_VIDEO_MODE = -4;
    private static final int ERROR_CANNOT_CLASSIFY_VIDEO_IN_IMAGE_MODE = -5;
    private static final int ERROR_INVALID_INPUT_MODE = -6;
    private static final int ERROR_WEBVIEWER_REQUIRED = -7;
    // below 2 are required since in PIC we import model
    private static final int ERROR_INVALID_MODEL_FILE = -8;
    private static final int ERROR_MODEL_REQUIRED = -9;



    private WebView webview = null;
    private String inputMode = MODE_VIDEO;
    private List<String> labels = Collections.emptyList();
    private String modelPath = null;
    private boolean running = false;
    // Unable to understand[may be min time for classifier to get ready]
    private int minClassTime = 0;



    // common for every extension
    public TeachableMachine(final Form form) {
        super(form);
        requestHardwareAcceleration(form);
        WebView.setWebContentsDebuggingEnabled(true);
        Log.d(LOG_TAG, "Created TeachableMachine component");
        Log.d(LOG_TAG, "FIRST");
    }

    private static final String MODEL_URL =  "https://teachablemachine.withgoogle.com/models/";




    // Common for model
    @SuppressLint("SetJavaScriptEnabled")
    private void configureWebView(WebView webview) {
        this.webview = webview;
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
        // adds a way to send strings to the javascript
        webview.addJavascriptInterface(new JsObject(), "TeachableMachine");
//        webview.addJavascriptInterface(new AppInventorTFJS(), "TeachableMachine");

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d(LOG_TAG, "shouldInterceptRequest called");
                Log.d(LOG_TAG, "SECOND");
                Log.d(LOG_TAG, url);
                // unable to understand
                try {
                    if ((url.startsWith(MODEL_URL)) || (url.startsWith("https://cdn.jsdelivr.net/npm/"))) {
                        return null;
                    }
                    InputStream file = null;
                    String charSet;
                    String contentType;
                    String fileName;

                    if (url.startsWith("http://localhost/")) {
                        fileName = url.substring("http://localhost/".length());
                        file = form.openAssetForExtension(TeachableMachine.this , fileName);
                    }
                    if (url.endsWith(".json")) {
                        contentType = "application/json";
                        charSet = "UTF-8";
                    } else {
                        contentType = "application/octet-stream";
                        charSet = "binary";
                    }

                    // For android permission
                    if (file != null) {
                        if (SdkLevel.getLevel() >= SdkLevel.LEVEL_LOLLIPOP) {
                            Map<String, String> responseHeaders = new HashMap<>();
                            responseHeaders.put("Access-Control-Allow-Origin", "*");
                            return new WebResourceResponse(contentType, charSet, 200, "OK", responseHeaders, file);
                        } else {
                            return new WebResourceResponse(contentType, charSet, file);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return super.shouldInterceptRequest(view, url);
                }

                return super.shouldInterceptRequest(view, url);
            }
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                final String url = request.getUrl().toString();
                Log.d(LOG_TAG, "shouldInterceptRequest called");
                Log.d(LOG_TAG, "THIRD");
                return shouldInterceptRequest(view, url);
            }
        });

        // permission to capture video
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d(LOG_TAG, "onPermissionRequest called");
                Log.d(LOG_TAG, "FOURTH");
                String[] requestedResources = request.getResources();
                for (String r : requestedResources) {
                    if (r.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                        request.grant(new String[]{PermissionRequest.RESOURCE_VIDEO_CAPTURE});
                    }
                }
            }
        });
    }

    // Web view component that camera see
    public void Initialize() {
        Log.d(LOG_TAG, "webview = " + webview);
        if (webview == null) {
            form.dispatchErrorOccurredEvent(this, "WebViewer",
                    ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_WEBVIEWER_REQUIRED, LOG_TAG,
                    "You must specify a WebViewer component in the WebViewer property.");
        }
        // if model not imported
        Log.d(LOG_TAG, "modelPath = " + modelPath);
        Log.d(LOG_TAG, "FIFTH");
        if (modelPath == null) {
            form.dispatchErrorOccurredEvent(this, "Model",
                    ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_MODEL_REQUIRED, LOG_TAG,
                    "You must provide a model file in the Model property");
        }
    }

    // Test
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    @SimpleProperty(userVisible = false)
    public void ModelLink(String link) {
        Log.d(LOG_TAG, "Model Link: " + link);
        Log.d(LOG_TAG, "SIXTH");

        if (link.contains(MODEL_URL)) {
            modelPath = link;

        }

       else {
           form.dispatchErrorOccurredEvent(this, "ModelLink",
                   ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_INVALID_MODEL_FILE, LOG_TAG,
                   "Incorrect Model Link: The link should look like " + MODEL_URL);
       }
    }





    // same as other extension[like look]
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT + ":com.google.appinventor.runtime.components.WebViewer")
    @SimpleProperty(userVisible = false)
    public void WebViewer(final WebViewer webviewer) {
        Runnable next = new Runnable() {
            public void run() {
                if (webviewer != null) {
                    configureWebView((WebView) webviewer.getView());
                    webview.requestLayout();
//                    Context c = Context.create("js");
                    try {
                        Log.d(LOG_TAG, "isHardwareAccelerated? " + webview.isHardwareAccelerated());
                        Log.d(LOG_TAG, "runnable called");
                        Log.d(LOG_TAG, "SEVENTH");
                        webview.loadUrl(form.getAssetPathForExtension(TeachableMachine.this, "teachable_machine.html"));
                        String js = "loadModel(\"" + modelPath + "\");";
                        Log.d(LOG_TAG, js);
                        webview.evaluateJavascript(js,null);

                        Log.d(LOG_TAG, "working?");

                    } catch (Exception e) {
                        Log.d(LOG_TAG, e.getMessage());
                        e.printStackTrace();
                    }

                }
            }
        };
        if (SDK26Helper.shouldAskForPermission(form)) {
            SDK26Helper.askForPermission(this, next);
        } else {
            next.run();
        }
    }



    // same as other extension[video(lock)]
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
            editorArgs = {MODE_VIDEO, MODE_IMAGE})
    @SimpleProperty
    public void InputMode(String mode) {
        Log.d(LOG_TAG,"INPUT MODE RUN");
        if (webview == null) {
            inputMode = mode;
            return;
        }
        if (MODE_VIDEO.equalsIgnoreCase(mode)) {
            webview.evaluateJavascript("setInputMode(\"video\");", null);
            inputMode = MODE_VIDEO;
        } else if (MODE_IMAGE.equalsIgnoreCase(mode)) {
            webview.evaluateJavascript("setInputMode(\"image\");", null);
            inputMode = MODE_IMAGE;
        } else {
            form.dispatchErrorOccurredEvent(this, "InputMode", ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_INVALID_INPUT_MODE, LOG_TAG, "Invalid input mode " + mode);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR,
            description = "Gets or sets the input mode for classification. Valid values are \"Video\" " +
                    "(the default) and \"Image\".")
    public String InputMode() {
        return inputMode;
    }

    // label like me or not me
    @SimpleProperty(description = "Gets all of the labels from this model. Only valid after ClassifierReady is signaled.")
    public List<String> ModelLabels() {
        return labels;
    }


    // return whether the classfier is running or not
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Running() {
        return running;
    }

    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
            defaultValue = "0")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void MinimumInterval(int interval) {
        minClassTime = interval;
        if (webview != null) {
            webview.evaluateJavascript("minClassTime = " + interval + ";", null);
        }
    }



    //continue from here

    @SimpleProperty
    public int MinimumInterval() {
        return minClassTime;
    }

    // classifying image data
    @SimpleFunction(description = "Performs classification on the image at the given path and triggers the GotClassification event when classification is finished successfully.")
    public void ClassifyImageData(final String image) {
        assertWebView("ClassifyImageData");
        Log.d(LOG_TAG, "Entered Classify");
        Log.d(LOG_TAG, image);

        String imagePath = (image == null) ? "" : image;
        BitmapDrawable imageDrawable;
        Bitmap scaledImageBitmap = null;

        try {
            imageDrawable = MediaUtil.getBitmapDrawable(form.$form(), imagePath);
            scaledImageBitmap = Bitmap.createScaledBitmap(imageDrawable.getBitmap(), IMAGE_WIDTH, (int) (imageDrawable.getBitmap().getHeight() * ((float) IMAGE_WIDTH) / imageDrawable.getBitmap().getWidth()), false);
        } catch (IOException ioe) {
            Log.e(LOG_TAG, "Unable to load " + imagePath);
        }

        // compression format of PNG -> not lossy
        Bitmap immagex = scaledImageBitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, IMAGE_QUALITY, baos);
        byte[] b = baos.toByteArray();

        String imageEncodedbase64String = Base64.encodeToString(b, 0).replace("\n", "");
        Log.d(LOG_TAG, "imageEncodedbase64String: " + imageEncodedbase64String);

        webview.evaluateJavascript("classifyImageData(\"" + imageEncodedbase64String + "\");", null);
    }

    // toogling camera
    @SimpleFunction(description = "Toggles between user-facing and environment-facing camera.")
    public void ToggleCameraFacingMode() {
        assertWebView("ToggleCameraFacingMode");
        webview.evaluateJavascript("toggleCameraFacingMode();", null);
        Log.d(LOG_TAG, "NINTH");
    }

    @SimpleFunction(description = "Performs classification on current video frame and triggers the GotClassification event when classification is finished successfully.")
    public void ClassifyVideoData() {
        assertWebView("ClassifyVideoData");
        webview.evaluateJavascript("classifyVideoData();", null);
        Log.d(LOG_TAG, "EIGHT");
    }

    // starts classifying data
    @SimpleFunction()
    public void StartContinuousClassification() {
        if (MODE_VIDEO.equals(inputMode) && !running) {
            assertWebView("StartVideoClassification");
            webview.evaluateJavascript("startVideoClassification();", null);
            running = true;
            Log.d(LOG_TAG, "starting classification");
        }
    }

    // stops the classification
    @SimpleFunction()
    public void StopContinuousClassification() {
        if (MODE_VIDEO.equals(inputMode) && running) {
            assertWebView("StopVideoClassification");
            webview.evaluateJavascript("stopVideoClassification();", null);
            running = false;
            Log.d(LOG_TAG, "Stopping classification");
        }
    }

    // when classifier is ready to do classiciation
    @SimpleEvent(description = "Event indicating that the classifier is ready.")
    public void ClassifierReady() {
        InputMode(inputMode);
        MinimumInterval(minClassTime);
        EventDispatcher.dispatchEvent(this, "ClassifierReady");
        Log.d(LOG_TAG, "IMPORTANT");
    }

    // data we get after classification is done
    @SimpleEvent(description = "Event indicating that classification has finished successfully. Result is of the form [[class1, confidence1], [class2, confidence2], ..., [class10, confidence10]].")
    public void GotClassification(YailDictionary result) {
        EventDispatcher.dispatchEvent(this, "GotClassification", result);
    }

    @SimpleEvent(description = "Event indicating that an error has occurred.")
    public void Error(final int errorCode) {
        EventDispatcher.dispatchEvent(this, "Error", errorCode);
    }

    ///REGION: Lifecycle handling

    // unable to understand
    @Override
    public void onPause() {
        if (MODE_VIDEO.equals(inputMode)) {
            webview.evaluateJavascript("stopVideo();", null);
        }
    }

    @Override
    public void onResume() {
        if (MODE_VIDEO.equals(inputMode)) {
            webview.evaluateJavascript("startVideo();", null);
        }
    }


    @Override
    public void onClear() {
        webview.evaluateJavascript("stopVideo();", null);
    }

    ///ENDREGION

    // same as other extension
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

    // unable to understand [may be different labels]
    private static List<String> parseLabels(String labels) {
        List<String> result = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(labels);
            for (int i = 0; i < arr.length(); i++) {
                result.add(arr.getString(i));
            }
        } catch (JSONException e) {
            throw new YailRuntimeError("Got unparsable array from Javascript", "RuntimeError");
        }
        return result;
    }

    // not understand
    private class JsObject {
        @JavascriptInterface
        public void ready(String labels) {
            Log.d(LOG_TAG, "Entered ready");
            Log.d(LOG_TAG, "TENTH");
            TeachableMachine.this.labels = parseLabels(labels);
            form.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ClassifierReady();
                }
            });
        }

        // not understand
        @JavascriptInterface
        public void reportResult(final String result) {
//            Log.d(LOG_TAG, "Entered reportResult: " + result);
            try {
                Log.d(LOG_TAG, "Entered try of reportResult");
                JSONArray list = new JSONArray(result);
                final YailDictionary resultDict = new YailDictionary();
                for (int i = 0; i < list.length(); i++) {
                    JSONArray pair = list.getJSONArray(i);
                    resultDict.put(pair.getString(0), pair.getDouble(1));
                }
                Log.d(LOG_TAG, "Result Dict: " + resultDict);
                form.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GotClassification(resultDict);
                    }

                });
            } catch (JSONException e) {
                Log.d(LOG_TAG, "Entered catch of reportResult");
                e.printStackTrace();
                Error(ERROR_CLASSIFICATION_FAILED);
            }
        }

        @JavascriptInterface
        public void error(final int errorCode) {
            Log.d(LOG_TAG, "Entered error: " + errorCode);
            form.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Error(errorCode);
                }
            });
        }

        @JavascriptInterface
        public String isLoaded(boolean isComplete) {
            String modelLink;
            if (isComplete) {
                modelLink = modelPath;

                Log.d(LOG_TAG, "Function in JsObject that is called from js");
                Log.d(LOG_TAG, "ELEVENTH");
                return modelLink;
            }
            return modelPath;
        }


    }

}
