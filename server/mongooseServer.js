

var mongoose = require('mongoose');
var Match = require('./model/match').Match
var User = require('./model/user').User
var Profile = require('./model/profile').Profile
mongoose.connect('mongodb://127.0.0.1/MusicSwap');

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error'))
db.once('open', function(callback) {
    var silence = new Match({ username: "Justin",
                              matches: [ { username: "Kevin", artist: "Frightened Rabbit"},
                                         { username: "Sam", artist: "Manchester Orchestra"} ]
                            })

    console.log(silence.matches[0].username)
})
