var io = require('socket.io')

var socket = require('socket.io-client')('http://127.0.0.1:8080');

// var profile = {
//     "username" : "Kevin",
//     "artists"  : ["Foo Fighters", "Manchester Orchestra", "We The Kings", "Local Natives", "Third Eye Blind"],
// }

socket.on('connect', function(){
    console.log("Connected to server")
    socket.emit('new_connect', 'justin')
    console.log('emitteddddd')
})

socket.on('event', function(data){})

socket.on('disconnect', function(){
    console.log("Disconnected from server")
})



