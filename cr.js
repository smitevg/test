//var http = require('http');
//var request = require('request');

//http.get("http://www.google.com/", function(res) {
//  console.log("Got response: " + res.statusCode);
  // consume response body
//  res.resume();
//}).on('error', function(e) {
//  console.log("Got error: " + e.message);
//});




var http = require('http');




http.get ({
    host: '192.168.1.120',
    port: 8080,
    path: 'https://coinmarketcap.com/'
}, function (response) {
   // console.log (response);
});




var options = {
  host: "192.168.1.120",
  port: 8080,
  path: 'https://coinmarketcap.com/'

};
http.get(options, function(res) {
  console.log(res);
  res.pipe(process.stdout);
});