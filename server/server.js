// Node.js server for connect.us   

////////////////////////
//  Crating listener  //
////////////////////////

  var express = require('express')
  , app = express()
  , server = require('http').createServer(app)
  
  var port = 8081
  var ip_address = '127.0.0.1'
  
  server.listen(port, ip_address, function () {
    log(new Date() + " - Server up! Listening on " + ip_address + ":" + port);
  });

////////////////////////
//      DataBase      //
////////////////////////

var mysql = require("mysql");

var db = mysql.createConnection({
  host: "connectus.cyjrsxxh2uz4.us-west-2.rds.amazonaws.com",
  port: "3306",
  user: "master",
  password: "Alpha7913555",
  database: "db"
});

db.connect(function(err){
  if(err){console.log('Error connecting to Db! error: '+err);return;}
  console.log(new Date() + " - Database connection up!");
});


////////////////////////
//    Server Code     //
////////////////////////

  //Listing connections
  app.get('/connections', function (req, res) {
    var userID = req.param('id');
    var json = {};
    json["users"] = [];
    var pending = {};    
        
    var connectionsCount = 0;    
    db.query('SELECT * FROM connections WHERE user1 = ? or user2 = ? ', [userID,userID] , function(err,rows){
        if(err) console.log(err);
        
        connections = rows.length; 
        
        for(i=0; i<rows.length; i++){
          if(rows[i].user1 != userID) connectionID = rows[i].user1;
          else connectionID = rows[i].user2; 
           
          pending[connectionID] = rows[i].pending; 
           
          db.query('SELECT * FROM users WHERE id = ?', connectionID, function(err,row){
            if(err) console.log(err);
            if(row[0]){
                var connectionJson = {};
                  connectionJson["id"] = row[0].id;  
                  connectionJson["pending"] = pending[row[0].id]; 
                  
                  if(pending[row[0].id] != 1){
                    connectionJson["name"] = row[0].name; 
                    connectionJson["phone"] = row[0].phone; 
                    connectionJson["email"] = row[0].email; 
                    connectionJson["avatar"] = row[0].avatar; 
                    connectionJson["sex"] = row[0].sex; 
                    connectionJson["inst"] = row[0].inst; 
                    connectionJson["fb"] = row[0].fb; 
                    connectionJson["snap"] = row[0].snap; 
                    connectionJson["linked"] = row[0].linked;
                  }
                json["users"].push(connectionJson);
            }
            
            connections--;            
            if(connections == 0)
              res.send(json)
              
          });
        }      
    });
  });

  //
  // CONNECTIONS
  //
  
  //Creating a new connection
  app.get('/connect', function (req, res) {
    var userID1 = req.param('id1');
    var userID2 = req.param('id2');
    var json = {};
    json["result"] = "false";

    var row={};
    row['user1'] = userID1;
    row['user2'] = userID2;
    row['pending'] = 1;
    db.query('INSERT INTO connections SET ?', row, function(dberr,dbres){
        if(dberr){
          json["error"] = dberr;
          log(dberr)
        }else{
          json["result"] = "true";  
        }
        
        res.send(json)
    });           
  });

  //Delete old connection
  app.get('/disconnect', function (req, res) {
    var userID1 = req.param('id1');
    var userID2 = req.param('id2');
    var json = {};
    json["result"] = "false";

    db.query('DELETE FROM connections WHERE (user1 = ? AND user2 = ?) or (user1 = ? AND user2 = ?)', [userID1,userID2,userID2,userID1], function(dberr,dbres){
        if(dberr){
          json["error"] = dberr;
          log(dberr)
        }else{
          json["result"] = "true";  
        }
        
        res.send(json)
    });           
  });
  
  //Accept a connection
  app.get('/accept', function (req, res) {
    var userID1 = req.param('id1');
    var userID2 = req.param('id2');
    var json = {};
    json["result"] = "false";

    db.query('UPDATE connections SET pending = ? WHERE user1 = ? AND user2 = ? ', [0,userID1,userID2], function(dberr,dbres){
        if(dberr){
          json["error"] = dberr;
          log(dberr)
        }else{
          json["result"] = "true";  
        }
        
        res.send(json)
    });           
  });  
  
  
  //
  // PROFILE
  //  

  //Get profile data
  app.get('/profile', function (req, res) {
    var userID = req.param('id');  
    var json = {};

      db.query('SELECT * FROM users WHERE id = ?', userID, function(dberr,row){
        if(dberr){
          json["error"] = dberr;
          log(dberr)
        }else{
          if(row[0]){
            json["id"] = row[0].id;  
            json["name"] = row[0].name; 
            json["phone"] = row[0].phone; 
            json["email"] = row[0].email; 
            json["avatar"] = row[0].avatar; 
            json["sex"] = row[0].sex; 
            json["inst"] = row[0].inst; 
            json["fb"] = row[0].fb; 
            json["snap"] = row[0].snap; 
            json["linked"] = row[0].linked;
          }
        } 
        res.send(json) 
      });                    
  }); 

  //Edit profile data
  app.get('/editprofile', function (req, res) {
    var userID = req.param('id');  
    var name = req.param('name');
    var data = req.param('data');
    var json = {};
    json["result"] = "false";

    db.query('UPDATE users SET '+name+' = ? WHERE id = ?', [data,userID], function(dberr,dbres){
        if(dberr){
          json["error"] = dberr;
          log(dberr)
        }else{
          json["result"] = "true";  
        }
        
        res.send(json)
    });           
  });  

////////////////////////
//  Helper functions  //
////////////////////////

  function log(text){
      console.log(text);
  }