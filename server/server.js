var sys = require('sys');
var http = require('http');
var url = require('url');
var fs = require('fs');
var mongoHandler = require('mongoHandler');

var server = http.createServer(function(req, res) {
    fs.readFile('./index.html', 'utf-8', function(error, content) {
        res.writeHead(200, {"Content-Type": "text/html"});
        res.end(content);
    });
});

var io = require('socket.io').listen(server);

io.sockets.on('connection', function (socket) {
    console.log('A user has connected to the server');
    socket.broadcast.emit('new_user');

    /* Chat related listeners */
    //TODO: Add username related information
    socket.on('chat_user_join', function(){
    	socket.broadcast.emit('user_join_chat');
    	console.log('A user has joined a chat room');
    })

    socket.on('chat_user_left', function(){
        socket.broadcast.emit('user_left');
        console.log('User has left the chat room');
    });

    socket.on('chat_new_message', function (message) {
        console.log('New message posted in the chat: ' + message);
        socket.broadcast.emit('new_message', message);
    });


    /* Match related listeners */
    socket.on('new_profile', function(profile){
    	console.log("Received profile");
    	var username = profile.username;
    	var band1 = profile.band1;
    	var band2 = profile.band2;
    	var band3 = profile.band3;

    	mongoHandler.modifyProfile(username, band1, band2, band3);
    });

    socket.on('find_chat_match', function(){
    	mongoHandler.
    })
});
server.listen(8080);
console.log("Server Running on 8080");
