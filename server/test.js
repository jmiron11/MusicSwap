var WebSocket = require('ws')
, ws = new WebSocket('ws://musicswap-jmironapps.rhcloud.com/8000');
ws.on('connect', function(){
    console.log("Connected")
});
ws.on('open', function() {
   console.log("WOOO")
});
ws.on('message', function(message) {
    console.log('received: %s', message);
});
