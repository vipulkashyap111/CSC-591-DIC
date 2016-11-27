var net = require('net');

var HOST = '127.0.0.1';
var PORT = 8080;
var client = new net.Socket();
var ip;
var getReqCoIp = function(callback){
  client.connect(PORT, HOST, function() {
      console.log('CONNECTED TO: ' + HOST + ':' + PORT);
  });

  // Add a 'data' event handler for the client socket
  // data is what the server sent to this socket
  client.on('data', function(data) {
      console.log('DATA: ' + data);
      ip = data.toString('utf8');
      // Close the client socket completely
      client.destroy();

  });

  // Add a 'close' event handler for the client socket
  client.on('close', function() {
      console.log('Connection closed ', ip);
      callback(null, ip);
  });
}

module.exports = {
  getReqCoIp: getReqCoIp
}
