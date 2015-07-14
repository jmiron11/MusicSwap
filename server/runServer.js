var MongoHandler = require('mongoHandler')
var http = require('http')
var server = http.createServer(function(req, res) {
    callback(req,res)
});
var io = require('socket.io')(server);
var server_port = process.env.OPENSHIFT_NODEJS_PORT || 8080
var server_ip_address = process.env.OPENSHIFT_NODEJS_IP || '127.0.0.1'

io.on('connection', handleClient)


function handleClient (socket) {
    var mongoHandler = new MongoHandler(socket, io)
    console.log('A user has connected to the server')

    socket.on('new_connect', function(username){
        mongoHandler.storeSocketId(username, socket.id)
    })

    /* Chat related listeners */
    //TODO: Add username related information
    // socket.on('chat_user_join', function(){
    //     socket.broadcast.emit('user_join_chat')
    //     console.log('A user has joined a chat room')
    // })

    // socket.on('chat_user_left', function(){
    //     socket.broadcast.emit('user_left')
    //     console.log('User has left the chat room')
    // })

    // socket.on('chat_new_message', function (message) {
    //     console.log('New message posted in the chat: ' + message)
    //     socket.broadcast.emit('new_message', message)
    // })

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
        mongoHandler.addToMatch(username, socket.id, artist1, artist2, artist3)
    })

    socket.on('request_matches', function(username){
        console.log('request_match received')
        mongoHandler.requestMatches(username)
    })

    socket.on('disconnect', function(){

    })
}

server.listen(server_port, server_ip_address, function(){
	console.log("Listening on " + server_ip_address + ", server_port " + server_port)
});

