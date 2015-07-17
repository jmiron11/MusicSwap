var io = require('socket.io')

var socket = require('socket.io-client')('http://127.0.0.1:8080');

// var profile = {
//     "username" : "Kevin",
//     "artists"  : ["Foo Fighters", "Manchester Orchestra", "We The Kings", "Local Natives", "Third Eye Blind"],
// }

socket.on('connect', function(){
    console.log("Connected to server")

    /* updateSocketId test */
    // socket.emit('new_connect', 'Justin')


    /* updateProfile test */
    // var profile = {
    // 	'username' : 'Kevin',
    // 	'artists'  : ['Red Hot Chilli Peppers', 'Jack Johnson', 'Foo Fighters', 'Fierce Bad Rabbit']
    // }
    // var profile2 = {
    //     'username' : 'William',
    //     'artists'  : ['Deathcab For Cutie', 'Frightened Rabbit', 'Chvrches']
    // }
    // // var profile3 = {
    // //     'username' : 'Sam',
    // //     'artists'  : ['Manchester Orchestra', 'Fierce Bad Rabbit']
    // // }
    // socket.emit('update_profile', profile)
    // socket.emit('update_profile', profile2)
    // socket.emit('update_profile', profile3)

    /* updateMatches test */
   	// socket.emit('update_matches', 'Sam')

    /* retreivingMatches test */
    socket.emit('request_matches', 'Sam')

})

socket.on('match_return', function(matches){
    console.log('Return Received:')
    for(i = 0; i < matches.length; ++i)
    {
        console.log(matches[i])
    }
})

socket.on('event', function(data){})

socket.on('disconnect', function(){
    console.log("Disconnected from server")
})



