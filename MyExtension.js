com.branextensions.MyExtension = function() {};

com.example.MyExtension.prototype.doSomething = function(text) {
  // Get the package and project name from the URL parameters
  var urlParams = new URLSearchParams(window.location.search);
  var packageName = urlParams.get("packageName");
  var projectName = urlParams.get("projectName");
  
  // Create the extension object
  var extension = new packageName + "." + projectName + ".MyExtension()";

  // Set the icon URL
  var iconUrl = "https://allmyassets.files.wordpress.com/2023/03/pixil-frame-0.png";
  
  // Call the extension method and return the result
  return extension.doSomething(text);
};
