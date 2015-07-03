var sys = require('sys');
var http = require('http');
var url = require('url');
var fs = require('fs');


var server = http.createServer();


var io = require('socket.io').listen(server);

var clients = 0;

io.sockets.on('connection', function (socket) {
    socket.emit('message', 'You are connected!');
    clients++;


    socket.on('new_user', function(username) {
        socket.username = username;
        console.log(username + ' connected to the chat server');
        socket.broadcast.emit('new_user', username);
        socket.emit('new_user', username);
    });

    socket.on('num_clients', function(){
        socket.emit('num_clients', clients);
    })

    socket.on('message', function (message) {
        console.log(socket.username + ' posted a message in the chat: ' + message);
        socket.broadcast.emit('new_message', socket.username, message);
    });
});
server.listen(8080);
console.log("Server Running on 8080");

