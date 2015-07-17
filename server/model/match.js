var mongoose = require('mongoose');

var matchSchema = mongoose.Schema({
        username: String,
        matches:  [{ 'username' : String,
                     'artist'   : String ,
                     _id: false} ]
    })

var Match = mongoose.model('Match', matchSchema)


module.exports = {
    Match: Match
}
