var express = require('express');
var bodyParser = require('body-parser');
var Slack = require('slack-node');
var request = require('request');
var app = express();

var tokenGet = process.env.SLACK_TOKEN_GET;
var tokenPut = process.env.SLACK_TOKEN_PUT

var clientURL = "http://localhost:8080";

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

app.post('/', function (req, res) {
  console.log("Request received", req.body);
  var response = req.body.command + " " + req.body.text;
  if(tokenGet == req.body.token || tokenPut == req.body.token){
    fetch(req.body, function(err, resp){
        if(!err){
          console.log("Sending response to Slack: " + resp);
          response = response + " : " + resp;
          res.send(response);
        }else{
          console.log("Sending error to Slack" + err);
          response = response + " : " + err;
          res.send(response);
        }
      })
  }else{
    res.send("Error: Suspicious request");
  }
});

var fetch = function(body,callback){
  var postURL = clientURL + body.command;
  console.log("Client URL: ", postURL);
  request.post({
    url: postURL,
    body: body.text,
  }, function(error, response){
    if(!error){
      console.log("Response received from client is: ", response.body);
      callback(null, response.body);
    }else{
      console.log("Error received from client is: ", error);
      callback(error);
    }
  });
}
