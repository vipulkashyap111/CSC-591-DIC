var net = require('net');

var HOST = '127.0.0.1';
var PORT = 6969;

var res, body;
var callReqCo = function(host, reqBody, callback){
  //HOST = host;
  body = JSON.stringify(reqBody);
  var client = new net.Socket();
  client.connect(PORT, HOST, function() {
      console.log('CONNECTED TO: ' + HOST + ':' + PORT);
      client.write(body);
  });

  // Add a 'data' event handler for the client socket
  // data is what the server sent to this socket
  client.on('data', function(data) {
      console.log('DATA: ' + data);
      res = data.toString('utf8');
      // Close the client socket completely
      client.destroy();

  });

  // Add a 'close' event handler for the client socket
  client.on('close', function() {
      console.log('Connection closed ', res);
      callback(null, res);
  });
}

module.exports = {
  callReqCo: callReqCo
}
