var http = require('http')
var server = http.createServer(function(req, res) {
    callback(req,res)
})
var io = require('socket.io')(server)

var server_port = process.env.OPENSHIFT_NODEJS_PORT || 8080
var server_ip_address = process.env.OPENSHIFT_NODEJS_IP || '127.0.0.1'

// initialize the mongoDB conncetion with mongoose
var mongoose = require('mongoose')
mongoose.set('debug', true)
var dbURI = 'mongodb://127.0.0.1/MusicSwap'
mongoose.connect(dbURI)
var db = mongoose.connection

// initialize handlers
var socketHandler = require('socketHandler')

// When the connection is succesful, initialize the websocket
db.on('connected', function() {
    console.log('Mongoose default connection open to ' + dbURI)

    io.on('connection', socketHandler.handleConnection)
    server.listen(server_port, server_ip_address, function(){
        console.log("Listening on " + server_ip_address + ", server_port " + server_port)
    })
})

// When there is an error on connecting to the server
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
