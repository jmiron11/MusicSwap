var mongoose = require('mongoose')

var dbURI = 'mongodb://127.0.0.1:8080/MusicSwap'

mongoose.connect(dbURI)

mongoose.connection.on('connected', function() {
    console.log('Mongoose default connection open to ' + dbURI)
})

// If the connection throws an error
mongoose.connection.on('error',function (err) {
  console.log('Mongoose default connection error: ' + err);
})

// When the connection is disconnected
mongoose.connection.on('disconnected', function () {
  console.log('Mongoose default connection disconnected');
})

// If the Node process ends, close the Mongoose connection
process.on('SIGINT', function() {
  mongoose.connection.close(function () {
    console.log('Mongoose default connection disconnected through SIGINT termination')
    process.exit(0);
  })
})

require('./../model/match').Match
require('./../model/user').User
require('./../model/profile').Profile
