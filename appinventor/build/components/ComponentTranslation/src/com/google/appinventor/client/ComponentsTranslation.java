package com.google.appinventor.client;

import java.util.HashMap;
import java.util.Map;

import static com.google.appinventor.client.Ode.MESSAGES;

public class ComponentsTranslation {
  public static Map<String, String> myMap = map();

  private static String getName(String key) {
    String value = myMap.get(key);
    if (key == null) {
      return "**Missing key in ComponentsTranslations**";
    } else {
      return value;
    }
  }

  public static String getPropertyName(String key) {
    String value = getName("PROPERTY-" + key);
    if(value == null) return key;
    return value;
  }

  public static String getPropertyDescription(String key) {
    String value = getName("PROPDESC-" + key);
    if(value == null) return key;
    return value;
  }

  public static String getMethodName(String key) {
    String value = getName("METHOD-" + key);
    if(value == null) return key;
    return value;
  }

  public static String getEventName(String key) {
    String value = getName("EVENT-" + key);
    if(value == null) return key;
    return value;
  }

  public static String getComponentName(String key) {
    String value = getName("COMPONENT-" + key);
    if(value == null) return key;
    return value;
  }

  public static String getCategoryName(String key) {
    String value = getName("CATEGORY-" + key);
    if(value == null) return key;
    return value;
  }

  public static String getComponentHelpString(String key) {
    String value = getName(key + "-helpString");
    if(value == null) return key;
    return value;
  }
  public static HashMap<String, String> map() {
    HashMap<String, String> map = new HashMap<String, String>();


/* Component: AccelerometerSensor */

    map.put("COMPONENT-AccelerometerSensor", MESSAGES.accelerometerSensorComponentPallette());

    map.put("AccelerometerSensor-helpString", MESSAGES.AccelerometerSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Available", MESSAGES.AvailableProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-LegacyMode", MESSAGES.LegacyModeProperties());
    map.put("PROPERTY-MinimumInterval", MESSAGES.MinimumIntervalProperties());
    map.put("PROPERTY-Sensitivity", MESSAGES.SensitivityProperties());
    map.put("PROPERTY-XAccel", MESSAGES.XAccelProperties());
    map.put("PROPERTY-YAccel", MESSAGES.YAccelProperties());
    map.put("PROPERTY-ZAccel", MESSAGES.ZAccelProperties());


/* Events */

    map.put("EVENT-AccelerationChanged", MESSAGES.AccelerationChangedEvents());
    map.put("EVENT-Shaking", MESSAGES.ShakingEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-xAccel", MESSAGES.xAccelParams());
    map.put("PARAM-yAccel", MESSAGES.yAccelParams());
    map.put("PARAM-zAccel", MESSAGES.zAccelParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: ActivityStarter */

    map.put("COMPONENT-ActivityStarter", MESSAGES.activityStarterComponentPallette());

    map.put("ActivityStarter-helpString", MESSAGES.ActivityStarterHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Action", MESSAGES.ActionProperties());
    map.put("PROPERTY-ActivityClass", MESSAGES.ActivityClassProperties());
    map.put("PROPERTY-ActivityPackage", MESSAGES.ActivityPackageProperties());
    map.put("PROPERTY-DataType", MESSAGES.DataTypeProperties());
    map.put("PROPERTY-DataUri", MESSAGES.DataUriProperties());
    map.put("PROPERTY-ExtraKey", MESSAGES.ExtraKeyProperties());
    map.put("PROPERTY-ExtraValue", MESSAGES.ExtraValueProperties());
    map.put("PROPERTY-Extras", MESSAGES.ExtrasProperties());
    map.put("PROPERTY-Result", MESSAGES.ResultProperties());
    map.put("PROPERTY-ResultName", MESSAGES.ResultNameProperties());
    map.put("PROPERTY-ResultType", MESSAGES.ResultTypeProperties());
    map.put("PROPERTY-ResultUri", MESSAGES.ResultUriProperties());


/* Events */

    map.put("EVENT-ActivityCanceled", MESSAGES.ActivityCanceledEvents());
    map.put("EVENT-AfterActivity", MESSAGES.AfterActivityEvents());


/* Methods */

    map.put("METHOD-ResolveActivity", MESSAGES.ResolveActivityMethods());
    map.put("METHOD-StartActivity", MESSAGES.StartActivityMethods());


/* Parameters */

    map.put("PARAM-result", MESSAGES.resultParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Ball */

    map.put("COMPONENT-Ball", MESSAGES.ballComponentPallette());

    map.put("Ball-helpString", MESSAGES.BallHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-Heading", MESSAGES.HeadingProperties());
    map.put("PROPERTY-Interval", MESSAGES.IntervalProperties());
    map.put("PROPERTY-OriginAtCenter", MESSAGES.OriginAtCenterProperties());
    map.put("PROPERTY-PaintColor", MESSAGES.PaintColorProperties());
    map.put("PROPERTY-Radius", MESSAGES.RadiusProperties());
    map.put("PROPERTY-Speed", MESSAGES.SpeedProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-X", MESSAGES.XProperties());
    map.put("PROPERTY-Y", MESSAGES.YProperties());
    map.put("PROPERTY-Z", MESSAGES.ZProperties());


/* Events */

    map.put("EVENT-CollidedWith", MESSAGES.CollidedWithEvents());
    map.put("EVENT-Dragged", MESSAGES.DraggedEvents());
    map.put("EVENT-EdgeReached", MESSAGES.EdgeReachedEvents());
    map.put("EVENT-Flung", MESSAGES.FlungEvents());
    map.put("EVENT-NoLongerCollidingWith", MESSAGES.NoLongerCollidingWithEvents());
    map.put("EVENT-TouchDown", MESSAGES.TouchDownEvents());
    map.put("EVENT-TouchUp", MESSAGES.TouchUpEvents());
    map.put("EVENT-Touched", MESSAGES.TouchedEvents());


/* Methods */

    map.put("METHOD-Bounce", MESSAGES.BounceMethods());
    map.put("METHOD-CollidingWith", MESSAGES.CollidingWithMethods());
    map.put("METHOD-MoveIntoBounds", MESSAGES.MoveIntoBoundsMethods());
    map.put("METHOD-MoveTo", MESSAGES.MoveToMethods());
    map.put("METHOD-PointInDirection", MESSAGES.PointInDirectionMethods());
    map.put("METHOD-PointTowards", MESSAGES.PointTowardsMethods());


/* Parameters */

    map.put("PARAM-other", MESSAGES.otherParams());
    map.put("PARAM-startX", MESSAGES.startXParams());
    map.put("PARAM-startY", MESSAGES.startYParams());
    map.put("PARAM-prevX", MESSAGES.prevXParams());
    map.put("PARAM-prevY", MESSAGES.prevYParams());
    map.put("PARAM-currentX", MESSAGES.currentXParams());
    map.put("PARAM-currentY", MESSAGES.currentYParams());
    map.put("PARAM-edge", MESSAGES.edgeParams());
    map.put("PARAM-x", MESSAGES.xParams());
    map.put("PARAM-y", MESSAGES.yParams());
    map.put("PARAM-speed", MESSAGES.speedParams());
    map.put("PARAM-heading", MESSAGES.headingParams());
    map.put("PARAM-xvel", MESSAGES.xvelParams());
    map.put("PARAM-yvel", MESSAGES.yvelParams());
    map.put("PARAM-target", MESSAGES.targetParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: BarcodeScanner */

    map.put("COMPONENT-BarcodeScanner", MESSAGES.barcodeScannerComponentPallette());

    map.put("BarcodeScanner-helpString", MESSAGES.BarcodeScannerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Result", MESSAGES.ResultProperties());
    map.put("PROPERTY-UseExternalScanner", MESSAGES.UseExternalScannerProperties());


/* Events */

    map.put("EVENT-AfterScan", MESSAGES.AfterScanEvents());


/* Methods */

    map.put("METHOD-DoScan", MESSAGES.DoScanMethods());


/* Parameters */

    map.put("PARAM-result", MESSAGES.resultParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Barometer */

    map.put("COMPONENT-Barometer", MESSAGES.barometerComponentPallette());

    map.put("Barometer-helpString", MESSAGES.BarometerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AirPressure", MESSAGES.AirPressureProperties());
    map.put("PROPERTY-Available", MESSAGES.AvailableProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-RefreshTime", MESSAGES.RefreshTimeProperties());


/* Events */

    map.put("EVENT-AirPressureChanged", MESSAGES.AirPressureChangedEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-pressure", MESSAGES.pressureParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: BluetoothClient */

    map.put("COMPONENT-BluetoothClient", MESSAGES.bluetoothClientComponentPallette());

    map.put("BluetoothClient-helpString", MESSAGES.BluetoothClientHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AddressesAndNames", MESSAGES.AddressesAndNamesProperties());
    map.put("PROPERTY-Available", MESSAGES.AvailableProperties());
    map.put("PROPERTY-CharacterEncoding", MESSAGES.CharacterEncodingProperties());
    map.put("PROPERTY-DelimiterByte", MESSAGES.DelimiterByteProperties());
    map.put("PROPERTY-DisconnectOnError", MESSAGES.DisconnectOnErrorProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-HighByteFirst", MESSAGES.HighByteFirstProperties());
    map.put("PROPERTY-IsConnected", MESSAGES.IsConnectedProperties());
    map.put("PROPERTY-Secure", MESSAGES.SecureProperties());


/* Events */



/* Methods */

    map.put("METHOD-BytesAvailableToReceive", MESSAGES.BytesAvailableToReceiveMethods());
    map.put("METHOD-Connect", MESSAGES.ConnectMethods());
    map.put("METHOD-ConnectWithUUID", MESSAGES.ConnectWithUUIDMethods());
    map.put("METHOD-Disconnect", MESSAGES.DisconnectMethods());
    map.put("METHOD-IsDevicePaired", MESSAGES.IsDevicePairedMethods());
    map.put("METHOD-ReceiveSigned1ByteNumber", MESSAGES.ReceiveSigned1ByteNumberMethods());
    map.put("METHOD-ReceiveSigned2ByteNumber", MESSAGES.ReceiveSigned2ByteNumberMethods());
    map.put("METHOD-ReceiveSigned4ByteNumber", MESSAGES.ReceiveSigned4ByteNumberMethods());
    map.put("METHOD-ReceiveSignedBytes", MESSAGES.ReceiveSignedBytesMethods());
    map.put("METHOD-ReceiveText", MESSAGES.ReceiveTextMethods());
    map.put("METHOD-ReceiveUnsigned1ByteNumber", MESSAGES.ReceiveUnsigned1ByteNumberMethods());
    map.put("METHOD-ReceiveUnsigned2ByteNumber", MESSAGES.ReceiveUnsigned2ByteNumberMethods());
    map.put("METHOD-ReceiveUnsigned4ByteNumber", MESSAGES.ReceiveUnsigned4ByteNumberMethods());
    map.put("METHOD-ReceiveUnsignedBytes", MESSAGES.ReceiveUnsignedBytesMethods());
    map.put("METHOD-Send1ByteNumber", MESSAGES.Send1ByteNumberMethods());
    map.put("METHOD-Send2ByteNumber", MESSAGES.Send2ByteNumberMethods());
    map.put("METHOD-Send4ByteNumber", MESSAGES.Send4ByteNumberMethods());
    map.put("METHOD-SendBytes", MESSAGES.SendBytesMethods());
    map.put("METHOD-SendText", MESSAGES.SendTextMethods());


/* Parameters */

    map.put("PARAM-address", MESSAGES.addressParams());
    map.put("PARAM-uuid", MESSAGES.uuidParams());
    map.put("PARAM-numberOfBytes", MESSAGES.numberOfBytesParams());
    map.put("PARAM-number", MESSAGES.numberParams());
    map.put("PARAM-list", MESSAGES.listParams());
    map.put("PARAM-text", MESSAGES.textParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: BluetoothServer */

    map.put("COMPONENT-BluetoothServer", MESSAGES.bluetoothServerComponentPallette());

    map.put("BluetoothServer-helpString", MESSAGES.BluetoothServerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Available", MESSAGES.AvailableProperties());
    map.put("PROPERTY-CharacterEncoding", MESSAGES.CharacterEncodingProperties());
    map.put("PROPERTY-DelimiterByte", MESSAGES.DelimiterByteProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-HighByteFirst", MESSAGES.HighByteFirstProperties());
    map.put("PROPERTY-IsAccepting", MESSAGES.IsAcceptingProperties());
    map.put("PROPERTY-IsConnected", MESSAGES.IsConnectedProperties());
    map.put("PROPERTY-Secure", MESSAGES.SecureProperties());


/* Events */

    map.put("EVENT-ConnectionAccepted", MESSAGES.ConnectionAcceptedEvents());


/* Methods */

    map.put("METHOD-AcceptConnection", MESSAGES.AcceptConnectionMethods());
    map.put("METHOD-AcceptConnectionWithUUID", MESSAGES.AcceptConnectionWithUUIDMethods());
    map.put("METHOD-BytesAvailableToReceive", MESSAGES.BytesAvailableToReceiveMethods());
    map.put("METHOD-Disconnect", MESSAGES.DisconnectMethods());
    map.put("METHOD-ReceiveSigned1ByteNumber", MESSAGES.ReceiveSigned1ByteNumberMethods());
    map.put("METHOD-ReceiveSigned2ByteNumber", MESSAGES.ReceiveSigned2ByteNumberMethods());
    map.put("METHOD-ReceiveSigned4ByteNumber", MESSAGES.ReceiveSigned4ByteNumberMethods());
    map.put("METHOD-ReceiveSignedBytes", MESSAGES.ReceiveSignedBytesMethods());
    map.put("METHOD-ReceiveText", MESSAGES.ReceiveTextMethods());
    map.put("METHOD-ReceiveUnsigned1ByteNumber", MESSAGES.ReceiveUnsigned1ByteNumberMethods());
    map.put("METHOD-ReceiveUnsigned2ByteNumber", MESSAGES.ReceiveUnsigned2ByteNumberMethods());
    map.put("METHOD-ReceiveUnsigned4ByteNumber", MESSAGES.ReceiveUnsigned4ByteNumberMethods());
    map.put("METHOD-ReceiveUnsignedBytes", MESSAGES.ReceiveUnsignedBytesMethods());
    map.put("METHOD-Send1ByteNumber", MESSAGES.Send1ByteNumberMethods());
    map.put("METHOD-Send2ByteNumber", MESSAGES.Send2ByteNumberMethods());
    map.put("METHOD-Send4ByteNumber", MESSAGES.Send4ByteNumberMethods());
    map.put("METHOD-SendBytes", MESSAGES.SendBytesMethods());
    map.put("METHOD-SendText", MESSAGES.SendTextMethods());
    map.put("METHOD-StopAccepting", MESSAGES.StopAcceptingMethods());


/* Parameters */

    map.put("PARAM-serviceName", MESSAGES.serviceNameParams());
    map.put("PARAM-uuid", MESSAGES.uuidParams());
    map.put("PARAM-numberOfBytes", MESSAGES.numberOfBytesParams());
    map.put("PARAM-number", MESSAGES.numberParams());
    map.put("PARAM-list", MESSAGES.listParams());
    map.put("PARAM-text", MESSAGES.textParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Button */

    map.put("COMPONENT-Button", MESSAGES.buttonComponentPallette());

    map.put("Button-helpString", MESSAGES.ButtonHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Image", MESSAGES.ImageProperties());
    map.put("PROPERTY-Shape", MESSAGES.ShapeProperties());
    map.put("PROPERTY-ShowFeedback", MESSAGES.ShowFeedbackProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextAlignment", MESSAGES.TextAlignmentProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-Click", MESSAGES.ClickEvents());
    map.put("EVENT-GotFocus", MESSAGES.GotFocusEvents());
    map.put("EVENT-LongClick", MESSAGES.LongClickEvents());
    map.put("EVENT-LostFocus", MESSAGES.LostFocusEvents());
    map.put("EVENT-TouchDown", MESSAGES.TouchDownEvents());
    map.put("EVENT-TouchUp", MESSAGES.TouchUpEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Camcorder */

    map.put("COMPONENT-Camcorder", MESSAGES.camcorderComponentPallette());

    map.put("Camcorder-helpString", MESSAGES.CamcorderHelpStringComponentPallette());



/* Properties */



/* Events */

    map.put("EVENT-AfterRecording", MESSAGES.AfterRecordingEvents());


/* Methods */

    map.put("METHOD-RecordVideo", MESSAGES.RecordVideoMethods());


/* Parameters */

    map.put("PARAM-clip", MESSAGES.clipParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Camera */

    map.put("COMPONENT-Camera", MESSAGES.cameraComponentPallette());

    map.put("Camera-helpString", MESSAGES.CameraHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-UseFront", MESSAGES.UseFrontProperties());


/* Events */

    map.put("EVENT-AfterPicture", MESSAGES.AfterPictureEvents());


/* Methods */

    map.put("METHOD-TakePicture", MESSAGES.TakePictureMethods());


/* Parameters */

    map.put("PARAM-image", MESSAGES.imageParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Canvas */

    map.put("COMPONENT-Canvas", MESSAGES.canvasComponentPallette());

    map.put("Canvas-helpString", MESSAGES.CanvasHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-BackgroundImage", MESSAGES.BackgroundImageProperties());
    map.put("PROPERTY-BackgroundImageinBase64", MESSAGES.BackgroundImageinBase64Properties());
    map.put("PROPERTY-ExtendMovesOutsideCanvas", MESSAGES.ExtendMovesOutsideCanvasProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-LineWidth", MESSAGES.LineWidthProperties());
    map.put("PROPERTY-PaintColor", MESSAGES.PaintColorProperties());
    map.put("PROPERTY-TextAlignment", MESSAGES.TextAlignmentProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-Dragged", MESSAGES.DraggedEvents());
    map.put("EVENT-Flung", MESSAGES.FlungEvents());
    map.put("EVENT-TouchDown", MESSAGES.TouchDownEvents());
    map.put("EVENT-TouchUp", MESSAGES.TouchUpEvents());
    map.put("EVENT-Touched", MESSAGES.TouchedEvents());


/* Methods */

    map.put("METHOD-Clear", MESSAGES.ClearMethods());
    map.put("METHOD-DrawArc", MESSAGES.DrawArcMethods());
    map.put("METHOD-DrawCircle", MESSAGES.DrawCircleMethods());
    map.put("METHOD-DrawLine", MESSAGES.DrawLineMethods());
    map.put("METHOD-DrawPoint", MESSAGES.DrawPointMethods());
    map.put("METHOD-DrawShape", MESSAGES.DrawShapeMethods());
    map.put("METHOD-DrawText", MESSAGES.DrawTextMethods());
    map.put("METHOD-DrawTextAtAngle", MESSAGES.DrawTextAtAngleMethods());
    map.put("METHOD-GetBackgroundPixelColor", MESSAGES.GetBackgroundPixelColorMethods());
    map.put("METHOD-GetPixelColor", MESSAGES.GetPixelColorMethods());
    map.put("METHOD-Save", MESSAGES.SaveMethods());
    map.put("METHOD-SaveAs", MESSAGES.SaveAsMethods());
    map.put("METHOD-SetBackgroundPixelColor", MESSAGES.SetBackgroundPixelColorMethods());


/* Parameters */

    map.put("PARAM-startX", MESSAGES.startXParams());
    map.put("PARAM-startY", MESSAGES.startYParams());
    map.put("PARAM-prevX", MESSAGES.prevXParams());
    map.put("PARAM-prevY", MESSAGES.prevYParams());
    map.put("PARAM-currentX", MESSAGES.currentXParams());
    map.put("PARAM-currentY", MESSAGES.currentYParams());
    map.put("PARAM-draggedAnySprite", MESSAGES.draggedAnySpriteParams());
    map.put("PARAM-x", MESSAGES.xParams());
    map.put("PARAM-y", MESSAGES.yParams());
    map.put("PARAM-speed", MESSAGES.speedParams());
    map.put("PARAM-heading", MESSAGES.headingParams());
    map.put("PARAM-xvel", MESSAGES.xvelParams());
    map.put("PARAM-yvel", MESSAGES.yvelParams());
    map.put("PARAM-flungSprite", MESSAGES.flungSpriteParams());
    map.put("PARAM-touchedAnySprite", MESSAGES.touchedAnySpriteParams());
    map.put("PARAM-left", MESSAGES.leftParams());
    map.put("PARAM-top", MESSAGES.topParams());
    map.put("PARAM-right", MESSAGES.rightParams());
    map.put("PARAM-bottom", MESSAGES.bottomParams());
    map.put("PARAM-startAngle", MESSAGES.startAngleParams());
    map.put("PARAM-sweepAngle", MESSAGES.sweepAngleParams());
    map.put("PARAM-useCenter", MESSAGES.useCenterParams());
    map.put("PARAM-fill", MESSAGES.fillParams());
    map.put("PARAM-centerX", MESSAGES.centerXParams());
    map.put("PARAM-centerY", MESSAGES.centerYParams());
    map.put("PARAM-radius", MESSAGES.radiusParams());
    map.put("PARAM-x1", MESSAGES.x1Params());
    map.put("PARAM-y1", MESSAGES.y1Params());
    map.put("PARAM-x2", MESSAGES.x2Params());
    map.put("PARAM-y2", MESSAGES.y2Params());
    map.put("PARAM-pointList", MESSAGES.pointListParams());
    map.put("PARAM-text", MESSAGES.textParams());
    map.put("PARAM-angle", MESSAGES.angleParams());
    map.put("PARAM-fileName", MESSAGES.fileNameParams());
    map.put("PARAM-color", MESSAGES.colorParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: CheckBox */

    map.put("COMPONENT-CheckBox", MESSAGES.checkBoxComponentPallette());

    map.put("CheckBox-helpString", MESSAGES.CheckBoxHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Checked", MESSAGES.CheckedProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-Changed", MESSAGES.ChangedEvents());
    map.put("EVENT-GotFocus", MESSAGES.GotFocusEvents());
    map.put("EVENT-LostFocus", MESSAGES.LostFocusEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Circle */

    map.put("COMPONENT-Circle", MESSAGES.circleComponentPallette());

    map.put("Circle-helpString", MESSAGES.CircleHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Description", MESSAGES.DescriptionProperties());
    map.put("PROPERTY-Draggable", MESSAGES.DraggableProperties());
    map.put("PROPERTY-EnableInfobox", MESSAGES.EnableInfoboxProperties());
    map.put("PROPERTY-FillColor", MESSAGES.FillColorProperties());
    map.put("PROPERTY-FillOpacity", MESSAGES.FillOpacityProperties());
    map.put("PROPERTY-Latitude", MESSAGES.LatitudeProperties());
    map.put("PROPERTY-Longitude", MESSAGES.LongitudeProperties());
    map.put("PROPERTY-Radius", MESSAGES.RadiusProperties());
    map.put("PROPERTY-StrokeColor", MESSAGES.StrokeColorProperties());
    map.put("PROPERTY-StrokeOpacity", MESSAGES.StrokeOpacityProperties());
    map.put("PROPERTY-StrokeWidth", MESSAGES.StrokeWidthProperties());
    map.put("PROPERTY-Title", MESSAGES.TitleProperties());
    map.put("PROPERTY-Type", MESSAGES.TypeProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());


/* Events */

    map.put("EVENT-Click", MESSAGES.ClickEvents());
    map.put("EVENT-Drag", MESSAGES.DragEvents());
    map.put("EVENT-LongClick", MESSAGES.LongClickEvents());
    map.put("EVENT-StartDrag", MESSAGES.StartDragEvents());
    map.put("EVENT-StopDrag", MESSAGES.StopDragEvents());


/* Methods */

    map.put("METHOD-DistanceToFeature", MESSAGES.DistanceToFeatureMethods());
    map.put("METHOD-DistanceToPoint", MESSAGES.DistanceToPointMethods());
    map.put("METHOD-HideInfobox", MESSAGES.HideInfoboxMethods());
    map.put("METHOD-SetLocation", MESSAGES.SetLocationMethods());
    map.put("METHOD-ShowInfobox", MESSAGES.ShowInfoboxMethods());


/* Parameters */

    map.put("PARAM-mapFeature", MESSAGES.mapFeatureParams());
    map.put("PARAM-centroids", MESSAGES.centroidsParams());
    map.put("PARAM-latitude", MESSAGES.latitudeParams());
    map.put("PARAM-longitude", MESSAGES.longitudeParams());
    map.put("PARAM-centroid", MESSAGES.centroidParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Clock */

    map.put("COMPONENT-Clock", MESSAGES.clockComponentPallette());

    map.put("Clock-helpString", MESSAGES.ClockHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-TimerAlwaysFires", MESSAGES.TimerAlwaysFiresProperties());
    map.put("PROPERTY-TimerEnabled", MESSAGES.TimerEnabledProperties());
    map.put("PROPERTY-TimerInterval", MESSAGES.TimerIntervalProperties());


/* Events */

    map.put("EVENT-Timer", MESSAGES.TimerEvents());


/* Methods */

    map.put("METHOD-AddDays", MESSAGES.AddDaysMethods());
    map.put("METHOD-AddDuration", MESSAGES.AddDurationMethods());
    map.put("METHOD-AddHours", MESSAGES.AddHoursMethods());
    map.put("METHOD-AddMinutes", MESSAGES.AddMinutesMethods());
    map.put("METHOD-AddMonths", MESSAGES.AddMonthsMethods());
    map.put("METHOD-AddSeconds", MESSAGES.AddSecondsMethods());
    map.put("METHOD-AddWeeks", MESSAGES.AddWeeksMethods());
    map.put("METHOD-AddYears", MESSAGES.AddYearsMethods());
    map.put("METHOD-DayOfMonth", MESSAGES.DayOfMonthMethods());
    map.put("METHOD-Duration", MESSAGES.DurationMethods());
    map.put("METHOD-DurationToDays", MESSAGES.DurationToDaysMethods());
    map.put("METHOD-DurationToHours", MESSAGES.DurationToHoursMethods());
    map.put("METHOD-DurationToMinutes", MESSAGES.DurationToMinutesMethods());
    map.put("METHOD-DurationToSeconds", MESSAGES.DurationToSecondsMethods());
    map.put("METHOD-DurationToWeeks", MESSAGES.DurationToWeeksMethods());
    map.put("METHOD-FormatDate", MESSAGES.FormatDateMethods());
    map.put("METHOD-FormatDateTime", MESSAGES.FormatDateTimeMethods());
    map.put("METHOD-FormatTime", MESSAGES.FormatTimeMethods());
    map.put("METHOD-GetMillis", MESSAGES.GetMillisMethods());
    map.put("METHOD-Hour", MESSAGES.HourMethods());
    map.put("METHOD-MakeDate", MESSAGES.MakeDateMethods());
    map.put("METHOD-MakeInstant", MESSAGES.MakeInstantMethods());
    map.put("METHOD-MakeInstantFromMillis", MESSAGES.MakeInstantFromMillisMethods());
    map.put("METHOD-MakeInstantFromParts", MESSAGES.MakeInstantFromPartsMethods());
    map.put("METHOD-MakeTime", MESSAGES.MakeTimeMethods());
    map.put("METHOD-Minute", MESSAGES.MinuteMethods());
    map.put("METHOD-Month", MESSAGES.MonthMethods());
    map.put("METHOD-MonthName", MESSAGES.MonthNameMethods());
    map.put("METHOD-Now", MESSAGES.NowMethods());
    map.put("METHOD-Second", MESSAGES.SecondMethods());
    map.put("METHOD-SystemTime", MESSAGES.SystemTimeMethods());
    map.put("METHOD-Weekday", MESSAGES.WeekdayMethods());
    map.put("METHOD-WeekdayName", MESSAGES.WeekdayNameMethods());
    map.put("METHOD-Year", MESSAGES.YearMethods());


/* Parameters */

    map.put("PARAM-instant", MESSAGES.instantParams());
    map.put("PARAM-quantity", MESSAGES.quantityParams());
    map.put("PARAM-start", MESSAGES.startParams());
    map.put("PARAM-end", MESSAGES.endParams());
    map.put("PARAM-duration", MESSAGES.durationParams());
    map.put("PARAM-pattern", MESSAGES.patternParams());
    map.put("PARAM-year", MESSAGES.yearParams());
    map.put("PARAM-month", MESSAGES.monthParams());
    map.put("PARAM-day", MESSAGES.dayParams());
    map.put("PARAM-from", MESSAGES.fromParams());
    map.put("PARAM-millis", MESSAGES.millisParams());
    map.put("PARAM-hour", MESSAGES.hourParams());
    map.put("PARAM-minute", MESSAGES.minuteParams());
    map.put("PARAM-second", MESSAGES.secondParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: CloudDB */

    map.put("COMPONENT-CloudDB", MESSAGES.cloudDBComponentPallette());

    map.put("CloudDB-helpString", MESSAGES.CloudDBHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-DefaultRedisServer", MESSAGES.DefaultRedisServerProperties());
    map.put("PROPERTY-ProjectID", MESSAGES.ProjectIDProperties());
    map.put("PROPERTY-RedisPort", MESSAGES.RedisPortProperties());
    map.put("PROPERTY-RedisServer", MESSAGES.RedisServerProperties());
    map.put("PROPERTY-Token", MESSAGES.TokenProperties());
    map.put("PROPERTY-UseSSL", MESSAGES.UseSSLProperties());


/* Events */

    map.put("EVENT-CloudDBError", MESSAGES.CloudDBErrorEvents());
    map.put("EVENT-DataChanged", MESSAGES.DataChangedEvents());
    map.put("EVENT-FirstRemoved", MESSAGES.FirstRemovedEvents());
    map.put("EVENT-GotValue", MESSAGES.GotValueEvents());
    map.put("EVENT-TagList", MESSAGES.TagListEvents());


/* Methods */

    map.put("METHOD-AppendValueToList", MESSAGES.AppendValueToListMethods());
    map.put("METHOD-ClearTag", MESSAGES.ClearTagMethods());
    map.put("METHOD-CloudConnected", MESSAGES.CloudConnectedMethods());
    map.put("METHOD-GetTagList", MESSAGES.GetTagListMethods());
    map.put("METHOD-GetValue", MESSAGES.GetValueMethods());
    map.put("METHOD-RemoveFirstFromList", MESSAGES.RemoveFirstFromListMethods());
    map.put("METHOD-StoreValue", MESSAGES.StoreValueMethods());


/* Parameters */

    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-tag", MESSAGES.tagParams());
    map.put("PARAM-value", MESSAGES.valueParams());
    map.put("PARAM-itemToAdd", MESSAGES.itemToAddParams());
    map.put("PARAM-valueIfTagNotThere", MESSAGES.valueIfTagNotThereParams());
    map.put("PARAM-valueToStore", MESSAGES.valueToStoreParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: ContactPicker */

    map.put("COMPONENT-ContactPicker", MESSAGES.contactPickerComponentPallette());

    map.put("ContactPicker-helpString", MESSAGES.ContactPickerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-ContactName", MESSAGES.ContactNameProperties());
    map.put("PROPERTY-ContactUri", MESSAGES.ContactUriProperties());
    map.put("PROPERTY-EmailAddress", MESSAGES.EmailAddressProperties());
    map.put("PROPERTY-EmailAddressList", MESSAGES.EmailAddressListProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Image", MESSAGES.ImageProperties());
    map.put("PROPERTY-PhoneNumber", MESSAGES.PhoneNumberProperties());
    map.put("PROPERTY-PhoneNumberList", MESSAGES.PhoneNumberListProperties());
    map.put("PROPERTY-Picture", MESSAGES.PictureProperties());
    map.put("PROPERTY-Shape", MESSAGES.ShapeProperties());
    map.put("PROPERTY-ShowFeedback", MESSAGES.ShowFeedbackProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextAlignment", MESSAGES.TextAlignmentProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-AfterPicking", MESSAGES.AfterPickingEvents());
    map.put("EVENT-BeforePicking", MESSAGES.BeforePickingEvents());
    map.put("EVENT-GotFocus", MESSAGES.GotFocusEvents());
    map.put("EVENT-LostFocus", MESSAGES.LostFocusEvents());
    map.put("EVENT-TouchDown", MESSAGES.TouchDownEvents());
    map.put("EVENT-TouchUp", MESSAGES.TouchUpEvents());


/* Methods */

    map.put("METHOD-Open", MESSAGES.OpenMethods());
    map.put("METHOD-ViewContact", MESSAGES.ViewContactMethods());


/* Parameters */

    map.put("PARAM-uri", MESSAGES.uriParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: DatePicker */

    map.put("COMPONENT-DatePicker", MESSAGES.datePickerComponentPallette());

    map.put("DatePicker-helpString", MESSAGES.DatePickerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Day", MESSAGES.DayProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Image", MESSAGES.ImageProperties());
    map.put("PROPERTY-Instant", MESSAGES.InstantProperties());
    map.put("PROPERTY-Month", MESSAGES.MonthProperties());
    map.put("PROPERTY-MonthInText", MESSAGES.MonthInTextProperties());
    map.put("PROPERTY-Shape", MESSAGES.ShapeProperties());
    map.put("PROPERTY-ShowFeedback", MESSAGES.ShowFeedbackProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextAlignment", MESSAGES.TextAlignmentProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());
    map.put("PROPERTY-Year", MESSAGES.YearProperties());


/* Events */

    map.put("EVENT-AfterDateSet", MESSAGES.AfterDateSetEvents());
    map.put("EVENT-GotFocus", MESSAGES.GotFocusEvents());
    map.put("EVENT-LostFocus", MESSAGES.LostFocusEvents());
    map.put("EVENT-TouchDown", MESSAGES.TouchDownEvents());
    map.put("EVENT-TouchUp", MESSAGES.TouchUpEvents());


/* Methods */

    map.put("METHOD-LaunchPicker", MESSAGES.LaunchPickerMethods());
    map.put("METHOD-SetDateToDisplay", MESSAGES.SetDateToDisplayMethods());
    map.put("METHOD-SetDateToDisplayFromInstant", MESSAGES.SetDateToDisplayFromInstantMethods());


/* Parameters */

    map.put("PARAM-year", MESSAGES.yearParams());
    map.put("PARAM-month", MESSAGES.monthParams());
    map.put("PARAM-day", MESSAGES.dayParams());
    map.put("PARAM-instant", MESSAGES.instantParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: EmailPicker */

    map.put("COMPONENT-EmailPicker", MESSAGES.emailPickerComponentPallette());

    map.put("EmailPicker-helpString", MESSAGES.EmailPickerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Hint", MESSAGES.HintProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextAlignment", MESSAGES.TextAlignmentProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-GotFocus", MESSAGES.GotFocusEvents());
    map.put("EVENT-LostFocus", MESSAGES.LostFocusEvents());


/* Methods */

    map.put("METHOD-RequestFocus", MESSAGES.RequestFocusMethods());


/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Ev3ColorSensor */

    map.put("COMPONENT-Ev3ColorSensor", MESSAGES.ev3ColorSensorComponentPallette());

    map.put("Ev3ColorSensor-helpString", MESSAGES.Ev3ColorSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AboveRangeEventEnabled", MESSAGES.AboveRangeEventEnabledProperties());
    map.put("PROPERTY-BelowRangeEventEnabled", MESSAGES.BelowRangeEventEnabledProperties());
    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());
    map.put("PROPERTY-BottomOfRange", MESSAGES.BottomOfRangeProperties());
    map.put("PROPERTY-ColorChangedEventEnabled", MESSAGES.ColorChangedEventEnabledProperties());
    map.put("PROPERTY-Mode", MESSAGES.ModeProperties());
    map.put("PROPERTY-SensorPort", MESSAGES.SensorPortProperties());
    map.put("PROPERTY-TopOfRange", MESSAGES.TopOfRangeProperties());
    map.put("PROPERTY-WithinRangeEventEnabled", MESSAGES.WithinRangeEventEnabledProperties());


/* Events */

    map.put("EVENT-AboveRange", MESSAGES.AboveRangeEvents());
    map.put("EVENT-BelowRange", MESSAGES.BelowRangeEvents());
    map.put("EVENT-ColorChanged", MESSAGES.ColorChangedEvents());
    map.put("EVENT-WithinRange", MESSAGES.WithinRangeEvents());


/* Methods */

    map.put("METHOD-GetColorCode", MESSAGES.GetColorCodeMethods());
    map.put("METHOD-GetColorName", MESSAGES.GetColorNameMethods());
    map.put("METHOD-GetLightLevel", MESSAGES.GetLightLevelMethods());
    map.put("METHOD-SetAmbientMode", MESSAGES.SetAmbientModeMethods());
    map.put("METHOD-SetColorMode", MESSAGES.SetColorModeMethods());
    map.put("METHOD-SetReflectedMode", MESSAGES.SetReflectedModeMethods());


/* Parameters */

    map.put("PARAM-colorCode", MESSAGES.colorCodeParams());
    map.put("PARAM-colorName", MESSAGES.colorNameParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Ev3Commands */

    map.put("COMPONENT-Ev3Commands", MESSAGES.ev3CommandsComponentPallette());

    map.put("Ev3Commands-helpString", MESSAGES.Ev3CommandsHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());


/* Events */



/* Methods */

    map.put("METHOD-GetBatteryCurrent", MESSAGES.GetBatteryCurrentMethods());
    map.put("METHOD-GetBatteryVoltage", MESSAGES.GetBatteryVoltageMethods());
    map.put("METHOD-GetFirmwareBuild", MESSAGES.GetFirmwareBuildMethods());
    map.put("METHOD-GetFirmwareVersion", MESSAGES.GetFirmwareVersionMethods());
    map.put("METHOD-GetHardwareVersion", MESSAGES.GetHardwareVersionMethods());
    map.put("METHOD-GetOSBuild", MESSAGES.GetOSBuildMethods());
    map.put("METHOD-GetOSVersion", MESSAGES.GetOSVersionMethods());
    map.put("METHOD-KeepAlive", MESSAGES.KeepAliveMethods());


/* Parameters */

    map.put("PARAM-minutes", MESSAGES.minutesParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Ev3GyroSensor */

    map.put("COMPONENT-Ev3GyroSensor", MESSAGES.ev3GyroSensorComponentPallette());

    map.put("Ev3GyroSensor-helpString", MESSAGES.Ev3GyroSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());
    map.put("PROPERTY-Mode", MESSAGES.ModeProperties());
    map.put("PROPERTY-SensorPort", MESSAGES.SensorPortProperties());
    map.put("PROPERTY-SensorValueChangedEventEnabled", MESSAGES.SensorValueChangedEventEnabledProperties());


/* Events */

    map.put("EVENT-SensorValueChanged", MESSAGES.SensorValueChangedEvents());


/* Methods */

    map.put("METHOD-GetSensorValue", MESSAGES.GetSensorValueMethods());
    map.put("METHOD-SetAngleMode", MESSAGES.SetAngleModeMethods());
    map.put("METHOD-SetRateMode", MESSAGES.SetRateModeMethods());


/* Parameters */

    map.put("PARAM-sensorValue", MESSAGES.sensorValueParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Ev3Motors */

    map.put("COMPONENT-Ev3Motors", MESSAGES.ev3MotorsComponentPallette());

    map.put("Ev3Motors-helpString", MESSAGES.Ev3MotorsHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());
    map.put("PROPERTY-EnableSpeedRegulation", MESSAGES.EnableSpeedRegulationProperties());
    map.put("PROPERTY-MotorPorts", MESSAGES.MotorPortsProperties());
    map.put("PROPERTY-ReverseDirection", MESSAGES.ReverseDirectionProperties());
    map.put("PROPERTY-StopBeforeDisconnect", MESSAGES.StopBeforeDisconnectProperties());
    map.put("PROPERTY-TachoCountChangedEventEnabled", MESSAGES.TachoCountChangedEventEnabledProperties());
    map.put("PROPERTY-WheelDiameter", MESSAGES.WheelDiameterProperties());


/* Events */

    map.put("EVENT-TachoCountChanged", MESSAGES.TachoCountChangedEvents());


/* Methods */

    map.put("METHOD-GetTachoCount", MESSAGES.GetTachoCountMethods());
    map.put("METHOD-ResetTachoCount", MESSAGES.ResetTachoCountMethods());
    map.put("METHOD-RotateInDistance", MESSAGES.RotateInDistanceMethods());
    map.put("METHOD-RotateInDuration", MESSAGES.RotateInDurationMethods());
    map.put("METHOD-RotateInTachoCounts", MESSAGES.RotateInTachoCountsMethods());
    map.put("METHOD-RotateIndefinitely", MESSAGES.RotateIndefinitelyMethods());
    map.put("METHOD-RotateSyncInDistance", MESSAGES.RotateSyncInDistanceMethods());
    map.put("METHOD-RotateSyncInDuration", MESSAGES.RotateSyncInDurationMethods());
    map.put("METHOD-RotateSyncInTachoCounts", MESSAGES.RotateSyncInTachoCountsMethods());
    map.put("METHOD-RotateSyncIndefinitely", MESSAGES.RotateSyncIndefinitelyMethods());
    map.put("METHOD-Stop", MESSAGES.StopMethods());
    map.put("METHOD-ToggleDirection", MESSAGES.ToggleDirectionMethods());


/* Parameters */

    map.put("PARAM-tachoCount", MESSAGES.tachoCountParams());
    map.put("PARAM-power", MESSAGES.powerParams());
    map.put("PARAM-distance", MESSAGES.distanceParams());
    map.put("PARAM-useBrake", MESSAGES.useBrakeParams());
    map.put("PARAM-milliseconds", MESSAGES.millisecondsParams());
    map.put("PARAM-tachoCounts", MESSAGES.tachoCountsParams());
    map.put("PARAM-turnRatio", MESSAGES.turnRatioParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Ev3Sound */

    map.put("COMPONENT-Ev3Sound", MESSAGES.ev3SoundComponentPallette());

    map.put("Ev3Sound-helpString", MESSAGES.Ev3SoundHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());


/* Events */



/* Methods */

    map.put("METHOD-PlayTone", MESSAGES.PlayToneMethods());
    map.put("METHOD-StopSound", MESSAGES.StopSoundMethods());


/* Parameters */

    map.put("PARAM-volume", MESSAGES.volumeParams());
    map.put("PARAM-frequency", MESSAGES.frequencyParams());
    map.put("PARAM-milliseconds", MESSAGES.millisecondsParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Ev3TouchSensor */

    map.put("COMPONENT-Ev3TouchSensor", MESSAGES.ev3TouchSensorComponentPallette());

    map.put("Ev3TouchSensor-helpString", MESSAGES.Ev3TouchSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());
    map.put("PROPERTY-PressedEventEnabled", MESSAGES.PressedEventEnabledProperties());
    map.put("PROPERTY-ReleasedEventEnabled", MESSAGES.ReleasedEventEnabledProperties());
    map.put("PROPERTY-SensorPort", MESSAGES.SensorPortProperties());


/* Events */

    map.put("EVENT-Pressed", MESSAGES.PressedEvents());
    map.put("EVENT-Released", MESSAGES.ReleasedEvents());


/* Methods */

    map.put("METHOD-IsPressed", MESSAGES.IsPressedMethods());


/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Ev3UI */

    map.put("COMPONENT-Ev3UI", MESSAGES.ev3UIComponentPallette());

    map.put("Ev3UI-helpString", MESSAGES.Ev3UIHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());


/* Events */



/* Methods */

    map.put("METHOD-DrawCircle", MESSAGES.DrawCircleMethods());
    map.put("METHOD-DrawIcon", MESSAGES.DrawIconMethods());
    map.put("METHOD-DrawLine", MESSAGES.DrawLineMethods());
    map.put("METHOD-DrawPoint", MESSAGES.DrawPointMethods());
    map.put("METHOD-DrawRect", MESSAGES.DrawRectMethods());
    map.put("METHOD-FillScreen", MESSAGES.FillScreenMethods());


/* Parameters */

    map.put("PARAM-color", MESSAGES.colorParams());
    map.put("PARAM-x", MESSAGES.xParams());
    map.put("PARAM-y", MESSAGES.yParams());
    map.put("PARAM-radius", MESSAGES.radiusParams());
    map.put("PARAM-fill", MESSAGES.fillParams());
    map.put("PARAM-type", MESSAGES.typeParams());
    map.put("PARAM-no", MESSAGES.noParams());
    map.put("PARAM-x1", MESSAGES.x1Params());
    map.put("PARAM-y1", MESSAGES.y1Params());
    map.put("PARAM-x2", MESSAGES.x2Params());
    map.put("PARAM-y2", MESSAGES.y2Params());
    map.put("PARAM-width", MESSAGES.widthParams());
    map.put("PARAM-height", MESSAGES.heightParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Ev3UltrasonicSensor */

    map.put("COMPONENT-Ev3UltrasonicSensor", MESSAGES.ev3UltrasonicSensorComponentPallette());

    map.put("Ev3UltrasonicSensor-helpString", MESSAGES.Ev3UltrasonicSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AboveRangeEventEnabled", MESSAGES.AboveRangeEventEnabledProperties());
    map.put("PROPERTY-BelowRangeEventEnabled", MESSAGES.BelowRangeEventEnabledProperties());
    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());
    map.put("PROPERTY-BottomOfRange", MESSAGES.BottomOfRangeProperties());
    map.put("PROPERTY-SensorPort", MESSAGES.SensorPortProperties());
    map.put("PROPERTY-TopOfRange", MESSAGES.TopOfRangeProperties());
    map.put("PROPERTY-Unit", MESSAGES.UnitProperties());
    map.put("PROPERTY-WithinRangeEventEnabled", MESSAGES.WithinRangeEventEnabledProperties());


/* Events */

    map.put("EVENT-AboveRange", MESSAGES.AboveRangeEvents());
    map.put("EVENT-BelowRange", MESSAGES.BelowRangeEvents());
    map.put("EVENT-WithinRange", MESSAGES.WithinRangeEvents());


/* Methods */

    map.put("METHOD-GetDistance", MESSAGES.GetDistanceMethods());
    map.put("METHOD-SetCmUnit", MESSAGES.SetCmUnitMethods());
    map.put("METHOD-SetInchUnit", MESSAGES.SetInchUnitMethods());


/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: FeatureCollection */

    map.put("COMPONENT-FeatureCollection", MESSAGES.featureCollectionComponentPallette());

    map.put("FeatureCollection-helpString", MESSAGES.FeatureCollectionHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Features", MESSAGES.FeaturesProperties());
    map.put("PROPERTY-FeaturesFromGeoJSON", MESSAGES.FeaturesFromGeoJSONProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Source", MESSAGES.SourceProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-FeatureClick", MESSAGES.FeatureClickEvents());
    map.put("EVENT-FeatureDrag", MESSAGES.FeatureDragEvents());
    map.put("EVENT-FeatureLongClick", MESSAGES.FeatureLongClickEvents());
    map.put("EVENT-FeatureStartDrag", MESSAGES.FeatureStartDragEvents());
    map.put("EVENT-FeatureStopDrag", MESSAGES.FeatureStopDragEvents());
    map.put("EVENT-GotFeatures", MESSAGES.GotFeaturesEvents());
    map.put("EVENT-LoadError", MESSAGES.LoadErrorEvents());


/* Methods */

    map.put("METHOD-FeatureFromDescription", MESSAGES.FeatureFromDescriptionMethods());
    map.put("METHOD-LoadFromURL", MESSAGES.LoadFromURLMethods());


/* Parameters */

    map.put("PARAM-feature", MESSAGES.featureParams());
    map.put("PARAM-url", MESSAGES.urlParams());
    map.put("PARAM-features", MESSAGES.featuresParams());
    map.put("PARAM-responseCode", MESSAGES.responseCodeParams());
    map.put("PARAM-errorMessage", MESSAGES.errorMessageParams());
    map.put("PARAM-description", MESSAGES.descriptionParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: File */

    map.put("COMPONENT-File", MESSAGES.fileComponentPallette());

    map.put("File-helpString", MESSAGES.FileHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-LegacyMode", MESSAGES.LegacyModeProperties());


/* Events */

    map.put("EVENT-AfterFileSaved", MESSAGES.AfterFileSavedEvents());
    map.put("EVENT-GotText", MESSAGES.GotTextEvents());


/* Methods */

    map.put("METHOD-AppendToFile", MESSAGES.AppendToFileMethods());
    map.put("METHOD-Delete", MESSAGES.DeleteMethods());
    map.put("METHOD-ReadFrom", MESSAGES.ReadFromMethods());
    map.put("METHOD-SaveFile", MESSAGES.SaveFileMethods());


/* Parameters */

    map.put("PARAM-fileName", MESSAGES.fileNameParams());
    map.put("PARAM-text", MESSAGES.textParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: FirebaseDB */

    map.put("COMPONENT-FirebaseDB", MESSAGES.firebaseDBComponentPallette());

    map.put("FirebaseDB-helpString", MESSAGES.FirebaseDBHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-DefaultURL", MESSAGES.DefaultURLProperties());
    map.put("PROPERTY-DeveloperBucket", MESSAGES.DeveloperBucketProperties());
    map.put("PROPERTY-FirebaseToken", MESSAGES.FirebaseTokenProperties());
    map.put("PROPERTY-FirebaseURL", MESSAGES.FirebaseURLProperties());
    map.put("PROPERTY-Persist", MESSAGES.PersistProperties());
    map.put("PROPERTY-ProjectBucket", MESSAGES.ProjectBucketProperties());


/* Events */

    map.put("EVENT-DataChanged", MESSAGES.DataChangedEvents());
    map.put("EVENT-FirebaseError", MESSAGES.FirebaseErrorEvents());
    map.put("EVENT-FirstRemoved", MESSAGES.FirstRemovedEvents());
    map.put("EVENT-GotValue", MESSAGES.GotValueEvents());
    map.put("EVENT-TagList", MESSAGES.TagListEvents());


/* Methods */

    map.put("METHOD-AppendValue", MESSAGES.AppendValueMethods());
    map.put("METHOD-ClearTag", MESSAGES.ClearTagMethods());
    map.put("METHOD-GetTagList", MESSAGES.GetTagListMethods());
    map.put("METHOD-GetValue", MESSAGES.GetValueMethods());
    map.put("METHOD-RemoveFirst", MESSAGES.RemoveFirstMethods());
    map.put("METHOD-StoreValue", MESSAGES.StoreValueMethods());
    map.put("METHOD-Unauthenticate", MESSAGES.UnauthenticateMethods());


/* Parameters */

    map.put("PARAM-tag", MESSAGES.tagParams());
    map.put("PARAM-value", MESSAGES.valueParams());
    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-valueToAdd", MESSAGES.valueToAddParams());
    map.put("PARAM-valueIfTagNotThere", MESSAGES.valueIfTagNotThereParams());
    map.put("PARAM-valueToStore", MESSAGES.valueToStoreParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Screen */

    map.put("COMPONENT-Screen", MESSAGES.screenComponentPallette());

    map.put("Screen-helpString", MESSAGES.ScreenHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AboutScreen", MESSAGES.AboutScreenProperties());
    map.put("PROPERTY-AccentColor", MESSAGES.AccentColorProperties());
    map.put("PROPERTY-ActionBar", MESSAGES.ActionBarProperties());
    map.put("PROPERTY-AlignHorizontal", MESSAGES.AlignHorizontalProperties());
    map.put("PROPERTY-AlignVertical", MESSAGES.AlignVerticalProperties());
    map.put("PROPERTY-AppName", MESSAGES.AppNameProperties());
    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-BackgroundImage", MESSAGES.BackgroundImageProperties());
    map.put("PROPERTY-BlocksToolkit", MESSAGES.BlocksToolkitProperties());
    map.put("PROPERTY-CloseScreenAnimation", MESSAGES.CloseScreenAnimationProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-Icon", MESSAGES.IconProperties());
    map.put("PROPERTY-OpenScreenAnimation", MESSAGES.OpenScreenAnimationProperties());
    map.put("PROPERTY-Platform", MESSAGES.PlatformProperties());
    map.put("PROPERTY-PlatformVersion", MESSAGES.PlatformVersionProperties());
    map.put("PROPERTY-PrimaryColor", MESSAGES.PrimaryColorProperties());
    map.put("PROPERTY-PrimaryColorDark", MESSAGES.PrimaryColorDarkProperties());
    map.put("PROPERTY-ScreenOrientation", MESSAGES.ScreenOrientationProperties());
    map.put("PROPERTY-Scrollable", MESSAGES.ScrollableProperties());
    map.put("PROPERTY-ShowListsAsJson", MESSAGES.ShowListsAsJsonProperties());
    map.put("PROPERTY-ShowStatusBar", MESSAGES.ShowStatusBarProperties());
    map.put("PROPERTY-Sizing", MESSAGES.SizingProperties());
    map.put("PROPERTY-Theme", MESSAGES.ThemeProperties());
    map.put("PROPERTY-Title", MESSAGES.TitleProperties());
    map.put("PROPERTY-TitleVisible", MESSAGES.TitleVisibleProperties());
    map.put("PROPERTY-TutorialURL", MESSAGES.TutorialURLProperties());
    map.put("PROPERTY-VersionCode", MESSAGES.VersionCodeProperties());
    map.put("PROPERTY-VersionName", MESSAGES.VersionNameProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());


/* Events */

    map.put("EVENT-BackPressed", MESSAGES.BackPressedEvents());
    map.put("EVENT-ErrorOccurred", MESSAGES.ErrorOccurredEvents());
    map.put("EVENT-Initialize", MESSAGES.InitializeEvents());
    map.put("EVENT-OtherScreenClosed", MESSAGES.OtherScreenClosedEvents());
    map.put("EVENT-PermissionDenied", MESSAGES.PermissionDeniedEvents());
    map.put("EVENT-PermissionGranted", MESSAGES.PermissionGrantedEvents());
    map.put("EVENT-ScreenOrientationChanged", MESSAGES.ScreenOrientationChangedEvents());


/* Methods */

    map.put("METHOD-AskForPermission", MESSAGES.AskForPermissionMethods());
    map.put("METHOD-HideKeyboard", MESSAGES.HideKeyboardMethods());


/* Parameters */

    map.put("PARAM-component", MESSAGES.componentParams());
    map.put("PARAM-functionName", MESSAGES.functionNameParams());
    map.put("PARAM-errorNumber", MESSAGES.errorNumberParams());
    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-otherScreenName", MESSAGES.otherScreenNameParams());
    map.put("PARAM-result", MESSAGES.resultParams());
    map.put("PARAM-permissionName", MESSAGES.permissionNameParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: FusiontablesControl */

    map.put("COMPONENT-FusiontablesControl", MESSAGES.fusiontablesControlComponentPallette());

    map.put("FusiontablesControl-helpString", MESSAGES.FusiontablesControlHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-ApiKey", MESSAGES.ApiKeyProperties());
    map.put("PROPERTY-KeyFile", MESSAGES.KeyFileProperties());
    map.put("PROPERTY-LoadingDialogMessage", MESSAGES.LoadingDialogMessageProperties());
    map.put("PROPERTY-Query", MESSAGES.QueryProperties());
    map.put("PROPERTY-ServiceAccountEmail", MESSAGES.ServiceAccountEmailProperties());
    map.put("PROPERTY-ShowLoadingDialog", MESSAGES.ShowLoadingDialogProperties());
    map.put("PROPERTY-UseServiceAuthentication", MESSAGES.UseServiceAuthenticationProperties());


/* Events */

    map.put("EVENT-GotResult", MESSAGES.GotResultEvents());


/* Methods */

    map.put("METHOD-DoQuery", MESSAGES.DoQueryMethods());
    map.put("METHOD-ForgetLogin", MESSAGES.ForgetLoginMethods());
    map.put("METHOD-GetRows", MESSAGES.GetRowsMethods());
    map.put("METHOD-GetRowsWithConditions", MESSAGES.GetRowsWithConditionsMethods());
    map.put("METHOD-InsertRow", MESSAGES.InsertRowMethods());
    map.put("METHOD-SendQuery", MESSAGES.SendQueryMethods());


/* Parameters */

    map.put("PARAM-result", MESSAGES.resultParams());
    map.put("PARAM-tableId", MESSAGES.tableIdParams());
    map.put("PARAM-columns", MESSAGES.columnsParams());
    map.put("PARAM-conditions", MESSAGES.conditionsParams());
    map.put("PARAM-values", MESSAGES.valuesParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: GameClient */

    map.put("COMPONENT-GameClient", MESSAGES.gameClientComponentPallette());

    map.put("GameClient-helpString", MESSAGES.GameClientHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-GameId", MESSAGES.GameIdProperties());
    map.put("PROPERTY-InstanceId", MESSAGES.InstanceIdProperties());
    map.put("PROPERTY-InvitedInstances", MESSAGES.InvitedInstancesProperties());
    map.put("PROPERTY-JoinedInstances", MESSAGES.JoinedInstancesProperties());
    map.put("PROPERTY-Leader", MESSAGES.LeaderProperties());
    map.put("PROPERTY-Players", MESSAGES.PlayersProperties());
    map.put("PROPERTY-PublicInstances", MESSAGES.PublicInstancesProperties());
    map.put("PROPERTY-ServiceURL", MESSAGES.ServiceURLProperties());
    map.put("PROPERTY-ServiceUrl", MESSAGES.ServiceUrlProperties());
    map.put("PROPERTY-UserEmailAddress", MESSAGES.UserEmailAddressProperties());


/* Events */

    map.put("EVENT-FunctionCompleted", MESSAGES.FunctionCompletedEvents());
    map.put("EVENT-GotMessage", MESSAGES.GotMessageEvents());
    map.put("EVENT-Info", MESSAGES.InfoEvents());
    map.put("EVENT-InstanceIdChanged", MESSAGES.InstanceIdChangedEvents());
    map.put("EVENT-Invited", MESSAGES.InvitedEvents());
    map.put("EVENT-NewInstanceMade", MESSAGES.NewInstanceMadeEvents());
    map.put("EVENT-NewLeader", MESSAGES.NewLeaderEvents());
    map.put("EVENT-PlayerJoined", MESSAGES.PlayerJoinedEvents());
    map.put("EVENT-PlayerLeft", MESSAGES.PlayerLeftEvents());
    map.put("EVENT-ServerCommandFailure", MESSAGES.ServerCommandFailureEvents());
    map.put("EVENT-ServerCommandSuccess", MESSAGES.ServerCommandSuccessEvents());
    map.put("EVENT-UserEmailAddressSet", MESSAGES.UserEmailAddressSetEvents());
    map.put("EVENT-WebServiceError", MESSAGES.WebServiceErrorEvents());


/* Methods */

    map.put("METHOD-GetInstanceLists", MESSAGES.GetInstanceListsMethods());
    map.put("METHOD-GetMessages", MESSAGES.GetMessagesMethods());
    map.put("METHOD-Invite", MESSAGES.InviteMethods());
    map.put("METHOD-LeaveInstance", MESSAGES.LeaveInstanceMethods());
    map.put("METHOD-MakeNewInstance", MESSAGES.MakeNewInstanceMethods());
    map.put("METHOD-SendMessage", MESSAGES.SendMessageMethods());
    map.put("METHOD-ServerCommand", MESSAGES.ServerCommandMethods());
    map.put("METHOD-SetInstance", MESSAGES.SetInstanceMethods());
    map.put("METHOD-SetLeader", MESSAGES.SetLeaderMethods());


/* Parameters */

    map.put("PARAM-functionName", MESSAGES.functionNameParams());
    map.put("PARAM-type", MESSAGES.typeParams());
    map.put("PARAM-sender", MESSAGES.senderParams());
    map.put("PARAM-contents", MESSAGES.contentsParams());
    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-instanceId", MESSAGES.instanceIdParams());
    map.put("PARAM-playerId", MESSAGES.playerIdParams());
    map.put("PARAM-command", MESSAGES.commandParams());
    map.put("PARAM-arguments", MESSAGES.argumentsParams());
    map.put("PARAM-response", MESSAGES.responseParams());
    map.put("PARAM-emailAddress", MESSAGES.emailAddressParams());
    map.put("PARAM-count", MESSAGES.countParams());
    map.put("PARAM-playerEmail", MESSAGES.playerEmailParams());
    map.put("PARAM-makePublic", MESSAGES.makePublicParams());
    map.put("PARAM-recipients", MESSAGES.recipientsParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: GyroscopeSensor */

    map.put("COMPONENT-GyroscopeSensor", MESSAGES.gyroscopeSensorComponentPallette());

    map.put("GyroscopeSensor-helpString", MESSAGES.GyroscopeSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Available", MESSAGES.AvailableProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-XAngularVelocity", MESSAGES.XAngularVelocityProperties());
    map.put("PROPERTY-YAngularVelocity", MESSAGES.YAngularVelocityProperties());
    map.put("PROPERTY-ZAngularVelocity", MESSAGES.ZAngularVelocityProperties());


/* Events */

    map.put("EVENT-GyroscopeChanged", MESSAGES.GyroscopeChangedEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-xAngularVelocity", MESSAGES.xAngularVelocityParams());
    map.put("PARAM-yAngularVelocity", MESSAGES.yAngularVelocityParams());
    map.put("PARAM-zAngularVelocity", MESSAGES.zAngularVelocityParams());
    map.put("PARAM-timestamp", MESSAGES.timestampParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: HorizontalArrangement */

    map.put("COMPONENT-HorizontalArrangement", MESSAGES.horizontalArrangementComponentPallette());

    map.put("HorizontalArrangement-helpString", MESSAGES.HorizontalArrangementHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AlignHorizontal", MESSAGES.AlignHorizontalProperties());
    map.put("PROPERTY-AlignVertical", MESSAGES.AlignVerticalProperties());
    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Image", MESSAGES.ImageProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */



/* Methods */



/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: HorizontalScrollArrangement */

    map.put("COMPONENT-HorizontalScrollArrangement", MESSAGES.horizontalScrollArrangementComponentPallette());

    map.put("HorizontalScrollArrangement-helpString", MESSAGES.HorizontalScrollArrangementHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AlignHorizontal", MESSAGES.AlignHorizontalProperties());
    map.put("PROPERTY-AlignVertical", MESSAGES.AlignVerticalProperties());
    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Image", MESSAGES.ImageProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */



/* Methods */



/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Hygrometer */

    map.put("COMPONENT-Hygrometer", MESSAGES.hygrometerComponentPallette());

    map.put("Hygrometer-helpString", MESSAGES.HygrometerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Available", MESSAGES.AvailableProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-Humidity", MESSAGES.HumidityProperties());
    map.put("PROPERTY-RefreshTime", MESSAGES.RefreshTimeProperties());


/* Events */

    map.put("EVENT-HumidityChanged", MESSAGES.HumidityChangedEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-humidity", MESSAGES.humidityParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Image */

    map.put("COMPONENT-Image", MESSAGES.imageComponentPallette());

    map.put("Image-helpString", MESSAGES.ImageHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Animation", MESSAGES.AnimationProperties());
    map.put("PROPERTY-Clickable", MESSAGES.ClickableProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Picture", MESSAGES.PictureProperties());
    map.put("PROPERTY-RotationAngle", MESSAGES.RotationAngleProperties());
    map.put("PROPERTY-ScalePictureToFit", MESSAGES.ScalePictureToFitProperties());
    map.put("PROPERTY-Scaling", MESSAGES.ScalingProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-Click", MESSAGES.ClickEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: ImagePicker */

    map.put("COMPONENT-ImagePicker", MESSAGES.imagePickerComponentPallette());

    map.put("ImagePicker-helpString", MESSAGES.ImagePickerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Image", MESSAGES.ImageProperties());
    map.put("PROPERTY-Selection", MESSAGES.SelectionProperties());
    map.put("PROPERTY-Shape", MESSAGES.ShapeProperties());
    map.put("PROPERTY-ShowFeedback", MESSAGES.ShowFeedbackProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextAlignment", MESSAGES.TextAlignmentProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-AfterPicking", MESSAGES.AfterPickingEvents());
    map.put("EVENT-BeforePicking", MESSAGES.BeforePickingEvents());
    map.put("EVENT-GotFocus", MESSAGES.GotFocusEvents());
    map.put("EVENT-LostFocus", MESSAGES.LostFocusEvents());
    map.put("EVENT-TouchDown", MESSAGES.TouchDownEvents());
    map.put("EVENT-TouchUp", MESSAGES.TouchUpEvents());


/* Methods */

    map.put("METHOD-Open", MESSAGES.OpenMethods());


/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: ImageSprite */

    map.put("COMPONENT-ImageSprite", MESSAGES.imageSpriteComponentPallette());

    map.put("ImageSprite-helpString", MESSAGES.ImageSpriteHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-Heading", MESSAGES.HeadingProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-Interval", MESSAGES.IntervalProperties());
    map.put("PROPERTY-Picture", MESSAGES.PictureProperties());
    map.put("PROPERTY-Rotates", MESSAGES.RotatesProperties());
    map.put("PROPERTY-Speed", MESSAGES.SpeedProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-X", MESSAGES.XProperties());
    map.put("PROPERTY-Y", MESSAGES.YProperties());
    map.put("PROPERTY-Z", MESSAGES.ZProperties());


/* Events */

    map.put("EVENT-CollidedWith", MESSAGES.CollidedWithEvents());
    map.put("EVENT-Dragged", MESSAGES.DraggedEvents());
    map.put("EVENT-EdgeReached", MESSAGES.EdgeReachedEvents());
    map.put("EVENT-Flung", MESSAGES.FlungEvents());
    map.put("EVENT-NoLongerCollidingWith", MESSAGES.NoLongerCollidingWithEvents());
    map.put("EVENT-TouchDown", MESSAGES.TouchDownEvents());
    map.put("EVENT-TouchUp", MESSAGES.TouchUpEvents());
    map.put("EVENT-Touched", MESSAGES.TouchedEvents());


/* Methods */

    map.put("METHOD-Bounce", MESSAGES.BounceMethods());
    map.put("METHOD-CollidingWith", MESSAGES.CollidingWithMethods());
    map.put("METHOD-MoveIntoBounds", MESSAGES.MoveIntoBoundsMethods());
    map.put("METHOD-MoveTo", MESSAGES.MoveToMethods());
    map.put("METHOD-PointInDirection", MESSAGES.PointInDirectionMethods());
    map.put("METHOD-PointTowards", MESSAGES.PointTowardsMethods());


/* Parameters */

    map.put("PARAM-other", MESSAGES.otherParams());
    map.put("PARAM-startX", MESSAGES.startXParams());
    map.put("PARAM-startY", MESSAGES.startYParams());
    map.put("PARAM-prevX", MESSAGES.prevXParams());
    map.put("PARAM-prevY", MESSAGES.prevYParams());
    map.put("PARAM-currentX", MESSAGES.currentXParams());
    map.put("PARAM-currentY", MESSAGES.currentYParams());
    map.put("PARAM-edge", MESSAGES.edgeParams());
    map.put("PARAM-x", MESSAGES.xParams());
    map.put("PARAM-y", MESSAGES.yParams());
    map.put("PARAM-speed", MESSAGES.speedParams());
    map.put("PARAM-heading", MESSAGES.headingParams());
    map.put("PARAM-xvel", MESSAGES.xvelParams());
    map.put("PARAM-yvel", MESSAGES.yvelParams());
    map.put("PARAM-target", MESSAGES.targetParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Label */

    map.put("COMPONENT-Label", MESSAGES.labelComponentPallette());

    map.put("Label-helpString", MESSAGES.LabelHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-HTMLContent", MESSAGES.HTMLContentProperties());
    map.put("PROPERTY-HTMLFormat", MESSAGES.HTMLFormatProperties());
    map.put("PROPERTY-HasMargins", MESSAGES.HasMarginsProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextAlignment", MESSAGES.TextAlignmentProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */



/* Methods */



/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: LightSensor */

    map.put("COMPONENT-LightSensor", MESSAGES.lightSensorComponentPallette());

    map.put("LightSensor-helpString", MESSAGES.LightSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Available", MESSAGES.AvailableProperties());
    map.put("PROPERTY-AverageLux", MESSAGES.AverageLuxProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-Lux", MESSAGES.LuxProperties());
    map.put("PROPERTY-RefreshTime", MESSAGES.RefreshTimeProperties());


/* Events */

    map.put("EVENT-LightChanged", MESSAGES.LightChangedEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-lux", MESSAGES.luxParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: LineString */

    map.put("COMPONENT-LineString", MESSAGES.lineStringComponentPallette());

    map.put("LineString-helpString", MESSAGES.LineStringHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Description", MESSAGES.DescriptionProperties());
    map.put("PROPERTY-Draggable", MESSAGES.DraggableProperties());
    map.put("PROPERTY-EnableInfobox", MESSAGES.EnableInfoboxProperties());
    map.put("PROPERTY-Points", MESSAGES.PointsProperties());
    map.put("PROPERTY-PointsFromString", MESSAGES.PointsFromStringProperties());
    map.put("PROPERTY-StrokeColor", MESSAGES.StrokeColorProperties());
    map.put("PROPERTY-StrokeOpacity", MESSAGES.StrokeOpacityProperties());
    map.put("PROPERTY-StrokeWidth", MESSAGES.StrokeWidthProperties());
    map.put("PROPERTY-Title", MESSAGES.TitleProperties());
    map.put("PROPERTY-Type", MESSAGES.TypeProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());


/* Events */

    map.put("EVENT-Click", MESSAGES.ClickEvents());
    map.put("EVENT-Drag", MESSAGES.DragEvents());
    map.put("EVENT-LongClick", MESSAGES.LongClickEvents());
    map.put("EVENT-StartDrag", MESSAGES.StartDragEvents());
    map.put("EVENT-StopDrag", MESSAGES.StopDragEvents());


/* Methods */

    map.put("METHOD-DistanceToFeature", MESSAGES.DistanceToFeatureMethods());
    map.put("METHOD-DistanceToPoint", MESSAGES.DistanceToPointMethods());
    map.put("METHOD-HideInfobox", MESSAGES.HideInfoboxMethods());
    map.put("METHOD-ShowInfobox", MESSAGES.ShowInfoboxMethods());


/* Parameters */

    map.put("PARAM-mapFeature", MESSAGES.mapFeatureParams());
    map.put("PARAM-centroids", MESSAGES.centroidsParams());
    map.put("PARAM-latitude", MESSAGES.latitudeParams());
    map.put("PARAM-longitude", MESSAGES.longitudeParams());
    map.put("PARAM-centroid", MESSAGES.centroidParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: ListPicker */

    map.put("COMPONENT-ListPicker", MESSAGES.listPickerComponentPallette());

    map.put("ListPicker-helpString", MESSAGES.ListPickerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Elements", MESSAGES.ElementsProperties());
    map.put("PROPERTY-ElementsFromString", MESSAGES.ElementsFromStringProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Image", MESSAGES.ImageProperties());
    map.put("PROPERTY-ItemBackgroundColor", MESSAGES.ItemBackgroundColorProperties());
    map.put("PROPERTY-ItemTextColor", MESSAGES.ItemTextColorProperties());
    map.put("PROPERTY-Selection", MESSAGES.SelectionProperties());
    map.put("PROPERTY-SelectionIndex", MESSAGES.SelectionIndexProperties());
    map.put("PROPERTY-Shape", MESSAGES.ShapeProperties());
    map.put("PROPERTY-ShowFeedback", MESSAGES.ShowFeedbackProperties());
    map.put("PROPERTY-ShowFilterBar", MESSAGES.ShowFilterBarProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextAlignment", MESSAGES.TextAlignmentProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-Title", MESSAGES.TitleProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-AfterPicking", MESSAGES.AfterPickingEvents());
    map.put("EVENT-BeforePicking", MESSAGES.BeforePickingEvents());
    map.put("EVENT-GotFocus", MESSAGES.GotFocusEvents());
    map.put("EVENT-LostFocus", MESSAGES.LostFocusEvents());
    map.put("EVENT-TouchDown", MESSAGES.TouchDownEvents());
    map.put("EVENT-TouchUp", MESSAGES.TouchUpEvents());


/* Methods */

    map.put("METHOD-Open", MESSAGES.OpenMethods());


/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: ListView */

    map.put("COMPONENT-ListView", MESSAGES.listViewComponentPallette());

    map.put("ListView-helpString", MESSAGES.ListViewHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Elements", MESSAGES.ElementsProperties());
    map.put("PROPERTY-ElementsFromString", MESSAGES.ElementsFromStringProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Selection", MESSAGES.SelectionProperties());
    map.put("PROPERTY-SelectionColor", MESSAGES.SelectionColorProperties());
    map.put("PROPERTY-SelectionIndex", MESSAGES.SelectionIndexProperties());
    map.put("PROPERTY-ShowFilterBar", MESSAGES.ShowFilterBarProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-TextSize", MESSAGES.TextSizeProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-AfterPicking", MESSAGES.AfterPickingEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: LocationSensor */

    map.put("COMPONENT-LocationSensor", MESSAGES.locationSensorComponentPallette());

    map.put("LocationSensor-helpString", MESSAGES.LocationSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Accuracy", MESSAGES.AccuracyProperties());
    map.put("PROPERTY-Altitude", MESSAGES.AltitudeProperties());
    map.put("PROPERTY-AvailableProviders", MESSAGES.AvailableProvidersProperties());
    map.put("PROPERTY-CurrentAddress", MESSAGES.CurrentAddressProperties());
    map.put("PROPERTY-DistanceInterval", MESSAGES.DistanceIntervalProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-HasAccuracy", MESSAGES.HasAccuracyProperties());
    map.put("PROPERTY-HasAltitude", MESSAGES.HasAltitudeProperties());
    map.put("PROPERTY-HasLongitudeLatitude", MESSAGES.HasLongitudeLatitudeProperties());
    map.put("PROPERTY-Latitude", MESSAGES.LatitudeProperties());
    map.put("PROPERTY-Longitude", MESSAGES.LongitudeProperties());
    map.put("PROPERTY-ProviderLocked", MESSAGES.ProviderLockedProperties());
    map.put("PROPERTY-ProviderName", MESSAGES.ProviderNameProperties());
    map.put("PROPERTY-TimeInterval", MESSAGES.TimeIntervalProperties());


/* Events */

    map.put("EVENT-LocationChanged", MESSAGES.LocationChangedEvents());
    map.put("EVENT-StatusChanged", MESSAGES.StatusChangedEvents());


/* Methods */

    map.put("METHOD-LatitudeFromAddress", MESSAGES.LatitudeFromAddressMethods());
    map.put("METHOD-LongitudeFromAddress", MESSAGES.LongitudeFromAddressMethods());


/* Parameters */

    map.put("PARAM-latitude", MESSAGES.latitudeParams());
    map.put("PARAM-longitude", MESSAGES.longitudeParams());
    map.put("PARAM-altitude", MESSAGES.altitudeParams());
    map.put("PARAM-speed", MESSAGES.speedParams());
    map.put("PARAM-provider", MESSAGES.providerParams());
    map.put("PARAM-status", MESSAGES.statusParams());
    map.put("PARAM-locationName", MESSAGES.locationNameParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: MagneticFieldSensor */

    map.put("COMPONENT-MagneticFieldSensor", MESSAGES.magneticFieldSensorComponentPallette());

    map.put("MagneticFieldSensor-helpString", MESSAGES.MagneticFieldSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AbsoluteStrength", MESSAGES.AbsoluteStrengthProperties());
    map.put("PROPERTY-Available", MESSAGES.AvailableProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-MaximumRange", MESSAGES.MaximumRangeProperties());
    map.put("PROPERTY-XStrength", MESSAGES.XStrengthProperties());
    map.put("PROPERTY-YStrength", MESSAGES.YStrengthProperties());
    map.put("PROPERTY-ZStrength", MESSAGES.ZStrengthProperties());


/* Events */

    map.put("EVENT-MagneticChanged", MESSAGES.MagneticChangedEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-xStrength", MESSAGES.xStrengthParams());
    map.put("PARAM-yStrength", MESSAGES.yStrengthParams());
    map.put("PARAM-zStrength", MESSAGES.zStrengthParams());
    map.put("PARAM-absoluteStrength", MESSAGES.absoluteStrengthParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Map */

    map.put("COMPONENT-Map", MESSAGES.mapComponentPallette());

    map.put("Map-helpString", MESSAGES.MapHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BoundingBox", MESSAGES.BoundingBoxProperties());
    map.put("PROPERTY-CenterFromString", MESSAGES.CenterFromStringProperties());
    map.put("PROPERTY-EnablePan", MESSAGES.EnablePanProperties());
    map.put("PROPERTY-EnableRotation", MESSAGES.EnableRotationProperties());
    map.put("PROPERTY-EnableZoom", MESSAGES.EnableZoomProperties());
    map.put("PROPERTY-Features", MESSAGES.FeaturesProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Latitude", MESSAGES.LatitudeProperties());
    map.put("PROPERTY-LocationSensor", MESSAGES.LocationSensorProperties());
    map.put("PROPERTY-Longitude", MESSAGES.LongitudeProperties());
    map.put("PROPERTY-MapType", MESSAGES.MapTypeProperties());
    map.put("PROPERTY-Rotation", MESSAGES.RotationProperties());
    map.put("PROPERTY-ScaleUnits", MESSAGES.ScaleUnitsProperties());
    map.put("PROPERTY-ShowCompass", MESSAGES.ShowCompassProperties());
    map.put("PROPERTY-ShowScale", MESSAGES.ShowScaleProperties());
    map.put("PROPERTY-ShowUser", MESSAGES.ShowUserProperties());
    map.put("PROPERTY-ShowZoom", MESSAGES.ShowZoomProperties());
    map.put("PROPERTY-UserLatitude", MESSAGES.UserLatitudeProperties());
    map.put("PROPERTY-UserLongitude", MESSAGES.UserLongitudeProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());
    map.put("PROPERTY-ZoomLevel", MESSAGES.ZoomLevelProperties());


/* Events */

    map.put("EVENT-BoundsChange", MESSAGES.BoundsChangeEvents());
    map.put("EVENT-DoubleTapAtPoint", MESSAGES.DoubleTapAtPointEvents());
    map.put("EVENT-FeatureClick", MESSAGES.FeatureClickEvents());
    map.put("EVENT-FeatureDrag", MESSAGES.FeatureDragEvents());
    map.put("EVENT-FeatureLongClick", MESSAGES.FeatureLongClickEvents());
    map.put("EVENT-FeatureStartDrag", MESSAGES.FeatureStartDragEvents());
    map.put("EVENT-FeatureStopDrag", MESSAGES.FeatureStopDragEvents());
    map.put("EVENT-GotFeatures", MESSAGES.GotFeaturesEvents());
    map.put("EVENT-InvalidPoint", MESSAGES.InvalidPointEvents());
    map.put("EVENT-LoadError", MESSAGES.LoadErrorEvents());
    map.put("EVENT-LongPressAtPoint", MESSAGES.LongPressAtPointEvents());
    map.put("EVENT-Ready", MESSAGES.ReadyEvents());
    map.put("EVENT-TapAtPoint", MESSAGES.TapAtPointEvents());
    map.put("EVENT-ZoomChange", MESSAGES.ZoomChangeEvents());


/* Methods */

    map.put("METHOD-CreateMarker", MESSAGES.CreateMarkerMethods());
    map.put("METHOD-FeatureFromDescription", MESSAGES.FeatureFromDescriptionMethods());
    map.put("METHOD-LoadFromURL", MESSAGES.LoadFromURLMethods());
    map.put("METHOD-PanTo", MESSAGES.PanToMethods());
    map.put("METHOD-Save", MESSAGES.SaveMethods());


/* Parameters */

    map.put("PARAM-latitude", MESSAGES.latitudeParams());
    map.put("PARAM-longitude", MESSAGES.longitudeParams());
    map.put("PARAM-feature", MESSAGES.featureParams());
    map.put("PARAM-url", MESSAGES.urlParams());
    map.put("PARAM-features", MESSAGES.featuresParams());
    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-responseCode", MESSAGES.responseCodeParams());
    map.put("PARAM-errorMessage", MESSAGES.errorMessageParams());
    map.put("PARAM-description", MESSAGES.descriptionParams());
    map.put("PARAM-zoom", MESSAGES.zoomParams());
    map.put("PARAM-path", MESSAGES.pathParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Marker */

    map.put("COMPONENT-Marker", MESSAGES.markerComponentPallette());

    map.put("Marker-helpString", MESSAGES.MarkerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AnchorHorizontal", MESSAGES.AnchorHorizontalProperties());
    map.put("PROPERTY-AnchorVertical", MESSAGES.AnchorVerticalProperties());
    map.put("PROPERTY-Description", MESSAGES.DescriptionProperties());
    map.put("PROPERTY-Draggable", MESSAGES.DraggableProperties());
    map.put("PROPERTY-EnableInfobox", MESSAGES.EnableInfoboxProperties());
    map.put("PROPERTY-FillColor", MESSAGES.FillColorProperties());
    map.put("PROPERTY-FillOpacity", MESSAGES.FillOpacityProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-ImageAsset", MESSAGES.ImageAssetProperties());
    map.put("PROPERTY-Latitude", MESSAGES.LatitudeProperties());
    map.put("PROPERTY-Longitude", MESSAGES.LongitudeProperties());
    map.put("PROPERTY-StrokeColor", MESSAGES.StrokeColorProperties());
    map.put("PROPERTY-StrokeOpacity", MESSAGES.StrokeOpacityProperties());
    map.put("PROPERTY-StrokeWidth", MESSAGES.StrokeWidthProperties());
    map.put("PROPERTY-Title", MESSAGES.TitleProperties());
    map.put("PROPERTY-Type", MESSAGES.TypeProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-Click", MESSAGES.ClickEvents());
    map.put("EVENT-Drag", MESSAGES.DragEvents());
    map.put("EVENT-LongClick", MESSAGES.LongClickEvents());
    map.put("EVENT-StartDrag", MESSAGES.StartDragEvents());
    map.put("EVENT-StopDrag", MESSAGES.StopDragEvents());


/* Methods */

    map.put("METHOD-BearingToFeature", MESSAGES.BearingToFeatureMethods());
    map.put("METHOD-BearingToPoint", MESSAGES.BearingToPointMethods());
    map.put("METHOD-DistanceToFeature", MESSAGES.DistanceToFeatureMethods());
    map.put("METHOD-DistanceToPoint", MESSAGES.DistanceToPointMethods());
    map.put("METHOD-HideInfobox", MESSAGES.HideInfoboxMethods());
    map.put("METHOD-SetLocation", MESSAGES.SetLocationMethods());
    map.put("METHOD-ShowInfobox", MESSAGES.ShowInfoboxMethods());


/* Parameters */

    map.put("PARAM-mapFeature", MESSAGES.mapFeatureParams());
    map.put("PARAM-centroids", MESSAGES.centroidsParams());
    map.put("PARAM-latitude", MESSAGES.latitudeParams());
    map.put("PARAM-longitude", MESSAGES.longitudeParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: MediaStore */

    map.put("COMPONENT-MediaStore", MESSAGES.mediaStoreComponentPallette());

    map.put("MediaStore-helpString", MESSAGES.MediaStoreHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-ServiceURL", MESSAGES.ServiceURLProperties());


/* Events */

    map.put("EVENT-MediaStored", MESSAGES.MediaStoredEvents());
    map.put("EVENT-WebServiceError", MESSAGES.WebServiceErrorEvents());


/* Methods */

    map.put("METHOD-PostMedia", MESSAGES.PostMediaMethods());


/* Parameters */

    map.put("PARAM-url", MESSAGES.urlParams());
    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-mediafile", MESSAGES.mediafileParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Navigation */

    map.put("COMPONENT-Navigation", MESSAGES.navigationComponentPallette());

    map.put("Navigation-helpString", MESSAGES.NavigationHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-ApiKey", MESSAGES.ApiKeyProperties());
    map.put("PROPERTY-EndLatitude", MESSAGES.EndLatitudeProperties());
    map.put("PROPERTY-EndLocation", MESSAGES.EndLocationProperties());
    map.put("PROPERTY-EndLongitude", MESSAGES.EndLongitudeProperties());
    map.put("PROPERTY-Language", MESSAGES.LanguageProperties());
    map.put("PROPERTY-ResponseContent", MESSAGES.ResponseContentProperties());
    map.put("PROPERTY-StartLatitude", MESSAGES.StartLatitudeProperties());
    map.put("PROPERTY-StartLocation", MESSAGES.StartLocationProperties());
    map.put("PROPERTY-StartLongitude", MESSAGES.StartLongitudeProperties());
    map.put("PROPERTY-TransportationMethod", MESSAGES.TransportationMethodProperties());


/* Events */

    map.put("EVENT-GotDirections", MESSAGES.GotDirectionsEvents());


/* Methods */

    map.put("METHOD-RequestDirections", MESSAGES.RequestDirectionsMethods());


/* Parameters */

    map.put("PARAM-directions", MESSAGES.directionsParams());
    map.put("PARAM-points", MESSAGES.pointsParams());
    map.put("PARAM-distance", MESSAGES.distanceParams());
    map.put("PARAM-duration", MESSAGES.durationParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: NearField */

    map.put("COMPONENT-NearField", MESSAGES.nearFieldComponentPallette());

    map.put("NearField-helpString", MESSAGES.NearFieldHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-LastMessage", MESSAGES.LastMessageProperties());
    map.put("PROPERTY-ReadMode", MESSAGES.ReadModeProperties());
    map.put("PROPERTY-TextToWrite", MESSAGES.TextToWriteProperties());
    map.put("PROPERTY-WriteType", MESSAGES.WriteTypeProperties());


/* Events */

    map.put("EVENT-TagRead", MESSAGES.TagReadEvents());
    map.put("EVENT-TagWritten", MESSAGES.TagWrittenEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Notifier */

    map.put("COMPONENT-Notifier", MESSAGES.notifierComponentPallette());

    map.put("Notifier-helpString", MESSAGES.NotifierHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-NotifierLength", MESSAGES.NotifierLengthProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());


/* Events */

    map.put("EVENT-AfterChoosing", MESSAGES.AfterChoosingEvents());
    map.put("EVENT-AfterTextInput", MESSAGES.AfterTextInputEvents());
    map.put("EVENT-ChoosingCanceled", MESSAGES.ChoosingCanceledEvents());
    map.put("EVENT-TextInputCanceled", MESSAGES.TextInputCanceledEvents());


/* Methods */

    map.put("METHOD-DismissProgressDialog", MESSAGES.DismissProgressDialogMethods());
    map.put("METHOD-LogError", MESSAGES.LogErrorMethods());
    map.put("METHOD-LogInfo", MESSAGES.LogInfoMethods());
    map.put("METHOD-LogWarning", MESSAGES.LogWarningMethods());
    map.put("METHOD-ShowAlert", MESSAGES.ShowAlertMethods());
    map.put("METHOD-ShowChooseDialog", MESSAGES.ShowChooseDialogMethods());
    map.put("METHOD-ShowMessageDialog", MESSAGES.ShowMessageDialogMethods());
    map.put("METHOD-ShowPasswordDialog", MESSAGES.ShowPasswordDialogMethods());
    map.put("METHOD-ShowProgressDialog", MESSAGES.ShowProgressDialogMethods());
    map.put("METHOD-ShowTextDialog", MESSAGES.ShowTextDialogMethods());


/* Parameters */

    map.put("PARAM-choice", MESSAGES.choiceParams());
    map.put("PARAM-response", MESSAGES.responseParams());
    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-notice", MESSAGES.noticeParams());
    map.put("PARAM-title", MESSAGES.titleParams());
    map.put("PARAM-button1Text", MESSAGES.button1TextParams());
    map.put("PARAM-button2Text", MESSAGES.button2TextParams());
    map.put("PARAM-cancelable", MESSAGES.cancelableParams());
    map.put("PARAM-buttonText", MESSAGES.buttonTextParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: NxtColorSensor */

    map.put("COMPONENT-NxtColorSensor", MESSAGES.nxtColorSensorComponentPallette());

    map.put("NxtColorSensor-helpString", MESSAGES.NxtColorSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AboveRangeEventEnabled", MESSAGES.AboveRangeEventEnabledProperties());
    map.put("PROPERTY-BelowRangeEventEnabled", MESSAGES.BelowRangeEventEnabledProperties());
    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());
    map.put("PROPERTY-BottomOfRange", MESSAGES.BottomOfRangeProperties());
    map.put("PROPERTY-ColorChangedEventEnabled", MESSAGES.ColorChangedEventEnabledProperties());
    map.put("PROPERTY-DetectColor", MESSAGES.DetectColorProperties());
    map.put("PROPERTY-GenerateColor", MESSAGES.GenerateColorProperties());
    map.put("PROPERTY-SensorPort", MESSAGES.SensorPortProperties());
    map.put("PROPERTY-TopOfRange", MESSAGES.TopOfRangeProperties());
    map.put("PROPERTY-WithinRangeEventEnabled", MESSAGES.WithinRangeEventEnabledProperties());


/* Events */

    map.put("EVENT-AboveRange", MESSAGES.AboveRangeEvents());
    map.put("EVENT-BelowRange", MESSAGES.BelowRangeEvents());
    map.put("EVENT-ColorChanged", MESSAGES.ColorChangedEvents());
    map.put("EVENT-WithinRange", MESSAGES.WithinRangeEvents());


/* Methods */

    map.put("METHOD-GetColor", MESSAGES.GetColorMethods());
    map.put("METHOD-GetLightLevel", MESSAGES.GetLightLevelMethods());


/* Parameters */

    map.put("PARAM-color", MESSAGES.colorParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: NxtDirectCommands */

    map.put("COMPONENT-NxtDirectCommands", MESSAGES.nxtDirectCommandsComponentPallette());

    map.put("NxtDirectCommands-helpString", MESSAGES.NxtDirectCommandsHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());


/* Events */



/* Methods */

    map.put("METHOD-DeleteFile", MESSAGES.DeleteFileMethods());
    map.put("METHOD-DownloadFile", MESSAGES.DownloadFileMethods());
    map.put("METHOD-GetBatteryLevel", MESSAGES.GetBatteryLevelMethods());
    map.put("METHOD-GetBrickName", MESSAGES.GetBrickNameMethods());
    map.put("METHOD-GetCurrentProgramName", MESSAGES.GetCurrentProgramNameMethods());
    map.put("METHOD-GetFirmwareVersion", MESSAGES.GetFirmwareVersionMethods());
    map.put("METHOD-GetInputValues", MESSAGES.GetInputValuesMethods());
    map.put("METHOD-GetOutputState", MESSAGES.GetOutputStateMethods());
    map.put("METHOD-KeepAlive", MESSAGES.KeepAliveMethods());
    map.put("METHOD-ListFiles", MESSAGES.ListFilesMethods());
    map.put("METHOD-LsGetStatus", MESSAGES.LsGetStatusMethods());
    map.put("METHOD-LsRead", MESSAGES.LsReadMethods());
    map.put("METHOD-LsWrite", MESSAGES.LsWriteMethods());
    map.put("METHOD-MessageRead", MESSAGES.MessageReadMethods());
    map.put("METHOD-MessageWrite", MESSAGES.MessageWriteMethods());
    map.put("METHOD-PlaySoundFile", MESSAGES.PlaySoundFileMethods());
    map.put("METHOD-PlayTone", MESSAGES.PlayToneMethods());
    map.put("METHOD-ResetInputScaledValue", MESSAGES.ResetInputScaledValueMethods());
    map.put("METHOD-ResetMotorPosition", MESSAGES.ResetMotorPositionMethods());
    map.put("METHOD-SetBrickName", MESSAGES.SetBrickNameMethods());
    map.put("METHOD-SetInputMode", MESSAGES.SetInputModeMethods());
    map.put("METHOD-SetOutputState", MESSAGES.SetOutputStateMethods());
    map.put("METHOD-StartProgram", MESSAGES.StartProgramMethods());
    map.put("METHOD-StopProgram", MESSAGES.StopProgramMethods());
    map.put("METHOD-StopSoundPlayback", MESSAGES.StopSoundPlaybackMethods());


/* Parameters */

    map.put("PARAM-fileName", MESSAGES.fileNameParams());
    map.put("PARAM-source", MESSAGES.sourceParams());
    map.put("PARAM-destination", MESSAGES.destinationParams());
    map.put("PARAM-sensorPortLetter", MESSAGES.sensorPortLetterParams());
    map.put("PARAM-motorPortLetter", MESSAGES.motorPortLetterParams());
    map.put("PARAM-wildcard", MESSAGES.wildcardParams());
    map.put("PARAM-list", MESSAGES.listParams());
    map.put("PARAM-rxDataLength", MESSAGES.rxDataLengthParams());
    map.put("PARAM-mailbox", MESSAGES.mailboxParams());
    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-frequencyHz", MESSAGES.frequencyHzParams());
    map.put("PARAM-durationMs", MESSAGES.durationMsParams());
    map.put("PARAM-relative", MESSAGES.relativeParams());
    map.put("PARAM-name", MESSAGES.nameParams());
    map.put("PARAM-sensorType", MESSAGES.sensorTypeParams());
    map.put("PARAM-sensorMode", MESSAGES.sensorModeParams());
    map.put("PARAM-power", MESSAGES.powerParams());
    map.put("PARAM-mode", MESSAGES.modeParams());
    map.put("PARAM-regulationMode", MESSAGES.regulationModeParams());
    map.put("PARAM-turnRatio", MESSAGES.turnRatioParams());
    map.put("PARAM-runState", MESSAGES.runStateParams());
    map.put("PARAM-tachoLimit", MESSAGES.tachoLimitParams());
    map.put("PARAM-programName", MESSAGES.programNameParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: NxtDrive */

    map.put("COMPONENT-NxtDrive", MESSAGES.nxtDriveComponentPallette());

    map.put("NxtDrive-helpString", MESSAGES.NxtDriveHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());
    map.put("PROPERTY-DriveMotors", MESSAGES.DriveMotorsProperties());
    map.put("PROPERTY-StopBeforeDisconnect", MESSAGES.StopBeforeDisconnectProperties());
    map.put("PROPERTY-WheelDiameter", MESSAGES.WheelDiameterProperties());


/* Events */



/* Methods */

    map.put("METHOD-MoveBackward", MESSAGES.MoveBackwardMethods());
    map.put("METHOD-MoveBackwardIndefinitely", MESSAGES.MoveBackwardIndefinitelyMethods());
    map.put("METHOD-MoveForward", MESSAGES.MoveForwardMethods());
    map.put("METHOD-MoveForwardIndefinitely", MESSAGES.MoveForwardIndefinitelyMethods());
    map.put("METHOD-Stop", MESSAGES.StopMethods());
    map.put("METHOD-TurnClockwiseIndefinitely", MESSAGES.TurnClockwiseIndefinitelyMethods());
    map.put("METHOD-TurnCounterClockwiseIndefinitely", MESSAGES.TurnCounterClockwiseIndefinitelyMethods());


/* Parameters */

    map.put("PARAM-power", MESSAGES.powerParams());
    map.put("PARAM-distance", MESSAGES.distanceParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: NxtLightSensor */

    map.put("COMPONENT-NxtLightSensor", MESSAGES.nxtLightSensorComponentPallette());

    map.put("NxtLightSensor-helpString", MESSAGES.NxtLightSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AboveRangeEventEnabled", MESSAGES.AboveRangeEventEnabledProperties());
    map.put("PROPERTY-BelowRangeEventEnabled", MESSAGES.BelowRangeEventEnabledProperties());
    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());
    map.put("PROPERTY-BottomOfRange", MESSAGES.BottomOfRangeProperties());
    map.put("PROPERTY-GenerateLight", MESSAGES.GenerateLightProperties());
    map.put("PROPERTY-SensorPort", MESSAGES.SensorPortProperties());
    map.put("PROPERTY-TopOfRange", MESSAGES.TopOfRangeProperties());
    map.put("PROPERTY-WithinRangeEventEnabled", MESSAGES.WithinRangeEventEnabledProperties());


/* Events */

    map.put("EVENT-AboveRange", MESSAGES.AboveRangeEvents());
    map.put("EVENT-BelowRange", MESSAGES.BelowRangeEvents());
    map.put("EVENT-WithinRange", MESSAGES.WithinRangeEvents());


/* Methods */

    map.put("METHOD-GetLightLevel", MESSAGES.GetLightLevelMethods());


/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: NxtSoundSensor */

    map.put("COMPONENT-NxtSoundSensor", MESSAGES.nxtSoundSensorComponentPallette());

    map.put("NxtSoundSensor-helpString", MESSAGES.NxtSoundSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AboveRangeEventEnabled", MESSAGES.AboveRangeEventEnabledProperties());
    map.put("PROPERTY-BelowRangeEventEnabled", MESSAGES.BelowRangeEventEnabledProperties());
    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());
    map.put("PROPERTY-BottomOfRange", MESSAGES.BottomOfRangeProperties());
    map.put("PROPERTY-SensorPort", MESSAGES.SensorPortProperties());
    map.put("PROPERTY-TopOfRange", MESSAGES.TopOfRangeProperties());
    map.put("PROPERTY-WithinRangeEventEnabled", MESSAGES.WithinRangeEventEnabledProperties());


/* Events */

    map.put("EVENT-AboveRange", MESSAGES.AboveRangeEvents());
    map.put("EVENT-BelowRange", MESSAGES.BelowRangeEvents());
    map.put("EVENT-WithinRange", MESSAGES.WithinRangeEvents());


/* Methods */

    map.put("METHOD-GetSoundLevel", MESSAGES.GetSoundLevelMethods());


/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: NxtTouchSensor */

    map.put("COMPONENT-NxtTouchSensor", MESSAGES.nxtTouchSensorComponentPallette());

    map.put("NxtTouchSensor-helpString", MESSAGES.NxtTouchSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());
    map.put("PROPERTY-PressedEventEnabled", MESSAGES.PressedEventEnabledProperties());
    map.put("PROPERTY-ReleasedEventEnabled", MESSAGES.ReleasedEventEnabledProperties());
    map.put("PROPERTY-SensorPort", MESSAGES.SensorPortProperties());


/* Events */

    map.put("EVENT-Pressed", MESSAGES.PressedEvents());
    map.put("EVENT-Released", MESSAGES.ReleasedEvents());


/* Methods */

    map.put("METHOD-IsPressed", MESSAGES.IsPressedMethods());


/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: NxtUltrasonicSensor */

    map.put("COMPONENT-NxtUltrasonicSensor", MESSAGES.nxtUltrasonicSensorComponentPallette());

    map.put("NxtUltrasonicSensor-helpString", MESSAGES.NxtUltrasonicSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AboveRangeEventEnabled", MESSAGES.AboveRangeEventEnabledProperties());
    map.put("PROPERTY-BelowRangeEventEnabled", MESSAGES.BelowRangeEventEnabledProperties());
    map.put("PROPERTY-BluetoothClient", MESSAGES.BluetoothClientProperties());
    map.put("PROPERTY-BottomOfRange", MESSAGES.BottomOfRangeProperties());
    map.put("PROPERTY-SensorPort", MESSAGES.SensorPortProperties());
    map.put("PROPERTY-TopOfRange", MESSAGES.TopOfRangeProperties());
    map.put("PROPERTY-WithinRangeEventEnabled", MESSAGES.WithinRangeEventEnabledProperties());


/* Events */

    map.put("EVENT-AboveRange", MESSAGES.AboveRangeEvents());
    map.put("EVENT-BelowRange", MESSAGES.BelowRangeEvents());
    map.put("EVENT-WithinRange", MESSAGES.WithinRangeEvents());


/* Methods */

    map.put("METHOD-GetDistance", MESSAGES.GetDistanceMethods());


/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: OrientationSensor */

    map.put("COMPONENT-OrientationSensor", MESSAGES.orientationSensorComponentPallette());

    map.put("OrientationSensor-helpString", MESSAGES.OrientationSensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Angle", MESSAGES.AngleProperties());
    map.put("PROPERTY-Available", MESSAGES.AvailableProperties());
    map.put("PROPERTY-Azimuth", MESSAGES.AzimuthProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-Magnitude", MESSAGES.MagnitudeProperties());
    map.put("PROPERTY-Pitch", MESSAGES.PitchProperties());
    map.put("PROPERTY-Roll", MESSAGES.RollProperties());


/* Events */

    map.put("EVENT-OrientationChanged", MESSAGES.OrientationChangedEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-azimuth", MESSAGES.azimuthParams());
    map.put("PARAM-pitch", MESSAGES.pitchParams());
    map.put("PARAM-roll", MESSAGES.rollParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: PasswordTextBox */

    map.put("COMPONENT-PasswordTextBox", MESSAGES.passwordTextBoxComponentPallette());

    map.put("PasswordTextBox-helpString", MESSAGES.PasswordTextBoxHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Hint", MESSAGES.HintProperties());
    map.put("PROPERTY-PasswordVisible", MESSAGES.PasswordVisibleProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextAlignment", MESSAGES.TextAlignmentProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-GotFocus", MESSAGES.GotFocusEvents());
    map.put("EVENT-LostFocus", MESSAGES.LostFocusEvents());


/* Methods */

    map.put("METHOD-RequestFocus", MESSAGES.RequestFocusMethods());


/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Pedometer */

    map.put("COMPONENT-Pedometer", MESSAGES.pedometerComponentPallette());

    map.put("Pedometer-helpString", MESSAGES.PedometerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-CalibrateStrideLength", MESSAGES.CalibrateStrideLengthProperties());
    map.put("PROPERTY-Distance", MESSAGES.DistanceProperties());
    map.put("PROPERTY-ElapsedTime", MESSAGES.ElapsedTimeProperties());
    map.put("PROPERTY-Moving", MESSAGES.MovingProperties());
    map.put("PROPERTY-SimpleSteps", MESSAGES.SimpleStepsProperties());
    map.put("PROPERTY-StopDetectionTimeout", MESSAGES.StopDetectionTimeoutProperties());
    map.put("PROPERTY-StrideLength", MESSAGES.StrideLengthProperties());
    map.put("PROPERTY-UseGPS", MESSAGES.UseGPSProperties());
    map.put("PROPERTY-WalkSteps", MESSAGES.WalkStepsProperties());


/* Events */

    map.put("EVENT-CalibrationFailed", MESSAGES.CalibrationFailedEvents());
    map.put("EVENT-GPSAvailable", MESSAGES.GPSAvailableEvents());
    map.put("EVENT-GPSLost", MESSAGES.GPSLostEvents());
    map.put("EVENT-SimpleStep", MESSAGES.SimpleStepEvents());
    map.put("EVENT-StartedMoving", MESSAGES.StartedMovingEvents());
    map.put("EVENT-StoppedMoving", MESSAGES.StoppedMovingEvents());
    map.put("EVENT-WalkStep", MESSAGES.WalkStepEvents());


/* Methods */

    map.put("METHOD-Pause", MESSAGES.PauseMethods());
    map.put("METHOD-Reset", MESSAGES.ResetMethods());
    map.put("METHOD-Resume", MESSAGES.ResumeMethods());
    map.put("METHOD-Save", MESSAGES.SaveMethods());
    map.put("METHOD-Start", MESSAGES.StartMethods());
    map.put("METHOD-Stop", MESSAGES.StopMethods());


/* Parameters */

    map.put("PARAM-simpleSteps", MESSAGES.simpleStepsParams());
    map.put("PARAM-distance", MESSAGES.distanceParams());
    map.put("PARAM-walkSteps", MESSAGES.walkStepsParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: PhoneCall */

    map.put("COMPONENT-PhoneCall", MESSAGES.phoneCallComponentPallette());

    map.put("PhoneCall-helpString", MESSAGES.PhoneCallHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-PhoneNumber", MESSAGES.PhoneNumberProperties());


/* Events */

    map.put("EVENT-IncomingCallAnswered", MESSAGES.IncomingCallAnsweredEvents());
    map.put("EVENT-PhoneCallEnded", MESSAGES.PhoneCallEndedEvents());
    map.put("EVENT-PhoneCallStarted", MESSAGES.PhoneCallStartedEvents());


/* Methods */

    map.put("METHOD-MakePhoneCall", MESSAGES.MakePhoneCallMethods());
    map.put("METHOD-MakePhoneCallDirect", MESSAGES.MakePhoneCallDirectMethods());


/* Parameters */

    map.put("PARAM-phoneNumber", MESSAGES.phoneNumberParams());
    map.put("PARAM-status", MESSAGES.statusParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: PhoneNumberPicker */

    map.put("COMPONENT-PhoneNumberPicker", MESSAGES.phoneNumberPickerComponentPallette());

    map.put("PhoneNumberPicker-helpString", MESSAGES.PhoneNumberPickerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-ContactName", MESSAGES.ContactNameProperties());
    map.put("PROPERTY-ContactUri", MESSAGES.ContactUriProperties());
    map.put("PROPERTY-EmailAddress", MESSAGES.EmailAddressProperties());
    map.put("PROPERTY-EmailAddressList", MESSAGES.EmailAddressListProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Image", MESSAGES.ImageProperties());
    map.put("PROPERTY-PhoneNumber", MESSAGES.PhoneNumberProperties());
    map.put("PROPERTY-PhoneNumberList", MESSAGES.PhoneNumberListProperties());
    map.put("PROPERTY-Picture", MESSAGES.PictureProperties());
    map.put("PROPERTY-Shape", MESSAGES.ShapeProperties());
    map.put("PROPERTY-ShowFeedback", MESSAGES.ShowFeedbackProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextAlignment", MESSAGES.TextAlignmentProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-AfterPicking", MESSAGES.AfterPickingEvents());
    map.put("EVENT-BeforePicking", MESSAGES.BeforePickingEvents());
    map.put("EVENT-GotFocus", MESSAGES.GotFocusEvents());
    map.put("EVENT-LostFocus", MESSAGES.LostFocusEvents());
    map.put("EVENT-TouchDown", MESSAGES.TouchDownEvents());
    map.put("EVENT-TouchUp", MESSAGES.TouchUpEvents());


/* Methods */

    map.put("METHOD-Open", MESSAGES.OpenMethods());
    map.put("METHOD-ViewContact", MESSAGES.ViewContactMethods());


/* Parameters */

    map.put("PARAM-uri", MESSAGES.uriParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: PhoneStatus */

    map.put("COMPONENT-PhoneStatus", MESSAGES.phoneStatusComponentPallette());

    map.put("PhoneStatus-helpString", MESSAGES.PhoneStatusHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-WebRTC", MESSAGES.WebRTCProperties());


/* Events */

    map.put("EVENT-OnSettings", MESSAGES.OnSettingsEvents());


/* Methods */

    map.put("METHOD-GetInstaller", MESSAGES.GetInstallerMethods());
    map.put("METHOD-GetVersionName", MESSAGES.GetVersionNameMethods());
    map.put("METHOD-GetWifiIpAddress", MESSAGES.GetWifiIpAddressMethods());
    map.put("METHOD-InstallationId", MESSAGES.InstallationIdMethods());
    map.put("METHOD-SdkLevel", MESSAGES.SdkLevelMethods());
    map.put("METHOD-doFault", MESSAGES.doFaultMethods());
    map.put("METHOD-installURL", MESSAGES.installURLMethods());
    map.put("METHOD-isConnected", MESSAGES.isConnectedMethods());
    map.put("METHOD-isDirect", MESSAGES.isDirectMethods());
    map.put("METHOD-setAssetsLoaded", MESSAGES.setAssetsLoadedMethods());
    map.put("METHOD-setHmacSeedReturnCode", MESSAGES.setHmacSeedReturnCodeMethods());
    map.put("METHOD-shutdown", MESSAGES.shutdownMethods());
    map.put("METHOD-startHTTPD", MESSAGES.startHTTPDMethods());
    map.put("METHOD-startWebRTC", MESSAGES.startWebRTCMethods());


/* Parameters */

    map.put("PARAM-url", MESSAGES.urlParams());
    map.put("PARAM-seed", MESSAGES.seedParams());
    map.put("PARAM-rendezvousServer", MESSAGES.rendezvousServerParams());
    map.put("PARAM-secure", MESSAGES.secureParams());
    map.put("PARAM-iceServers", MESSAGES.iceServersParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Player */

    map.put("COMPONENT-Player", MESSAGES.playerComponentPallette());

    map.put("Player-helpString", MESSAGES.PlayerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-IsPlaying", MESSAGES.IsPlayingProperties());
    map.put("PROPERTY-Loop", MESSAGES.LoopProperties());
    map.put("PROPERTY-PlayOnlyInForeground", MESSAGES.PlayOnlyInForegroundProperties());
    map.put("PROPERTY-Source", MESSAGES.SourceProperties());
    map.put("PROPERTY-Volume", MESSAGES.VolumeProperties());


/* Events */

    map.put("EVENT-Completed", MESSAGES.CompletedEvents());
    map.put("EVENT-OtherPlayerStarted", MESSAGES.OtherPlayerStartedEvents());


/* Methods */

    map.put("METHOD-Pause", MESSAGES.PauseMethods());
    map.put("METHOD-Start", MESSAGES.StartMethods());
    map.put("METHOD-Stop", MESSAGES.StopMethods());
    map.put("METHOD-Vibrate", MESSAGES.VibrateMethods());


/* Parameters */

    map.put("PARAM-milliseconds", MESSAGES.millisecondsParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Polygon */

    map.put("COMPONENT-Polygon", MESSAGES.polygonComponentPallette());

    map.put("Polygon-helpString", MESSAGES.PolygonHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Description", MESSAGES.DescriptionProperties());
    map.put("PROPERTY-Draggable", MESSAGES.DraggableProperties());
    map.put("PROPERTY-EnableInfobox", MESSAGES.EnableInfoboxProperties());
    map.put("PROPERTY-FillColor", MESSAGES.FillColorProperties());
    map.put("PROPERTY-FillOpacity", MESSAGES.FillOpacityProperties());
    map.put("PROPERTY-HolePoints", MESSAGES.HolePointsProperties());
    map.put("PROPERTY-HolePointsFromString", MESSAGES.HolePointsFromStringProperties());
    map.put("PROPERTY-Points", MESSAGES.PointsProperties());
    map.put("PROPERTY-PointsFromString", MESSAGES.PointsFromStringProperties());
    map.put("PROPERTY-StrokeColor", MESSAGES.StrokeColorProperties());
    map.put("PROPERTY-StrokeOpacity", MESSAGES.StrokeOpacityProperties());
    map.put("PROPERTY-StrokeWidth", MESSAGES.StrokeWidthProperties());
    map.put("PROPERTY-Title", MESSAGES.TitleProperties());
    map.put("PROPERTY-Type", MESSAGES.TypeProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());


/* Events */

    map.put("EVENT-Click", MESSAGES.ClickEvents());
    map.put("EVENT-Drag", MESSAGES.DragEvents());
    map.put("EVENT-LongClick", MESSAGES.LongClickEvents());
    map.put("EVENT-StartDrag", MESSAGES.StartDragEvents());
    map.put("EVENT-StopDrag", MESSAGES.StopDragEvents());


/* Methods */

    map.put("METHOD-Centroid", MESSAGES.CentroidMethods());
    map.put("METHOD-DistanceToFeature", MESSAGES.DistanceToFeatureMethods());
    map.put("METHOD-DistanceToPoint", MESSAGES.DistanceToPointMethods());
    map.put("METHOD-HideInfobox", MESSAGES.HideInfoboxMethods());
    map.put("METHOD-ShowInfobox", MESSAGES.ShowInfoboxMethods());


/* Parameters */

    map.put("PARAM-mapFeature", MESSAGES.mapFeatureParams());
    map.put("PARAM-centroids", MESSAGES.centroidsParams());
    map.put("PARAM-latitude", MESSAGES.latitudeParams());
    map.put("PARAM-longitude", MESSAGES.longitudeParams());
    map.put("PARAM-centroid", MESSAGES.centroidParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: ProximitySensor */

    map.put("COMPONENT-ProximitySensor", MESSAGES.proximitySensorComponentPallette());

    map.put("ProximitySensor-helpString", MESSAGES.ProximitySensorHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Available", MESSAGES.AvailableProperties());
    map.put("PROPERTY-Distance", MESSAGES.DistanceProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-KeepRunningWhenOnPause", MESSAGES.KeepRunningWhenOnPauseProperties());
    map.put("PROPERTY-MaximumRange", MESSAGES.MaximumRangeProperties());


/* Events */

    map.put("EVENT-ProximityChanged", MESSAGES.ProximityChangedEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-distance", MESSAGES.distanceParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Rectangle */

    map.put("COMPONENT-Rectangle", MESSAGES.rectangleComponentPallette());

    map.put("Rectangle-helpString", MESSAGES.RectangleHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Description", MESSAGES.DescriptionProperties());
    map.put("PROPERTY-Draggable", MESSAGES.DraggableProperties());
    map.put("PROPERTY-EastLongitude", MESSAGES.EastLongitudeProperties());
    map.put("PROPERTY-EnableInfobox", MESSAGES.EnableInfoboxProperties());
    map.put("PROPERTY-FillColor", MESSAGES.FillColorProperties());
    map.put("PROPERTY-FillOpacity", MESSAGES.FillOpacityProperties());
    map.put("PROPERTY-NorthLatitude", MESSAGES.NorthLatitudeProperties());
    map.put("PROPERTY-SouthLatitude", MESSAGES.SouthLatitudeProperties());
    map.put("PROPERTY-StrokeColor", MESSAGES.StrokeColorProperties());
    map.put("PROPERTY-StrokeOpacity", MESSAGES.StrokeOpacityProperties());
    map.put("PROPERTY-StrokeWidth", MESSAGES.StrokeWidthProperties());
    map.put("PROPERTY-Title", MESSAGES.TitleProperties());
    map.put("PROPERTY-Type", MESSAGES.TypeProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-WestLongitude", MESSAGES.WestLongitudeProperties());


/* Events */

    map.put("EVENT-Click", MESSAGES.ClickEvents());
    map.put("EVENT-Drag", MESSAGES.DragEvents());
    map.put("EVENT-LongClick", MESSAGES.LongClickEvents());
    map.put("EVENT-StartDrag", MESSAGES.StartDragEvents());
    map.put("EVENT-StopDrag", MESSAGES.StopDragEvents());


/* Methods */

    map.put("METHOD-Bounds", MESSAGES.BoundsMethods());
    map.put("METHOD-Center", MESSAGES.CenterMethods());
    map.put("METHOD-DistanceToFeature", MESSAGES.DistanceToFeatureMethods());
    map.put("METHOD-DistanceToPoint", MESSAGES.DistanceToPointMethods());
    map.put("METHOD-HideInfobox", MESSAGES.HideInfoboxMethods());
    map.put("METHOD-SetCenter", MESSAGES.SetCenterMethods());
    map.put("METHOD-ShowInfobox", MESSAGES.ShowInfoboxMethods());


/* Parameters */

    map.put("PARAM-mapFeature", MESSAGES.mapFeatureParams());
    map.put("PARAM-centroids", MESSAGES.centroidsParams());
    map.put("PARAM-latitude", MESSAGES.latitudeParams());
    map.put("PARAM-longitude", MESSAGES.longitudeParams());
    map.put("PARAM-centroid", MESSAGES.centroidParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Serial */

    map.put("COMPONENT-Serial", MESSAGES.serialComponentPallette());

    map.put("Serial-helpString", MESSAGES.SerialHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BaudRate", MESSAGES.BaudRateProperties());
    map.put("PROPERTY-BufferSize", MESSAGES.BufferSizeProperties());
    map.put("PROPERTY-IsInitialized", MESSAGES.IsInitializedProperties());
    map.put("PROPERTY-IsOpen", MESSAGES.IsOpenProperties());


/* Events */



/* Methods */

    map.put("METHOD-CloseSerial", MESSAGES.CloseSerialMethods());
    map.put("METHOD-InitializeSerial", MESSAGES.InitializeSerialMethods());
    map.put("METHOD-OpenSerial", MESSAGES.OpenSerialMethods());
    map.put("METHOD-PrintSerial", MESSAGES.PrintSerialMethods());
    map.put("METHOD-ReadSerial", MESSAGES.ReadSerialMethods());
    map.put("METHOD-WriteSerial", MESSAGES.WriteSerialMethods());


/* Parameters */

    map.put("PARAM-data", MESSAGES.dataParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Sharing */

    map.put("COMPONENT-Sharing", MESSAGES.sharingComponentPallette());

    map.put("Sharing-helpString", MESSAGES.SharingHelpStringComponentPallette());



/* Properties */



/* Events */



/* Methods */

    map.put("METHOD-ShareFile", MESSAGES.ShareFileMethods());
    map.put("METHOD-ShareFileWithMessage", MESSAGES.ShareFileWithMessageMethods());
    map.put("METHOD-ShareMessage", MESSAGES.ShareMessageMethods());


/* Parameters */

    map.put("PARAM-file", MESSAGES.fileParams());
    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Slider */

    map.put("COMPONENT-Slider", MESSAGES.sliderComponentPallette());

    map.put("Slider-helpString", MESSAGES.SliderHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-ColorLeft", MESSAGES.ColorLeftProperties());
    map.put("PROPERTY-ColorRight", MESSAGES.ColorRightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-MaxValue", MESSAGES.MaxValueProperties());
    map.put("PROPERTY-MinValue", MESSAGES.MinValueProperties());
    map.put("PROPERTY-ThumbEnabled", MESSAGES.ThumbEnabledProperties());
    map.put("PROPERTY-ThumbPosition", MESSAGES.ThumbPositionProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-PositionChanged", MESSAGES.PositionChangedEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-thumbPosition", MESSAGES.thumbPositionParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Sound */

    map.put("COMPONENT-Sound", MESSAGES.soundComponentPallette());

    map.put("Sound-helpString", MESSAGES.SoundHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-MinimumInterval", MESSAGES.MinimumIntervalProperties());
    map.put("PROPERTY-Source", MESSAGES.SourceProperties());


/* Events */



/* Methods */

    map.put("METHOD-Pause", MESSAGES.PauseMethods());
    map.put("METHOD-Play", MESSAGES.PlayMethods());
    map.put("METHOD-Resume", MESSAGES.ResumeMethods());
    map.put("METHOD-Stop", MESSAGES.StopMethods());
    map.put("METHOD-Vibrate", MESSAGES.VibrateMethods());


/* Parameters */

    map.put("PARAM-millisecs", MESSAGES.millisecsParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: SoundRecorder */

    map.put("COMPONENT-SoundRecorder", MESSAGES.soundRecorderComponentPallette());

    map.put("SoundRecorder-helpString", MESSAGES.SoundRecorderHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-SavedRecording", MESSAGES.SavedRecordingProperties());


/* Events */

    map.put("EVENT-AfterSoundRecorded", MESSAGES.AfterSoundRecordedEvents());
    map.put("EVENT-StartedRecording", MESSAGES.StartedRecordingEvents());
    map.put("EVENT-StoppedRecording", MESSAGES.StoppedRecordingEvents());


/* Methods */

    map.put("METHOD-Start", MESSAGES.StartMethods());
    map.put("METHOD-Stop", MESSAGES.StopMethods());


/* Parameters */

    map.put("PARAM-sound", MESSAGES.soundParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: SpeechRecognizer */

    map.put("COMPONENT-SpeechRecognizer", MESSAGES.speechRecognizerComponentPallette());

    map.put("SpeechRecognizer-helpString", MESSAGES.SpeechRecognizerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Result", MESSAGES.ResultProperties());
    map.put("PROPERTY-UseLegacy", MESSAGES.UseLegacyProperties());


/* Events */

    map.put("EVENT-AfterGettingText", MESSAGES.AfterGettingTextEvents());
    map.put("EVENT-BeforeGettingText", MESSAGES.BeforeGettingTextEvents());


/* Methods */

    map.put("METHOD-GetText", MESSAGES.GetTextMethods());
    map.put("METHOD-Stop", MESSAGES.StopMethods());


/* Parameters */

    map.put("PARAM-result", MESSAGES.resultParams());
    map.put("PARAM-partial", MESSAGES.partialParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Spinner */

    map.put("COMPONENT-Spinner", MESSAGES.spinnerComponentPallette());

    map.put("Spinner-helpString", MESSAGES.SpinnerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Elements", MESSAGES.ElementsProperties());
    map.put("PROPERTY-ElementsFromString", MESSAGES.ElementsFromStringProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Prompt", MESSAGES.PromptProperties());
    map.put("PROPERTY-Selection", MESSAGES.SelectionProperties());
    map.put("PROPERTY-SelectionIndex", MESSAGES.SelectionIndexProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-AfterSelecting", MESSAGES.AfterSelectingEvents());


/* Methods */

    map.put("METHOD-DisplayDropdown", MESSAGES.DisplayDropdownMethods());


/* Parameters */

    map.put("PARAM-selection", MESSAGES.selectionParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Switch */

    map.put("COMPONENT-Switch", MESSAGES.switchComponentPallette());

    map.put("Switch-helpString", MESSAGES.SwitchHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-On", MESSAGES.OnProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-ThumbColorActive", MESSAGES.ThumbColorActiveProperties());
    map.put("PROPERTY-ThumbColorInactive", MESSAGES.ThumbColorInactiveProperties());
    map.put("PROPERTY-TrackColorActive", MESSAGES.TrackColorActiveProperties());
    map.put("PROPERTY-TrackColorInactive", MESSAGES.TrackColorInactiveProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-Changed", MESSAGES.ChangedEvents());
    map.put("EVENT-GotFocus", MESSAGES.GotFocusEvents());
    map.put("EVENT-LostFocus", MESSAGES.LostFocusEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: TableArrangement */

    map.put("COMPONENT-TableArrangement", MESSAGES.tableArrangementComponentPallette());

    map.put("TableArrangement-helpString", MESSAGES.TableArrangementHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Columns", MESSAGES.ColumnsProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Rows", MESSAGES.RowsProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */



/* Methods */



/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: TextBox */

    map.put("COMPONENT-TextBox", MESSAGES.textBoxComponentPallette());

    map.put("TextBox-helpString", MESSAGES.TextBoxHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Hint", MESSAGES.HintProperties());
    map.put("PROPERTY-MultiLine", MESSAGES.MultiLineProperties());
    map.put("PROPERTY-NumbersOnly", MESSAGES.NumbersOnlyProperties());
    map.put("PROPERTY-ReadOnly", MESSAGES.ReadOnlyProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextAlignment", MESSAGES.TextAlignmentProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-GotFocus", MESSAGES.GotFocusEvents());
    map.put("EVENT-LostFocus", MESSAGES.LostFocusEvents());


/* Methods */

    map.put("METHOD-HideKeyboard", MESSAGES.HideKeyboardMethods());
    map.put("METHOD-RequestFocus", MESSAGES.RequestFocusMethods());


/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: TextToSpeech */

    map.put("COMPONENT-TextToSpeech", MESSAGES.textToSpeechComponentPallette());

    map.put("TextToSpeech-helpString", MESSAGES.TextToSpeechHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AvailableCountries", MESSAGES.AvailableCountriesProperties());
    map.put("PROPERTY-AvailableLanguages", MESSAGES.AvailableLanguagesProperties());
    map.put("PROPERTY-Country", MESSAGES.CountryProperties());
    map.put("PROPERTY-Language", MESSAGES.LanguageProperties());
    map.put("PROPERTY-Pitch", MESSAGES.PitchProperties());
    map.put("PROPERTY-Result", MESSAGES.ResultProperties());
    map.put("PROPERTY-SpeechRate", MESSAGES.SpeechRateProperties());


/* Events */

    map.put("EVENT-AfterSpeaking", MESSAGES.AfterSpeakingEvents());
    map.put("EVENT-BeforeSpeaking", MESSAGES.BeforeSpeakingEvents());


/* Methods */

    map.put("METHOD-Speak", MESSAGES.SpeakMethods());


/* Parameters */

    map.put("PARAM-result", MESSAGES.resultParams());
    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Texting */

    map.put("COMPONENT-Texting", MESSAGES.textingComponentPallette());

    map.put("Texting-helpString", MESSAGES.TextingHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-GoogleVoiceEnabled", MESSAGES.GoogleVoiceEnabledProperties());
    map.put("PROPERTY-Message", MESSAGES.MessageProperties());
    map.put("PROPERTY-PhoneNumber", MESSAGES.PhoneNumberProperties());
    map.put("PROPERTY-ReceivingEnabled", MESSAGES.ReceivingEnabledProperties());


/* Events */

    map.put("EVENT-MessageReceived", MESSAGES.MessageReceivedEvents());


/* Methods */

    map.put("METHOD-SendMessage", MESSAGES.SendMessageMethods());
    map.put("METHOD-SendMessageDirect", MESSAGES.SendMessageDirectMethods());


/* Parameters */

    map.put("PARAM-number", MESSAGES.numberParams());
    map.put("PARAM-messageText", MESSAGES.messageTextParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Thermometer */

    map.put("COMPONENT-Thermometer", MESSAGES.thermometerComponentPallette());

    map.put("Thermometer-helpString", MESSAGES.ThermometerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Available", MESSAGES.AvailableProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-RefreshTime", MESSAGES.RefreshTimeProperties());
    map.put("PROPERTY-Temperature", MESSAGES.TemperatureProperties());


/* Events */

    map.put("EVENT-TemperatureChanged", MESSAGES.TemperatureChangedEvents());


/* Methods */



/* Parameters */

    map.put("PARAM-temperature", MESSAGES.temperatureParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: TimePicker */

    map.put("COMPONENT-TimePicker", MESSAGES.timePickerComponentPallette());

    map.put("TimePicker-helpString", MESSAGES.TimePickerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Enabled", MESSAGES.EnabledProperties());
    map.put("PROPERTY-FontBold", MESSAGES.FontBoldProperties());
    map.put("PROPERTY-FontItalic", MESSAGES.FontItalicProperties());
    map.put("PROPERTY-FontSize", MESSAGES.FontSizeProperties());
    map.put("PROPERTY-FontTypeface", MESSAGES.FontTypefaceProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Hour", MESSAGES.HourProperties());
    map.put("PROPERTY-Image", MESSAGES.ImageProperties());
    map.put("PROPERTY-Instant", MESSAGES.InstantProperties());
    map.put("PROPERTY-Minute", MESSAGES.MinuteProperties());
    map.put("PROPERTY-Shape", MESSAGES.ShapeProperties());
    map.put("PROPERTY-ShowFeedback", MESSAGES.ShowFeedbackProperties());
    map.put("PROPERTY-Text", MESSAGES.TextProperties());
    map.put("PROPERTY-TextAlignment", MESSAGES.TextAlignmentProperties());
    map.put("PROPERTY-TextColor", MESSAGES.TextColorProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-AfterTimeSet", MESSAGES.AfterTimeSetEvents());
    map.put("EVENT-GotFocus", MESSAGES.GotFocusEvents());
    map.put("EVENT-LostFocus", MESSAGES.LostFocusEvents());
    map.put("EVENT-TouchDown", MESSAGES.TouchDownEvents());
    map.put("EVENT-TouchUp", MESSAGES.TouchUpEvents());


/* Methods */

    map.put("METHOD-LaunchPicker", MESSAGES.LaunchPickerMethods());
    map.put("METHOD-SetTimeToDisplay", MESSAGES.SetTimeToDisplayMethods());
    map.put("METHOD-SetTimeToDisplayFromInstant", MESSAGES.SetTimeToDisplayFromInstantMethods());


/* Parameters */

    map.put("PARAM-hour", MESSAGES.hourParams());
    map.put("PARAM-minute", MESSAGES.minuteParams());
    map.put("PARAM-instant", MESSAGES.instantParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: TinyDB */

    map.put("COMPONENT-TinyDB", MESSAGES.tinyDBComponentPallette());

    map.put("TinyDB-helpString", MESSAGES.TinyDBHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-Namespace", MESSAGES.NamespaceProperties());


/* Events */



/* Methods */

    map.put("METHOD-ClearAll", MESSAGES.ClearAllMethods());
    map.put("METHOD-ClearTag", MESSAGES.ClearTagMethods());
    map.put("METHOD-GetTags", MESSAGES.GetTagsMethods());
    map.put("METHOD-GetValue", MESSAGES.GetValueMethods());
    map.put("METHOD-StoreValue", MESSAGES.StoreValueMethods());


/* Parameters */

    map.put("PARAM-tag", MESSAGES.tagParams());
    map.put("PARAM-valueIfTagNotThere", MESSAGES.valueIfTagNotThereParams());
    map.put("PARAM-valueToStore", MESSAGES.valueToStoreParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: TinyWebDB */

    map.put("COMPONENT-TinyWebDB", MESSAGES.tinyWebDBComponentPallette());

    map.put("TinyWebDB-helpString", MESSAGES.TinyWebDBHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-ServiceURL", MESSAGES.ServiceURLProperties());


/* Events */

    map.put("EVENT-GotValue", MESSAGES.GotValueEvents());
    map.put("EVENT-ValueStored", MESSAGES.ValueStoredEvents());
    map.put("EVENT-WebServiceError", MESSAGES.WebServiceErrorEvents());


/* Methods */

    map.put("METHOD-GetValue", MESSAGES.GetValueMethods());
    map.put("METHOD-StoreValue", MESSAGES.StoreValueMethods());


/* Parameters */

    map.put("PARAM-tagFromWebDB", MESSAGES.tagFromWebDBParams());
    map.put("PARAM-valueFromWebDB", MESSAGES.valueFromWebDBParams());
    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-tag", MESSAGES.tagParams());
    map.put("PARAM-valueToStore", MESSAGES.valueToStoreParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Twitter */

    map.put("COMPONENT-Twitter", MESSAGES.twitterComponentPallette());

    map.put("Twitter-helpString", MESSAGES.TwitterHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-ConsumerKey", MESSAGES.ConsumerKeyProperties());
    map.put("PROPERTY-ConsumerSecret", MESSAGES.ConsumerSecretProperties());
    map.put("PROPERTY-DirectMessages", MESSAGES.DirectMessagesProperties());
    map.put("PROPERTY-Followers", MESSAGES.FollowersProperties());
    map.put("PROPERTY-FriendTimeline", MESSAGES.FriendTimelineProperties());
    map.put("PROPERTY-Mentions", MESSAGES.MentionsProperties());
    map.put("PROPERTY-SearchResults", MESSAGES.SearchResultsProperties());
    map.put("PROPERTY-TwitPic_API_Key", MESSAGES.TwitPic_API_KeyProperties());
    map.put("PROPERTY-Username", MESSAGES.UsernameProperties());


/* Events */

    map.put("EVENT-DirectMessagesReceived", MESSAGES.DirectMessagesReceivedEvents());
    map.put("EVENT-FollowersReceived", MESSAGES.FollowersReceivedEvents());
    map.put("EVENT-FriendTimelineReceived", MESSAGES.FriendTimelineReceivedEvents());
    map.put("EVENT-IsAuthorized", MESSAGES.IsAuthorizedEvents());
    map.put("EVENT-MentionsReceived", MESSAGES.MentionsReceivedEvents());
    map.put("EVENT-SearchSuccessful", MESSAGES.SearchSuccessfulEvents());


/* Methods */

    map.put("METHOD-Authorize", MESSAGES.AuthorizeMethods());
    map.put("METHOD-CheckAuthorized", MESSAGES.CheckAuthorizedMethods());
    map.put("METHOD-DeAuthorize", MESSAGES.DeAuthorizeMethods());
    map.put("METHOD-DirectMessage", MESSAGES.DirectMessageMethods());
    map.put("METHOD-Follow", MESSAGES.FollowMethods());
    map.put("METHOD-RequestDirectMessages", MESSAGES.RequestDirectMessagesMethods());
    map.put("METHOD-RequestFollowers", MESSAGES.RequestFollowersMethods());
    map.put("METHOD-RequestFriendTimeline", MESSAGES.RequestFriendTimelineMethods());
    map.put("METHOD-RequestMentions", MESSAGES.RequestMentionsMethods());
    map.put("METHOD-SearchTwitter", MESSAGES.SearchTwitterMethods());
    map.put("METHOD-StopFollowing", MESSAGES.StopFollowingMethods());
    map.put("METHOD-Tweet", MESSAGES.TweetMethods());
    map.put("METHOD-TweetWithImage", MESSAGES.TweetWithImageMethods());


/* Parameters */

    map.put("PARAM-messages", MESSAGES.messagesParams());
    map.put("PARAM-followers2", MESSAGES.followers2Params());
    map.put("PARAM-timeline", MESSAGES.timelineParams());
    map.put("PARAM-mentions", MESSAGES.mentionsParams());
    map.put("PARAM-searchResults", MESSAGES.searchResultsParams());
    map.put("PARAM-user", MESSAGES.userParams());
    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-query", MESSAGES.queryParams());
    map.put("PARAM-status", MESSAGES.statusParams());
    map.put("PARAM-imagePath", MESSAGES.imagePathParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: VerticalArrangement */

    map.put("COMPONENT-VerticalArrangement", MESSAGES.verticalArrangementComponentPallette());

    map.put("VerticalArrangement-helpString", MESSAGES.VerticalArrangementHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AlignHorizontal", MESSAGES.AlignHorizontalProperties());
    map.put("PROPERTY-AlignVertical", MESSAGES.AlignVerticalProperties());
    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Image", MESSAGES.ImageProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */



/* Methods */



/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: VerticalScrollArrangement */

    map.put("COMPONENT-VerticalScrollArrangement", MESSAGES.verticalScrollArrangementComponentPallette());

    map.put("VerticalScrollArrangement-helpString", MESSAGES.VerticalScrollArrangementHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AlignHorizontal", MESSAGES.AlignHorizontalProperties());
    map.put("PROPERTY-AlignVertical", MESSAGES.AlignVerticalProperties());
    map.put("PROPERTY-BackgroundColor", MESSAGES.BackgroundColorProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Image", MESSAGES.ImageProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */



/* Methods */



/* Parameters */

    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: VideoPlayer */

    map.put("COMPONENT-VideoPlayer", MESSAGES.videoPlayerComponentPallette());

    map.put("VideoPlayer-helpString", MESSAGES.VideoPlayerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-FullScreen", MESSAGES.FullScreenProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-Source", MESSAGES.SourceProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-Volume", MESSAGES.VolumeProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-Completed", MESSAGES.CompletedEvents());


/* Methods */

    map.put("METHOD-GetDuration", MESSAGES.GetDurationMethods());
    map.put("METHOD-Pause", MESSAGES.PauseMethods());
    map.put("METHOD-SeekTo", MESSAGES.SeekToMethods());
    map.put("METHOD-Start", MESSAGES.StartMethods());
    map.put("METHOD-Stop", MESSAGES.StopMethods());


/* Parameters */

    map.put("PARAM-ms", MESSAGES.msParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Voting */

    map.put("COMPONENT-Voting", MESSAGES.votingComponentPallette());

    map.put("Voting-helpString", MESSAGES.VotingHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-BallotOptions", MESSAGES.BallotOptionsProperties());
    map.put("PROPERTY-BallotQuestion", MESSAGES.BallotQuestionProperties());
    map.put("PROPERTY-ServiceURL", MESSAGES.ServiceURLProperties());
    map.put("PROPERTY-UserChoice", MESSAGES.UserChoiceProperties());
    map.put("PROPERTY-UserEmailAddress", MESSAGES.UserEmailAddressProperties());
    map.put("PROPERTY-UserId", MESSAGES.UserIdProperties());


/* Events */

    map.put("EVENT-GotBallot", MESSAGES.GotBallotEvents());
    map.put("EVENT-GotBallotConfirmation", MESSAGES.GotBallotConfirmationEvents());
    map.put("EVENT-NoOpenPoll", MESSAGES.NoOpenPollEvents());
    map.put("EVENT-WebServiceError", MESSAGES.WebServiceErrorEvents());


/* Methods */

    map.put("METHOD-RequestBallot", MESSAGES.RequestBallotMethods());
    map.put("METHOD-SendBallot", MESSAGES.SendBallotMethods());


/* Parameters */

    map.put("PARAM-message", MESSAGES.messageParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: Web */

    map.put("COMPONENT-Web", MESSAGES.webComponentPallette());

    map.put("Web-helpString", MESSAGES.WebHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-AllowCookies", MESSAGES.AllowCookiesProperties());
    map.put("PROPERTY-RequestHeaders", MESSAGES.RequestHeadersProperties());
    map.put("PROPERTY-ResponseFileName", MESSAGES.ResponseFileNameProperties());
    map.put("PROPERTY-SaveResponse", MESSAGES.SaveResponseProperties());
    map.put("PROPERTY-Timeout", MESSAGES.TimeoutProperties());
    map.put("PROPERTY-Url", MESSAGES.UrlProperties());


/* Events */

    map.put("EVENT-GotFile", MESSAGES.GotFileEvents());
    map.put("EVENT-GotText", MESSAGES.GotTextEvents());
    map.put("EVENT-TimedOut", MESSAGES.TimedOutEvents());


/* Methods */

    map.put("METHOD-BuildRequestData", MESSAGES.BuildRequestDataMethods());
    map.put("METHOD-ClearCookies", MESSAGES.ClearCookiesMethods());
    map.put("METHOD-Delete", MESSAGES.DeleteMethods());
    map.put("METHOD-Get", MESSAGES.GetMethods());
    map.put("METHOD-HtmlTextDecode", MESSAGES.HtmlTextDecodeMethods());
    map.put("METHOD-JsonObjectEncode", MESSAGES.JsonObjectEncodeMethods());
    map.put("METHOD-JsonTextDecode", MESSAGES.JsonTextDecodeMethods());
    map.put("METHOD-JsonTextDecodeWithDictionaries", MESSAGES.JsonTextDecodeWithDictionariesMethods());
    map.put("METHOD-PostFile", MESSAGES.PostFileMethods());
    map.put("METHOD-PostText", MESSAGES.PostTextMethods());
    map.put("METHOD-PostTextWithEncoding", MESSAGES.PostTextWithEncodingMethods());
    map.put("METHOD-PutFile", MESSAGES.PutFileMethods());
    map.put("METHOD-PutText", MESSAGES.PutTextMethods());
    map.put("METHOD-PutTextWithEncoding", MESSAGES.PutTextWithEncodingMethods());
    map.put("METHOD-UriDecode", MESSAGES.UriDecodeMethods());
    map.put("METHOD-UriEncode", MESSAGES.UriEncodeMethods());
    map.put("METHOD-XMLTextDecode", MESSAGES.XMLTextDecodeMethods());
    map.put("METHOD-XMLTextDecodeAsDictionary", MESSAGES.XMLTextDecodeAsDictionaryMethods());


/* Parameters */

    map.put("PARAM-url", MESSAGES.urlParams());
    map.put("PARAM-responseCode", MESSAGES.responseCodeParams());
    map.put("PARAM-responseType", MESSAGES.responseTypeParams());
    map.put("PARAM-fileName", MESSAGES.fileNameParams());
    map.put("PARAM-responseContent", MESSAGES.responseContentParams());
    map.put("PARAM-list", MESSAGES.listParams());
    map.put("PARAM-htmlText", MESSAGES.htmlTextParams());
    map.put("PARAM-jsonObject", MESSAGES.jsonObjectParams());
    map.put("PARAM-jsonText", MESSAGES.jsonTextParams());
    map.put("PARAM-path", MESSAGES.pathParams());
    map.put("PARAM-text", MESSAGES.textParams());
    map.put("PARAM-encoding", MESSAGES.encodingParams());
    map.put("PARAM-XmlText", MESSAGES.xmlTextParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: WebViewer */

    map.put("COMPONENT-WebViewer", MESSAGES.webViewerComponentPallette());

    map.put("WebViewer-helpString", MESSAGES.WebViewerHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-CurrentPageTitle", MESSAGES.CurrentPageTitleProperties());
    map.put("PROPERTY-CurrentUrl", MESSAGES.CurrentUrlProperties());
    map.put("PROPERTY-FollowLinks", MESSAGES.FollowLinksProperties());
    map.put("PROPERTY-Height", MESSAGES.HeightProperties());
    map.put("PROPERTY-HeightPercent", MESSAGES.HeightPercentProperties());
    map.put("PROPERTY-HomeUrl", MESSAGES.HomeUrlProperties());
    map.put("PROPERTY-IgnoreSslErrors", MESSAGES.IgnoreSslErrorsProperties());
    map.put("PROPERTY-PromptforPermission", MESSAGES.PromptforPermissionProperties());
    map.put("PROPERTY-UsesLocation", MESSAGES.UsesLocationProperties());
    map.put("PROPERTY-Visible", MESSAGES.VisibleProperties());
    map.put("PROPERTY-WebViewString", MESSAGES.WebViewStringProperties());
    map.put("PROPERTY-Width", MESSAGES.WidthProperties());
    map.put("PROPERTY-WidthPercent", MESSAGES.WidthPercentProperties());


/* Events */

    map.put("EVENT-BeforePageLoad", MESSAGES.BeforePageLoadEvents());
    map.put("EVENT-ErrorOccurred", MESSAGES.ErrorOccurredEvents());
    map.put("EVENT-PageLoaded", MESSAGES.PageLoadedEvents());
    map.put("EVENT-WebViewStringChange", MESSAGES.WebViewStringChangeEvents());


/* Methods */

    map.put("METHOD-CanGoBack", MESSAGES.CanGoBackMethods());
    map.put("METHOD-CanGoForward", MESSAGES.CanGoForwardMethods());
    map.put("METHOD-ClearCaches", MESSAGES.ClearCachesMethods());
    map.put("METHOD-ClearCookies", MESSAGES.ClearCookiesMethods());
    map.put("METHOD-ClearLocations", MESSAGES.ClearLocationsMethods());
    map.put("METHOD-GoBack", MESSAGES.GoBackMethods());
    map.put("METHOD-GoForward", MESSAGES.GoForwardMethods());
    map.put("METHOD-GoHome", MESSAGES.GoHomeMethods());
    map.put("METHOD-GoToUrl", MESSAGES.GoToUrlMethods());
    map.put("METHOD-Reload", MESSAGES.ReloadMethods());
    map.put("METHOD-RunJavaScript", MESSAGES.RunJavaScriptMethods());
    map.put("METHOD-StopLoading", MESSAGES.StopLoadingMethods());


/* Parameters */

    map.put("PARAM-url", MESSAGES.urlParams());
    map.put("PARAM-errorCode", MESSAGES.errorCodeParams());
    map.put("PARAM-description", MESSAGES.descriptionParams());
    map.put("PARAM-failingUrl", MESSAGES.failingUrlParams());
    map.put("PARAM-value", MESSAGES.valueParams());
    map.put("PARAM-js", MESSAGES.jsParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


/* Component: YandexTranslate */

    map.put("COMPONENT-YandexTranslate", MESSAGES.yandexTranslateComponentPallette());

    map.put("YandexTranslate-helpString", MESSAGES.YandexTranslateHelpStringComponentPallette());



/* Properties */

    map.put("PROPERTY-ApiKey", MESSAGES.ApiKeyProperties());


/* Events */

    map.put("EVENT-GotTranslation", MESSAGES.GotTranslationEvents());


/* Methods */

    map.put("METHOD-RequestTranslation", MESSAGES.RequestTranslationMethods());


/* Parameters */

    map.put("PARAM-responseCode", MESSAGES.responseCodeParams());
    map.put("PARAM-translation", MESSAGES.translationParams());
    map.put("PARAM-languageToTranslateTo", MESSAGES.languageToTranslateToParams());
    map.put("PARAM-textToTranslate", MESSAGES.textToTranslateParams());
    map.put("PARAM-notAlreadyHandled", MESSAGES.notAlreadyHandledParams());


    /* Descriptions */

    map.put("PROPDESC-AboutScreenPropertyDescriptions", MESSAGES.AboutScreenPropertyDescriptions());
    map.put("PROPDESC-AbsoluteStrengthPropertyDescriptions", MESSAGES.AbsoluteStrengthPropertyDescriptions());
    map.put("EVENTDESC-AccelerationChangedEventDescriptions", MESSAGES.AccelerationChangedEventDescriptions());
    map.put("PROPDESC-AccelerometerSensor.AvailablePropertyDescriptions", MESSAGES.AccelerometerSensor__AvailablePropertyDescriptions());
    map.put("PROPDESC-AccelerometerSensor.EnabledPropertyDescriptions", MESSAGES.AccelerometerSensor__EnabledPropertyDescriptions());
    map.put("PROPDESC-AccelerometerSensor.LegacyModePropertyDescriptions", MESSAGES.AccelerometerSensor__LegacyModePropertyDescriptions());
    map.put("PROPDESC-AccelerometerSensor.MinimumIntervalPropertyDescriptions", MESSAGES.AccelerometerSensor__MinimumIntervalPropertyDescriptions());
    map.put("PROPDESC-AccentColorPropertyDescriptions", MESSAGES.AccentColorPropertyDescriptions());
    map.put("METHODDESC-AcceptConnectionMethodDescriptions", MESSAGES.AcceptConnectionMethodDescriptions());
    map.put("METHODDESC-AcceptConnectionWithUUIDMethodDescriptions", MESSAGES.AcceptConnectionWithUUIDMethodDescriptions());
    map.put("PROPDESC-AccuracyPropertyDescriptions", MESSAGES.AccuracyPropertyDescriptions());
    map.put("PROPDESC-ActionBarPropertyDescriptions", MESSAGES.ActionBarPropertyDescriptions());
    map.put("PROPDESC-ActionPropertyDescriptions", MESSAGES.ActionPropertyDescriptions());
    map.put("EVENTDESC-ActivityCanceledEventDescriptions", MESSAGES.ActivityCanceledEventDescriptions());
    map.put("PROPDESC-ActivityClassPropertyDescriptions", MESSAGES.ActivityClassPropertyDescriptions());
    map.put("EVENTDESC-ActivityErrorEventDescriptions", MESSAGES.ActivityErrorEventDescriptions());
    map.put("PROPDESC-ActivityPackagePropertyDescriptions", MESSAGES.ActivityPackagePropertyDescriptions());
    map.put("PROPDESC-ActivityStarter.ResultPropertyDescriptions", MESSAGES.ActivityStarter__ResultPropertyDescriptions());
    map.put("METHODDESC-AddDaysMethodDescriptions", MESSAGES.AddDaysMethodDescriptions());
    map.put("METHODDESC-AddDurationMethodDescriptions", MESSAGES.AddDurationMethodDescriptions());
    map.put("METHODDESC-AddHoursMethodDescriptions", MESSAGES.AddHoursMethodDescriptions());
    map.put("METHODDESC-AddMinutesMethodDescriptions", MESSAGES.AddMinutesMethodDescriptions());
    map.put("METHODDESC-AddMonthsMethodDescriptions", MESSAGES.AddMonthsMethodDescriptions());
    map.put("METHODDESC-AddSecondsMethodDescriptions", MESSAGES.AddSecondsMethodDescriptions());
    map.put("METHODDESC-AddWeeksMethodDescriptions", MESSAGES.AddWeeksMethodDescriptions());
    map.put("METHODDESC-AddYearsMethodDescriptions", MESSAGES.AddYearsMethodDescriptions());
    map.put("PROPDESC-AddressesAndNamesPropertyDescriptions", MESSAGES.AddressesAndNamesPropertyDescriptions());
    map.put("EVENTDESC-AfterActivityEventDescriptions", MESSAGES.AfterActivityEventDescriptions());
    map.put("EVENTDESC-AfterChoosingEventDescriptions", MESSAGES.AfterChoosingEventDescriptions());
    map.put("EVENTDESC-AfterDateSetEventDescriptions", MESSAGES.AfterDateSetEventDescriptions());
    map.put("EVENTDESC-AfterFileSavedEventDescriptions", MESSAGES.AfterFileSavedEventDescriptions());
    map.put("EVENTDESC-AfterGettingTextEventDescriptions", MESSAGES.AfterGettingTextEventDescriptions());
    map.put("EVENTDESC-AfterPictureEventDescriptions", MESSAGES.AfterPictureEventDescriptions());
    map.put("EVENTDESC-AfterRecordingEventDescriptions", MESSAGES.AfterRecordingEventDescriptions());
    map.put("EVENTDESC-AfterScanEventDescriptions", MESSAGES.AfterScanEventDescriptions());
    map.put("EVENTDESC-AfterSelectingEventDescriptions", MESSAGES.AfterSelectingEventDescriptions());
    map.put("EVENTDESC-AfterSoundRecordedEventDescriptions", MESSAGES.AfterSoundRecordedEventDescriptions());
    map.put("EVENTDESC-AfterSpeakingEventDescriptions", MESSAGES.AfterSpeakingEventDescriptions());
    map.put("EVENTDESC-AfterTextInputEventDescriptions", MESSAGES.AfterTextInputEventDescriptions());
    map.put("EVENTDESC-AfterTimeSetEventDescriptions", MESSAGES.AfterTimeSetEventDescriptions());
    map.put("EVENTDESC-AirPressureChangedEventDescriptions", MESSAGES.AirPressureChangedEventDescriptions());
    map.put("PROPDESC-AirPressurePropertyDescriptions", MESSAGES.AirPressurePropertyDescriptions());
    map.put("PROPDESC-AllowCookiesPropertyDescriptions", MESSAGES.AllowCookiesPropertyDescriptions());
    map.put("PROPDESC-AltitudePropertyDescriptions", MESSAGES.AltitudePropertyDescriptions());
    map.put("PROPDESC-AnchorHorizontalPropertyDescriptions", MESSAGES.AnchorHorizontalPropertyDescriptions());
    map.put("PROPDESC-AnchorVerticalPropertyDescriptions", MESSAGES.AnchorVerticalPropertyDescriptions());
    map.put("PROPDESC-AnglePropertyDescriptions", MESSAGES.AnglePropertyDescriptions());
    map.put("PROPDESC-AnimationPropertyDescriptions", MESSAGES.AnimationPropertyDescriptions());
    map.put("PROPDESC-AppNamePropertyDescriptions", MESSAGES.AppNamePropertyDescriptions());
    map.put("METHODDESC-AppendToFileMethodDescriptions", MESSAGES.AppendToFileMethodDescriptions());
    map.put("METHODDESC-AppendValueMethodDescriptions", MESSAGES.AppendValueMethodDescriptions());
    map.put("METHODDESC-AppendValueToListMethodDescriptions", MESSAGES.AppendValueToListMethodDescriptions());
    map.put("METHODDESC-AskForPermissionMethodDescriptions", MESSAGES.AskForPermissionMethodDescriptions());
    map.put("METHODDESC-AuthorizeMethodDescriptions", MESSAGES.AuthorizeMethodDescriptions());
    map.put("PROPDESC-AvailableCountriesPropertyDescriptions", MESSAGES.AvailableCountriesPropertyDescriptions());
    map.put("PROPDESC-AvailableLanguagesPropertyDescriptions", MESSAGES.AvailableLanguagesPropertyDescriptions());
    map.put("PROPDESC-AvailableProvidersPropertyDescriptions", MESSAGES.AvailableProvidersPropertyDescriptions());
    map.put("PROPDESC-AverageLuxPropertyDescriptions", MESSAGES.AverageLuxPropertyDescriptions());
    map.put("PROPDESC-AzimuthPropertyDescriptions", MESSAGES.AzimuthPropertyDescriptions());
    map.put("EVENTDESC-BackPressedEventDescriptions", MESSAGES.BackPressedEventDescriptions());
    map.put("PROPDESC-BackgroundImageinBase64PropertyDescriptions", MESSAGES.BackgroundImageinBase64PropertyDescriptions());
    map.put("METHODDESC-Ball.BounceMethodDescriptions", MESSAGES.Ball__BounceMethodDescriptions());
    map.put("METHODDESC-Ball.CollidingWithMethodDescriptions", MESSAGES.Ball__CollidingWithMethodDescriptions());
    map.put("EVENTDESC-Ball.DraggedEventDescriptions", MESSAGES.Ball__DraggedEventDescriptions());
    map.put("EVENTDESC-Ball.EdgeReachedEventDescriptions", MESSAGES.Ball__EdgeReachedEventDescriptions());
    map.put("PROPDESC-Ball.EnabledPropertyDescriptions", MESSAGES.Ball__EnabledPropertyDescriptions());
    map.put("EVENTDESC-Ball.FlungEventDescriptions", MESSAGES.Ball__FlungEventDescriptions());
    map.put("PROPDESC-Ball.HeadingPropertyDescriptions", MESSAGES.Ball__HeadingPropertyDescriptions());
    map.put("PROPDESC-Ball.IntervalPropertyDescriptions", MESSAGES.Ball__IntervalPropertyDescriptions());
    map.put("METHODDESC-Ball.MoveIntoBoundsMethodDescriptions", MESSAGES.Ball__MoveIntoBoundsMethodDescriptions());
    map.put("METHODDESC-Ball.MoveToMethodDescriptions", MESSAGES.Ball__MoveToMethodDescriptions());
    map.put("PROPDESC-Ball.PaintColorPropertyDescriptions", MESSAGES.Ball__PaintColorPropertyDescriptions());
    map.put("METHODDESC-Ball.PointInDirectionMethodDescriptions", MESSAGES.Ball__PointInDirectionMethodDescriptions());
    map.put("METHODDESC-Ball.PointTowardsMethodDescriptions", MESSAGES.Ball__PointTowardsMethodDescriptions());
    map.put("PROPDESC-Ball.RadiusPropertyDescriptions", MESSAGES.Ball__RadiusPropertyDescriptions());
    map.put("PROPDESC-Ball.SpeedPropertyDescriptions", MESSAGES.Ball__SpeedPropertyDescriptions());
    map.put("EVENTDESC-Ball.TouchDownEventDescriptions", MESSAGES.Ball__TouchDownEventDescriptions());
    map.put("EVENTDESC-Ball.TouchUpEventDescriptions", MESSAGES.Ball__TouchUpEventDescriptions());
    map.put("EVENTDESC-Ball.TouchedEventDescriptions", MESSAGES.Ball__TouchedEventDescriptions());
    map.put("PROPDESC-Ball.VisiblePropertyDescriptions", MESSAGES.Ball__VisiblePropertyDescriptions());
    map.put("PROPDESC-Ball.XPropertyDescriptions", MESSAGES.Ball__XPropertyDescriptions());
    map.put("PROPDESC-Ball.YPropertyDescriptions", MESSAGES.Ball__YPropertyDescriptions());
    map.put("PROPDESC-Ball.ZPropertyDescriptions", MESSAGES.Ball__ZPropertyDescriptions());
    map.put("PROPDESC-BallotOptionsPropertyDescriptions", MESSAGES.BallotOptionsPropertyDescriptions());
    map.put("PROPDESC-BallotQuestionPropertyDescriptions", MESSAGES.BallotQuestionPropertyDescriptions());
    map.put("PROPDESC-BarcodeScanner.ResultPropertyDescriptions", MESSAGES.BarcodeScanner__ResultPropertyDescriptions());
    map.put("PROPDESC-Barometer.AvailablePropertyDescriptions", MESSAGES.Barometer__AvailablePropertyDescriptions());
    map.put("PROPDESC-Barometer.EnabledPropertyDescriptions", MESSAGES.Barometer__EnabledPropertyDescriptions());
    map.put("PROPDESC-BaudRatePropertyDescriptions", MESSAGES.BaudRatePropertyDescriptions());
    map.put("METHODDESC-BearingToFeatureMethodDescriptions", MESSAGES.BearingToFeatureMethodDescriptions());
    map.put("METHODDESC-BearingToPointMethodDescriptions", MESSAGES.BearingToPointMethodDescriptions());
    map.put("EVENTDESC-BeforeGettingTextEventDescriptions", MESSAGES.BeforeGettingTextEventDescriptions());
    map.put("EVENTDESC-BeforePageLoadEventDescriptions", MESSAGES.BeforePageLoadEventDescriptions());
    map.put("EVENTDESC-BeforeSpeakingEventDescriptions", MESSAGES.BeforeSpeakingEventDescriptions());
    map.put("PROPDESC-BlocksToolkitPropertyDescriptions", MESSAGES.BlocksToolkitPropertyDescriptions());
    map.put("PROPDESC-BluetoothClientPropertyDescriptions", MESSAGES.BluetoothClientPropertyDescriptions());
    map.put("PROPDESC-BluetoothClient.AvailablePropertyDescriptions", MESSAGES.BluetoothClient__AvailablePropertyDescriptions());
    map.put("PROPDESC-BluetoothClient.EnabledPropertyDescriptions", MESSAGES.BluetoothClient__EnabledPropertyDescriptions());
    map.put("EVENTDESC-BluetoothErrorEventDescriptions", MESSAGES.BluetoothErrorEventDescriptions());
    map.put("PROPDESC-BluetoothServer.AvailablePropertyDescriptions", MESSAGES.BluetoothServer__AvailablePropertyDescriptions());
    map.put("PROPDESC-BluetoothServer.EnabledPropertyDescriptions", MESSAGES.BluetoothServer__EnabledPropertyDescriptions());
    map.put("PROPDESC-BottomOfRangePropertyDescriptions", MESSAGES.BottomOfRangePropertyDescriptions());
    map.put("PROPDESC-BoundingBoxPropertyDescriptions", MESSAGES.BoundingBoxPropertyDescriptions());
    map.put("EVENTDESC-BoundsChangeEventDescriptions", MESSAGES.BoundsChangeEventDescriptions());
    map.put("METHODDESC-BoundsMethodDescriptions", MESSAGES.BoundsMethodDescriptions());
    map.put("PROPDESC-BufferSizePropertyDescriptions", MESSAGES.BufferSizePropertyDescriptions());
    map.put("METHODDESC-BuildRequestDataMethodDescriptions", MESSAGES.BuildRequestDataMethodDescriptions());
    map.put("PROPDESC-Button.BackgroundColorPropertyDescriptions", MESSAGES.Button__BackgroundColorPropertyDescriptions());
    map.put("EVENTDESC-Button.ClickEventDescriptions", MESSAGES.Button__ClickEventDescriptions());
    map.put("PROPDESC-Button.EnabledPropertyDescriptions", MESSAGES.Button__EnabledPropertyDescriptions());
    map.put("PROPDESC-Button.FontBoldPropertyDescriptions", MESSAGES.Button__FontBoldPropertyDescriptions());
    map.put("PROPDESC-Button.FontItalicPropertyDescriptions", MESSAGES.Button__FontItalicPropertyDescriptions());
    map.put("PROPDESC-Button.FontSizePropertyDescriptions", MESSAGES.Button__FontSizePropertyDescriptions());
    map.put("PROPDESC-Button.FontTypefacePropertyDescriptions", MESSAGES.Button__FontTypefacePropertyDescriptions());
    map.put("EVENTDESC-Button.GotFocusEventDescriptions", MESSAGES.Button__GotFocusEventDescriptions());
    map.put("PROPDESC-Button.HeightPercentPropertyDescriptions", MESSAGES.Button__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-Button.HeightPropertyDescriptions", MESSAGES.Button__HeightPropertyDescriptions());
    map.put("PROPDESC-Button.ImagePropertyDescriptions", MESSAGES.Button__ImagePropertyDescriptions());
    map.put("EVENTDESC-Button.LongClickEventDescriptions", MESSAGES.Button__LongClickEventDescriptions());
    map.put("EVENTDESC-Button.LostFocusEventDescriptions", MESSAGES.Button__LostFocusEventDescriptions());
    map.put("PROPDESC-Button.ShapePropertyDescriptions", MESSAGES.Button__ShapePropertyDescriptions());
    map.put("PROPDESC-Button.ShowFeedbackPropertyDescriptions", MESSAGES.Button__ShowFeedbackPropertyDescriptions());
    map.put("PROPDESC-Button.TextAlignmentPropertyDescriptions", MESSAGES.Button__TextAlignmentPropertyDescriptions());
    map.put("PROPDESC-Button.TextColorPropertyDescriptions", MESSAGES.Button__TextColorPropertyDescriptions());
    map.put("PROPDESC-Button.TextPropertyDescriptions", MESSAGES.Button__TextPropertyDescriptions());
    map.put("EVENTDESC-Button.TouchDownEventDescriptions", MESSAGES.Button__TouchDownEventDescriptions());
    map.put("EVENTDESC-Button.TouchUpEventDescriptions", MESSAGES.Button__TouchUpEventDescriptions());
    map.put("PROPDESC-Button.VisiblePropertyDescriptions", MESSAGES.Button__VisiblePropertyDescriptions());
    map.put("PROPDESC-Button.WidthPercentPropertyDescriptions", MESSAGES.Button__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-Button.WidthPropertyDescriptions", MESSAGES.Button__WidthPropertyDescriptions());
    map.put("METHODDESC-BytesAvailableToReceiveMethodDescriptions", MESSAGES.BytesAvailableToReceiveMethodDescriptions());
    map.put("PROPDESC-CalibrateStrideLengthPropertyDescriptions", MESSAGES.CalibrateStrideLengthPropertyDescriptions());
    map.put("EVENTDESC-CalibrationFailedEventDescriptions", MESSAGES.CalibrationFailedEventDescriptions());
    map.put("METHODDESC-CanGoBackMethodDescriptions", MESSAGES.CanGoBackMethodDescriptions());
    map.put("METHODDESC-CanGoForwardMethodDescriptions", MESSAGES.CanGoForwardMethodDescriptions());
    map.put("PROPDESC-Canvas.BackgroundColorPropertyDescriptions", MESSAGES.Canvas__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-Canvas.BackgroundImagePropertyDescriptions", MESSAGES.Canvas__BackgroundImagePropertyDescriptions());
    map.put("EVENTDESC-Canvas.DraggedEventDescriptions", MESSAGES.Canvas__DraggedEventDescriptions());
    map.put("METHODDESC-Canvas.DrawCircleMethodDescriptions", MESSAGES.Canvas__DrawCircleMethodDescriptions());
    map.put("METHODDESC-Canvas.DrawLineMethodDescriptions", MESSAGES.Canvas__DrawLineMethodDescriptions());
    map.put("METHODDESC-Canvas.DrawPointMethodDescriptions", MESSAGES.Canvas__DrawPointMethodDescriptions());
    map.put("EVENTDESC-Canvas.FlungEventDescriptions", MESSAGES.Canvas__FlungEventDescriptions());
    map.put("PROPDESC-Canvas.FontSizePropertyDescriptions", MESSAGES.Canvas__FontSizePropertyDescriptions());
    map.put("PROPDESC-Canvas.HeightPercentPropertyDescriptions", MESSAGES.Canvas__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-Canvas.HeightPropertyDescriptions", MESSAGES.Canvas__HeightPropertyDescriptions());
    map.put("PROPDESC-Canvas.PaintColorPropertyDescriptions", MESSAGES.Canvas__PaintColorPropertyDescriptions());
    map.put("METHODDESC-Canvas.SaveMethodDescriptions", MESSAGES.Canvas__SaveMethodDescriptions());
    map.put("PROPDESC-Canvas.TextAlignmentPropertyDescriptions", MESSAGES.Canvas__TextAlignmentPropertyDescriptions());
    map.put("EVENTDESC-Canvas.TouchDownEventDescriptions", MESSAGES.Canvas__TouchDownEventDescriptions());
    map.put("EVENTDESC-Canvas.TouchUpEventDescriptions", MESSAGES.Canvas__TouchUpEventDescriptions());
    map.put("EVENTDESC-Canvas.TouchedEventDescriptions", MESSAGES.Canvas__TouchedEventDescriptions());
    map.put("PROPDESC-Canvas.VisiblePropertyDescriptions", MESSAGES.Canvas__VisiblePropertyDescriptions());
    map.put("PROPDESC-Canvas.WidthPercentPropertyDescriptions", MESSAGES.Canvas__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-Canvas.WidthPropertyDescriptions", MESSAGES.Canvas__WidthPropertyDescriptions());
    map.put("PROPDESC-CenterFromStringPropertyDescriptions", MESSAGES.CenterFromStringPropertyDescriptions());
    map.put("METHODDESC-CenterMethodDescriptions", MESSAGES.CenterMethodDescriptions());
    map.put("METHODDESC-CentroidMethodDescriptions", MESSAGES.CentroidMethodDescriptions());
    map.put("PROPDESC-CharacterEncodingPropertyDescriptions", MESSAGES.CharacterEncodingPropertyDescriptions());
    map.put("METHODDESC-CheckAuthorizedMethodDescriptions", MESSAGES.CheckAuthorizedMethodDescriptions());
    map.put("PROPDESC-CheckBox.BackgroundColorPropertyDescriptions", MESSAGES.CheckBox__BackgroundColorPropertyDescriptions());
    map.put("EVENTDESC-CheckBox.ChangedEventDescriptions", MESSAGES.CheckBox__ChangedEventDescriptions());
    map.put("PROPDESC-CheckBox.EnabledPropertyDescriptions", MESSAGES.CheckBox__EnabledPropertyDescriptions());
    map.put("PROPDESC-CheckBox.FontBoldPropertyDescriptions", MESSAGES.CheckBox__FontBoldPropertyDescriptions());
    map.put("PROPDESC-CheckBox.FontItalicPropertyDescriptions", MESSAGES.CheckBox__FontItalicPropertyDescriptions());
    map.put("PROPDESC-CheckBox.FontSizePropertyDescriptions", MESSAGES.CheckBox__FontSizePropertyDescriptions());
    map.put("PROPDESC-CheckBox.FontTypefacePropertyDescriptions", MESSAGES.CheckBox__FontTypefacePropertyDescriptions());
    map.put("EVENTDESC-CheckBox.GotFocusEventDescriptions", MESSAGES.CheckBox__GotFocusEventDescriptions());
    map.put("PROPDESC-CheckBox.HeightPercentPropertyDescriptions", MESSAGES.CheckBox__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-CheckBox.HeightPropertyDescriptions", MESSAGES.CheckBox__HeightPropertyDescriptions());
    map.put("EVENTDESC-CheckBox.LostFocusEventDescriptions", MESSAGES.CheckBox__LostFocusEventDescriptions());
    map.put("PROPDESC-CheckBox.TextColorPropertyDescriptions", MESSAGES.CheckBox__TextColorPropertyDescriptions());
    map.put("PROPDESC-CheckBox.TextPropertyDescriptions", MESSAGES.CheckBox__TextPropertyDescriptions());
    map.put("PROPDESC-CheckBox.VisiblePropertyDescriptions", MESSAGES.CheckBox__VisiblePropertyDescriptions());
    map.put("PROPDESC-CheckBox.WidthPercentPropertyDescriptions", MESSAGES.CheckBox__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-CheckBox.WidthPropertyDescriptions", MESSAGES.CheckBox__WidthPropertyDescriptions());
    map.put("PROPDESC-CheckedPropertyDescriptions", MESSAGES.CheckedPropertyDescriptions());
    map.put("EVENTDESC-ChoosingCanceledEventDescriptions", MESSAGES.ChoosingCanceledEventDescriptions());
    map.put("EVENTDESC-Circle.ClickEventDescriptions", MESSAGES.Circle__ClickEventDescriptions());
    map.put("PROPDESC-Circle.DescriptionPropertyDescriptions", MESSAGES.Circle__DescriptionPropertyDescriptions());
    map.put("METHODDESC-Circle.DistanceToPointMethodDescriptions", MESSAGES.Circle__DistanceToPointMethodDescriptions());
    map.put("EVENTDESC-Circle.DragEventDescriptions", MESSAGES.Circle__DragEventDescriptions());
    map.put("PROPDESC-Circle.DraggablePropertyDescriptions", MESSAGES.Circle__DraggablePropertyDescriptions());
    map.put("PROPDESC-Circle.EnableInfoboxPropertyDescriptions", MESSAGES.Circle__EnableInfoboxPropertyDescriptions());
    map.put("PROPDESC-Circle.FillColorPropertyDescriptions", MESSAGES.Circle__FillColorPropertyDescriptions());
    map.put("PROPDESC-Circle.LatitudePropertyDescriptions", MESSAGES.Circle__LatitudePropertyDescriptions());
    map.put("EVENTDESC-Circle.LongClickEventDescriptions", MESSAGES.Circle__LongClickEventDescriptions());
    map.put("PROPDESC-Circle.LongitudePropertyDescriptions", MESSAGES.Circle__LongitudePropertyDescriptions());
    map.put("PROPDESC-Circle.RadiusPropertyDescriptions", MESSAGES.Circle__RadiusPropertyDescriptions());
    map.put("METHODDESC-Circle.SetLocationMethodDescriptions", MESSAGES.Circle__SetLocationMethodDescriptions());
    map.put("METHODDESC-Circle.ShowInfoboxMethodDescriptions", MESSAGES.Circle__ShowInfoboxMethodDescriptions());
    map.put("PROPDESC-Circle.StrokeColorPropertyDescriptions", MESSAGES.Circle__StrokeColorPropertyDescriptions());
    map.put("PROPDESC-Circle.StrokeWidthPropertyDescriptions", MESSAGES.Circle__StrokeWidthPropertyDescriptions());
    map.put("PROPDESC-Circle.TitlePropertyDescriptions", MESSAGES.Circle__TitlePropertyDescriptions());
    map.put("PROPDESC-Circle.TypePropertyDescriptions", MESSAGES.Circle__TypePropertyDescriptions());
    map.put("PROPDESC-Circle.VisiblePropertyDescriptions", MESSAGES.Circle__VisiblePropertyDescriptions());
    map.put("EVENTDESC-ClassifierReadyEventDescriptions", MESSAGES.ClassifierReadyEventDescriptions());
    map.put("METHODDESC-ClassifyImageDataMethodDescriptions", MESSAGES.ClassifyImageDataMethodDescriptions());
    map.put("METHODDESC-ClassifyVideoDataMethodDescriptions", MESSAGES.ClassifyVideoDataMethodDescriptions());
    map.put("METHODDESC-ClearAllMethodDescriptions", MESSAGES.ClearAllMethodDescriptions());
    map.put("METHODDESC-ClearCachesMethodDescriptions", MESSAGES.ClearCachesMethodDescriptions());
    map.put("METHODDESC-ClearLocationsMethodDescriptions", MESSAGES.ClearLocationsMethodDescriptions());
    map.put("METHODDESC-ClearMethodDescriptions", MESSAGES.ClearMethodDescriptions());
    map.put("PROPDESC-ClickablePropertyDescriptions", MESSAGES.ClickablePropertyDescriptions());
    map.put("PROPDESC-CloseScreenAnimationPropertyDescriptions", MESSAGES.CloseScreenAnimationPropertyDescriptions());
    map.put("METHODDESC-CloseSerialMethodDescriptions", MESSAGES.CloseSerialMethodDescriptions());
    map.put("METHODDESC-CloudConnectedMethodDescriptions", MESSAGES.CloudConnectedMethodDescriptions());
    map.put("EVENTDESC-CloudDBErrorEventDescriptions", MESSAGES.CloudDBErrorEventDescriptions());
    map.put("METHODDESC-CloudDB.ClearTagMethodDescriptions", MESSAGES.CloudDB__ClearTagMethodDescriptions());
    map.put("EVENTDESC-CloudDB.DataChangedEventDescriptions", MESSAGES.CloudDB__DataChangedEventDescriptions());
    map.put("EVENTDESC-CloudDB.FirstRemovedEventDescriptions", MESSAGES.CloudDB__FirstRemovedEventDescriptions());
    map.put("METHODDESC-CloudDB.GetValueMethodDescriptions", MESSAGES.CloudDB__GetValueMethodDescriptions());
    map.put("EVENTDESC-CloudDB.GotValueEventDescriptions", MESSAGES.CloudDB__GotValueEventDescriptions());
    map.put("METHODDESC-CloudDB.StoreValueMethodDescriptions", MESSAGES.CloudDB__StoreValueMethodDescriptions());
    map.put("EVENTDESC-CollidedWithEventDescriptions", MESSAGES.CollidedWithEventDescriptions());
    map.put("PROPDESC-ColorLeftPropertyDescriptions", MESSAGES.ColorLeftPropertyDescriptions());
    map.put("PROPDESC-ColorRightPropertyDescriptions", MESSAGES.ColorRightPropertyDescriptions());
    map.put("PROPDESC-ColumnPropertyDescriptions", MESSAGES.ColumnPropertyDescriptions());
    map.put("PROPDESC-ColumnsPropertyDescriptions", MESSAGES.ColumnsPropertyDescriptions());
    map.put("METHODDESC-ConnectMethodDescriptions", MESSAGES.ConnectMethodDescriptions());
    map.put("METHODDESC-ConnectWithUUIDMethodDescriptions", MESSAGES.ConnectWithUUIDMethodDescriptions());
    map.put("EVENTDESC-ConnectionAcceptedEventDescriptions", MESSAGES.ConnectionAcceptedEventDescriptions());
    map.put("PROPDESC-ConsumerKeyPropertyDescriptions", MESSAGES.ConsumerKeyPropertyDescriptions());
    map.put("PROPDESC-ConsumerSecretPropertyDescriptions", MESSAGES.ConsumerSecretPropertyDescriptions());
    map.put("PROPDESC-ContactNamePropertyDescriptions", MESSAGES.ContactNamePropertyDescriptions());
    map.put("EVENTDESC-ContactPicker.AfterPickingEventDescriptions", MESSAGES.ContactPicker__AfterPickingEventDescriptions());
    map.put("PROPDESC-ContactPicker.BackgroundColorPropertyDescriptions", MESSAGES.ContactPicker__BackgroundColorPropertyDescriptions());
    map.put("EVENTDESC-ContactPicker.BeforePickingEventDescriptions", MESSAGES.ContactPicker__BeforePickingEventDescriptions());
    map.put("PROPDESC-ContactPicker.EnabledPropertyDescriptions", MESSAGES.ContactPicker__EnabledPropertyDescriptions());
    map.put("PROPDESC-ContactPicker.FontBoldPropertyDescriptions", MESSAGES.ContactPicker__FontBoldPropertyDescriptions());
    map.put("PROPDESC-ContactPicker.FontItalicPropertyDescriptions", MESSAGES.ContactPicker__FontItalicPropertyDescriptions());
    map.put("PROPDESC-ContactPicker.FontSizePropertyDescriptions", MESSAGES.ContactPicker__FontSizePropertyDescriptions());
    map.put("PROPDESC-ContactPicker.FontTypefacePropertyDescriptions", MESSAGES.ContactPicker__FontTypefacePropertyDescriptions());
    map.put("EVENTDESC-ContactPicker.GotFocusEventDescriptions", MESSAGES.ContactPicker__GotFocusEventDescriptions());
    map.put("PROPDESC-ContactPicker.HeightPercentPropertyDescriptions", MESSAGES.ContactPicker__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-ContactPicker.HeightPropertyDescriptions", MESSAGES.ContactPicker__HeightPropertyDescriptions());
    map.put("PROPDESC-ContactPicker.ImagePropertyDescriptions", MESSAGES.ContactPicker__ImagePropertyDescriptions());
    map.put("EVENTDESC-ContactPicker.LostFocusEventDescriptions", MESSAGES.ContactPicker__LostFocusEventDescriptions());
    map.put("METHODDESC-ContactPicker.OpenMethodDescriptions", MESSAGES.ContactPicker__OpenMethodDescriptions());
    map.put("PROPDESC-ContactPicker.PhoneNumberPropertyDescriptions", MESSAGES.ContactPicker__PhoneNumberPropertyDescriptions());
    map.put("PROPDESC-ContactPicker.PicturePropertyDescriptions", MESSAGES.ContactPicker__PicturePropertyDescriptions());
    map.put("PROPDESC-ContactPicker.ShapePropertyDescriptions", MESSAGES.ContactPicker__ShapePropertyDescriptions());
    map.put("PROPDESC-ContactPicker.ShowFeedbackPropertyDescriptions", MESSAGES.ContactPicker__ShowFeedbackPropertyDescriptions());
    map.put("PROPDESC-ContactPicker.TextAlignmentPropertyDescriptions", MESSAGES.ContactPicker__TextAlignmentPropertyDescriptions());
    map.put("PROPDESC-ContactPicker.TextColorPropertyDescriptions", MESSAGES.ContactPicker__TextColorPropertyDescriptions());
    map.put("PROPDESC-ContactPicker.TextPropertyDescriptions", MESSAGES.ContactPicker__TextPropertyDescriptions());
    map.put("EVENTDESC-ContactPicker.TouchDownEventDescriptions", MESSAGES.ContactPicker__TouchDownEventDescriptions());
    map.put("EVENTDESC-ContactPicker.TouchUpEventDescriptions", MESSAGES.ContactPicker__TouchUpEventDescriptions());
    map.put("PROPDESC-ContactPicker.VisiblePropertyDescriptions", MESSAGES.ContactPicker__VisiblePropertyDescriptions());
    map.put("PROPDESC-ContactPicker.WidthPercentPropertyDescriptions", MESSAGES.ContactPicker__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-ContactPicker.WidthPropertyDescriptions", MESSAGES.ContactPicker__WidthPropertyDescriptions());
    map.put("PROPDESC-ContactUriPropertyDescriptions", MESSAGES.ContactUriPropertyDescriptions());
    map.put("PROPDESC-CountryPropertyDescriptions", MESSAGES.CountryPropertyDescriptions());
    map.put("METHODDESC-CreateMarkerMethodDescriptions", MESSAGES.CreateMarkerMethodDescriptions());
    map.put("PROPDESC-CurrentAddressPropertyDescriptions", MESSAGES.CurrentAddressPropertyDescriptions());
    map.put("PROPDESC-CurrentPageTitlePropertyDescriptions", MESSAGES.CurrentPageTitlePropertyDescriptions());
    map.put("PROPDESC-CurrentUrlPropertyDescriptions", MESSAGES.CurrentUrlPropertyDescriptions());
    map.put("PROPDESC-DataTypePropertyDescriptions", MESSAGES.DataTypePropertyDescriptions());
    map.put("PROPDESC-DataUriPropertyDescriptions", MESSAGES.DataUriPropertyDescriptions());
    map.put("PROPDESC-DatePicker.BackgroundColorPropertyDescriptions", MESSAGES.DatePicker__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-DatePicker.EnabledPropertyDescriptions", MESSAGES.DatePicker__EnabledPropertyDescriptions());
    map.put("PROPDESC-DatePicker.FontBoldPropertyDescriptions", MESSAGES.DatePicker__FontBoldPropertyDescriptions());
    map.put("PROPDESC-DatePicker.FontItalicPropertyDescriptions", MESSAGES.DatePicker__FontItalicPropertyDescriptions());
    map.put("PROPDESC-DatePicker.FontSizePropertyDescriptions", MESSAGES.DatePicker__FontSizePropertyDescriptions());
    map.put("PROPDESC-DatePicker.FontTypefacePropertyDescriptions", MESSAGES.DatePicker__FontTypefacePropertyDescriptions());
    map.put("EVENTDESC-DatePicker.GotFocusEventDescriptions", MESSAGES.DatePicker__GotFocusEventDescriptions());
    map.put("PROPDESC-DatePicker.HeightPercentPropertyDescriptions", MESSAGES.DatePicker__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-DatePicker.HeightPropertyDescriptions", MESSAGES.DatePicker__HeightPropertyDescriptions());
    map.put("PROPDESC-DatePicker.ImagePropertyDescriptions", MESSAGES.DatePicker__ImagePropertyDescriptions());
    map.put("PROPDESC-DatePicker.InstantPropertyDescriptions", MESSAGES.DatePicker__InstantPropertyDescriptions());
    map.put("METHODDESC-DatePicker.LaunchPickerMethodDescriptions", MESSAGES.DatePicker__LaunchPickerMethodDescriptions());
    map.put("EVENTDESC-DatePicker.LostFocusEventDescriptions", MESSAGES.DatePicker__LostFocusEventDescriptions());
    map.put("PROPDESC-DatePicker.ShapePropertyDescriptions", MESSAGES.DatePicker__ShapePropertyDescriptions());
    map.put("PROPDESC-DatePicker.ShowFeedbackPropertyDescriptions", MESSAGES.DatePicker__ShowFeedbackPropertyDescriptions());
    map.put("PROPDESC-DatePicker.TextAlignmentPropertyDescriptions", MESSAGES.DatePicker__TextAlignmentPropertyDescriptions());
    map.put("PROPDESC-DatePicker.TextColorPropertyDescriptions", MESSAGES.DatePicker__TextColorPropertyDescriptions());
    map.put("PROPDESC-DatePicker.TextPropertyDescriptions", MESSAGES.DatePicker__TextPropertyDescriptions());
    map.put("EVENTDESC-DatePicker.TouchDownEventDescriptions", MESSAGES.DatePicker__TouchDownEventDescriptions());
    map.put("EVENTDESC-DatePicker.TouchUpEventDescriptions", MESSAGES.DatePicker__TouchUpEventDescriptions());
    map.put("PROPDESC-DatePicker.VisiblePropertyDescriptions", MESSAGES.DatePicker__VisiblePropertyDescriptions());
    map.put("PROPDESC-DatePicker.WidthPercentPropertyDescriptions", MESSAGES.DatePicker__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-DatePicker.WidthPropertyDescriptions", MESSAGES.DatePicker__WidthPropertyDescriptions());
    map.put("METHODDESC-DayOfMonthMethodDescriptions", MESSAGES.DayOfMonthMethodDescriptions());
    map.put("PROPDESC-DayPropertyDescriptions", MESSAGES.DayPropertyDescriptions());
    map.put("METHODDESC-DeAuthorizeMethodDescriptions", MESSAGES.DeAuthorizeMethodDescriptions());
    map.put("PROPDESC-DefaultRedisServerPropertyDescriptions", MESSAGES.DefaultRedisServerPropertyDescriptions());
    map.put("PROPDESC-DefaultURLPropertyDescriptions", MESSAGES.DefaultURLPropertyDescriptions());
    map.put("METHODDESC-DeleteFileMethodDescriptions", MESSAGES.DeleteFileMethodDescriptions());
    map.put("PROPDESC-DelimiterBytePropertyDescriptions", MESSAGES.DelimiterBytePropertyDescriptions());
    map.put("PROPDESC-DetectColorPropertyDescriptions", MESSAGES.DetectColorPropertyDescriptions());
    map.put("PROPDESC-DeveloperBucketPropertyDescriptions", MESSAGES.DeveloperBucketPropertyDescriptions());
    map.put("METHODDESC-DirectMessageMethodDescriptions", MESSAGES.DirectMessageMethodDescriptions());
    map.put("PROPDESC-DirectMessagesPropertyDescriptions", MESSAGES.DirectMessagesPropertyDescriptions());
    map.put("EVENTDESC-DirectMessagesReceivedEventDescriptions", MESSAGES.DirectMessagesReceivedEventDescriptions());
    map.put("METHODDESC-DisconnectMethodDescriptions", MESSAGES.DisconnectMethodDescriptions());
    map.put("PROPDESC-DisconnectOnErrorPropertyDescriptions", MESSAGES.DisconnectOnErrorPropertyDescriptions());
    map.put("METHODDESC-DismissProgressDialogMethodDescriptions", MESSAGES.DismissProgressDialogMethodDescriptions());
    map.put("METHODDESC-DisplayDropdownMethodDescriptions", MESSAGES.DisplayDropdownMethodDescriptions());
    map.put("PROPDESC-DistanceIntervalPropertyDescriptions", MESSAGES.DistanceIntervalPropertyDescriptions());
    map.put("METHODDESC-DistanceToFeatureMethodDescriptions", MESSAGES.DistanceToFeatureMethodDescriptions());
    map.put("METHODDESC-DoQueryMethodDescriptions", MESSAGES.DoQueryMethodDescriptions());
    map.put("METHODDESC-DoScanMethodDescriptions", MESSAGES.DoScanMethodDescriptions());
    map.put("EVENTDESC-DoubleTapAtPointEventDescriptions", MESSAGES.DoubleTapAtPointEventDescriptions());
    map.put("METHODDESC-DownloadFileMethodDescriptions", MESSAGES.DownloadFileMethodDescriptions());
    map.put("METHODDESC-DrawArcMethodDescriptions", MESSAGES.DrawArcMethodDescriptions());
    map.put("METHODDESC-DrawIconMethodDescriptions", MESSAGES.DrawIconMethodDescriptions());
    map.put("METHODDESC-DrawRectMethodDescriptions", MESSAGES.DrawRectMethodDescriptions());
    map.put("METHODDESC-DrawShapeMethodDescriptions", MESSAGES.DrawShapeMethodDescriptions());
    map.put("METHODDESC-DrawTextAtAngleMethodDescriptions", MESSAGES.DrawTextAtAngleMethodDescriptions());
    map.put("METHODDESC-DrawTextMethodDescriptions", MESSAGES.DrawTextMethodDescriptions());
    map.put("PROPDESC-DriveMotorsPropertyDescriptions", MESSAGES.DriveMotorsPropertyDescriptions());
    map.put("METHODDESC-DurationMethodDescriptions", MESSAGES.DurationMethodDescriptions());
    map.put("METHODDESC-DurationToDaysMethodDescriptions", MESSAGES.DurationToDaysMethodDescriptions());
    map.put("METHODDESC-DurationToHoursMethodDescriptions", MESSAGES.DurationToHoursMethodDescriptions());
    map.put("METHODDESC-DurationToMinutesMethodDescriptions", MESSAGES.DurationToMinutesMethodDescriptions());
    map.put("METHODDESC-DurationToSecondsMethodDescriptions", MESSAGES.DurationToSecondsMethodDescriptions());
    map.put("METHODDESC-DurationToWeeksMethodDescriptions", MESSAGES.DurationToWeeksMethodDescriptions());
    map.put("PROPDESC-EastLongitudePropertyDescriptions", MESSAGES.EastLongitudePropertyDescriptions());
    map.put("PROPDESC-ElapsedTimePropertyDescriptions", MESSAGES.ElapsedTimePropertyDescriptions());
    map.put("PROPDESC-EmailAddressListPropertyDescriptions", MESSAGES.EmailAddressListPropertyDescriptions());
    map.put("PROPDESC-EmailAddressPropertyDescriptions", MESSAGES.EmailAddressPropertyDescriptions());
    map.put("PROPDESC-EmailPicker.BackgroundColorPropertyDescriptions", MESSAGES.EmailPicker__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-EmailPicker.EnabledPropertyDescriptions", MESSAGES.EmailPicker__EnabledPropertyDescriptions());
    map.put("PROPDESC-EmailPicker.FontBoldPropertyDescriptions", MESSAGES.EmailPicker__FontBoldPropertyDescriptions());
    map.put("PROPDESC-EmailPicker.FontItalicPropertyDescriptions", MESSAGES.EmailPicker__FontItalicPropertyDescriptions());
    map.put("PROPDESC-EmailPicker.FontSizePropertyDescriptions", MESSAGES.EmailPicker__FontSizePropertyDescriptions());
    map.put("PROPDESC-EmailPicker.FontTypefacePropertyDescriptions", MESSAGES.EmailPicker__FontTypefacePropertyDescriptions());
    map.put("EVENTDESC-EmailPicker.GotFocusEventDescriptions", MESSAGES.EmailPicker__GotFocusEventDescriptions());
    map.put("PROPDESC-EmailPicker.HeightPercentPropertyDescriptions", MESSAGES.EmailPicker__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-EmailPicker.HeightPropertyDescriptions", MESSAGES.EmailPicker__HeightPropertyDescriptions());
    map.put("PROPDESC-EmailPicker.HintPropertyDescriptions", MESSAGES.EmailPicker__HintPropertyDescriptions());
    map.put("EVENTDESC-EmailPicker.LostFocusEventDescriptions", MESSAGES.EmailPicker__LostFocusEventDescriptions());
    map.put("METHODDESC-EmailPicker.RequestFocusMethodDescriptions", MESSAGES.EmailPicker__RequestFocusMethodDescriptions());
    map.put("PROPDESC-EmailPicker.TextAlignmentPropertyDescriptions", MESSAGES.EmailPicker__TextAlignmentPropertyDescriptions());
    map.put("PROPDESC-EmailPicker.TextColorPropertyDescriptions", MESSAGES.EmailPicker__TextColorPropertyDescriptions());
    map.put("PROPDESC-EmailPicker.TextPropertyDescriptions", MESSAGES.EmailPicker__TextPropertyDescriptions());
    map.put("PROPDESC-EmailPicker.VisiblePropertyDescriptions", MESSAGES.EmailPicker__VisiblePropertyDescriptions());
    map.put("PROPDESC-EmailPicker.WidthPercentPropertyDescriptions", MESSAGES.EmailPicker__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-EmailPicker.WidthPropertyDescriptions", MESSAGES.EmailPicker__WidthPropertyDescriptions());
    map.put("PROPDESC-EnablePanPropertyDescriptions", MESSAGES.EnablePanPropertyDescriptions());
    map.put("PROPDESC-EnableRotationPropertyDescriptions", MESSAGES.EnableRotationPropertyDescriptions());
    map.put("PROPDESC-EnableSpeedRegulationPropertyDescriptions", MESSAGES.EnableSpeedRegulationPropertyDescriptions());
    map.put("PROPDESC-EnableZoomPropertyDescriptions", MESSAGES.EnableZoomPropertyDescriptions());
    map.put("PROPDESC-EndLatitudePropertyDescriptions", MESSAGES.EndLatitudePropertyDescriptions());
    map.put("PROPDESC-EndLocationPropertyDescriptions", MESSAGES.EndLocationPropertyDescriptions());
    map.put("PROPDESC-EndLongitudePropertyDescriptions", MESSAGES.EndLongitudePropertyDescriptions());
    map.put("EVENTDESC-ErrorEventDescriptions", MESSAGES.ErrorEventDescriptions());
    map.put("EVENTDESC-Ev3ColorSensor.AboveRangeEventDescriptions", MESSAGES.Ev3ColorSensor__AboveRangeEventDescriptions());
    map.put("PROPDESC-Ev3ColorSensor.AboveRangeEventEnabledPropertyDescriptions", MESSAGES.Ev3ColorSensor__AboveRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-Ev3ColorSensor.BelowRangeEventDescriptions", MESSAGES.Ev3ColorSensor__BelowRangeEventDescriptions());
    map.put("PROPDESC-Ev3ColorSensor.BelowRangeEventEnabledPropertyDescriptions", MESSAGES.Ev3ColorSensor__BelowRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-Ev3ColorSensor.ColorChangedEventDescriptions", MESSAGES.Ev3ColorSensor__ColorChangedEventDescriptions());
    map.put("PROPDESC-Ev3ColorSensor.ColorChangedEventEnabledPropertyDescriptions", MESSAGES.Ev3ColorSensor__ColorChangedEventEnabledPropertyDescriptions());
    map.put("METHODDESC-Ev3ColorSensor.GetLightLevelMethodDescriptions", MESSAGES.Ev3ColorSensor__GetLightLevelMethodDescriptions());
    map.put("EVENTDESC-Ev3ColorSensor.WithinRangeEventDescriptions", MESSAGES.Ev3ColorSensor__WithinRangeEventDescriptions());
    map.put("PROPDESC-Ev3ColorSensor.WithinRangeEventEnabledPropertyDescriptions", MESSAGES.Ev3ColorSensor__WithinRangeEventEnabledPropertyDescriptions());
    map.put("METHODDESC-Ev3Commands.GetFirmwareVersionMethodDescriptions", MESSAGES.Ev3Commands__GetFirmwareVersionMethodDescriptions());
    map.put("METHODDESC-Ev3Commands.KeepAliveMethodDescriptions", MESSAGES.Ev3Commands__KeepAliveMethodDescriptions());
    map.put("PROPDESC-Ev3Motors.StopBeforeDisconnectPropertyDescriptions", MESSAGES.Ev3Motors__StopBeforeDisconnectPropertyDescriptions());
    map.put("METHODDESC-Ev3Motors.StopMethodDescriptions", MESSAGES.Ev3Motors__StopMethodDescriptions());
    map.put("PROPDESC-Ev3Motors.WheelDiameterPropertyDescriptions", MESSAGES.Ev3Motors__WheelDiameterPropertyDescriptions());
    map.put("EVENTDESC-Ev3TouchSensor.PressedEventDescriptions", MESSAGES.Ev3TouchSensor__PressedEventDescriptions());
    map.put("PROPDESC-Ev3TouchSensor.PressedEventEnabledPropertyDescriptions", MESSAGES.Ev3TouchSensor__PressedEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-Ev3TouchSensor.ReleasedEventDescriptions", MESSAGES.Ev3TouchSensor__ReleasedEventDescriptions());
    map.put("METHODDESC-Ev3UI.DrawCircleMethodDescriptions", MESSAGES.Ev3UI__DrawCircleMethodDescriptions());
    map.put("METHODDESC-Ev3UI.DrawLineMethodDescriptions", MESSAGES.Ev3UI__DrawLineMethodDescriptions());
    map.put("METHODDESC-Ev3UI.DrawPointMethodDescriptions", MESSAGES.Ev3UI__DrawPointMethodDescriptions());
    map.put("EVENTDESC-Ev3UltrasonicSensor.AboveRangeEventDescriptions", MESSAGES.Ev3UltrasonicSensor__AboveRangeEventDescriptions());
    map.put("PROPDESC-Ev3UltrasonicSensor.AboveRangeEventEnabledPropertyDescriptions", MESSAGES.Ev3UltrasonicSensor__AboveRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-Ev3UltrasonicSensor.BelowRangeEventDescriptions", MESSAGES.Ev3UltrasonicSensor__BelowRangeEventDescriptions());
    map.put("PROPDESC-Ev3UltrasonicSensor.BelowRangeEventEnabledPropertyDescriptions", MESSAGES.Ev3UltrasonicSensor__BelowRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-Ev3UltrasonicSensor.WithinRangeEventDescriptions", MESSAGES.Ev3UltrasonicSensor__WithinRangeEventDescriptions());
    map.put("PROPDESC-Ev3UltrasonicSensor.WithinRangeEventEnabledPropertyDescriptions", MESSAGES.Ev3UltrasonicSensor__WithinRangeEventEnabledPropertyDescriptions());
    map.put("PROPDESC-ExtendMovesOutsideCanvasPropertyDescriptions", MESSAGES.ExtendMovesOutsideCanvasPropertyDescriptions());
    map.put("PROPDESC-ExtraKeyPropertyDescriptions", MESSAGES.ExtraKeyPropertyDescriptions());
    map.put("PROPDESC-ExtraValuePropertyDescriptions", MESSAGES.ExtraValuePropertyDescriptions());
    map.put("PROPDESC-ExtrasPropertyDescriptions", MESSAGES.ExtrasPropertyDescriptions());
    map.put("EVENTDESC-FeatureClickEventDescriptions", MESSAGES.FeatureClickEventDescriptions());
    map.put("PROPDESC-FeatureCollection.FeaturesPropertyDescriptions", MESSAGES.FeatureCollection__FeaturesPropertyDescriptions());
    map.put("PROPDESC-FeatureCollection.HeightPercentPropertyDescriptions", MESSAGES.FeatureCollection__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-FeatureCollection.HeightPropertyDescriptions", MESSAGES.FeatureCollection__HeightPropertyDescriptions());
    map.put("PROPDESC-FeatureCollection.SourcePropertyDescriptions", MESSAGES.FeatureCollection__SourcePropertyDescriptions());
    map.put("PROPDESC-FeatureCollection.VisiblePropertyDescriptions", MESSAGES.FeatureCollection__VisiblePropertyDescriptions());
    map.put("PROPDESC-FeatureCollection.WidthPercentPropertyDescriptions", MESSAGES.FeatureCollection__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-FeatureCollection.WidthPropertyDescriptions", MESSAGES.FeatureCollection__WidthPropertyDescriptions());
    map.put("EVENTDESC-FeatureDragEventDescriptions", MESSAGES.FeatureDragEventDescriptions());
    map.put("METHODDESC-FeatureFromDescriptionMethodDescriptions", MESSAGES.FeatureFromDescriptionMethodDescriptions());
    map.put("EVENTDESC-FeatureLongClickEventDescriptions", MESSAGES.FeatureLongClickEventDescriptions());
    map.put("EVENTDESC-FeatureStartDragEventDescriptions", MESSAGES.FeatureStartDragEventDescriptions());
    map.put("EVENTDESC-FeatureStopDragEventDescriptions", MESSAGES.FeatureStopDragEventDescriptions());
    map.put("PROPDESC-FeaturesFromGeoJSONPropertyDescriptions", MESSAGES.FeaturesFromGeoJSONPropertyDescriptions());
    map.put("METHODDESC-File.DeleteMethodDescriptions", MESSAGES.File__DeleteMethodDescriptions());
    map.put("EVENTDESC-File.GotTextEventDescriptions", MESSAGES.File__GotTextEventDescriptions());
    map.put("PROPDESC-File.LegacyModePropertyDescriptions", MESSAGES.File__LegacyModePropertyDescriptions());
    map.put("PROPDESC-FillOpacityPropertyDescriptions", MESSAGES.FillOpacityPropertyDescriptions());
    map.put("METHODDESC-FillScreenMethodDescriptions", MESSAGES.FillScreenMethodDescriptions());
    map.put("METHODDESC-FirebaseDB.ClearTagMethodDescriptions", MESSAGES.FirebaseDB__ClearTagMethodDescriptions());
    map.put("EVENTDESC-FirebaseDB.DataChangedEventDescriptions", MESSAGES.FirebaseDB__DataChangedEventDescriptions());
    map.put("EVENTDESC-FirebaseDB.FirstRemovedEventDescriptions", MESSAGES.FirebaseDB__FirstRemovedEventDescriptions());
    map.put("METHODDESC-FirebaseDB.GetValueMethodDescriptions", MESSAGES.FirebaseDB__GetValueMethodDescriptions());
    map.put("EVENTDESC-FirebaseDB.GotValueEventDescriptions", MESSAGES.FirebaseDB__GotValueEventDescriptions());
    map.put("METHODDESC-FirebaseDB.StoreValueMethodDescriptions", MESSAGES.FirebaseDB__StoreValueMethodDescriptions());
    map.put("EVENTDESC-FirebaseErrorEventDescriptions", MESSAGES.FirebaseErrorEventDescriptions());
    map.put("PROPDESC-FirebaseTokenPropertyDescriptions", MESSAGES.FirebaseTokenPropertyDescriptions());
    map.put("PROPDESC-FirebaseURLPropertyDescriptions", MESSAGES.FirebaseURLPropertyDescriptions());
    map.put("PROPDESC-FollowLinksPropertyDescriptions", MESSAGES.FollowLinksPropertyDescriptions());
    map.put("METHODDESC-FollowMethodDescriptions", MESSAGES.FollowMethodDescriptions());
    map.put("PROPDESC-FollowersPropertyDescriptions", MESSAGES.FollowersPropertyDescriptions());
    map.put("EVENTDESC-FollowersReceivedEventDescriptions", MESSAGES.FollowersReceivedEventDescriptions());
    map.put("METHODDESC-ForgetLoginMethodDescriptions", MESSAGES.ForgetLoginMethodDescriptions());
    map.put("METHODDESC-FormatDateMethodDescriptions", MESSAGES.FormatDateMethodDescriptions());
    map.put("METHODDESC-FormatDateTimeMethodDescriptions", MESSAGES.FormatDateTimeMethodDescriptions());
    map.put("METHODDESC-FormatTimeMethodDescriptions", MESSAGES.FormatTimeMethodDescriptions());
    map.put("PROPDESC-FriendTimelinePropertyDescriptions", MESSAGES.FriendTimelinePropertyDescriptions());
    map.put("EVENTDESC-FriendTimelineReceivedEventDescriptions", MESSAGES.FriendTimelineReceivedEventDescriptions());
    map.put("PROPDESC-FullScreenPropertyDescriptions", MESSAGES.FullScreenPropertyDescriptions());
    map.put("EVENTDESC-FunctionCompletedEventDescriptions", MESSAGES.FunctionCompletedEventDescriptions());
    map.put("PROPDESC-FusiontablesControl.ApiKeyPropertyDescriptions", MESSAGES.FusiontablesControl__ApiKeyPropertyDescriptions());
    map.put("EVENTDESC-GPSAvailableEventDescriptions", MESSAGES.GPSAvailableEventDescriptions());
    map.put("EVENTDESC-GPSLostEventDescriptions", MESSAGES.GPSLostEventDescriptions());
    map.put("METHODDESC-GameClient.SendMessageMethodDescriptions", MESSAGES.GameClient__SendMessageMethodDescriptions());
    map.put("PROPDESC-GameClient.ServiceURLPropertyDescriptions", MESSAGES.GameClient__ServiceURLPropertyDescriptions());
    map.put("PROPDESC-GameClient.UserEmailAddressPropertyDescriptions", MESSAGES.GameClient__UserEmailAddressPropertyDescriptions());
    map.put("EVENTDESC-GameClient.WebServiceErrorEventDescriptions", MESSAGES.GameClient__WebServiceErrorEventDescriptions());
    map.put("PROPDESC-GameIdPropertyDescriptions", MESSAGES.GameIdPropertyDescriptions());
    map.put("PROPDESC-GenerateColorPropertyDescriptions", MESSAGES.GenerateColorPropertyDescriptions());
    map.put("PROPDESC-GenerateLightPropertyDescriptions", MESSAGES.GenerateLightPropertyDescriptions());
    map.put("METHODDESC-GetBackgroundPixelColorMethodDescriptions", MESSAGES.GetBackgroundPixelColorMethodDescriptions());
    map.put("METHODDESC-GetBatteryCurrentMethodDescriptions", MESSAGES.GetBatteryCurrentMethodDescriptions());
    map.put("METHODDESC-GetBatteryLevelMethodDescriptions", MESSAGES.GetBatteryLevelMethodDescriptions());
    map.put("METHODDESC-GetBatteryVoltageMethodDescriptions", MESSAGES.GetBatteryVoltageMethodDescriptions());
    map.put("METHODDESC-GetBrickNameMethodDescriptions", MESSAGES.GetBrickNameMethodDescriptions());
    map.put("METHODDESC-GetColorCodeMethodDescriptions", MESSAGES.GetColorCodeMethodDescriptions());
    map.put("METHODDESC-GetColorMethodDescriptions", MESSAGES.GetColorMethodDescriptions());
    map.put("METHODDESC-GetColorNameMethodDescriptions", MESSAGES.GetColorNameMethodDescriptions());
    map.put("METHODDESC-GetCurrentProgramNameMethodDescriptions", MESSAGES.GetCurrentProgramNameMethodDescriptions());
    map.put("METHODDESC-GetDistanceMethodDescriptions", MESSAGES.GetDistanceMethodDescriptions());
    map.put("METHODDESC-GetDurationMethodDescriptions", MESSAGES.GetDurationMethodDescriptions());
    map.put("METHODDESC-GetFirmwareBuildMethodDescriptions", MESSAGES.GetFirmwareBuildMethodDescriptions());
    map.put("METHODDESC-GetHardwareVersionMethodDescriptions", MESSAGES.GetHardwareVersionMethodDescriptions());
    map.put("METHODDESC-GetInputValuesMethodDescriptions", MESSAGES.GetInputValuesMethodDescriptions());
    map.put("METHODDESC-GetInstallerMethodDescriptions", MESSAGES.GetInstallerMethodDescriptions());
    map.put("METHODDESC-GetInstanceListsMethodDescriptions", MESSAGES.GetInstanceListsMethodDescriptions());
    map.put("METHODDESC-GetMessagesMethodDescriptions", MESSAGES.GetMessagesMethodDescriptions());
    map.put("METHODDESC-GetMethodDescriptions", MESSAGES.GetMethodDescriptions());
    map.put("METHODDESC-GetMillisMethodDescriptions", MESSAGES.GetMillisMethodDescriptions());
    map.put("METHODDESC-GetOSBuildMethodDescriptions", MESSAGES.GetOSBuildMethodDescriptions());
    map.put("METHODDESC-GetOSVersionMethodDescriptions", MESSAGES.GetOSVersionMethodDescriptions());
    map.put("METHODDESC-GetOutputStateMethodDescriptions", MESSAGES.GetOutputStateMethodDescriptions());
    map.put("METHODDESC-GetPixelColorMethodDescriptions", MESSAGES.GetPixelColorMethodDescriptions());
    map.put("METHODDESC-GetRowsMethodDescriptions", MESSAGES.GetRowsMethodDescriptions());
    map.put("METHODDESC-GetRowsWithConditionsMethodDescriptions", MESSAGES.GetRowsWithConditionsMethodDescriptions());
    map.put("METHODDESC-GetSensorValueMethodDescriptions", MESSAGES.GetSensorValueMethodDescriptions());
    map.put("METHODDESC-GetSoundLevelMethodDescriptions", MESSAGES.GetSoundLevelMethodDescriptions());
    map.put("METHODDESC-GetTachoCountMethodDescriptions", MESSAGES.GetTachoCountMethodDescriptions());
    map.put("METHODDESC-GetTagListMethodDescriptions", MESSAGES.GetTagListMethodDescriptions());
    map.put("METHODDESC-GetTagsMethodDescriptions", MESSAGES.GetTagsMethodDescriptions());
    map.put("METHODDESC-GetTextMethodDescriptions", MESSAGES.GetTextMethodDescriptions());
    map.put("METHODDESC-GetVersionNameMethodDescriptions", MESSAGES.GetVersionNameMethodDescriptions());
    map.put("METHODDESC-GetWifiIpAddressMethodDescriptions", MESSAGES.GetWifiIpAddressMethodDescriptions());
    map.put("METHODDESC-GoBackMethodDescriptions", MESSAGES.GoBackMethodDescriptions());
    map.put("METHODDESC-GoForwardMethodDescriptions", MESSAGES.GoForwardMethodDescriptions());
    map.put("METHODDESC-GoHomeMethodDescriptions", MESSAGES.GoHomeMethodDescriptions());
    map.put("METHODDESC-GoToUrlMethodDescriptions", MESSAGES.GoToUrlMethodDescriptions());
    map.put("PROPDESC-GoogleVoiceEnabledPropertyDescriptions", MESSAGES.GoogleVoiceEnabledPropertyDescriptions());
    map.put("EVENTDESC-GotBallotConfirmationEventDescriptions", MESSAGES.GotBallotConfirmationEventDescriptions());
    map.put("EVENTDESC-GotBallotEventDescriptions", MESSAGES.GotBallotEventDescriptions());
    map.put("EVENTDESC-GotClassificationEventDescriptions", MESSAGES.GotClassificationEventDescriptions());
    map.put("EVENTDESC-GotDirectionsEventDescriptions", MESSAGES.GotDirectionsEventDescriptions());
    map.put("EVENTDESC-GotFeaturesEventDescriptions", MESSAGES.GotFeaturesEventDescriptions());
    map.put("EVENTDESC-GotFileEventDescriptions", MESSAGES.GotFileEventDescriptions());
    map.put("EVENTDESC-GotMessageEventDescriptions", MESSAGES.GotMessageEventDescriptions());
    map.put("EVENTDESC-GotResultEventDescriptions", MESSAGES.GotResultEventDescriptions());
    map.put("EVENTDESC-GotTranslationEventDescriptions", MESSAGES.GotTranslationEventDescriptions());
    map.put("EVENTDESC-GyroscopeChangedEventDescriptions", MESSAGES.GyroscopeChangedEventDescriptions());
    map.put("PROPDESC-GyroscopeSensor.AvailablePropertyDescriptions", MESSAGES.GyroscopeSensor__AvailablePropertyDescriptions());
    map.put("PROPDESC-GyroscopeSensor.EnabledPropertyDescriptions", MESSAGES.GyroscopeSensor__EnabledPropertyDescriptions());
    map.put("PROPDESC-HTMLContentPropertyDescriptions", MESSAGES.HTMLContentPropertyDescriptions());
    map.put("PROPDESC-HTMLFormatPropertyDescriptions", MESSAGES.HTMLFormatPropertyDescriptions());
    map.put("PROPDESC-HasAccuracyPropertyDescriptions", MESSAGES.HasAccuracyPropertyDescriptions());
    map.put("PROPDESC-HasAltitudePropertyDescriptions", MESSAGES.HasAltitudePropertyDescriptions());
    map.put("PROPDESC-HasLongitudeLatitudePropertyDescriptions", MESSAGES.HasLongitudeLatitudePropertyDescriptions());
    map.put("PROPDESC-HasMarginsPropertyDescriptions", MESSAGES.HasMarginsPropertyDescriptions());
    map.put("METHODDESC-HideInfoboxMethodDescriptions", MESSAGES.HideInfoboxMethodDescriptions());
    map.put("PROPDESC-HighByteFirstPropertyDescriptions", MESSAGES.HighByteFirstPropertyDescriptions());
    map.put("PROPDESC-HolePointsFromStringPropertyDescriptions", MESSAGES.HolePointsFromStringPropertyDescriptions());
    map.put("PROPDESC-HolePointsPropertyDescriptions", MESSAGES.HolePointsPropertyDescriptions());
    map.put("PROPDESC-HomeUrlPropertyDescriptions", MESSAGES.HomeUrlPropertyDescriptions());
    map.put("PROPDESC-HorizontalArrangement.AlignHorizontalPropertyDescriptions", MESSAGES.HorizontalArrangement__AlignHorizontalPropertyDescriptions());
    map.put("PROPDESC-HorizontalArrangement.AlignVerticalPropertyDescriptions", MESSAGES.HorizontalArrangement__AlignVerticalPropertyDescriptions());
    map.put("PROPDESC-HorizontalArrangement.BackgroundColorPropertyDescriptions", MESSAGES.HorizontalArrangement__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-HorizontalArrangement.HeightPercentPropertyDescriptions", MESSAGES.HorizontalArrangement__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-HorizontalArrangement.HeightPropertyDescriptions", MESSAGES.HorizontalArrangement__HeightPropertyDescriptions());
    map.put("PROPDESC-HorizontalArrangement.ImagePropertyDescriptions", MESSAGES.HorizontalArrangement__ImagePropertyDescriptions());
    map.put("PROPDESC-HorizontalArrangement.VisiblePropertyDescriptions", MESSAGES.HorizontalArrangement__VisiblePropertyDescriptions());
    map.put("PROPDESC-HorizontalArrangement.WidthPercentPropertyDescriptions", MESSAGES.HorizontalArrangement__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-HorizontalArrangement.WidthPropertyDescriptions", MESSAGES.HorizontalArrangement__WidthPropertyDescriptions());
    map.put("PROPDESC-HorizontalScrollArrangement.AlignHorizontalPropertyDescriptions", MESSAGES.HorizontalScrollArrangement__AlignHorizontalPropertyDescriptions());
    map.put("PROPDESC-HorizontalScrollArrangement.AlignVerticalPropertyDescriptions", MESSAGES.HorizontalScrollArrangement__AlignVerticalPropertyDescriptions());
    map.put("PROPDESC-HorizontalScrollArrangement.BackgroundColorPropertyDescriptions", MESSAGES.HorizontalScrollArrangement__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-HorizontalScrollArrangement.HeightPercentPropertyDescriptions", MESSAGES.HorizontalScrollArrangement__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-HorizontalScrollArrangement.HeightPropertyDescriptions", MESSAGES.HorizontalScrollArrangement__HeightPropertyDescriptions());
    map.put("PROPDESC-HorizontalScrollArrangement.ImagePropertyDescriptions", MESSAGES.HorizontalScrollArrangement__ImagePropertyDescriptions());
    map.put("PROPDESC-HorizontalScrollArrangement.VisiblePropertyDescriptions", MESSAGES.HorizontalScrollArrangement__VisiblePropertyDescriptions());
    map.put("PROPDESC-HorizontalScrollArrangement.WidthPercentPropertyDescriptions", MESSAGES.HorizontalScrollArrangement__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-HorizontalScrollArrangement.WidthPropertyDescriptions", MESSAGES.HorizontalScrollArrangement__WidthPropertyDescriptions());
    map.put("METHODDESC-HourMethodDescriptions", MESSAGES.HourMethodDescriptions());
    map.put("PROPDESC-HourPropertyDescriptions", MESSAGES.HourPropertyDescriptions());
    map.put("METHODDESC-HtmlTextDecodeMethodDescriptions", MESSAGES.HtmlTextDecodeMethodDescriptions());
    map.put("EVENTDESC-HumidityChangedEventDescriptions", MESSAGES.HumidityChangedEventDescriptions());
    map.put("PROPDESC-HumidityPropertyDescriptions", MESSAGES.HumidityPropertyDescriptions());
    map.put("PROPDESC-Hygrometer.AvailablePropertyDescriptions", MESSAGES.Hygrometer__AvailablePropertyDescriptions());
    map.put("PROPDESC-Hygrometer.EnabledPropertyDescriptions", MESSAGES.Hygrometer__EnabledPropertyDescriptions());
    map.put("PROPDESC-IconPropertyDescriptions", MESSAGES.IconPropertyDescriptions());
    map.put("PROPDESC-IgnoreSslErrorsPropertyDescriptions", MESSAGES.IgnoreSslErrorsPropertyDescriptions());
    map.put("PROPDESC-ImageAssetPropertyDescriptions", MESSAGES.ImageAssetPropertyDescriptions());
    map.put("EVENTDESC-ImagePicker.AfterPickingEventDescriptions", MESSAGES.ImagePicker__AfterPickingEventDescriptions());
    map.put("PROPDESC-ImagePicker.BackgroundColorPropertyDescriptions", MESSAGES.ImagePicker__BackgroundColorPropertyDescriptions());
    map.put("EVENTDESC-ImagePicker.BeforePickingEventDescriptions", MESSAGES.ImagePicker__BeforePickingEventDescriptions());
    map.put("PROPDESC-ImagePicker.EnabledPropertyDescriptions", MESSAGES.ImagePicker__EnabledPropertyDescriptions());
    map.put("PROPDESC-ImagePicker.FontBoldPropertyDescriptions", MESSAGES.ImagePicker__FontBoldPropertyDescriptions());
    map.put("PROPDESC-ImagePicker.FontItalicPropertyDescriptions", MESSAGES.ImagePicker__FontItalicPropertyDescriptions());
    map.put("PROPDESC-ImagePicker.FontSizePropertyDescriptions", MESSAGES.ImagePicker__FontSizePropertyDescriptions());
    map.put("PROPDESC-ImagePicker.FontTypefacePropertyDescriptions", MESSAGES.ImagePicker__FontTypefacePropertyDescriptions());
    map.put("EVENTDESC-ImagePicker.GotFocusEventDescriptions", MESSAGES.ImagePicker__GotFocusEventDescriptions());
    map.put("PROPDESC-ImagePicker.HeightPercentPropertyDescriptions", MESSAGES.ImagePicker__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-ImagePicker.HeightPropertyDescriptions", MESSAGES.ImagePicker__HeightPropertyDescriptions());
    map.put("PROPDESC-ImagePicker.ImagePropertyDescriptions", MESSAGES.ImagePicker__ImagePropertyDescriptions());
    map.put("EVENTDESC-ImagePicker.LostFocusEventDescriptions", MESSAGES.ImagePicker__LostFocusEventDescriptions());
    map.put("METHODDESC-ImagePicker.OpenMethodDescriptions", MESSAGES.ImagePicker__OpenMethodDescriptions());
    map.put("PROPDESC-ImagePicker.SelectionPropertyDescriptions", MESSAGES.ImagePicker__SelectionPropertyDescriptions());
    map.put("PROPDESC-ImagePicker.ShapePropertyDescriptions", MESSAGES.ImagePicker__ShapePropertyDescriptions());
    map.put("PROPDESC-ImagePicker.ShowFeedbackPropertyDescriptions", MESSAGES.ImagePicker__ShowFeedbackPropertyDescriptions());
    map.put("PROPDESC-ImagePicker.TextAlignmentPropertyDescriptions", MESSAGES.ImagePicker__TextAlignmentPropertyDescriptions());
    map.put("PROPDESC-ImagePicker.TextColorPropertyDescriptions", MESSAGES.ImagePicker__TextColorPropertyDescriptions());
    map.put("PROPDESC-ImagePicker.TextPropertyDescriptions", MESSAGES.ImagePicker__TextPropertyDescriptions());
    map.put("EVENTDESC-ImagePicker.TouchDownEventDescriptions", MESSAGES.ImagePicker__TouchDownEventDescriptions());
    map.put("EVENTDESC-ImagePicker.TouchUpEventDescriptions", MESSAGES.ImagePicker__TouchUpEventDescriptions());
    map.put("PROPDESC-ImagePicker.VisiblePropertyDescriptions", MESSAGES.ImagePicker__VisiblePropertyDescriptions());
    map.put("PROPDESC-ImagePicker.WidthPercentPropertyDescriptions", MESSAGES.ImagePicker__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-ImagePicker.WidthPropertyDescriptions", MESSAGES.ImagePicker__WidthPropertyDescriptions());
    map.put("METHODDESC-ImageSprite.BounceMethodDescriptions", MESSAGES.ImageSprite__BounceMethodDescriptions());
    map.put("METHODDESC-ImageSprite.CollidingWithMethodDescriptions", MESSAGES.ImageSprite__CollidingWithMethodDescriptions());
    map.put("EVENTDESC-ImageSprite.DraggedEventDescriptions", MESSAGES.ImageSprite__DraggedEventDescriptions());
    map.put("EVENTDESC-ImageSprite.EdgeReachedEventDescriptions", MESSAGES.ImageSprite__EdgeReachedEventDescriptions());
    map.put("PROPDESC-ImageSprite.EnabledPropertyDescriptions", MESSAGES.ImageSprite__EnabledPropertyDescriptions());
    map.put("EVENTDESC-ImageSprite.FlungEventDescriptions", MESSAGES.ImageSprite__FlungEventDescriptions());
    map.put("PROPDESC-ImageSprite.HeadingPropertyDescriptions", MESSAGES.ImageSprite__HeadingPropertyDescriptions());
    map.put("PROPDESC-ImageSprite.HeightPropertyDescriptions", MESSAGES.ImageSprite__HeightPropertyDescriptions());
    map.put("PROPDESC-ImageSprite.IntervalPropertyDescriptions", MESSAGES.ImageSprite__IntervalPropertyDescriptions());
    map.put("METHODDESC-ImageSprite.MoveIntoBoundsMethodDescriptions", MESSAGES.ImageSprite__MoveIntoBoundsMethodDescriptions());
    map.put("METHODDESC-ImageSprite.MoveToMethodDescriptions", MESSAGES.ImageSprite__MoveToMethodDescriptions());
    map.put("PROPDESC-ImageSprite.PicturePropertyDescriptions", MESSAGES.ImageSprite__PicturePropertyDescriptions());
    map.put("METHODDESC-ImageSprite.PointInDirectionMethodDescriptions", MESSAGES.ImageSprite__PointInDirectionMethodDescriptions());
    map.put("METHODDESC-ImageSprite.PointTowardsMethodDescriptions", MESSAGES.ImageSprite__PointTowardsMethodDescriptions());
    map.put("PROPDESC-ImageSprite.SpeedPropertyDescriptions", MESSAGES.ImageSprite__SpeedPropertyDescriptions());
    map.put("EVENTDESC-ImageSprite.TouchDownEventDescriptions", MESSAGES.ImageSprite__TouchDownEventDescriptions());
    map.put("EVENTDESC-ImageSprite.TouchUpEventDescriptions", MESSAGES.ImageSprite__TouchUpEventDescriptions());
    map.put("EVENTDESC-ImageSprite.TouchedEventDescriptions", MESSAGES.ImageSprite__TouchedEventDescriptions());
    map.put("PROPDESC-ImageSprite.VisiblePropertyDescriptions", MESSAGES.ImageSprite__VisiblePropertyDescriptions());
    map.put("PROPDESC-ImageSprite.WidthPropertyDescriptions", MESSAGES.ImageSprite__WidthPropertyDescriptions());
    map.put("PROPDESC-ImageSprite.XPropertyDescriptions", MESSAGES.ImageSprite__XPropertyDescriptions());
    map.put("PROPDESC-ImageSprite.YPropertyDescriptions", MESSAGES.ImageSprite__YPropertyDescriptions());
    map.put("PROPDESC-ImageSprite.ZPropertyDescriptions", MESSAGES.ImageSprite__ZPropertyDescriptions());
    map.put("EVENTDESC-Image.ClickEventDescriptions", MESSAGES.Image__ClickEventDescriptions());
    map.put("PROPDESC-Image.HeightPercentPropertyDescriptions", MESSAGES.Image__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-Image.HeightPropertyDescriptions", MESSAGES.Image__HeightPropertyDescriptions());
    map.put("PROPDESC-Image.PicturePropertyDescriptions", MESSAGES.Image__PicturePropertyDescriptions());
    map.put("PROPDESC-Image.VisiblePropertyDescriptions", MESSAGES.Image__VisiblePropertyDescriptions());
    map.put("PROPDESC-Image.WidthPercentPropertyDescriptions", MESSAGES.Image__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-Image.WidthPropertyDescriptions", MESSAGES.Image__WidthPropertyDescriptions());
    map.put("EVENTDESC-IncomingCallAnsweredEventDescriptions", MESSAGES.IncomingCallAnsweredEventDescriptions());
    map.put("EVENTDESC-InfoEventDescriptions", MESSAGES.InfoEventDescriptions());
    map.put("EVENTDESC-InitializeEventDescriptions", MESSAGES.InitializeEventDescriptions());
    map.put("METHODDESC-InitializeSerialMethodDescriptions", MESSAGES.InitializeSerialMethodDescriptions());
    map.put("PROPDESC-InputModePropertyDescriptions", MESSAGES.InputModePropertyDescriptions());
    map.put("METHODDESC-InsertRowMethodDescriptions", MESSAGES.InsertRowMethodDescriptions());
    map.put("METHODDESC-InstallationIdMethodDescriptions", MESSAGES.InstallationIdMethodDescriptions());
    map.put("EVENTDESC-InstanceIdChangedEventDescriptions", MESSAGES.InstanceIdChangedEventDescriptions());
    map.put("PROPDESC-InstanceIdPropertyDescriptions", MESSAGES.InstanceIdPropertyDescriptions());
    map.put("EVENTDESC-InvalidPointEventDescriptions", MESSAGES.InvalidPointEventDescriptions());
    map.put("METHODDESC-InviteMethodDescriptions", MESSAGES.InviteMethodDescriptions());
    map.put("EVENTDESC-InvitedEventDescriptions", MESSAGES.InvitedEventDescriptions());
    map.put("PROPDESC-InvitedInstancesPropertyDescriptions", MESSAGES.InvitedInstancesPropertyDescriptions());
    map.put("PROPDESC-IsAcceptingPropertyDescriptions", MESSAGES.IsAcceptingPropertyDescriptions());
    map.put("EVENTDESC-IsAuthorizedEventDescriptions", MESSAGES.IsAuthorizedEventDescriptions());
    map.put("PROPDESC-IsConnectedPropertyDescriptions", MESSAGES.IsConnectedPropertyDescriptions());
    map.put("METHODDESC-IsDevicePairedMethodDescriptions", MESSAGES.IsDevicePairedMethodDescriptions());
    map.put("PROPDESC-IsInitializedPropertyDescriptions", MESSAGES.IsInitializedPropertyDescriptions());
    map.put("PROPDESC-IsOpenPropertyDescriptions", MESSAGES.IsOpenPropertyDescriptions());
    map.put("PROPDESC-IsPlayingPropertyDescriptions", MESSAGES.IsPlayingPropertyDescriptions());
    map.put("METHODDESC-IsPressedMethodDescriptions", MESSAGES.IsPressedMethodDescriptions());
    map.put("PROPDESC-ItemBackgroundColorPropertyDescriptions", MESSAGES.ItemBackgroundColorPropertyDescriptions());
    map.put("PROPDESC-ItemTextColorPropertyDescriptions", MESSAGES.ItemTextColorPropertyDescriptions());
    map.put("PROPDESC-JoinedInstancesPropertyDescriptions", MESSAGES.JoinedInstancesPropertyDescriptions());
    map.put("METHODDESC-JsonObjectEncodeMethodDescriptions", MESSAGES.JsonObjectEncodeMethodDescriptions());
    map.put("METHODDESC-JsonTextDecodeMethodDescriptions", MESSAGES.JsonTextDecodeMethodDescriptions());
    map.put("METHODDESC-JsonTextDecodeWithDictionariesMethodDescriptions", MESSAGES.JsonTextDecodeWithDictionariesMethodDescriptions());
    map.put("PROPDESC-KeepRunningWhenOnPausePropertyDescriptions", MESSAGES.KeepRunningWhenOnPausePropertyDescriptions());
    map.put("PROPDESC-KeyFilePropertyDescriptions", MESSAGES.KeyFilePropertyDescriptions());
    map.put("PROPDESC-Label.BackgroundColorPropertyDescriptions", MESSAGES.Label__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-Label.FontBoldPropertyDescriptions", MESSAGES.Label__FontBoldPropertyDescriptions());
    map.put("PROPDESC-Label.FontItalicPropertyDescriptions", MESSAGES.Label__FontItalicPropertyDescriptions());
    map.put("PROPDESC-Label.FontSizePropertyDescriptions", MESSAGES.Label__FontSizePropertyDescriptions());
    map.put("PROPDESC-Label.FontTypefacePropertyDescriptions", MESSAGES.Label__FontTypefacePropertyDescriptions());
    map.put("PROPDESC-Label.HeightPercentPropertyDescriptions", MESSAGES.Label__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-Label.HeightPropertyDescriptions", MESSAGES.Label__HeightPropertyDescriptions());
    map.put("PROPDESC-Label.TextAlignmentPropertyDescriptions", MESSAGES.Label__TextAlignmentPropertyDescriptions());
    map.put("PROPDESC-Label.TextColorPropertyDescriptions", MESSAGES.Label__TextColorPropertyDescriptions());
    map.put("PROPDESC-Label.TextPropertyDescriptions", MESSAGES.Label__TextPropertyDescriptions());
    map.put("PROPDESC-Label.VisiblePropertyDescriptions", MESSAGES.Label__VisiblePropertyDescriptions());
    map.put("PROPDESC-Label.WidthPercentPropertyDescriptions", MESSAGES.Label__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-Label.WidthPropertyDescriptions", MESSAGES.Label__WidthPropertyDescriptions());
    map.put("PROPDESC-LastMessagePropertyDescriptions", MESSAGES.LastMessagePropertyDescriptions());
    map.put("METHODDESC-LatitudeFromAddressMethodDescriptions", MESSAGES.LatitudeFromAddressMethodDescriptions());
    map.put("PROPDESC-LeaderPropertyDescriptions", MESSAGES.LeaderPropertyDescriptions());
    map.put("METHODDESC-LeaveInstanceMethodDescriptions", MESSAGES.LeaveInstanceMethodDescriptions());
    map.put("EVENTDESC-LightChangedEventDescriptions", MESSAGES.LightChangedEventDescriptions());
    map.put("PROPDESC-LightSensor.AvailablePropertyDescriptions", MESSAGES.LightSensor__AvailablePropertyDescriptions());
    map.put("PROPDESC-LightSensor.EnabledPropertyDescriptions", MESSAGES.LightSensor__EnabledPropertyDescriptions());
    map.put("EVENTDESC-LineString.ClickEventDescriptions", MESSAGES.LineString__ClickEventDescriptions());
    map.put("PROPDESC-LineString.DescriptionPropertyDescriptions", MESSAGES.LineString__DescriptionPropertyDescriptions());
    map.put("METHODDESC-LineString.DistanceToPointMethodDescriptions", MESSAGES.LineString__DistanceToPointMethodDescriptions());
    map.put("EVENTDESC-LineString.DragEventDescriptions", MESSAGES.LineString__DragEventDescriptions());
    map.put("PROPDESC-LineString.DraggablePropertyDescriptions", MESSAGES.LineString__DraggablePropertyDescriptions());
    map.put("PROPDESC-LineString.EnableInfoboxPropertyDescriptions", MESSAGES.LineString__EnableInfoboxPropertyDescriptions());
    map.put("EVENTDESC-LineString.LongClickEventDescriptions", MESSAGES.LineString__LongClickEventDescriptions());
    map.put("PROPDESC-LineString.PointsFromStringPropertyDescriptions", MESSAGES.LineString__PointsFromStringPropertyDescriptions());
    map.put("PROPDESC-LineString.PointsPropertyDescriptions", MESSAGES.LineString__PointsPropertyDescriptions());
    map.put("METHODDESC-LineString.ShowInfoboxMethodDescriptions", MESSAGES.LineString__ShowInfoboxMethodDescriptions());
    map.put("PROPDESC-LineString.StrokeColorPropertyDescriptions", MESSAGES.LineString__StrokeColorPropertyDescriptions());
    map.put("PROPDESC-LineString.StrokeWidthPropertyDescriptions", MESSAGES.LineString__StrokeWidthPropertyDescriptions());
    map.put("PROPDESC-LineString.TitlePropertyDescriptions", MESSAGES.LineString__TitlePropertyDescriptions());
    map.put("PROPDESC-LineString.TypePropertyDescriptions", MESSAGES.LineString__TypePropertyDescriptions());
    map.put("PROPDESC-LineString.VisiblePropertyDescriptions", MESSAGES.LineString__VisiblePropertyDescriptions());
    map.put("PROPDESC-LineWidthPropertyDescriptions", MESSAGES.LineWidthPropertyDescriptions());
    map.put("METHODDESC-ListFilesMethodDescriptions", MESSAGES.ListFilesMethodDescriptions());
    map.put("EVENTDESC-ListPicker.AfterPickingEventDescriptions", MESSAGES.ListPicker__AfterPickingEventDescriptions());
    map.put("PROPDESC-ListPicker.BackgroundColorPropertyDescriptions", MESSAGES.ListPicker__BackgroundColorPropertyDescriptions());
    map.put("EVENTDESC-ListPicker.BeforePickingEventDescriptions", MESSAGES.ListPicker__BeforePickingEventDescriptions());
    map.put("PROPDESC-ListPicker.ElementsFromStringPropertyDescriptions", MESSAGES.ListPicker__ElementsFromStringPropertyDescriptions());
    map.put("PROPDESC-ListPicker.ElementsPropertyDescriptions", MESSAGES.ListPicker__ElementsPropertyDescriptions());
    map.put("PROPDESC-ListPicker.EnabledPropertyDescriptions", MESSAGES.ListPicker__EnabledPropertyDescriptions());
    map.put("PROPDESC-ListPicker.FontBoldPropertyDescriptions", MESSAGES.ListPicker__FontBoldPropertyDescriptions());
    map.put("PROPDESC-ListPicker.FontItalicPropertyDescriptions", MESSAGES.ListPicker__FontItalicPropertyDescriptions());
    map.put("PROPDESC-ListPicker.FontSizePropertyDescriptions", MESSAGES.ListPicker__FontSizePropertyDescriptions());
    map.put("PROPDESC-ListPicker.FontTypefacePropertyDescriptions", MESSAGES.ListPicker__FontTypefacePropertyDescriptions());
    map.put("EVENTDESC-ListPicker.GotFocusEventDescriptions", MESSAGES.ListPicker__GotFocusEventDescriptions());
    map.put("PROPDESC-ListPicker.HeightPercentPropertyDescriptions", MESSAGES.ListPicker__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-ListPicker.HeightPropertyDescriptions", MESSAGES.ListPicker__HeightPropertyDescriptions());
    map.put("PROPDESC-ListPicker.ImagePropertyDescriptions", MESSAGES.ListPicker__ImagePropertyDescriptions());
    map.put("EVENTDESC-ListPicker.LostFocusEventDescriptions", MESSAGES.ListPicker__LostFocusEventDescriptions());
    map.put("METHODDESC-ListPicker.OpenMethodDescriptions", MESSAGES.ListPicker__OpenMethodDescriptions());
    map.put("PROPDESC-ListPicker.SelectionIndexPropertyDescriptions", MESSAGES.ListPicker__SelectionIndexPropertyDescriptions());
    map.put("PROPDESC-ListPicker.SelectionPropertyDescriptions", MESSAGES.ListPicker__SelectionPropertyDescriptions());
    map.put("PROPDESC-ListPicker.ShapePropertyDescriptions", MESSAGES.ListPicker__ShapePropertyDescriptions());
    map.put("PROPDESC-ListPicker.ShowFeedbackPropertyDescriptions", MESSAGES.ListPicker__ShowFeedbackPropertyDescriptions());
    map.put("PROPDESC-ListPicker.ShowFilterBarPropertyDescriptions", MESSAGES.ListPicker__ShowFilterBarPropertyDescriptions());
    map.put("PROPDESC-ListPicker.TextAlignmentPropertyDescriptions", MESSAGES.ListPicker__TextAlignmentPropertyDescriptions());
    map.put("PROPDESC-ListPicker.TextColorPropertyDescriptions", MESSAGES.ListPicker__TextColorPropertyDescriptions());
    map.put("PROPDESC-ListPicker.TextPropertyDescriptions", MESSAGES.ListPicker__TextPropertyDescriptions());
    map.put("PROPDESC-ListPicker.TitlePropertyDescriptions", MESSAGES.ListPicker__TitlePropertyDescriptions());
    map.put("EVENTDESC-ListPicker.TouchDownEventDescriptions", MESSAGES.ListPicker__TouchDownEventDescriptions());
    map.put("EVENTDESC-ListPicker.TouchUpEventDescriptions", MESSAGES.ListPicker__TouchUpEventDescriptions());
    map.put("PROPDESC-ListPicker.VisiblePropertyDescriptions", MESSAGES.ListPicker__VisiblePropertyDescriptions());
    map.put("PROPDESC-ListPicker.WidthPercentPropertyDescriptions", MESSAGES.ListPicker__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-ListPicker.WidthPropertyDescriptions", MESSAGES.ListPicker__WidthPropertyDescriptions());
    map.put("EVENTDESC-ListView.AfterPickingEventDescriptions", MESSAGES.ListView__AfterPickingEventDescriptions());
    map.put("PROPDESC-ListView.BackgroundColorPropertyDescriptions", MESSAGES.ListView__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-ListView.ElementsFromStringPropertyDescriptions", MESSAGES.ListView__ElementsFromStringPropertyDescriptions());
    map.put("PROPDESC-ListView.ElementsPropertyDescriptions", MESSAGES.ListView__ElementsPropertyDescriptions());
    map.put("PROPDESC-ListView.HeightPercentPropertyDescriptions", MESSAGES.ListView__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-ListView.HeightPropertyDescriptions", MESSAGES.ListView__HeightPropertyDescriptions());
    map.put("PROPDESC-ListView.SelectionIndexPropertyDescriptions", MESSAGES.ListView__SelectionIndexPropertyDescriptions());
    map.put("PROPDESC-ListView.SelectionPropertyDescriptions", MESSAGES.ListView__SelectionPropertyDescriptions());
    map.put("PROPDESC-ListView.ShowFilterBarPropertyDescriptions", MESSAGES.ListView__ShowFilterBarPropertyDescriptions());
    map.put("PROPDESC-ListView.TextColorPropertyDescriptions", MESSAGES.ListView__TextColorPropertyDescriptions());
    map.put("PROPDESC-ListView.VisiblePropertyDescriptions", MESSAGES.ListView__VisiblePropertyDescriptions());
    map.put("PROPDESC-ListView.WidthPercentPropertyDescriptions", MESSAGES.ListView__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-ListView.WidthPropertyDescriptions", MESSAGES.ListView__WidthPropertyDescriptions());
    map.put("EVENTDESC-LoadErrorEventDescriptions", MESSAGES.LoadErrorEventDescriptions());
    map.put("METHODDESC-LoadFromURLMethodDescriptions", MESSAGES.LoadFromURLMethodDescriptions());
    map.put("PROPDESC-LoadingDialogMessagePropertyDescriptions", MESSAGES.LoadingDialogMessagePropertyDescriptions());
    map.put("EVENTDESC-LocationChangedEventDescriptions", MESSAGES.LocationChangedEventDescriptions());
    map.put("PROPDESC-LocationSensorPropertyDescriptions", MESSAGES.LocationSensorPropertyDescriptions());
    map.put("PROPDESC-LocationSensor.EnabledPropertyDescriptions", MESSAGES.LocationSensor__EnabledPropertyDescriptions());
    map.put("PROPDESC-LocationSensor.LatitudePropertyDescriptions", MESSAGES.LocationSensor__LatitudePropertyDescriptions());
    map.put("PROPDESC-LocationSensor.LongitudePropertyDescriptions", MESSAGES.LocationSensor__LongitudePropertyDescriptions());
    map.put("METHODDESC-LogErrorMethodDescriptions", MESSAGES.LogErrorMethodDescriptions());
    map.put("METHODDESC-LogInfoMethodDescriptions", MESSAGES.LogInfoMethodDescriptions());
    map.put("METHODDESC-LogWarningMethodDescriptions", MESSAGES.LogWarningMethodDescriptions());
    map.put("METHODDESC-LoginMethodDescriptions", MESSAGES.LoginMethodDescriptions());
    map.put("EVENTDESC-LongPressAtPointEventDescriptions", MESSAGES.LongPressAtPointEventDescriptions());
    map.put("METHODDESC-LongitudeFromAddressMethodDescriptions", MESSAGES.LongitudeFromAddressMethodDescriptions());
    map.put("PROPDESC-LoopPropertyDescriptions", MESSAGES.LoopPropertyDescriptions());
    map.put("METHODDESC-LsGetStatusMethodDescriptions", MESSAGES.LsGetStatusMethodDescriptions());
    map.put("METHODDESC-LsReadMethodDescriptions", MESSAGES.LsReadMethodDescriptions());
    map.put("METHODDESC-LsWriteMethodDescriptions", MESSAGES.LsWriteMethodDescriptions());
    map.put("PROPDESC-LuxPropertyDescriptions", MESSAGES.LuxPropertyDescriptions());
    map.put("EVENTDESC-MagneticChangedEventDescriptions", MESSAGES.MagneticChangedEventDescriptions());
    map.put("PROPDESC-MagneticFieldSensor.AvailablePropertyDescriptions", MESSAGES.MagneticFieldSensor__AvailablePropertyDescriptions());
    map.put("PROPDESC-MagneticFieldSensor.EnabledPropertyDescriptions", MESSAGES.MagneticFieldSensor__EnabledPropertyDescriptions());
    map.put("PROPDESC-MagneticFieldSensor.MaximumRangePropertyDescriptions", MESSAGES.MagneticFieldSensor__MaximumRangePropertyDescriptions());
    map.put("PROPDESC-MagnitudePropertyDescriptions", MESSAGES.MagnitudePropertyDescriptions());
    map.put("METHODDESC-MakeDateMethodDescriptions", MESSAGES.MakeDateMethodDescriptions());
    map.put("METHODDESC-MakeInstantFromMillisMethodDescriptions", MESSAGES.MakeInstantFromMillisMethodDescriptions());
    map.put("METHODDESC-MakeInstantFromPartsMethodDescriptions", MESSAGES.MakeInstantFromPartsMethodDescriptions());
    map.put("METHODDESC-MakeInstantMethodDescriptions", MESSAGES.MakeInstantMethodDescriptions());
    map.put("METHODDESC-MakeNewInstanceMethodDescriptions", MESSAGES.MakeNewInstanceMethodDescriptions());
    map.put("METHODDESC-MakePhoneCallDirectMethodDescriptions", MESSAGES.MakePhoneCallDirectMethodDescriptions());
    map.put("METHODDESC-MakePhoneCallMethodDescriptions", MESSAGES.MakePhoneCallMethodDescriptions());
    map.put("METHODDESC-MakeTimeMethodDescriptions", MESSAGES.MakeTimeMethodDescriptions());
    map.put("PROPDESC-MapTypePropertyDescriptions", MESSAGES.MapTypePropertyDescriptions());
    map.put("PROPDESC-Map.FeaturesPropertyDescriptions", MESSAGES.Map__FeaturesPropertyDescriptions());
    map.put("PROPDESC-Map.HeightPercentPropertyDescriptions", MESSAGES.Map__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-Map.HeightPropertyDescriptions", MESSAGES.Map__HeightPropertyDescriptions());
    map.put("PROPDESC-Map.LatitudePropertyDescriptions", MESSAGES.Map__LatitudePropertyDescriptions());
    map.put("PROPDESC-Map.LongitudePropertyDescriptions", MESSAGES.Map__LongitudePropertyDescriptions());
    map.put("METHODDESC-Map.SaveMethodDescriptions", MESSAGES.Map__SaveMethodDescriptions());
    map.put("PROPDESC-Map.VisiblePropertyDescriptions", MESSAGES.Map__VisiblePropertyDescriptions());
    map.put("PROPDESC-Map.WidthPercentPropertyDescriptions", MESSAGES.Map__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-Map.WidthPropertyDescriptions", MESSAGES.Map__WidthPropertyDescriptions());
    map.put("EVENTDESC-Marker.ClickEventDescriptions", MESSAGES.Marker__ClickEventDescriptions());
    map.put("PROPDESC-Marker.DescriptionPropertyDescriptions", MESSAGES.Marker__DescriptionPropertyDescriptions());
    map.put("METHODDESC-Marker.DistanceToPointMethodDescriptions", MESSAGES.Marker__DistanceToPointMethodDescriptions());
    map.put("EVENTDESC-Marker.DragEventDescriptions", MESSAGES.Marker__DragEventDescriptions());
    map.put("PROPDESC-Marker.DraggablePropertyDescriptions", MESSAGES.Marker__DraggablePropertyDescriptions());
    map.put("PROPDESC-Marker.EnableInfoboxPropertyDescriptions", MESSAGES.Marker__EnableInfoboxPropertyDescriptions());
    map.put("PROPDESC-Marker.FillColorPropertyDescriptions", MESSAGES.Marker__FillColorPropertyDescriptions());
    map.put("PROPDESC-Marker.HeightPercentPropertyDescriptions", MESSAGES.Marker__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-Marker.HeightPropertyDescriptions", MESSAGES.Marker__HeightPropertyDescriptions());
    map.put("PROPDESC-Marker.LatitudePropertyDescriptions", MESSAGES.Marker__LatitudePropertyDescriptions());
    map.put("EVENTDESC-Marker.LongClickEventDescriptions", MESSAGES.Marker__LongClickEventDescriptions());
    map.put("PROPDESC-Marker.LongitudePropertyDescriptions", MESSAGES.Marker__LongitudePropertyDescriptions());
    map.put("METHODDESC-Marker.SetLocationMethodDescriptions", MESSAGES.Marker__SetLocationMethodDescriptions());
    map.put("METHODDESC-Marker.ShowInfoboxMethodDescriptions", MESSAGES.Marker__ShowInfoboxMethodDescriptions());
    map.put("PROPDESC-Marker.StrokeColorPropertyDescriptions", MESSAGES.Marker__StrokeColorPropertyDescriptions());
    map.put("PROPDESC-Marker.StrokeWidthPropertyDescriptions", MESSAGES.Marker__StrokeWidthPropertyDescriptions());
    map.put("PROPDESC-Marker.TitlePropertyDescriptions", MESSAGES.Marker__TitlePropertyDescriptions());
    map.put("PROPDESC-Marker.TypePropertyDescriptions", MESSAGES.Marker__TypePropertyDescriptions());
    map.put("PROPDESC-Marker.VisiblePropertyDescriptions", MESSAGES.Marker__VisiblePropertyDescriptions());
    map.put("PROPDESC-Marker.WidthPercentPropertyDescriptions", MESSAGES.Marker__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-Marker.WidthPropertyDescriptions", MESSAGES.Marker__WidthPropertyDescriptions());
    map.put("PROPDESC-MaxValuePropertyDescriptions", MESSAGES.MaxValuePropertyDescriptions());
    map.put("PROPDESC-MediaStore.ServiceURLPropertyDescriptions", MESSAGES.MediaStore__ServiceURLPropertyDescriptions());
    map.put("EVENTDESC-MediaStore.WebServiceErrorEventDescriptions", MESSAGES.MediaStore__WebServiceErrorEventDescriptions());
    map.put("EVENTDESC-MediaStoredEventDescriptions", MESSAGES.MediaStoredEventDescriptions());
    map.put("PROPDESC-MentionsPropertyDescriptions", MESSAGES.MentionsPropertyDescriptions());
    map.put("EVENTDESC-MentionsReceivedEventDescriptions", MESSAGES.MentionsReceivedEventDescriptions());
    map.put("PROPDESC-MessagePropertyDescriptions", MESSAGES.MessagePropertyDescriptions());
    map.put("METHODDESC-MessageReadMethodDescriptions", MESSAGES.MessageReadMethodDescriptions());
    map.put("EVENTDESC-MessageReceivedEventDescriptions", MESSAGES.MessageReceivedEventDescriptions());
    map.put("METHODDESC-MessageWriteMethodDescriptions", MESSAGES.MessageWriteMethodDescriptions());
    map.put("PROPDESC-MinValuePropertyDescriptions", MESSAGES.MinValuePropertyDescriptions());
    map.put("METHODDESC-MinuteMethodDescriptions", MESSAGES.MinuteMethodDescriptions());
    map.put("PROPDESC-MinutePropertyDescriptions", MESSAGES.MinutePropertyDescriptions());
    map.put("PROPDESC-ModePropertyDescriptions", MESSAGES.ModePropertyDescriptions());
    map.put("PROPDESC-ModelLabelsPropertyDescriptions", MESSAGES.ModelLabelsPropertyDescriptions());
    map.put("PROPDESC-MonthInTextPropertyDescriptions", MESSAGES.MonthInTextPropertyDescriptions());
    map.put("METHODDESC-MonthMethodDescriptions", MESSAGES.MonthMethodDescriptions());
    map.put("METHODDESC-MonthNameMethodDescriptions", MESSAGES.MonthNameMethodDescriptions());
    map.put("PROPDESC-MonthPropertyDescriptions", MESSAGES.MonthPropertyDescriptions());
    map.put("PROPDESC-MotorPortsPropertyDescriptions", MESSAGES.MotorPortsPropertyDescriptions());
    map.put("METHODDESC-MoveBackwardIndefinitelyMethodDescriptions", MESSAGES.MoveBackwardIndefinitelyMethodDescriptions());
    map.put("METHODDESC-MoveBackwardMethodDescriptions", MESSAGES.MoveBackwardMethodDescriptions());
    map.put("METHODDESC-MoveForwardIndefinitelyMethodDescriptions", MESSAGES.MoveForwardIndefinitelyMethodDescriptions());
    map.put("METHODDESC-MoveForwardMethodDescriptions", MESSAGES.MoveForwardMethodDescriptions());
    map.put("PROPDESC-MovingPropertyDescriptions", MESSAGES.MovingPropertyDescriptions());
    map.put("PROPDESC-MultiLinePropertyDescriptions", MESSAGES.MultiLinePropertyDescriptions());
    map.put("PROPDESC-NamespacePropertyDescriptions", MESSAGES.NamespacePropertyDescriptions());
    map.put("PROPDESC-Navigation.ApiKeyPropertyDescriptions", MESSAGES.Navigation__ApiKeyPropertyDescriptions());
    map.put("PROPDESC-Navigation.LanguagePropertyDescriptions", MESSAGES.Navigation__LanguagePropertyDescriptions());
    map.put("PROPDESC-Navigation.ServiceURLPropertyDescriptions", MESSAGES.Navigation__ServiceURLPropertyDescriptions());
    map.put("EVENTDESC-NewInstanceMadeEventDescriptions", MESSAGES.NewInstanceMadeEventDescriptions());
    map.put("EVENTDESC-NewLeaderEventDescriptions", MESSAGES.NewLeaderEventDescriptions());
    map.put("EVENTDESC-NoLongerCollidingWithEventDescriptions", MESSAGES.NoLongerCollidingWithEventDescriptions());
    map.put("EVENTDESC-NoOpenPollEventDescriptions", MESSAGES.NoOpenPollEventDescriptions());
    map.put("PROPDESC-NorthLatitudePropertyDescriptions", MESSAGES.NorthLatitudePropertyDescriptions());
    map.put("PROPDESC-NotifierLengthPropertyDescriptions", MESSAGES.NotifierLengthPropertyDescriptions());
    map.put("PROPDESC-Notifier.BackgroundColorPropertyDescriptions", MESSAGES.Notifier__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-Notifier.TextColorPropertyDescriptions", MESSAGES.Notifier__TextColorPropertyDescriptions());
    map.put("METHODDESC-NowMethodDescriptions", MESSAGES.NowMethodDescriptions());
    map.put("PROPDESC-NumbersOnlyPropertyDescriptions", MESSAGES.NumbersOnlyPropertyDescriptions());
    map.put("EVENTDESC-NxtColorSensor.AboveRangeEventDescriptions", MESSAGES.NxtColorSensor__AboveRangeEventDescriptions());
    map.put("PROPDESC-NxtColorSensor.AboveRangeEventEnabledPropertyDescriptions", MESSAGES.NxtColorSensor__AboveRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-NxtColorSensor.BelowRangeEventDescriptions", MESSAGES.NxtColorSensor__BelowRangeEventDescriptions());
    map.put("PROPDESC-NxtColorSensor.BelowRangeEventEnabledPropertyDescriptions", MESSAGES.NxtColorSensor__BelowRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-NxtColorSensor.ColorChangedEventDescriptions", MESSAGES.NxtColorSensor__ColorChangedEventDescriptions());
    map.put("PROPDESC-NxtColorSensor.ColorChangedEventEnabledPropertyDescriptions", MESSAGES.NxtColorSensor__ColorChangedEventEnabledPropertyDescriptions());
    map.put("METHODDESC-NxtColorSensor.GetLightLevelMethodDescriptions", MESSAGES.NxtColorSensor__GetLightLevelMethodDescriptions());
    map.put("EVENTDESC-NxtColorSensor.WithinRangeEventDescriptions", MESSAGES.NxtColorSensor__WithinRangeEventDescriptions());
    map.put("PROPDESC-NxtColorSensor.WithinRangeEventEnabledPropertyDescriptions", MESSAGES.NxtColorSensor__WithinRangeEventEnabledPropertyDescriptions());
    map.put("METHODDESC-NxtDirectCommands.GetFirmwareVersionMethodDescriptions", MESSAGES.NxtDirectCommands__GetFirmwareVersionMethodDescriptions());
    map.put("METHODDESC-NxtDirectCommands.KeepAliveMethodDescriptions", MESSAGES.NxtDirectCommands__KeepAliveMethodDescriptions());
    map.put("PROPDESC-NxtDrive.StopBeforeDisconnectPropertyDescriptions", MESSAGES.NxtDrive__StopBeforeDisconnectPropertyDescriptions());
    map.put("METHODDESC-NxtDrive.StopMethodDescriptions", MESSAGES.NxtDrive__StopMethodDescriptions());
    map.put("PROPDESC-NxtDrive.WheelDiameterPropertyDescriptions", MESSAGES.NxtDrive__WheelDiameterPropertyDescriptions());
    map.put("EVENTDESC-NxtLightSensor.AboveRangeEventDescriptions", MESSAGES.NxtLightSensor__AboveRangeEventDescriptions());
    map.put("PROPDESC-NxtLightSensor.AboveRangeEventEnabledPropertyDescriptions", MESSAGES.NxtLightSensor__AboveRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-NxtLightSensor.BelowRangeEventDescriptions", MESSAGES.NxtLightSensor__BelowRangeEventDescriptions());
    map.put("PROPDESC-NxtLightSensor.BelowRangeEventEnabledPropertyDescriptions", MESSAGES.NxtLightSensor__BelowRangeEventEnabledPropertyDescriptions());
    map.put("METHODDESC-NxtLightSensor.GetLightLevelMethodDescriptions", MESSAGES.NxtLightSensor__GetLightLevelMethodDescriptions());
    map.put("EVENTDESC-NxtLightSensor.WithinRangeEventDescriptions", MESSAGES.NxtLightSensor__WithinRangeEventDescriptions());
    map.put("PROPDESC-NxtLightSensor.WithinRangeEventEnabledPropertyDescriptions", MESSAGES.NxtLightSensor__WithinRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-NxtSoundSensor.AboveRangeEventDescriptions", MESSAGES.NxtSoundSensor__AboveRangeEventDescriptions());
    map.put("PROPDESC-NxtSoundSensor.AboveRangeEventEnabledPropertyDescriptions", MESSAGES.NxtSoundSensor__AboveRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-NxtSoundSensor.BelowRangeEventDescriptions", MESSAGES.NxtSoundSensor__BelowRangeEventDescriptions());
    map.put("PROPDESC-NxtSoundSensor.BelowRangeEventEnabledPropertyDescriptions", MESSAGES.NxtSoundSensor__BelowRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-NxtSoundSensor.WithinRangeEventDescriptions", MESSAGES.NxtSoundSensor__WithinRangeEventDescriptions());
    map.put("PROPDESC-NxtSoundSensor.WithinRangeEventEnabledPropertyDescriptions", MESSAGES.NxtSoundSensor__WithinRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-NxtTouchSensor.PressedEventDescriptions", MESSAGES.NxtTouchSensor__PressedEventDescriptions());
    map.put("PROPDESC-NxtTouchSensor.PressedEventEnabledPropertyDescriptions", MESSAGES.NxtTouchSensor__PressedEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-NxtTouchSensor.ReleasedEventDescriptions", MESSAGES.NxtTouchSensor__ReleasedEventDescriptions());
    map.put("EVENTDESC-NxtUltrasonicSensor.AboveRangeEventDescriptions", MESSAGES.NxtUltrasonicSensor__AboveRangeEventDescriptions());
    map.put("PROPDESC-NxtUltrasonicSensor.AboveRangeEventEnabledPropertyDescriptions", MESSAGES.NxtUltrasonicSensor__AboveRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-NxtUltrasonicSensor.BelowRangeEventDescriptions", MESSAGES.NxtUltrasonicSensor__BelowRangeEventDescriptions());
    map.put("PROPDESC-NxtUltrasonicSensor.BelowRangeEventEnabledPropertyDescriptions", MESSAGES.NxtUltrasonicSensor__BelowRangeEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-NxtUltrasonicSensor.WithinRangeEventDescriptions", MESSAGES.NxtUltrasonicSensor__WithinRangeEventDescriptions());
    map.put("PROPDESC-NxtUltrasonicSensor.WithinRangeEventEnabledPropertyDescriptions", MESSAGES.NxtUltrasonicSensor__WithinRangeEventEnabledPropertyDescriptions());
    map.put("PROPDESC-OnPropertyDescriptions", MESSAGES.OnPropertyDescriptions());
    map.put("EVENTDESC-OnSettingsEventDescriptions", MESSAGES.OnSettingsEventDescriptions());
    map.put("PROPDESC-OpenScreenAnimationPropertyDescriptions", MESSAGES.OpenScreenAnimationPropertyDescriptions());
    map.put("METHODDESC-OpenSerialMethodDescriptions", MESSAGES.OpenSerialMethodDescriptions());
    map.put("EVENTDESC-OrientationChangedEventDescriptions", MESSAGES.OrientationChangedEventDescriptions());
    map.put("PROPDESC-OrientationSensor.AvailablePropertyDescriptions", MESSAGES.OrientationSensor__AvailablePropertyDescriptions());
    map.put("PROPDESC-OrientationSensor.EnabledPropertyDescriptions", MESSAGES.OrientationSensor__EnabledPropertyDescriptions());
    map.put("PROPDESC-OrientationSensor.PitchPropertyDescriptions", MESSAGES.OrientationSensor__PitchPropertyDescriptions());
    map.put("PROPDESC-OriginAtCenterPropertyDescriptions", MESSAGES.OriginAtCenterPropertyDescriptions());
    map.put("EVENTDESC-OtherPlayerStartedEventDescriptions", MESSAGES.OtherPlayerStartedEventDescriptions());
    map.put("EVENTDESC-OtherScreenClosedEventDescriptions", MESSAGES.OtherScreenClosedEventDescriptions());
    map.put("EVENTDESC-PageLoadedEventDescriptions", MESSAGES.PageLoadedEventDescriptions());
    map.put("METHODDESC-PanToMethodDescriptions", MESSAGES.PanToMethodDescriptions());
    map.put("PROPDESC-PasswordTextBox.BackgroundColorPropertyDescriptions", MESSAGES.PasswordTextBox__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-PasswordTextBox.EnabledPropertyDescriptions", MESSAGES.PasswordTextBox__EnabledPropertyDescriptions());
    map.put("PROPDESC-PasswordTextBox.FontBoldPropertyDescriptions", MESSAGES.PasswordTextBox__FontBoldPropertyDescriptions());
    map.put("PROPDESC-PasswordTextBox.FontItalicPropertyDescriptions", MESSAGES.PasswordTextBox__FontItalicPropertyDescriptions());
    map.put("PROPDESC-PasswordTextBox.FontSizePropertyDescriptions", MESSAGES.PasswordTextBox__FontSizePropertyDescriptions());
    map.put("PROPDESC-PasswordTextBox.FontTypefacePropertyDescriptions", MESSAGES.PasswordTextBox__FontTypefacePropertyDescriptions());
    map.put("EVENTDESC-PasswordTextBox.GotFocusEventDescriptions", MESSAGES.PasswordTextBox__GotFocusEventDescriptions());
    map.put("PROPDESC-PasswordTextBox.HeightPercentPropertyDescriptions", MESSAGES.PasswordTextBox__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-PasswordTextBox.HeightPropertyDescriptions", MESSAGES.PasswordTextBox__HeightPropertyDescriptions());
    map.put("PROPDESC-PasswordTextBox.HintPropertyDescriptions", MESSAGES.PasswordTextBox__HintPropertyDescriptions());
    map.put("EVENTDESC-PasswordTextBox.LostFocusEventDescriptions", MESSAGES.PasswordTextBox__LostFocusEventDescriptions());
    map.put("METHODDESC-PasswordTextBox.RequestFocusMethodDescriptions", MESSAGES.PasswordTextBox__RequestFocusMethodDescriptions());
    map.put("PROPDESC-PasswordTextBox.TextAlignmentPropertyDescriptions", MESSAGES.PasswordTextBox__TextAlignmentPropertyDescriptions());
    map.put("PROPDESC-PasswordTextBox.TextColorPropertyDescriptions", MESSAGES.PasswordTextBox__TextColorPropertyDescriptions());
    map.put("PROPDESC-PasswordTextBox.TextPropertyDescriptions", MESSAGES.PasswordTextBox__TextPropertyDescriptions());
    map.put("PROPDESC-PasswordTextBox.VisiblePropertyDescriptions", MESSAGES.PasswordTextBox__VisiblePropertyDescriptions());
    map.put("PROPDESC-PasswordTextBox.WidthPercentPropertyDescriptions", MESSAGES.PasswordTextBox__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-PasswordTextBox.WidthPropertyDescriptions", MESSAGES.PasswordTextBox__WidthPropertyDescriptions());
    map.put("PROPDESC-PasswordVisiblePropertyDescriptions", MESSAGES.PasswordVisiblePropertyDescriptions());
    map.put("PROPDESC-Pedometer.DistancePropertyDescriptions", MESSAGES.Pedometer__DistancePropertyDescriptions());
    map.put("METHODDESC-Pedometer.PauseMethodDescriptions", MESSAGES.Pedometer__PauseMethodDescriptions());
    map.put("METHODDESC-Pedometer.ResumeMethodDescriptions", MESSAGES.Pedometer__ResumeMethodDescriptions());
    map.put("METHODDESC-Pedometer.SaveMethodDescriptions", MESSAGES.Pedometer__SaveMethodDescriptions());
    map.put("METHODDESC-Pedometer.StartMethodDescriptions", MESSAGES.Pedometer__StartMethodDescriptions());
    map.put("METHODDESC-Pedometer.StopMethodDescriptions", MESSAGES.Pedometer__StopMethodDescriptions());
    map.put("EVENTDESC-PermissionDeniedEventDescriptions", MESSAGES.PermissionDeniedEventDescriptions());
    map.put("EVENTDESC-PermissionGrantedEventDescriptions", MESSAGES.PermissionGrantedEventDescriptions());
    map.put("PROPDESC-PersistPropertyDescriptions", MESSAGES.PersistPropertyDescriptions());
    map.put("EVENTDESC-PhoneCallEndedEventDescriptions", MESSAGES.PhoneCallEndedEventDescriptions());
    map.put("EVENTDESC-PhoneCallStartedEventDescriptions", MESSAGES.PhoneCallStartedEventDescriptions());
    map.put("PROPDESC-PhoneCall.PhoneNumberPropertyDescriptions", MESSAGES.PhoneCall__PhoneNumberPropertyDescriptions());
    map.put("PROPDESC-PhoneNumberListPropertyDescriptions", MESSAGES.PhoneNumberListPropertyDescriptions());
    map.put("EVENTDESC-PhoneNumberPicker.AfterPickingEventDescriptions", MESSAGES.PhoneNumberPicker__AfterPickingEventDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.BackgroundColorPropertyDescriptions", MESSAGES.PhoneNumberPicker__BackgroundColorPropertyDescriptions());
    map.put("EVENTDESC-PhoneNumberPicker.BeforePickingEventDescriptions", MESSAGES.PhoneNumberPicker__BeforePickingEventDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.EnabledPropertyDescriptions", MESSAGES.PhoneNumberPicker__EnabledPropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.FontBoldPropertyDescriptions", MESSAGES.PhoneNumberPicker__FontBoldPropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.FontItalicPropertyDescriptions", MESSAGES.PhoneNumberPicker__FontItalicPropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.FontSizePropertyDescriptions", MESSAGES.PhoneNumberPicker__FontSizePropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.FontTypefacePropertyDescriptions", MESSAGES.PhoneNumberPicker__FontTypefacePropertyDescriptions());
    map.put("EVENTDESC-PhoneNumberPicker.GotFocusEventDescriptions", MESSAGES.PhoneNumberPicker__GotFocusEventDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.HeightPercentPropertyDescriptions", MESSAGES.PhoneNumberPicker__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.HeightPropertyDescriptions", MESSAGES.PhoneNumberPicker__HeightPropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.ImagePropertyDescriptions", MESSAGES.PhoneNumberPicker__ImagePropertyDescriptions());
    map.put("EVENTDESC-PhoneNumberPicker.LostFocusEventDescriptions", MESSAGES.PhoneNumberPicker__LostFocusEventDescriptions());
    map.put("METHODDESC-PhoneNumberPicker.OpenMethodDescriptions", MESSAGES.PhoneNumberPicker__OpenMethodDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.PhoneNumberPropertyDescriptions", MESSAGES.PhoneNumberPicker__PhoneNumberPropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.PicturePropertyDescriptions", MESSAGES.PhoneNumberPicker__PicturePropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.ShapePropertyDescriptions", MESSAGES.PhoneNumberPicker__ShapePropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.ShowFeedbackPropertyDescriptions", MESSAGES.PhoneNumberPicker__ShowFeedbackPropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.TextAlignmentPropertyDescriptions", MESSAGES.PhoneNumberPicker__TextAlignmentPropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.TextColorPropertyDescriptions", MESSAGES.PhoneNumberPicker__TextColorPropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.TextPropertyDescriptions", MESSAGES.PhoneNumberPicker__TextPropertyDescriptions());
    map.put("EVENTDESC-PhoneNumberPicker.TouchDownEventDescriptions", MESSAGES.PhoneNumberPicker__TouchDownEventDescriptions());
    map.put("EVENTDESC-PhoneNumberPicker.TouchUpEventDescriptions", MESSAGES.PhoneNumberPicker__TouchUpEventDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.VisiblePropertyDescriptions", MESSAGES.PhoneNumberPicker__VisiblePropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.WidthPercentPropertyDescriptions", MESSAGES.PhoneNumberPicker__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-PhoneNumberPicker.WidthPropertyDescriptions", MESSAGES.PhoneNumberPicker__WidthPropertyDescriptions());
    map.put("PROPDESC-PlatformPropertyDescriptions", MESSAGES.PlatformPropertyDescriptions());
    map.put("PROPDESC-PlatformVersionPropertyDescriptions", MESSAGES.PlatformVersionPropertyDescriptions());
    map.put("METHODDESC-PlayMethodDescriptions", MESSAGES.PlayMethodDescriptions());
    map.put("PROPDESC-PlayOnlyInForegroundPropertyDescriptions", MESSAGES.PlayOnlyInForegroundPropertyDescriptions());
    map.put("METHODDESC-PlaySoundFileMethodDescriptions", MESSAGES.PlaySoundFileMethodDescriptions());
    map.put("METHODDESC-PlayToneMethodDescriptions", MESSAGES.PlayToneMethodDescriptions());
    map.put("EVENTDESC-PlayerErrorEventDescriptions", MESSAGES.PlayerErrorEventDescriptions());
    map.put("EVENTDESC-PlayerJoinedEventDescriptions", MESSAGES.PlayerJoinedEventDescriptions());
    map.put("EVENTDESC-PlayerLeftEventDescriptions", MESSAGES.PlayerLeftEventDescriptions());
    map.put("EVENTDESC-Player.CompletedEventDescriptions", MESSAGES.Player__CompletedEventDescriptions());
    map.put("METHODDESC-Player.PauseMethodDescriptions", MESSAGES.Player__PauseMethodDescriptions());
    map.put("PROPDESC-Player.SourcePropertyDescriptions", MESSAGES.Player__SourcePropertyDescriptions());
    map.put("METHODDESC-Player.StartMethodDescriptions", MESSAGES.Player__StartMethodDescriptions());
    map.put("METHODDESC-Player.StopMethodDescriptions", MESSAGES.Player__StopMethodDescriptions());
    map.put("METHODDESC-Player.VibrateMethodDescriptions", MESSAGES.Player__VibrateMethodDescriptions());
    map.put("PROPDESC-Player.VolumePropertyDescriptions", MESSAGES.Player__VolumePropertyDescriptions());
    map.put("PROPDESC-PlayersPropertyDescriptions", MESSAGES.PlayersPropertyDescriptions());
    map.put("EVENTDESC-Polygon.ClickEventDescriptions", MESSAGES.Polygon__ClickEventDescriptions());
    map.put("PROPDESC-Polygon.DescriptionPropertyDescriptions", MESSAGES.Polygon__DescriptionPropertyDescriptions());
    map.put("METHODDESC-Polygon.DistanceToPointMethodDescriptions", MESSAGES.Polygon__DistanceToPointMethodDescriptions());
    map.put("EVENTDESC-Polygon.DragEventDescriptions", MESSAGES.Polygon__DragEventDescriptions());
    map.put("PROPDESC-Polygon.DraggablePropertyDescriptions", MESSAGES.Polygon__DraggablePropertyDescriptions());
    map.put("PROPDESC-Polygon.EnableInfoboxPropertyDescriptions", MESSAGES.Polygon__EnableInfoboxPropertyDescriptions());
    map.put("PROPDESC-Polygon.FillColorPropertyDescriptions", MESSAGES.Polygon__FillColorPropertyDescriptions());
    map.put("EVENTDESC-Polygon.LongClickEventDescriptions", MESSAGES.Polygon__LongClickEventDescriptions());
    map.put("PROPDESC-Polygon.PointsFromStringPropertyDescriptions", MESSAGES.Polygon__PointsFromStringPropertyDescriptions());
    map.put("PROPDESC-Polygon.PointsPropertyDescriptions", MESSAGES.Polygon__PointsPropertyDescriptions());
    map.put("METHODDESC-Polygon.ShowInfoboxMethodDescriptions", MESSAGES.Polygon__ShowInfoboxMethodDescriptions());
    map.put("PROPDESC-Polygon.StrokeColorPropertyDescriptions", MESSAGES.Polygon__StrokeColorPropertyDescriptions());
    map.put("PROPDESC-Polygon.StrokeWidthPropertyDescriptions", MESSAGES.Polygon__StrokeWidthPropertyDescriptions());
    map.put("PROPDESC-Polygon.TitlePropertyDescriptions", MESSAGES.Polygon__TitlePropertyDescriptions());
    map.put("PROPDESC-Polygon.TypePropertyDescriptions", MESSAGES.Polygon__TypePropertyDescriptions());
    map.put("PROPDESC-Polygon.VisiblePropertyDescriptions", MESSAGES.Polygon__VisiblePropertyDescriptions());
    map.put("EVENTDESC-PositionChangedEventDescriptions", MESSAGES.PositionChangedEventDescriptions());
    map.put("METHODDESC-PostFileMethodDescriptions", MESSAGES.PostFileMethodDescriptions());
    map.put("METHODDESC-PostMediaMethodDescriptions", MESSAGES.PostMediaMethodDescriptions());
    map.put("METHODDESC-PostTextMethodDescriptions", MESSAGES.PostTextMethodDescriptions());
    map.put("METHODDESC-PostTextWithEncodingMethodDescriptions", MESSAGES.PostTextWithEncodingMethodDescriptions());
    map.put("PROPDESC-PrimaryColorDarkPropertyDescriptions", MESSAGES.PrimaryColorDarkPropertyDescriptions());
    map.put("PROPDESC-PrimaryColorPropertyDescriptions", MESSAGES.PrimaryColorPropertyDescriptions());
    map.put("METHODDESC-PrintSerialMethodDescriptions", MESSAGES.PrintSerialMethodDescriptions());
    map.put("PROPDESC-ProjectBucketPropertyDescriptions", MESSAGES.ProjectBucketPropertyDescriptions());
    map.put("PROPDESC-ProjectIDPropertyDescriptions", MESSAGES.ProjectIDPropertyDescriptions());
    map.put("PROPDESC-PromptPropertyDescriptions", MESSAGES.PromptPropertyDescriptions());
    map.put("PROPDESC-PromptforPermissionPropertyDescriptions", MESSAGES.PromptforPermissionPropertyDescriptions());
    map.put("PROPDESC-ProviderLockedPropertyDescriptions", MESSAGES.ProviderLockedPropertyDescriptions());
    map.put("PROPDESC-ProviderNamePropertyDescriptions", MESSAGES.ProviderNamePropertyDescriptions());
    map.put("EVENTDESC-ProximityChangedEventDescriptions", MESSAGES.ProximityChangedEventDescriptions());
    map.put("PROPDESC-ProximitySensor.AvailablePropertyDescriptions", MESSAGES.ProximitySensor__AvailablePropertyDescriptions());
    map.put("PROPDESC-ProximitySensor.DistancePropertyDescriptions", MESSAGES.ProximitySensor__DistancePropertyDescriptions());
    map.put("PROPDESC-ProximitySensor.EnabledPropertyDescriptions", MESSAGES.ProximitySensor__EnabledPropertyDescriptions());
    map.put("PROPDESC-ProximitySensor.MaximumRangePropertyDescriptions", MESSAGES.ProximitySensor__MaximumRangePropertyDescriptions());
    map.put("PROPDESC-PublicInstancesPropertyDescriptions", MESSAGES.PublicInstancesPropertyDescriptions());
    map.put("METHODDESC-PutFileMethodDescriptions", MESSAGES.PutFileMethodDescriptions());
    map.put("METHODDESC-PutTextMethodDescriptions", MESSAGES.PutTextMethodDescriptions());
    map.put("METHODDESC-PutTextWithEncodingMethodDescriptions", MESSAGES.PutTextWithEncodingMethodDescriptions());
    map.put("PROPDESC-QueryPropertyDescriptions", MESSAGES.QueryPropertyDescriptions());
    map.put("METHODDESC-ReadFromMethodDescriptions", MESSAGES.ReadFromMethodDescriptions());
    map.put("PROPDESC-ReadModePropertyDescriptions", MESSAGES.ReadModePropertyDescriptions());
    map.put("PROPDESC-ReadOnlyPropertyDescriptions", MESSAGES.ReadOnlyPropertyDescriptions());
    map.put("METHODDESC-ReadSerialMethodDescriptions", MESSAGES.ReadSerialMethodDescriptions());
    map.put("EVENTDESC-ReadyEventDescriptions", MESSAGES.ReadyEventDescriptions());
    map.put("METHODDESC-ReceiveSigned1ByteNumberMethodDescriptions", MESSAGES.ReceiveSigned1ByteNumberMethodDescriptions());
    map.put("METHODDESC-ReceiveSigned2ByteNumberMethodDescriptions", MESSAGES.ReceiveSigned2ByteNumberMethodDescriptions());
    map.put("METHODDESC-ReceiveSigned4ByteNumberMethodDescriptions", MESSAGES.ReceiveSigned4ByteNumberMethodDescriptions());
    map.put("METHODDESC-ReceiveSignedBytesMethodDescriptions", MESSAGES.ReceiveSignedBytesMethodDescriptions());
    map.put("METHODDESC-ReceiveTextMethodDescriptions", MESSAGES.ReceiveTextMethodDescriptions());
    map.put("METHODDESC-ReceiveUnsigned1ByteNumberMethodDescriptions", MESSAGES.ReceiveUnsigned1ByteNumberMethodDescriptions());
    map.put("METHODDESC-ReceiveUnsigned2ByteNumberMethodDescriptions", MESSAGES.ReceiveUnsigned2ByteNumberMethodDescriptions());
    map.put("METHODDESC-ReceiveUnsigned4ByteNumberMethodDescriptions", MESSAGES.ReceiveUnsigned4ByteNumberMethodDescriptions());
    map.put("METHODDESC-ReceiveUnsignedBytesMethodDescriptions", MESSAGES.ReceiveUnsignedBytesMethodDescriptions());
    map.put("PROPDESC-ReceivingEnabledPropertyDescriptions", MESSAGES.ReceivingEnabledPropertyDescriptions());
    map.put("METHODDESC-RecordVideoMethodDescriptions", MESSAGES.RecordVideoMethodDescriptions());
    map.put("EVENTDESC-Rectangle.ClickEventDescriptions", MESSAGES.Rectangle__ClickEventDescriptions());
    map.put("PROPDESC-Rectangle.DescriptionPropertyDescriptions", MESSAGES.Rectangle__DescriptionPropertyDescriptions());
    map.put("METHODDESC-Rectangle.DistanceToPointMethodDescriptions", MESSAGES.Rectangle__DistanceToPointMethodDescriptions());
    map.put("EVENTDESC-Rectangle.DragEventDescriptions", MESSAGES.Rectangle__DragEventDescriptions());
    map.put("PROPDESC-Rectangle.DraggablePropertyDescriptions", MESSAGES.Rectangle__DraggablePropertyDescriptions());
    map.put("PROPDESC-Rectangle.EnableInfoboxPropertyDescriptions", MESSAGES.Rectangle__EnableInfoboxPropertyDescriptions());
    map.put("PROPDESC-Rectangle.FillColorPropertyDescriptions", MESSAGES.Rectangle__FillColorPropertyDescriptions());
    map.put("EVENTDESC-Rectangle.LongClickEventDescriptions", MESSAGES.Rectangle__LongClickEventDescriptions());
    map.put("METHODDESC-Rectangle.ShowInfoboxMethodDescriptions", MESSAGES.Rectangle__ShowInfoboxMethodDescriptions());
    map.put("PROPDESC-Rectangle.StrokeColorPropertyDescriptions", MESSAGES.Rectangle__StrokeColorPropertyDescriptions());
    map.put("PROPDESC-Rectangle.StrokeWidthPropertyDescriptions", MESSAGES.Rectangle__StrokeWidthPropertyDescriptions());
    map.put("PROPDESC-Rectangle.TitlePropertyDescriptions", MESSAGES.Rectangle__TitlePropertyDescriptions());
    map.put("PROPDESC-Rectangle.TypePropertyDescriptions", MESSAGES.Rectangle__TypePropertyDescriptions());
    map.put("PROPDESC-Rectangle.VisiblePropertyDescriptions", MESSAGES.Rectangle__VisiblePropertyDescriptions());
    map.put("PROPDESC-RedisPortPropertyDescriptions", MESSAGES.RedisPortPropertyDescriptions());
    map.put("PROPDESC-RedisServerPropertyDescriptions", MESSAGES.RedisServerPropertyDescriptions());
    map.put("PROPDESC-RefreshTimePropertyDescriptions", MESSAGES.RefreshTimePropertyDescriptions());
    map.put("PROPDESC-ReleasedEventEnabledPropertyDescriptions", MESSAGES.ReleasedEventEnabledPropertyDescriptions());
    map.put("METHODDESC-ReloadMethodDescriptions", MESSAGES.ReloadMethodDescriptions());
    map.put("METHODDESC-RemoveFirstFromListMethodDescriptions", MESSAGES.RemoveFirstFromListMethodDescriptions());
    map.put("METHODDESC-RemoveFirstMethodDescriptions", MESSAGES.RemoveFirstMethodDescriptions());
    map.put("METHODDESC-RequestBallotMethodDescriptions", MESSAGES.RequestBallotMethodDescriptions());
    map.put("METHODDESC-RequestDirectMessagesMethodDescriptions", MESSAGES.RequestDirectMessagesMethodDescriptions());
    map.put("METHODDESC-RequestDirectionsMethodDescriptions", MESSAGES.RequestDirectionsMethodDescriptions());
    map.put("METHODDESC-RequestFollowersMethodDescriptions", MESSAGES.RequestFollowersMethodDescriptions());
    map.put("METHODDESC-RequestFriendTimelineMethodDescriptions", MESSAGES.RequestFriendTimelineMethodDescriptions());
    map.put("PROPDESC-RequestHeadersPropertyDescriptions", MESSAGES.RequestHeadersPropertyDescriptions());
    map.put("METHODDESC-RequestMentionsMethodDescriptions", MESSAGES.RequestMentionsMethodDescriptions());
    map.put("METHODDESC-RequestTranslationMethodDescriptions", MESSAGES.RequestTranslationMethodDescriptions());
    map.put("METHODDESC-ResetInputScaledValueMethodDescriptions", MESSAGES.ResetInputScaledValueMethodDescriptions());
    map.put("METHODDESC-ResetMethodDescriptions", MESSAGES.ResetMethodDescriptions());
    map.put("METHODDESC-ResetMotorPositionMethodDescriptions", MESSAGES.ResetMotorPositionMethodDescriptions());
    map.put("METHODDESC-ResetTachoCountMethodDescriptions", MESSAGES.ResetTachoCountMethodDescriptions());
    map.put("METHODDESC-ResolveActivityMethodDescriptions", MESSAGES.ResolveActivityMethodDescriptions());
    map.put("PROPDESC-ResponseContentPropertyDescriptions", MESSAGES.ResponseContentPropertyDescriptions());
    map.put("PROPDESC-ResponseFileNamePropertyDescriptions", MESSAGES.ResponseFileNamePropertyDescriptions());
    map.put("PROPDESC-ResultNamePropertyDescriptions", MESSAGES.ResultNamePropertyDescriptions());
    map.put("PROPDESC-ResultTypePropertyDescriptions", MESSAGES.ResultTypePropertyDescriptions());
    map.put("PROPDESC-ResultUriPropertyDescriptions", MESSAGES.ResultUriPropertyDescriptions());
    map.put("PROPDESC-ReverseDirectionPropertyDescriptions", MESSAGES.ReverseDirectionPropertyDescriptions());
    map.put("PROPDESC-RollPropertyDescriptions", MESSAGES.RollPropertyDescriptions());
    map.put("METHODDESC-RotateInDistanceMethodDescriptions", MESSAGES.RotateInDistanceMethodDescriptions());
    map.put("METHODDESC-RotateInDurationMethodDescriptions", MESSAGES.RotateInDurationMethodDescriptions());
    map.put("METHODDESC-RotateInTachoCountsMethodDescriptions", MESSAGES.RotateInTachoCountsMethodDescriptions());
    map.put("METHODDESC-RotateIndefinitelyMethodDescriptions", MESSAGES.RotateIndefinitelyMethodDescriptions());
    map.put("METHODDESC-RotateSyncInDistanceMethodDescriptions", MESSAGES.RotateSyncInDistanceMethodDescriptions());
    map.put("METHODDESC-RotateSyncInDurationMethodDescriptions", MESSAGES.RotateSyncInDurationMethodDescriptions());
    map.put("METHODDESC-RotateSyncInTachoCountsMethodDescriptions", MESSAGES.RotateSyncInTachoCountsMethodDescriptions());
    map.put("METHODDESC-RotateSyncIndefinitelyMethodDescriptions", MESSAGES.RotateSyncIndefinitelyMethodDescriptions());
    map.put("PROPDESC-RotatesPropertyDescriptions", MESSAGES.RotatesPropertyDescriptions());
    map.put("PROPDESC-RotationAnglePropertyDescriptions", MESSAGES.RotationAnglePropertyDescriptions());
    map.put("PROPDESC-RotationPropertyDescriptions", MESSAGES.RotationPropertyDescriptions());
    map.put("PROPDESC-RowPropertyDescriptions", MESSAGES.RowPropertyDescriptions());
    map.put("PROPDESC-RowsPropertyDescriptions", MESSAGES.RowsPropertyDescriptions());
    map.put("METHODDESC-RunJavaScriptMethodDescriptions", MESSAGES.RunJavaScriptMethodDescriptions());
    map.put("PROPDESC-RunningPropertyDescriptions", MESSAGES.RunningPropertyDescriptions());
    map.put("METHODDESC-SaveAsMethodDescriptions", MESSAGES.SaveAsMethodDescriptions());
    map.put("METHODDESC-SaveFileMethodDescriptions", MESSAGES.SaveFileMethodDescriptions());
    map.put("PROPDESC-SaveResponsePropertyDescriptions", MESSAGES.SaveResponsePropertyDescriptions());
    map.put("PROPDESC-SavedRecordingPropertyDescriptions", MESSAGES.SavedRecordingPropertyDescriptions());
    map.put("PROPDESC-ScalePictureToFitPropertyDescriptions", MESSAGES.ScalePictureToFitPropertyDescriptions());
    map.put("PROPDESC-ScaleUnitsPropertyDescriptions", MESSAGES.ScaleUnitsPropertyDescriptions());
    map.put("PROPDESC-ScalingPropertyDescriptions", MESSAGES.ScalingPropertyDescriptions());
    map.put("EVENTDESC-ScreenOrientationChangedEventDescriptions", MESSAGES.ScreenOrientationChangedEventDescriptions());
    map.put("PROPDESC-ScreenOrientationPropertyDescriptions", MESSAGES.ScreenOrientationPropertyDescriptions());
    map.put("PROPDESC-Screen.AlignHorizontalPropertyDescriptions", MESSAGES.Screen__AlignHorizontalPropertyDescriptions());
    map.put("PROPDESC-Screen.AlignVerticalPropertyDescriptions", MESSAGES.Screen__AlignVerticalPropertyDescriptions());
    map.put("PROPDESC-Screen.BackgroundColorPropertyDescriptions", MESSAGES.Screen__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-Screen.BackgroundImagePropertyDescriptions", MESSAGES.Screen__BackgroundImagePropertyDescriptions());
    map.put("EVENTDESC-Screen.ErrorOccurredEventDescriptions", MESSAGES.Screen__ErrorOccurredEventDescriptions());
    map.put("PROPDESC-Screen.HeightPropertyDescriptions", MESSAGES.Screen__HeightPropertyDescriptions());
    map.put("METHODDESC-Screen.HideKeyboardMethodDescriptions", MESSAGES.Screen__HideKeyboardMethodDescriptions());
    map.put("PROPDESC-Screen.TitlePropertyDescriptions", MESSAGES.Screen__TitlePropertyDescriptions());
    map.put("PROPDESC-Screen.WidthPropertyDescriptions", MESSAGES.Screen__WidthPropertyDescriptions());
    map.put("PROPDESC-ScrollablePropertyDescriptions", MESSAGES.ScrollablePropertyDescriptions());
    map.put("METHODDESC-SdkLevelMethodDescriptions", MESSAGES.SdkLevelMethodDescriptions());
    map.put("PROPDESC-SearchResultsPropertyDescriptions", MESSAGES.SearchResultsPropertyDescriptions());
    map.put("EVENTDESC-SearchSuccessfulEventDescriptions", MESSAGES.SearchSuccessfulEventDescriptions());
    map.put("METHODDESC-SearchTwitterMethodDescriptions", MESSAGES.SearchTwitterMethodDescriptions());
    map.put("METHODDESC-SecondMethodDescriptions", MESSAGES.SecondMethodDescriptions());
    map.put("PROPDESC-SecurePropertyDescriptions", MESSAGES.SecurePropertyDescriptions());
    map.put("METHODDESC-SeekToMethodDescriptions", MESSAGES.SeekToMethodDescriptions());
    map.put("PROPDESC-SelectionColorPropertyDescriptions", MESSAGES.SelectionColorPropertyDescriptions());
    map.put("METHODDESC-Send1ByteNumberMethodDescriptions", MESSAGES.Send1ByteNumberMethodDescriptions());
    map.put("METHODDESC-Send2ByteNumberMethodDescriptions", MESSAGES.Send2ByteNumberMethodDescriptions());
    map.put("METHODDESC-Send4ByteNumberMethodDescriptions", MESSAGES.Send4ByteNumberMethodDescriptions());
    map.put("METHODDESC-SendBallotMethodDescriptions", MESSAGES.SendBallotMethodDescriptions());
    map.put("METHODDESC-SendBytesMethodDescriptions", MESSAGES.SendBytesMethodDescriptions());
    map.put("METHODDESC-SendMessageDirectMethodDescriptions", MESSAGES.SendMessageDirectMethodDescriptions());
    map.put("METHODDESC-SendQueryMethodDescriptions", MESSAGES.SendQueryMethodDescriptions());
    map.put("METHODDESC-SendTextMethodDescriptions", MESSAGES.SendTextMethodDescriptions());
    map.put("PROPDESC-SensitivityPropertyDescriptions", MESSAGES.SensitivityPropertyDescriptions());
    map.put("PROPDESC-SensorPortPropertyDescriptions", MESSAGES.SensorPortPropertyDescriptions());
    map.put("EVENTDESC-SensorValueChangedEventDescriptions", MESSAGES.SensorValueChangedEventDescriptions());
    map.put("PROPDESC-SensorValueChangedEventEnabledPropertyDescriptions", MESSAGES.SensorValueChangedEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-ServerCommandFailureEventDescriptions", MESSAGES.ServerCommandFailureEventDescriptions());
    map.put("METHODDESC-ServerCommandMethodDescriptions", MESSAGES.ServerCommandMethodDescriptions());
    map.put("EVENTDESC-ServerCommandSuccessEventDescriptions", MESSAGES.ServerCommandSuccessEventDescriptions());
    map.put("PROPDESC-ServiceAccountEmailPropertyDescriptions", MESSAGES.ServiceAccountEmailPropertyDescriptions());
    map.put("PROPDESC-ServiceUrlPropertyDescriptions", MESSAGES.ServiceUrlPropertyDescriptions());
    map.put("METHODDESC-SetAmbientModeMethodDescriptions", MESSAGES.SetAmbientModeMethodDescriptions());
    map.put("METHODDESC-SetAngleModeMethodDescriptions", MESSAGES.SetAngleModeMethodDescriptions());
    map.put("METHODDESC-SetBackgroundPixelColorMethodDescriptions", MESSAGES.SetBackgroundPixelColorMethodDescriptions());
    map.put("METHODDESC-SetBrickNameMethodDescriptions", MESSAGES.SetBrickNameMethodDescriptions());
    map.put("METHODDESC-SetCenterMethodDescriptions", MESSAGES.SetCenterMethodDescriptions());
    map.put("METHODDESC-SetCmUnitMethodDescriptions", MESSAGES.SetCmUnitMethodDescriptions());
    map.put("METHODDESC-SetColorModeMethodDescriptions", MESSAGES.SetColorModeMethodDescriptions());
    map.put("METHODDESC-SetDateToDisplayFromInstantMethodDescriptions", MESSAGES.SetDateToDisplayFromInstantMethodDescriptions());
    map.put("METHODDESC-SetDateToDisplayMethodDescriptions", MESSAGES.SetDateToDisplayMethodDescriptions());
    map.put("METHODDESC-SetInchUnitMethodDescriptions", MESSAGES.SetInchUnitMethodDescriptions());
    map.put("METHODDESC-SetInputModeMethodDescriptions", MESSAGES.SetInputModeMethodDescriptions());
    map.put("METHODDESC-SetInstanceMethodDescriptions", MESSAGES.SetInstanceMethodDescriptions());
    map.put("METHODDESC-SetLeaderMethodDescriptions", MESSAGES.SetLeaderMethodDescriptions());
    map.put("METHODDESC-SetOutputStateMethodDescriptions", MESSAGES.SetOutputStateMethodDescriptions());
    map.put("METHODDESC-SetRateModeMethodDescriptions", MESSAGES.SetRateModeMethodDescriptions());
    map.put("METHODDESC-SetReflectedModeMethodDescriptions", MESSAGES.SetReflectedModeMethodDescriptions());
    map.put("METHODDESC-SetTimeToDisplayFromInstantMethodDescriptions", MESSAGES.SetTimeToDisplayFromInstantMethodDescriptions());
    map.put("METHODDESC-SetTimeToDisplayMethodDescriptions", MESSAGES.SetTimeToDisplayMethodDescriptions());
    map.put("EVENTDESC-ShakingEventDescriptions", MESSAGES.ShakingEventDescriptions());
    map.put("METHODDESC-ShareFileMethodDescriptions", MESSAGES.ShareFileMethodDescriptions());
    map.put("METHODDESC-ShareFileWithMessageMethodDescriptions", MESSAGES.ShareFileWithMessageMethodDescriptions());
    map.put("METHODDESC-ShareMessageMethodDescriptions", MESSAGES.ShareMessageMethodDescriptions());
    map.put("METHODDESC-ShowAlertMethodDescriptions", MESSAGES.ShowAlertMethodDescriptions());
    map.put("METHODDESC-ShowChooseDialogMethodDescriptions", MESSAGES.ShowChooseDialogMethodDescriptions());
    map.put("PROPDESC-ShowCompassPropertyDescriptions", MESSAGES.ShowCompassPropertyDescriptions());
    map.put("PROPDESC-ShowListsAsJsonPropertyDescriptions", MESSAGES.ShowListsAsJsonPropertyDescriptions());
    map.put("PROPDESC-ShowLoadingDialogPropertyDescriptions", MESSAGES.ShowLoadingDialogPropertyDescriptions());
    map.put("METHODDESC-ShowMessageDialogMethodDescriptions", MESSAGES.ShowMessageDialogMethodDescriptions());
    map.put("METHODDESC-ShowPasswordDialogMethodDescriptions", MESSAGES.ShowPasswordDialogMethodDescriptions());
    map.put("METHODDESC-ShowProgressDialogMethodDescriptions", MESSAGES.ShowProgressDialogMethodDescriptions());
    map.put("PROPDESC-ShowScalePropertyDescriptions", MESSAGES.ShowScalePropertyDescriptions());
    map.put("PROPDESC-ShowShadowPropertyDescriptions", MESSAGES.ShowShadowPropertyDescriptions());
    map.put("PROPDESC-ShowStatusBarPropertyDescriptions", MESSAGES.ShowStatusBarPropertyDescriptions());
    map.put("METHODDESC-ShowTextDialogMethodDescriptions", MESSAGES.ShowTextDialogMethodDescriptions());
    map.put("PROPDESC-ShowUserPropertyDescriptions", MESSAGES.ShowUserPropertyDescriptions());
    map.put("PROPDESC-ShowZoomPropertyDescriptions", MESSAGES.ShowZoomPropertyDescriptions());
    map.put("EVENTDESC-SimpleStepEventDescriptions", MESSAGES.SimpleStepEventDescriptions());
    map.put("PROPDESC-SimpleStepsPropertyDescriptions", MESSAGES.SimpleStepsPropertyDescriptions());
    map.put("PROPDESC-SizingPropertyDescriptions", MESSAGES.SizingPropertyDescriptions());
    map.put("PROPDESC-Slider.HeightPercentPropertyDescriptions", MESSAGES.Slider__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-Slider.VisiblePropertyDescriptions", MESSAGES.Slider__VisiblePropertyDescriptions());
    map.put("PROPDESC-Slider.WidthPercentPropertyDescriptions", MESSAGES.Slider__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-Slider.WidthPropertyDescriptions", MESSAGES.Slider__WidthPropertyDescriptions());
    map.put("EVENTDESC-SoundErrorEventDescriptions", MESSAGES.SoundErrorEventDescriptions());
    map.put("METHODDESC-SoundRecorder.StartMethodDescriptions", MESSAGES.SoundRecorder__StartMethodDescriptions());
    map.put("METHODDESC-SoundRecorder.StopMethodDescriptions", MESSAGES.SoundRecorder__StopMethodDescriptions());
    map.put("PROPDESC-Sound.MinimumIntervalPropertyDescriptions", MESSAGES.Sound__MinimumIntervalPropertyDescriptions());
    map.put("METHODDESC-Sound.PauseMethodDescriptions", MESSAGES.Sound__PauseMethodDescriptions());
    map.put("METHODDESC-Sound.ResumeMethodDescriptions", MESSAGES.Sound__ResumeMethodDescriptions());
    map.put("PROPDESC-Sound.SourcePropertyDescriptions", MESSAGES.Sound__SourcePropertyDescriptions());
    map.put("METHODDESC-Sound.StopMethodDescriptions", MESSAGES.Sound__StopMethodDescriptions());
    map.put("METHODDESC-Sound.VibrateMethodDescriptions", MESSAGES.Sound__VibrateMethodDescriptions());
    map.put("PROPDESC-SouthLatitudePropertyDescriptions", MESSAGES.SouthLatitudePropertyDescriptions());
    map.put("METHODDESC-SpeakMethodDescriptions", MESSAGES.SpeakMethodDescriptions());
    map.put("PROPDESC-SpeechRatePropertyDescriptions", MESSAGES.SpeechRatePropertyDescriptions());
    map.put("PROPDESC-SpeechRecognizer.ResultPropertyDescriptions", MESSAGES.SpeechRecognizer__ResultPropertyDescriptions());
    map.put("METHODDESC-SpeechRecognizer.StopMethodDescriptions", MESSAGES.SpeechRecognizer__StopMethodDescriptions());
    map.put("PROPDESC-Spinner.ElementsFromStringPropertyDescriptions", MESSAGES.Spinner__ElementsFromStringPropertyDescriptions());
    map.put("PROPDESC-Spinner.ElementsPropertyDescriptions", MESSAGES.Spinner__ElementsPropertyDescriptions());
    map.put("PROPDESC-Spinner.HeightPercentPropertyDescriptions", MESSAGES.Spinner__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-Spinner.HeightPropertyDescriptions", MESSAGES.Spinner__HeightPropertyDescriptions());
    map.put("PROPDESC-Spinner.SelectionIndexPropertyDescriptions", MESSAGES.Spinner__SelectionIndexPropertyDescriptions());
    map.put("PROPDESC-Spinner.SelectionPropertyDescriptions", MESSAGES.Spinner__SelectionPropertyDescriptions());
    map.put("PROPDESC-Spinner.VisiblePropertyDescriptions", MESSAGES.Spinner__VisiblePropertyDescriptions());
    map.put("PROPDESC-Spinner.WidthPercentPropertyDescriptions", MESSAGES.Spinner__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-Spinner.WidthPropertyDescriptions", MESSAGES.Spinner__WidthPropertyDescriptions());
    map.put("METHODDESC-StartActivityMethodDescriptions", MESSAGES.StartActivityMethodDescriptions());
    map.put("METHODDESC-StartContinuousClassificationMethodDescriptions", MESSAGES.StartContinuousClassificationMethodDescriptions());
    map.put("EVENTDESC-StartDragEventDescriptions", MESSAGES.StartDragEventDescriptions());
    map.put("PROPDESC-StartLatitudePropertyDescriptions", MESSAGES.StartLatitudePropertyDescriptions());
    map.put("PROPDESC-StartLocationPropertyDescriptions", MESSAGES.StartLocationPropertyDescriptions());
    map.put("PROPDESC-StartLongitudePropertyDescriptions", MESSAGES.StartLongitudePropertyDescriptions());
    map.put("METHODDESC-StartProgramMethodDescriptions", MESSAGES.StartProgramMethodDescriptions());
    map.put("EVENTDESC-StartedMovingEventDescriptions", MESSAGES.StartedMovingEventDescriptions());
    map.put("EVENTDESC-StartedRecordingEventDescriptions", MESSAGES.StartedRecordingEventDescriptions());
    map.put("EVENTDESC-StatusChangedEventDescriptions", MESSAGES.StatusChangedEventDescriptions());
    map.put("METHODDESC-StopAcceptingMethodDescriptions", MESSAGES.StopAcceptingMethodDescriptions());
    map.put("METHODDESC-StopContinuousClassificationMethodDescriptions", MESSAGES.StopContinuousClassificationMethodDescriptions());
    map.put("PROPDESC-StopDetectionTimeoutPropertyDescriptions", MESSAGES.StopDetectionTimeoutPropertyDescriptions());
    map.put("EVENTDESC-StopDragEventDescriptions", MESSAGES.StopDragEventDescriptions());
    map.put("METHODDESC-StopFollowingMethodDescriptions", MESSAGES.StopFollowingMethodDescriptions());
    map.put("METHODDESC-StopLoadingMethodDescriptions", MESSAGES.StopLoadingMethodDescriptions());
    map.put("METHODDESC-StopProgramMethodDescriptions", MESSAGES.StopProgramMethodDescriptions());
    map.put("METHODDESC-StopSoundMethodDescriptions", MESSAGES.StopSoundMethodDescriptions());
    map.put("METHODDESC-StopSoundPlaybackMethodDescriptions", MESSAGES.StopSoundPlaybackMethodDescriptions());
    map.put("EVENTDESC-StoppedMovingEventDescriptions", MESSAGES.StoppedMovingEventDescriptions());
    map.put("EVENTDESC-StoppedRecordingEventDescriptions", MESSAGES.StoppedRecordingEventDescriptions());
    map.put("PROPDESC-StrideLengthPropertyDescriptions", MESSAGES.StrideLengthPropertyDescriptions());
    map.put("PROPDESC-StrokeOpacityPropertyDescriptions", MESSAGES.StrokeOpacityPropertyDescriptions());
    map.put("PROPDESC-Switch.BackgroundColorPropertyDescriptions", MESSAGES.Switch__BackgroundColorPropertyDescriptions());
    map.put("EVENTDESC-Switch.ChangedEventDescriptions", MESSAGES.Switch__ChangedEventDescriptions());
    map.put("PROPDESC-Switch.EnabledPropertyDescriptions", MESSAGES.Switch__EnabledPropertyDescriptions());
    map.put("PROPDESC-Switch.FontBoldPropertyDescriptions", MESSAGES.Switch__FontBoldPropertyDescriptions());
    map.put("PROPDESC-Switch.FontItalicPropertyDescriptions", MESSAGES.Switch__FontItalicPropertyDescriptions());
    map.put("PROPDESC-Switch.FontSizePropertyDescriptions", MESSAGES.Switch__FontSizePropertyDescriptions());
    map.put("PROPDESC-Switch.FontTypefacePropertyDescriptions", MESSAGES.Switch__FontTypefacePropertyDescriptions());
    map.put("EVENTDESC-Switch.GotFocusEventDescriptions", MESSAGES.Switch__GotFocusEventDescriptions());
    map.put("PROPDESC-Switch.HeightPercentPropertyDescriptions", MESSAGES.Switch__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-Switch.HeightPropertyDescriptions", MESSAGES.Switch__HeightPropertyDescriptions());
    map.put("EVENTDESC-Switch.LostFocusEventDescriptions", MESSAGES.Switch__LostFocusEventDescriptions());
    map.put("PROPDESC-Switch.TextColorPropertyDescriptions", MESSAGES.Switch__TextColorPropertyDescriptions());
    map.put("PROPDESC-Switch.TextPropertyDescriptions", MESSAGES.Switch__TextPropertyDescriptions());
    map.put("PROPDESC-Switch.VisiblePropertyDescriptions", MESSAGES.Switch__VisiblePropertyDescriptions());
    map.put("PROPDESC-Switch.WidthPercentPropertyDescriptions", MESSAGES.Switch__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-Switch.WidthPropertyDescriptions", MESSAGES.Switch__WidthPropertyDescriptions());
    map.put("METHODDESC-SystemTimeMethodDescriptions", MESSAGES.SystemTimeMethodDescriptions());
    map.put("PROPDESC-TableArrangement.HeightPercentPropertyDescriptions", MESSAGES.TableArrangement__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-TableArrangement.HeightPropertyDescriptions", MESSAGES.TableArrangement__HeightPropertyDescriptions());
    map.put("PROPDESC-TableArrangement.VisiblePropertyDescriptions", MESSAGES.TableArrangement__VisiblePropertyDescriptions());
    map.put("PROPDESC-TableArrangement.WidthPercentPropertyDescriptions", MESSAGES.TableArrangement__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-TableArrangement.WidthPropertyDescriptions", MESSAGES.TableArrangement__WidthPropertyDescriptions());
    map.put("EVENTDESC-TachoCountChangedEventDescriptions", MESSAGES.TachoCountChangedEventDescriptions());
    map.put("PROPDESC-TachoCountChangedEventEnabledPropertyDescriptions", MESSAGES.TachoCountChangedEventEnabledPropertyDescriptions());
    map.put("EVENTDESC-TagListEventDescriptions", MESSAGES.TagListEventDescriptions());
    map.put("EVENTDESC-TagReadEventDescriptions", MESSAGES.TagReadEventDescriptions());
    map.put("EVENTDESC-TagWrittenEventDescriptions", MESSAGES.TagWrittenEventDescriptions());
    map.put("METHODDESC-TakePictureMethodDescriptions", MESSAGES.TakePictureMethodDescriptions());
    map.put("EVENTDESC-TapAtPointEventDescriptions", MESSAGES.TapAtPointEventDescriptions());
    map.put("PROPDESC-TeachableMachine.MinimumIntervalPropertyDescriptions", MESSAGES.TeachableMachine__MinimumIntervalPropertyDescriptions());
    map.put("EVENTDESC-TemperatureChangedEventDescriptions", MESSAGES.TemperatureChangedEventDescriptions());
    map.put("PROPDESC-TemperaturePropertyDescriptions", MESSAGES.TemperaturePropertyDescriptions());
    map.put("PROPDESC-TextBox.BackgroundColorPropertyDescriptions", MESSAGES.TextBox__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-TextBox.EnabledPropertyDescriptions", MESSAGES.TextBox__EnabledPropertyDescriptions());
    map.put("PROPDESC-TextBox.FontBoldPropertyDescriptions", MESSAGES.TextBox__FontBoldPropertyDescriptions());
    map.put("PROPDESC-TextBox.FontItalicPropertyDescriptions", MESSAGES.TextBox__FontItalicPropertyDescriptions());
    map.put("PROPDESC-TextBox.FontSizePropertyDescriptions", MESSAGES.TextBox__FontSizePropertyDescriptions());
    map.put("PROPDESC-TextBox.FontTypefacePropertyDescriptions", MESSAGES.TextBox__FontTypefacePropertyDescriptions());
    map.put("EVENTDESC-TextBox.GotFocusEventDescriptions", MESSAGES.TextBox__GotFocusEventDescriptions());
    map.put("PROPDESC-TextBox.HeightPercentPropertyDescriptions", MESSAGES.TextBox__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-TextBox.HeightPropertyDescriptions", MESSAGES.TextBox__HeightPropertyDescriptions());
    map.put("METHODDESC-TextBox.HideKeyboardMethodDescriptions", MESSAGES.TextBox__HideKeyboardMethodDescriptions());
    map.put("PROPDESC-TextBox.HintPropertyDescriptions", MESSAGES.TextBox__HintPropertyDescriptions());
    map.put("EVENTDESC-TextBox.LostFocusEventDescriptions", MESSAGES.TextBox__LostFocusEventDescriptions());
    map.put("METHODDESC-TextBox.RequestFocusMethodDescriptions", MESSAGES.TextBox__RequestFocusMethodDescriptions());
    map.put("PROPDESC-TextBox.TextAlignmentPropertyDescriptions", MESSAGES.TextBox__TextAlignmentPropertyDescriptions());
    map.put("PROPDESC-TextBox.TextColorPropertyDescriptions", MESSAGES.TextBox__TextColorPropertyDescriptions());
    map.put("PROPDESC-TextBox.TextPropertyDescriptions", MESSAGES.TextBox__TextPropertyDescriptions());
    map.put("PROPDESC-TextBox.VisiblePropertyDescriptions", MESSAGES.TextBox__VisiblePropertyDescriptions());
    map.put("PROPDESC-TextBox.WidthPercentPropertyDescriptions", MESSAGES.TextBox__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-TextBox.WidthPropertyDescriptions", MESSAGES.TextBox__WidthPropertyDescriptions());
    map.put("EVENTDESC-TextInputCanceledEventDescriptions", MESSAGES.TextInputCanceledEventDescriptions());
    map.put("PROPDESC-TextSizePropertyDescriptions", MESSAGES.TextSizePropertyDescriptions());
    map.put("PROPDESC-TextToSpeech.LanguagePropertyDescriptions", MESSAGES.TextToSpeech__LanguagePropertyDescriptions());
    map.put("PROPDESC-TextToSpeech.PitchPropertyDescriptions", MESSAGES.TextToSpeech__PitchPropertyDescriptions());
    map.put("PROPDESC-TextToSpeech.ResultPropertyDescriptions", MESSAGES.TextToSpeech__ResultPropertyDescriptions());
    map.put("PROPDESC-TextToWritePropertyDescriptions", MESSAGES.TextToWritePropertyDescriptions());
    map.put("PROPDESC-Texting.PhoneNumberPropertyDescriptions", MESSAGES.Texting__PhoneNumberPropertyDescriptions());
    map.put("METHODDESC-Texting.SendMessageMethodDescriptions", MESSAGES.Texting__SendMessageMethodDescriptions());
    map.put("PROPDESC-ThemePropertyDescriptions", MESSAGES.ThemePropertyDescriptions());
    map.put("PROPDESC-Thermometer.AvailablePropertyDescriptions", MESSAGES.Thermometer__AvailablePropertyDescriptions());
    map.put("PROPDESC-Thermometer.EnabledPropertyDescriptions", MESSAGES.Thermometer__EnabledPropertyDescriptions());
    map.put("PROPDESC-ThumbColorActivePropertyDescriptions", MESSAGES.ThumbColorActivePropertyDescriptions());
    map.put("PROPDESC-ThumbColorInactivePropertyDescriptions", MESSAGES.ThumbColorInactivePropertyDescriptions());
    map.put("PROPDESC-ThumbEnabledPropertyDescriptions", MESSAGES.ThumbEnabledPropertyDescriptions());
    map.put("PROPDESC-ThumbPositionPropertyDescriptions", MESSAGES.ThumbPositionPropertyDescriptions());
    map.put("PROPDESC-TimeIntervalPropertyDescriptions", MESSAGES.TimeIntervalPropertyDescriptions());
    map.put("PROPDESC-TimePicker.BackgroundColorPropertyDescriptions", MESSAGES.TimePicker__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-TimePicker.EnabledPropertyDescriptions", MESSAGES.TimePicker__EnabledPropertyDescriptions());
    map.put("PROPDESC-TimePicker.FontBoldPropertyDescriptions", MESSAGES.TimePicker__FontBoldPropertyDescriptions());
    map.put("PROPDESC-TimePicker.FontItalicPropertyDescriptions", MESSAGES.TimePicker__FontItalicPropertyDescriptions());
    map.put("PROPDESC-TimePicker.FontSizePropertyDescriptions", MESSAGES.TimePicker__FontSizePropertyDescriptions());
    map.put("PROPDESC-TimePicker.FontTypefacePropertyDescriptions", MESSAGES.TimePicker__FontTypefacePropertyDescriptions());
    map.put("EVENTDESC-TimePicker.GotFocusEventDescriptions", MESSAGES.TimePicker__GotFocusEventDescriptions());
    map.put("PROPDESC-TimePicker.HeightPercentPropertyDescriptions", MESSAGES.TimePicker__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-TimePicker.HeightPropertyDescriptions", MESSAGES.TimePicker__HeightPropertyDescriptions());
    map.put("PROPDESC-TimePicker.ImagePropertyDescriptions", MESSAGES.TimePicker__ImagePropertyDescriptions());
    map.put("PROPDESC-TimePicker.InstantPropertyDescriptions", MESSAGES.TimePicker__InstantPropertyDescriptions());
    map.put("METHODDESC-TimePicker.LaunchPickerMethodDescriptions", MESSAGES.TimePicker__LaunchPickerMethodDescriptions());
    map.put("EVENTDESC-TimePicker.LostFocusEventDescriptions", MESSAGES.TimePicker__LostFocusEventDescriptions());
    map.put("PROPDESC-TimePicker.ShapePropertyDescriptions", MESSAGES.TimePicker__ShapePropertyDescriptions());
    map.put("PROPDESC-TimePicker.ShowFeedbackPropertyDescriptions", MESSAGES.TimePicker__ShowFeedbackPropertyDescriptions());
    map.put("PROPDESC-TimePicker.TextAlignmentPropertyDescriptions", MESSAGES.TimePicker__TextAlignmentPropertyDescriptions());
    map.put("PROPDESC-TimePicker.TextColorPropertyDescriptions", MESSAGES.TimePicker__TextColorPropertyDescriptions());
    map.put("PROPDESC-TimePicker.TextPropertyDescriptions", MESSAGES.TimePicker__TextPropertyDescriptions());
    map.put("EVENTDESC-TimePicker.TouchDownEventDescriptions", MESSAGES.TimePicker__TouchDownEventDescriptions());
    map.put("EVENTDESC-TimePicker.TouchUpEventDescriptions", MESSAGES.TimePicker__TouchUpEventDescriptions());
    map.put("PROPDESC-TimePicker.VisiblePropertyDescriptions", MESSAGES.TimePicker__VisiblePropertyDescriptions());
    map.put("PROPDESC-TimePicker.WidthPercentPropertyDescriptions", MESSAGES.TimePicker__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-TimePicker.WidthPropertyDescriptions", MESSAGES.TimePicker__WidthPropertyDescriptions());
    map.put("EVENTDESC-TimedOutEventDescriptions", MESSAGES.TimedOutEventDescriptions());
    map.put("PROPDESC-TimeoutPropertyDescriptions", MESSAGES.TimeoutPropertyDescriptions());
    map.put("PROPDESC-TimerAlwaysFiresPropertyDescriptions", MESSAGES.TimerAlwaysFiresPropertyDescriptions());
    map.put("PROPDESC-TimerEnabledPropertyDescriptions", MESSAGES.TimerEnabledPropertyDescriptions());
    map.put("EVENTDESC-TimerEventDescriptions", MESSAGES.TimerEventDescriptions());
    map.put("PROPDESC-TimerIntervalPropertyDescriptions", MESSAGES.TimerIntervalPropertyDescriptions());
    map.put("METHODDESC-TinyDB.ClearTagMethodDescriptions", MESSAGES.TinyDB__ClearTagMethodDescriptions());
    map.put("METHODDESC-TinyDB.GetValueMethodDescriptions", MESSAGES.TinyDB__GetValueMethodDescriptions());
    map.put("METHODDESC-TinyDB.StoreValueMethodDescriptions", MESSAGES.TinyDB__StoreValueMethodDescriptions());
    map.put("METHODDESC-TinyWebDB.GetValueMethodDescriptions", MESSAGES.TinyWebDB__GetValueMethodDescriptions());
    map.put("EVENTDESC-TinyWebDB.GotValueEventDescriptions", MESSAGES.TinyWebDB__GotValueEventDescriptions());
    map.put("PROPDESC-TinyWebDB.ServiceURLPropertyDescriptions", MESSAGES.TinyWebDB__ServiceURLPropertyDescriptions());
    map.put("METHODDESC-TinyWebDB.StoreValueMethodDescriptions", MESSAGES.TinyWebDB__StoreValueMethodDescriptions());
    map.put("EVENTDESC-TinyWebDB.WebServiceErrorEventDescriptions", MESSAGES.TinyWebDB__WebServiceErrorEventDescriptions());
    map.put("PROPDESC-TitleVisiblePropertyDescriptions", MESSAGES.TitleVisiblePropertyDescriptions());
    map.put("METHODDESC-ToggleCameraFacingModeMethodDescriptions", MESSAGES.ToggleCameraFacingModeMethodDescriptions());
    map.put("METHODDESC-ToggleDirectionMethodDescriptions", MESSAGES.ToggleDirectionMethodDescriptions());
    map.put("PROPDESC-TokenPropertyDescriptions", MESSAGES.TokenPropertyDescriptions());
    map.put("PROPDESC-TopOfRangePropertyDescriptions", MESSAGES.TopOfRangePropertyDescriptions());
    map.put("PROPDESC-TrackColorActivePropertyDescriptions", MESSAGES.TrackColorActivePropertyDescriptions());
    map.put("PROPDESC-TrackColorInactivePropertyDescriptions", MESSAGES.TrackColorInactivePropertyDescriptions());
    map.put("PROPDESC-TransportationMethodPropertyDescriptions", MESSAGES.TransportationMethodPropertyDescriptions());
    map.put("METHODDESC-TurnClockwiseIndefinitelyMethodDescriptions", MESSAGES.TurnClockwiseIndefinitelyMethodDescriptions());
    map.put("METHODDESC-TurnCounterClockwiseIndefinitelyMethodDescriptions", MESSAGES.TurnCounterClockwiseIndefinitelyMethodDescriptions());
    map.put("PROPDESC-TutorialURLPropertyDescriptions", MESSAGES.TutorialURLPropertyDescriptions());
    map.put("METHODDESC-TweetMethodDescriptions", MESSAGES.TweetMethodDescriptions());
    map.put("METHODDESC-TweetWithImageMethodDescriptions", MESSAGES.TweetWithImageMethodDescriptions());
    map.put("PROPDESC-TwitPic_API_KeyPropertyDescriptions", MESSAGES.TwitPic_API_KeyPropertyDescriptions());
    map.put("METHODDESC-UnauthenticateMethodDescriptions", MESSAGES.UnauthenticateMethodDescriptions());
    map.put("PROPDESC-UnitPropertyDescriptions", MESSAGES.UnitPropertyDescriptions());
    map.put("METHODDESC-UriDecodeMethodDescriptions", MESSAGES.UriDecodeMethodDescriptions());
    map.put("METHODDESC-UriEncodeMethodDescriptions", MESSAGES.UriEncodeMethodDescriptions());
    map.put("PROPDESC-UrlPropertyDescriptions", MESSAGES.UrlPropertyDescriptions());
    map.put("PROPDESC-UseExternalScannerPropertyDescriptions", MESSAGES.UseExternalScannerPropertyDescriptions());
    map.put("PROPDESC-UseFrontPropertyDescriptions", MESSAGES.UseFrontPropertyDescriptions());
    map.put("PROPDESC-UseGPSPropertyDescriptions", MESSAGES.UseGPSPropertyDescriptions());
    map.put("PROPDESC-UseLegacyPropertyDescriptions", MESSAGES.UseLegacyPropertyDescriptions());
    map.put("PROPDESC-UseSSLPropertyDescriptions", MESSAGES.UseSSLPropertyDescriptions());
    map.put("PROPDESC-UseServiceAuthenticationPropertyDescriptions", MESSAGES.UseServiceAuthenticationPropertyDescriptions());
    map.put("PROPDESC-UserChoicePropertyDescriptions", MESSAGES.UserChoicePropertyDescriptions());
    map.put("EVENTDESC-UserEmailAddressSetEventDescriptions", MESSAGES.UserEmailAddressSetEventDescriptions());
    map.put("PROPDESC-UserIdPropertyDescriptions", MESSAGES.UserIdPropertyDescriptions());
    map.put("PROPDESC-UserLatitudePropertyDescriptions", MESSAGES.UserLatitudePropertyDescriptions());
    map.put("PROPDESC-UserLongitudePropertyDescriptions", MESSAGES.UserLongitudePropertyDescriptions());
    map.put("PROPDESC-UsernamePropertyDescriptions", MESSAGES.UsernamePropertyDescriptions());
    map.put("PROPDESC-UsesLocationPropertyDescriptions", MESSAGES.UsesLocationPropertyDescriptions());
    map.put("EVENTDESC-ValueStoredEventDescriptions", MESSAGES.ValueStoredEventDescriptions());
    map.put("PROPDESC-VersionCodePropertyDescriptions", MESSAGES.VersionCodePropertyDescriptions());
    map.put("PROPDESC-VersionNamePropertyDescriptions", MESSAGES.VersionNamePropertyDescriptions());
    map.put("PROPDESC-VerticalArrangement.AlignHorizontalPropertyDescriptions", MESSAGES.VerticalArrangement__AlignHorizontalPropertyDescriptions());
    map.put("PROPDESC-VerticalArrangement.AlignVerticalPropertyDescriptions", MESSAGES.VerticalArrangement__AlignVerticalPropertyDescriptions());
    map.put("PROPDESC-VerticalArrangement.BackgroundColorPropertyDescriptions", MESSAGES.VerticalArrangement__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-VerticalArrangement.HeightPercentPropertyDescriptions", MESSAGES.VerticalArrangement__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-VerticalArrangement.HeightPropertyDescriptions", MESSAGES.VerticalArrangement__HeightPropertyDescriptions());
    map.put("PROPDESC-VerticalArrangement.ImagePropertyDescriptions", MESSAGES.VerticalArrangement__ImagePropertyDescriptions());
    map.put("PROPDESC-VerticalArrangement.VisiblePropertyDescriptions", MESSAGES.VerticalArrangement__VisiblePropertyDescriptions());
    map.put("PROPDESC-VerticalArrangement.WidthPercentPropertyDescriptions", MESSAGES.VerticalArrangement__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-VerticalArrangement.WidthPropertyDescriptions", MESSAGES.VerticalArrangement__WidthPropertyDescriptions());
    map.put("PROPDESC-VerticalScrollArrangement.AlignHorizontalPropertyDescriptions", MESSAGES.VerticalScrollArrangement__AlignHorizontalPropertyDescriptions());
    map.put("PROPDESC-VerticalScrollArrangement.AlignVerticalPropertyDescriptions", MESSAGES.VerticalScrollArrangement__AlignVerticalPropertyDescriptions());
    map.put("PROPDESC-VerticalScrollArrangement.BackgroundColorPropertyDescriptions", MESSAGES.VerticalScrollArrangement__BackgroundColorPropertyDescriptions());
    map.put("PROPDESC-VerticalScrollArrangement.HeightPercentPropertyDescriptions", MESSAGES.VerticalScrollArrangement__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-VerticalScrollArrangement.HeightPropertyDescriptions", MESSAGES.VerticalScrollArrangement__HeightPropertyDescriptions());
    map.put("PROPDESC-VerticalScrollArrangement.ImagePropertyDescriptions", MESSAGES.VerticalScrollArrangement__ImagePropertyDescriptions());
    map.put("PROPDESC-VerticalScrollArrangement.VisiblePropertyDescriptions", MESSAGES.VerticalScrollArrangement__VisiblePropertyDescriptions());
    map.put("PROPDESC-VerticalScrollArrangement.WidthPercentPropertyDescriptions", MESSAGES.VerticalScrollArrangement__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-VerticalScrollArrangement.WidthPropertyDescriptions", MESSAGES.VerticalScrollArrangement__WidthPropertyDescriptions());
    map.put("EVENTDESC-VideoPlayerErrorEventDescriptions", MESSAGES.VideoPlayerErrorEventDescriptions());
    map.put("EVENTDESC-VideoPlayer.CompletedEventDescriptions", MESSAGES.VideoPlayer__CompletedEventDescriptions());
    map.put("PROPDESC-VideoPlayer.HeightPercentPropertyDescriptions", MESSAGES.VideoPlayer__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-VideoPlayer.HeightPropertyDescriptions", MESSAGES.VideoPlayer__HeightPropertyDescriptions());
    map.put("METHODDESC-VideoPlayer.PauseMethodDescriptions", MESSAGES.VideoPlayer__PauseMethodDescriptions());
    map.put("PROPDESC-VideoPlayer.SourcePropertyDescriptions", MESSAGES.VideoPlayer__SourcePropertyDescriptions());
    map.put("METHODDESC-VideoPlayer.StartMethodDescriptions", MESSAGES.VideoPlayer__StartMethodDescriptions());
    map.put("METHODDESC-VideoPlayer.StopMethodDescriptions", MESSAGES.VideoPlayer__StopMethodDescriptions());
    map.put("PROPDESC-VideoPlayer.VisiblePropertyDescriptions", MESSAGES.VideoPlayer__VisiblePropertyDescriptions());
    map.put("PROPDESC-VideoPlayer.VolumePropertyDescriptions", MESSAGES.VideoPlayer__VolumePropertyDescriptions());
    map.put("PROPDESC-VideoPlayer.WidthPercentPropertyDescriptions", MESSAGES.VideoPlayer__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-VideoPlayer.WidthPropertyDescriptions", MESSAGES.VideoPlayer__WidthPropertyDescriptions());
    map.put("METHODDESC-ViewContactMethodDescriptions", MESSAGES.ViewContactMethodDescriptions());
    map.put("PROPDESC-Voting.ServiceURLPropertyDescriptions", MESSAGES.Voting__ServiceURLPropertyDescriptions());
    map.put("PROPDESC-Voting.UserEmailAddressPropertyDescriptions", MESSAGES.Voting__UserEmailAddressPropertyDescriptions());
    map.put("EVENTDESC-Voting.WebServiceErrorEventDescriptions", MESSAGES.Voting__WebServiceErrorEventDescriptions());
    map.put("EVENTDESC-WalkStepEventDescriptions", MESSAGES.WalkStepEventDescriptions());
    map.put("PROPDESC-WalkStepsPropertyDescriptions", MESSAGES.WalkStepsPropertyDescriptions());
    map.put("PROPDESC-WebRTCPropertyDescriptions", MESSAGES.WebRTCPropertyDescriptions());
    map.put("EVENTDESC-WebViewStringChangeEventDescriptions", MESSAGES.WebViewStringChangeEventDescriptions());
    map.put("PROPDESC-WebViewStringPropertyDescriptions", MESSAGES.WebViewStringPropertyDescriptions());
    map.put("PROPDESC-WebViewerPropertyDescriptions", MESSAGES.WebViewerPropertyDescriptions());
    map.put("METHODDESC-WebViewer.ClearCookiesMethodDescriptions", MESSAGES.WebViewer__ClearCookiesMethodDescriptions());
    map.put("EVENTDESC-WebViewer.ErrorOccurredEventDescriptions", MESSAGES.WebViewer__ErrorOccurredEventDescriptions());
    map.put("PROPDESC-WebViewer.HeightPercentPropertyDescriptions", MESSAGES.WebViewer__HeightPercentPropertyDescriptions());
    map.put("PROPDESC-WebViewer.HeightPropertyDescriptions", MESSAGES.WebViewer__HeightPropertyDescriptions());
    map.put("PROPDESC-WebViewer.VisiblePropertyDescriptions", MESSAGES.WebViewer__VisiblePropertyDescriptions());
    map.put("PROPDESC-WebViewer.WidthPercentPropertyDescriptions", MESSAGES.WebViewer__WidthPercentPropertyDescriptions());
    map.put("PROPDESC-WebViewer.WidthPropertyDescriptions", MESSAGES.WebViewer__WidthPropertyDescriptions());
    map.put("METHODDESC-Web.ClearCookiesMethodDescriptions", MESSAGES.Web__ClearCookiesMethodDescriptions());
    map.put("METHODDESC-Web.DeleteMethodDescriptions", MESSAGES.Web__DeleteMethodDescriptions());
    map.put("EVENTDESC-Web.GotTextEventDescriptions", MESSAGES.Web__GotTextEventDescriptions());
    map.put("METHODDESC-WeekdayMethodDescriptions", MESSAGES.WeekdayMethodDescriptions());
    map.put("METHODDESC-WeekdayNameMethodDescriptions", MESSAGES.WeekdayNameMethodDescriptions());
    map.put("PROPDESC-WestLongitudePropertyDescriptions", MESSAGES.WestLongitudePropertyDescriptions());
    map.put("METHODDESC-WriteSerialMethodDescriptions", MESSAGES.WriteSerialMethodDescriptions());
    map.put("PROPDESC-WriteTypePropertyDescriptions", MESSAGES.WriteTypePropertyDescriptions());
    map.put("PROPDESC-XAccelPropertyDescriptions", MESSAGES.XAccelPropertyDescriptions());
    map.put("PROPDESC-XAngularVelocityPropertyDescriptions", MESSAGES.XAngularVelocityPropertyDescriptions());
    map.put("METHODDESC-XMLTextDecodeAsDictionaryMethodDescriptions", MESSAGES.XMLTextDecodeAsDictionaryMethodDescriptions());
    map.put("METHODDESC-XMLTextDecodeMethodDescriptions", MESSAGES.XMLTextDecodeMethodDescriptions());
    map.put("PROPDESC-XStrengthPropertyDescriptions", MESSAGES.XStrengthPropertyDescriptions());
    map.put("PROPDESC-YAccelPropertyDescriptions", MESSAGES.YAccelPropertyDescriptions());
    map.put("PROPDESC-YAngularVelocityPropertyDescriptions", MESSAGES.YAngularVelocityPropertyDescriptions());
    map.put("PROPDESC-YStrengthPropertyDescriptions", MESSAGES.YStrengthPropertyDescriptions());
    map.put("PROPDESC-YandexTranslate.ApiKeyPropertyDescriptions", MESSAGES.YandexTranslate__ApiKeyPropertyDescriptions());
    map.put("METHODDESC-YearMethodDescriptions", MESSAGES.YearMethodDescriptions());
    map.put("PROPDESC-YearPropertyDescriptions", MESSAGES.YearPropertyDescriptions());
    map.put("PROPDESC-ZAccelPropertyDescriptions", MESSAGES.ZAccelPropertyDescriptions());
    map.put("PROPDESC-ZAngularVelocityPropertyDescriptions", MESSAGES.ZAngularVelocityPropertyDescriptions());
    map.put("PROPDESC-ZStrengthPropertyDescriptions", MESSAGES.ZStrengthPropertyDescriptions());
    map.put("EVENTDESC-ZoomChangeEventDescriptions", MESSAGES.ZoomChangeEventDescriptions());
    map.put("PROPDESC-ZoomLevelPropertyDescriptions", MESSAGES.ZoomLevelPropertyDescriptions());
    map.put("METHODDESC-doFaultMethodDescriptions", MESSAGES.doFaultMethodDescriptions());
    map.put("METHODDESC-installURLMethodDescriptions", MESSAGES.installURLMethodDescriptions());
    map.put("METHODDESC-isConnectedMethodDescriptions", MESSAGES.isConnectedMethodDescriptions());
    map.put("METHODDESC-isDirectMethodDescriptions", MESSAGES.isDirectMethodDescriptions());
    map.put("METHODDESC-setAssetsLoadedMethodDescriptions", MESSAGES.setAssetsLoadedMethodDescriptions());
    map.put("METHODDESC-setHmacSeedReturnCodeMethodDescriptions", MESSAGES.setHmacSeedReturnCodeMethodDescriptions());
    map.put("METHODDESC-shutdownMethodDescriptions", MESSAGES.shutdownMethodDescriptions());
    map.put("METHODDESC-startHTTPDMethodDescriptions", MESSAGES.startHTTPDMethodDescriptions());
    map.put("METHODDESC-startWebRTCMethodDescriptions", MESSAGES.startWebRTCMethodDescriptions());


    /* Categories */

    map.put("CATEGORY-Connectivity", MESSAGES.connectivityComponentPallette());
    map.put("CATEGORY-Drawing and Animation", MESSAGES.drawingAndAnimationComponentPallette());
    map.put("CATEGORY-Experimental", MESSAGES.experimentalComponentPallette());
    map.put("CATEGORY-Extension", MESSAGES.extensionComponentPallette());
    map.put("CATEGORY-For internal use only", MESSAGES.forInternalUseOnlyComponentPallette());
    map.put("CATEGORY-LEGO® MINDSTORMS®", MESSAGES.legoMindstormsComponentPallette());
    map.put("CATEGORY-Layout", MESSAGES.layoutComponentPallette());
    map.put("CATEGORY-Maps", MESSAGES.mapsComponentPallette());
    map.put("CATEGORY-Media", MESSAGES.mediaComponentPallette());
    map.put("CATEGORY-Sensors", MESSAGES.sensorsComponentPallette());
    map.put("CATEGORY-Social", MESSAGES.socialComponentPallette());
    map.put("CATEGORY-Storage", MESSAGES.storageComponentPallette());
    map.put("CATEGORY-User Interface", MESSAGES.userInterfaceComponentPallette());
  return map;
  }
}
