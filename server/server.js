var http = require('http')
var server = http.createServer(function(req, res) {
    callback(req,res)
})
var io = require('socket.io')(server)

var server_port = process.env.OPENSHIFT_NODEJS_PORT || 8080
var server_ip_address = process.env.OPENSHIFT_NODEJS_IP || '127.0.0.1'

var mongoose = require('mongoose')
var dbURI = 'mongodb://127.0.0.1/MusicSwap'
mongoose.connect(dbURI)
var db = mongoose.connection
var gooseHandler = require('gooseHandler')


db.on('error', function (err) {
  console.log('Mongoose default connection error: ' + err)
})

// When the connection is disconnected
db.on('disconnected', function () {
  console.log('Mongoose default connection disconnected')
})

// If the Node process ends, close the Mongoose connection
process.on('SIGINT', function() {
  db.close(function () {
    console.log('Mongoose default connection disconnected through SIGINT termination')
    process.exit(0)
  })
})

function handleClient (socket) {
    console.log('A user has connected to the server')

    socket.on('new_connect', function(username){
        gooseHandler.updateSocketId(username, socket.id)
    })

    // socket.on('find_match', function(profile){
    //     var username = profile.username
    //     var artists = profile.artists
    //     mongoHandler.addToMatch(username, socket.id, artists)
    // })

    // socket.on('request_matches', function(username){
    //     console.log('request_match received')
    //     mongoHandler.requestMatches(username)
    // })

    socket.on('disconnect', function(){
        console.log('A user has disconnected from the server')
    })
}


db.on('connected', function() {
    console.log('Mongoose default connection open to ' + dbURI)

    io.on('connection', handleClient)
    server.listen(server_port, server_ip_address, function(){
        console.log("Listening on " + server_ip_address + ", server_port " + server_port)
    })
})
