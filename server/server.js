var sys = require('sys')
var http = require('http')
var url = require('url')
var fs = require('fs')
var mongoHandler = require('mongoHandler')


var server = http.createServer(function(req, res) {
    callback(req,res)
});

var io = require('socket.io').listen(server)

io.sockets.on('connection', function (socket) {
    console.log('A user has connected to the server')
    socket.broadcast.emit('new_user')

    /* Chat related listeners */
    //TODO: Add username related information
    socket.on('chat_user_join', function(){
    	socket.broadcast.emit('user_join_chat')
    	console.log('A user has joined a chat room')
    })

    socket.on('chat_user_left', function(){
        socket.broadcast.emit('user_left')
        console.log('User has left the chat room')
    })

    socket.on('chat_new_message', function (message) {
        console.log('New message posted in the chat: ' + message)
        socket.broadcast.emit('new_message', message)
    })

    /* Match related listeners */
    socket.on('new_profile', function(profile){
    	console.log("Received profile")
    	var username = profile.username
    	var artist1 = profile.artist1
    	var artist2 = profile.artist2
    	var artist3 = profile.artist3

    	mongoHandler.modifyProfile(username, artist1, artist2, artist3)
    })

    socket.on('find_match', function(profile){
        var username = profile.username
        var artist1 = profile.artist1
        var artist2 = profile.artist2
        var artist3 = profile.artist3
        mongoHandler.findMatch(username, socket.id, artist1, artist2, artist3, io)
    })

    socket.on('disconnect', function(){

    })
})
server.listen(8080)
console.log("Server Running on 8080");

exports.io = io
