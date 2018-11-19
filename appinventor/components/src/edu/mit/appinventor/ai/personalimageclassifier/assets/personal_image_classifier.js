"use strict";

console.log("PersonalImageClassifier: Using TensorFlow.js version " + tf.version.tfjs);

const MOBILENET_MODEL_PATH = "https://storage.googleapis.com/tfjs-models/tfjs/mobilenet_v1_0.25_224/model.json";
const PERSONAL_MODEL_PREFIX = "https://appinventor.mit.edu/personal-image-classifier/";
const PERSONAL_MODEL_JSON_SUFFIX = "model.json";
const PERSONAL_MODEL_WEIGHTS_SUFFIX = "model.weights.bin";
const PERSONAL_MODEL_LABELS_SUFFIX = "model_labels.json";

const IMAGE_SIZE = 224;

// make sure error codes are consistent with those defined in PersonalImageClassifier.java
const ERROR_CLASSIFICATION_NOT_SUPPORTED = -1;
const ERROR_CLASSIFICATION_FAILED = -2;
const ERROR_CANNOT_TOGGLE_CAMERA_IN_IMAGE_MODE = -3;
const ERROR_CANNOT_CLASSIFY_IMAGE_IN_VIDEO_MODE = -4;
const ERROR_CANNOT_CLASSIFY_VIDEO_IN_IMAGE_MODE = -5;
const ERROR_INVALID_INPUT_MODE = -6;

// Inputs are passed through an activation of mobilenet before being fed into
// the user provided model
let mobilenet;
let model;

// Mapping of model predictions to label names
let modelLabels;
let topk_predictions;

async function loadMobilenet() {
  const mobilenet = await tf.loadModel(MOBILENET_MODEL_PATH);

  // Return an internal activation of mobilenet.
  const layer = mobilenet.getLayer('conv_pw_13_relu');
  return tf.model({inputs: mobilenet.inputs, outputs: layer.output});
}

async function loadModelFile(url, json) {
  const modelFileResponse = await fetch(url);

  console.log("Done fetching file");

  if (json) {
    return await modelFileResponse.json();
  }
  return await modelFileResponse.blob();
}

// From https://stackoverflow.com/questions/27159179/how-to-convert-blob-to-file-in-javascript
function blobToFile(blob, fileName){
    // A Blob() is almost a File() - it's just missing the two properties below which we will add
    blob.lastModifiedDate = new Date();
    blob.name = fileName;
    return blob;
}

const mobilenetDemo = async () => {
  try {
    // Load the mobelnet activation and the user's personal model
    mobilenet = await loadMobilenet();

    const modelTopologyBlob = await loadModelFile(PERSONAL_MODEL_PREFIX + PERSONAL_MODEL_JSON_SUFFIX, false);
    const modelTopologyFile = blobToFile(modelTopologyBlob, PERSONAL_MODEL_JSON_SUFFIX);

    const modelWeightsBlob = await loadModelFile(PERSONAL_MODEL_PREFIX + PERSONAL_MODEL_WEIGHTS_SUFFIX, false);
    const modelWeightsFile = blobToFile(modelWeightsBlob, PERSONAL_MODEL_WEIGHTS_SUFFIX);

    model = await tf.loadModel(tf.io.browserFiles([modelTopologyFile, modelWeightsFile]));

    // Loads the model labels mapping
    modelLabels = await loadModelFile(PERSONAL_MODEL_PREFIX + PERSONAL_MODEL_LABELS_SUFFIX, true);
    topk_predictions = Math.min(3, Object.keys(modelLabels).length);

    const zeros = tf.zeros([1, IMAGE_SIZE, IMAGE_SIZE, 3]);
    mobilenet.predict(zeros).dispose();
    zeros.dispose();
    console.log("PersonalImageClassifier: Mobilenet activation and personal model are ready");
    PersonalImageClassifier.ready();
  } catch (error) {
    console.log("PersonalImageClassifier: " + error);
    PersonalImageClassifier.error(ERROR_CLASSIFICATION_NOT_SUPPORTED);
  }
};

async function predict(pixels) {
  try {
    const logits = tf.tidy(() => {
      const img = tf.image.resizeBilinear(tf.fromPixels(pixels).toFloat(), [IMAGE_SIZE, IMAGE_SIZE]);
      const offset = tf.scalar(127.5);
      const normalized = img.sub(offset).div(offset);
      const batched = normalized.reshape([1, IMAGE_SIZE, IMAGE_SIZE, 3]);

      // Make a prediction, first using the mobilenet activation and then
      // feeding that into the user provided model
      const activation = mobilenet.predict(batched);
      const predictions = model.predict(activation);
      return predictions.as1D();
    });

    const topPredictions = await logits.topk(topk_predictions);

    const predictionIndices = await topPredictions.indices.data();
    const predictionValues = await topPredictions.values.data();

    var result = [];
    logits.dispose();

    for (let i = 0; i < topk_predictions; i++) {
      const currentIndex = predictionIndices[i];
      const currentValue = predictionValues[i];

      const labelName = modelLabels[currentIndex];

      result.push([labelName, currentValue.toFixed(5)]);
    }

    console.log("PersonalImageClassifier: prediction is " + JSON.stringify(result));
    PersonalImageClassifier.reportResult(JSON.stringify(result));
  } catch (error) {
    console.log("PersonalImageClassifier: " + error);
    PersonalImageClassifier.error(ERROR_CLASSIFICATION_NOT_SUPPORTED);
  }
}

var img = document.createElement("img");
img.width = window.innerWidth;
img.style.display = "block";

var video = document.createElement("video");
video.setAttribute("autoplay", "");
video.setAttribute("playsinline", "");
video.width = window.innerWidth;
video.style.display = "none";

var frontFacing = false;
var isVideoMode = false;

document.body.appendChild(img);
document.body.appendChild(video);

video.addEventListener("loadedmetadata", function() {
  video.height = this.videoHeight * video.width / this.videoWidth;
}, false);

function startVideo() {
  if (isVideoMode) {
    navigator.mediaDevices.getUserMedia({video: {facingMode: frontFacing ? "user" : "environment"}, audio: false})
    .then(stream => (video.srcObject = stream))
    .catch(e => log(e));
    video.style.display = "block";
  }
}

function stopVideo() {
  if (isVideoMode && video.srcObject) {
    video.srcObject.getTracks().forEach(t => t.stop());
    video.style.display = "none";
  }
}

function toggleCameraFacingMode() {
  if (isVideoMode) {
    frontFacing = !frontFacing;
    stopVideo();
    startVideo();
  } else {
    PersonalImageClassifier.error(ERROR_CANNOT_TOGGLE_CAMERA_IN_IMAGE_MODE);
  }
}

function classifyImageData(imageData) {
  if (!isVideoMode) {
    img.onload = function() {
      predict(img);
    }
    img.src = "data:image/png;base64," + imageData;
  } else {
    PersonalImageClassifier.error(ERROR_CANNOT_CLASSIFY_IMAGE_IN_VIDEO_MODE);
  }
}

function classifyVideoData() {
  if (isVideoMode) {
    predict(video);
  } else {
    PersonalImageClassifier.error(ERROR_CANNOT_CLASSIFY_VIDEO_IN_IMAGE_MODE);
  }
}

function setInputMode(inputMode) {
  if (inputMode === "image" && isVideoMode) {
    stopVideo();
    isVideoMode = false;
    img.style.display = "block";
  } else if (inputMode === "video" && !isVideoMode) {
    img.style.display = "none";
    isVideoMode = true;
    startVideo();
  } else if (inputMode !== "image" && inputMode !== "video") {
    PersonalImageClassifier.error(ERROR_INVALID_INPUT_MODE);
  }
}

window.addEventListener("resize", function() {
  img.width = window.innerWidth;
  video.width = window.innerWidth;
  video.height = video.videoHeight * window.innerWidth / video.videoWidth;
});

mobilenetDemo();
