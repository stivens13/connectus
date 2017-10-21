// Node.js server for connect.us   

////////////////////////
//  Crating listener  //
////////////////////////

  var express = require('express')
  , app = express()
  , server = require('http').createServer(app)
  , io = require("socket.io").listen(server)
  
  var port = 8080
  var ip_address = '127.0.0.1'
  
  server.listen(port, ip_address, function () {
    log(new Date() + " - Server up! Listening on " + ip_address + ":" + port);
  });


////////////////////////
//    Server Code     //
////////////////////////

  io.on("connection", function(socket){
          log(new Date() + " New Conncetion - " + socket.id + " " + io.sockets.connected[socket.id].handshake.headers['x-forwarded-for'] + " - online: " +  connections);
  });

////////////////////////
//  Helper functions  //
////////////////////////

  function log(text){
      io.emit("AdminLog", text);
      console.log(text);
  }