var express = require('express');
var bodyParser = require('body-parser');
var app = express();
var getIp = require('./get_ip.js');
var makeRequest = require('./make_request.js');

app.use(bodyParser.urlencoded({
    extended: true
}));

app.use(bodyParser.json());

var reqBody;
app.get('/', function (req, res) {
  res.send('Hello there!');
})

app.listen(8000, function () {
  console.log('Example app listening on port 8000!');
})

app.post('/get', function (req, res) {
  reqBody = req.body;

  getIp.getReqCoIp(function(err, ip){
    console.log("In client : IP ", ip);
    makeRequest.callReqCo(ip, reqBody, function(error, response){
      res.json(response);
    });
  });
})
