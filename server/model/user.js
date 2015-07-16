var mongoose = require('mongoose');

var userSchema = mongoose.Schema({
        username: String,
        socketid: String
    })

var User = mongoose.model('User', userSchema)


module.exports = {
    User: User
}
