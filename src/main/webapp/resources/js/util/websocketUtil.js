/**
 * Pure STOMP WebSocket Utility
 * 
 * Protocol: STOMP + WebSocket
 * Body: JSON
 * Dependency: Server Side - Spring WebSocket(4.x), Spring Messaging(4.x)
 *             Client Side - stomp.js(https://stomp.github.io/), WebSocket(HTML5)
 */

var PureStompManager;
(function(){
	'use strict';
	
	PureStompManager = (function(){
		var defaultOptions = {
			host: '',
			retryCount: 0,
			sendTimeout: null,
			
			preConnect: function(){
				console.log('Connecting!!');
			},
			postConnect: function(){
				console.log('Connected!!');
			},
			preDisconnect: function(){
				console.log('disconnecting!!');
			},
			postDisconnect: function(){
				console.log('disconnected!!');
			},
			subscribes: {
				sample: {
					destination: '/topic/sample',
					recevProc: function(data){
						console.log("data:", data);
					}
				}
			}
		};
		
		function PureStompManager(options){
			if(options && options.host && options.host.indexOf('://') < 0){
				options.host = Util.getHost() + options.host;
			}
			this.options = options = $.extend(true, defaultOptions, options)
			console.info('Pure Stomp Options', options);
			
			this.client;
			this.sendQueue = new Queue();
			
		}
		
		PureStompManager.prototype.connect = function(){
			var THIS = this;
			var options = this.options;
			var client = this.client = Stomp.client(options.host);
			
			if(typeof options.preConnect == 'function'){
				options.preConnect();
			}
			
			var connectCallback = function(frame){
				options.retryCount = 0;
				if(typeof options.postConnect == 'function'){
					options.postConnect();
				}
			}
			var errorCallback = function(frame){
				options.retryCount++;
				console.warn('Pure Stomp Connect Close or Fail. retry:%d', options.retryCount);
				THIS.connect();
			}
			
			client.connect({}, connectCallback, errorCallback);
		}
		
		PureStompManager.prototype.disconnect = function(){
			var options = this.options;
			var client = this.client;
			
			if(typeof options.preDisconnect == 'function'){
				options.preDisconnect();
			}
			client.disconnect(function(){
				if(typeof options.postDisconnect == 'function'){
					options.postDisconnect();
				}
			});
		}
		
		PureStompManager.prototype.send = function(api, message){
			var options = this.options;
			var client = this.client;
			
			if(typeof api == 'object'){
				message = api.message;
				api = api.api;
			}
			if(typeof message == 'object'){
				message = JSON.stringify(message);
			}
			
			var sendData = {
					destination: api,
					payload: message
			}
			
			this.sendQueue.enqueue(sendData);
			console.info('Pure Stomp send. data:%O', sendData);
			if(this.sendTimeout == null){
				this._sendProcess();
			}
		}
		
		PureStompManager.prototype._sendProcess = function(){
			var THIS = this;
			var client = this.client;
			console.info('stomp client.', client);
			if(!client || !client.connected){
				this.sendTimeout = setTimeout(function(){
					THIS._sendProcess();
				}, 100);
				return;
			}
			
			this.sendTimeout = null;
			while(!this.sendQueue.isEmpty()){
				console.info('Pure Stomp Send Queue size:%d', this.sendQueue.length());
				var sendData = this.sendQueue.dequeue();
				client.send(sendData.destination, {}, sendData.payload);
			}
		}
		
		return PureStompManager;
	})();
	
	var Util = {
		getHost: function(){
			var protocol = window.location.protocol;
			var wsProtocol = protocol == 'http:' ? 'ws:' : 'wss:';
			var host = protocol + '//' + window.location.host;
			
			var base = document.querySelector("base");
			if(base != null){
				host = base.href.replace(protocol, wsProtocol);
			}
			return host;
		}
	};
	
	var Queue = function(){
		this.datas = [];
	}
	Queue.prototype.isEmpty = function(){
		return this.datas.length <= 0;
	}
	Queue.prototype.length = function(){
		return this.datas.length;
	}
	Queue.prototype.enqueue = function(element){
		this.datas.push(element);
	}
	Queue.prototype.dequeue = function(){
		return this.datas.shift();
	}
	
}).call(this);


var p = new PureStompManager({host: '/ws/default'})
p.connect();

/*for(i=0;i<1000;i++){
	p.send({
		api: '/app/send',
		message: {test: 'message'+i}
	});
}*/