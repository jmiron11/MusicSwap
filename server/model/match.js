var mongoose = require('mongoose');

var matchSchema = mongoose.Schema({
        username: String,
        matches: [{ username: String, artist: String }]
    })

var Match = mongoose.model('Match', matchSchema)


module.exports = {
    Match: Match
}
