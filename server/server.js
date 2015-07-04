var sys = require('sys');
var http = require('http');
var url = require('url');
var fs = require('fs');


var server = http.createServer(function(req, res) {
    fs.readFile('./index.html', 'utf-8', function(error, content) {
        res.writeHead(200, {"Content-Type": "text/html"});
        res.end(content);
    });
});

var io = require('socket.io').listen(server);

var clients = 0;

io.sockets.on('connection', function (socket) {
    console.log('A user has connected to the chat server');
    socket.broadcast.emit('new_user');
    clients++;

    socket.on('user_left', function(){
        socket.emit('user_left');
        console.log('User has left the room');
        clients--;
    });

    socket.on('num_clients', function(){
        socket.emit('num_clients', clients);
    })

    socket.on('new_message', function (message) {
        console.log('New message posted in the chat: ' + message);
        socket.broadcast.emit('new_message', message);
    });
});
server.listen(8080);
console.log("Server Running on 8080");

