var mongoose = require('mongoose');

var profileSchema = mongoose.Schema({
        username: String,
        artists: [String]
    })

var Profile = mongoose.model('Profile', profileSchema)


module.exports = {
    Profile: Profile
}
