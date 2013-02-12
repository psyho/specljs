var page = require('webpage').create();
var address = "runner.html";

function getStatus() {
  return page.evaluate(function() {
    return consoleReporter.status;
  });
}

page.onConsoleMessage = function(msg){
  console.log(msg);

  if(msg === "ConsoleReporter finished") {
    var status = getStatus();

    if(status == "success") {
      phantom.exit(0);
    } else {
      phantom.exit(1);
    }
  }
};

page.open(address, function(status) {
  if(status != "success") {
    console.log("Can't load the runner");
    phantom.exit(1);
  }
});
