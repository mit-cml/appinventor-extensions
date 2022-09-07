"use strict";

console.log("TeachableMachine");
loaded();
// const fs = require('fs');




const IMAGE_SIZE = 224;

// make sure error codes are consistent with those defined in PersonalImageClassifier.java
const ERROR_CLASSIFICATION_NOT_SUPPORTED = -1;
const ERROR_CLASSIFICATION_FAILED = -2;
const ERROR_CANNOT_TOGGLE_CAMERA_IN_IMAGE_MODE = -3;
const ERROR_CANNOT_CLASSIFY_IMAGE_IN_VIDEO_MODE = -4;
const ERROR_CANNOT_CLASSIFY_VIDEO_IN_IMAGE_MODE = -5;
const ERROR_INVALID_INPUT_MODE = -6;
// Error -10 was failure to load labels
const ERROR_FAILED_TO_START_VIDEO = -11;

// Inputs are passed through an activation of the transfer before being fed into
// the user provided model
let transferModel;
let model, maxPredictions;
var prediction;

// Data required to use the model
let modelLabels;


let img = document.createElement("img");
img.width = window.innerWidth;
img.style.display = "block";

let frontFacing = false;
let isVideoMode = false;
let isRunning = false;
let minClassTime = 0;
let lastClassification = new Date();
let webcamHolder = document.getElementById('webcam-box');
let androidWebcam;


async function loadModel(baseUrl) {
  console.log(typeof baseUrl);
  // const URL = fs.readFileSync('path.txt','utf-8');
  // const modelURL = "https://teachablemachine.withgoogle.com/models/1pFPFr9fA/" + "model.json";
  const modelURL = baseUrl + "model.json";
  console.log(modelURL);
  const metadataURL = baseUrl + "metadata.json";
  model = await tmImage.load(modelURL, metadataURL);
  console.log('2nd part')
  maxPredictions = model.getTotalClasses();
  console.log("Model Loaded !!");
  window.requestAnimationFrame(loop);
}


// Inputing image data
const flip = true;
androidWebcam = new tmImage.Webcam( IMAGE_SIZE,IMAGE_SIZE, flip);
androidWebcam.setup()
  .then(() => androidWebcam.play())
  .then(() => webcamHolder.appendChild(androidWebcam.canvas))



function loop() {
  androidWebcam.update()
  console.log(webcamHolder);
  predict().then(() =>window.requestAnimationFrame(loop))
}





async function predict() {
  prediction = await model.predict(androidWebcam.canvas);
  console.log('Prediction done')
  let result = [];

  for (let i = 0; i < maxPredictions; i++) {
      
    const currentValue = prediction[i].probability;

      
    const labelName = prediction[i].className

    result.push([labelName, currentValue]);
  }

  console.log("TeachableMachine: prediction is " + JSON.stringify(result));
  TeachableMachine.reportResult(JSON.stringify(result));

}

function updateVideoSize() {
  let windowWidth = document.body.offsetWidth;
  let windowHeight = document.body.offsetHeight;
  let size = Math.min(windowWidth, windowHeight);
  webcamHolder.style.width = size + 'px';
  webcamHolder.style.height = size + 'px';
  let width = video.videoWidth;
  let height = video.videoHeight;
  let aspectRatio = width / height;
  if (width >= height) {
    video.width = aspectRatio * size;
    video.height = size;
    video.style.left = (size - video.width) / 2.0 + 'px';
    video.style.top = '0px';
  } else {
    video.height = size / aspectRatio;
    video.width = size;
    video.style.left = '0px';
    video.style.top = (size - video.height) / 2.0 + 'px';
  }
}



document.body.appendChild(img);

function startVideo() {
  if (isVideoMode) {
    navigator.mediaDevices.getUserMedia({
      video: {facingMode: frontFacing ? "user" : "environment"},
      audio: false
    })
      .then(stream => (video.srcObject = stream))
      .catch(e => {
        TeachableMachine.error(ERROR_FAILED_TO_START_VIDEO);
        console.error(e);
      });
    webcamHolder.style.display = 'block';
    video.style.display = "block";
    if (frontFacing) {  // flip the front facing camera to make it 'natural'
      video.style.transform = 'scaleX(-1)';
    } else {
      video.style.transform = '';
    }
  }
}

function stopVideo() {
  if (isVideoMode && video.srcObject) {
    video.srcObject.getTracks().forEach(t => t.stop());
    webcamHolder.style.display = 'none';
    video.style.display = "none";
  }
}

// Called from TeachableMachine.java
// noinspection JSUnusedGlobalSymbols
function toggleCameraFacingMode() {
  if (isVideoMode) {
    stopVideo();
    frontFacing = !frontFacing;
    startVideo();
  } else {
    TeachableMachine.error(ERROR_CANNOT_TOGGLE_CAMERA_IN_IMAGE_MODE);
  }
}

// Called from TeachableMachine.java
// noinspection JSUnusedGlobalSymbols
function classifyImageData(imageData) {
  if (!isVideoMode) {
    img.onload = function() {
      predict(img).catch(() => TeachableMachine.error(ERROR_CLASSIFICATION_FAILED));
    }
    img.src = "data:image/png;base64," + imageData;
  } else {
    TeachableMachine.error(ERROR_CANNOT_CLASSIFY_IMAGE_IN_VIDEO_MODE);
  }
}

// Called from TeachableMachine.java
// noinspection JSUnusedGlobalSymbols
function classifyVideoData() {
  if (isVideoMode) {
    predict(video, true).catch(() => TeachableMachine.error(ERROR_CLASSIFICATION_FAILED));
  } else {
    TeachableMachine.error(ERROR_CANNOT_CLASSIFY_VIDEO_IN_IMAGE_MODE);
  }
}

function cvcHandler() {
  if (!isRunning || !isVideoMode) {
    return;
  }
  let now = new Date();
  if (now.getTime() - lastClassification.getTime() > minClassTime) {
    lastClassification = now;
    predict(video, true).then(() => requestAnimationFrame(cvcHandler));
  } else {
    requestAnimationFrame(cvcHandler);
  }
}

// Called from TeachableMachine.java
// noinspection JSUnusedGlobalSymbols
function startVideoClassification() {
  if (isRunning || !isVideoMode) {
    return;
  }
  isRunning = true;
  setTimeout(cvcHandler, 16);
}

// Called from TeachableMachine.java
// noinspection JSUnusedGlobalSymbols
function stopVideoClassification() {
  if (!isRunning || !isVideoMode) {
    return;
  }
  isRunning = false;
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
    TeachableMachine.error(ERROR_INVALID_INPUT_MODE);
  }
}

window.addEventListener("resize", function() {
  img.width = window.innerWidth;
  video.width = window.innerWidth;
  video.height = video.videoHeight * window.innerWidth / video.videoWidth;
});



window.addEventListener('orientationchange', function() {
  if (isVideoMode) {
    // The event fires before the video actually rotates, so we delay updating the frame until
    // a later time.
    setTimeout(updateVideoSize, 500);
  }
});


function loaded() {
  let link = TeachableMachine.isLoaded(true);
  loadModel(link);
}

