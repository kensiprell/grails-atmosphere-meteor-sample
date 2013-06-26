<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Atmosphere Test</title>
	<r:require module="jquery"/>
	%{--TODO change the module below to either atmosphere-meteor or atmosphere-meteor-jquery --}%
	<r:require module="atmosphere-meteor"/>
	<r:layoutResources/>
</head>

<body>
<h1>Chat</h1>
<p>
	<button id="chat-subscribe">Subscribe</button>
</p>
<div id="chat-window"></div>
<input id="chat-input" type="text"/>

<h1>Notification</h1>
<p>
	<button id="notification-subscribe">Subscribe</button>
	<button id="notification-send">Send</button>
</p>
<p id="notification"></p>

<h1>Public</h1>
<p>After clicking the Subscribe and Trigger buttons, the area below should update every 2 seconds for 5 times.</p>
<p id="public-update"></p>
<p>
	<button id="public-subscribe">Subscribe</button>
	<button id="public-trigger">Trigger</button>
</p>

<h1>Unsubscribe</h1>
<p>Clicking the button below will end all subscriptions.</p>
<p>
	<button id="unsubscribe">Unsubscribe</button>
</p>

<script type="text/javascript">
	// required for IE console logging
	if (!window.console) console = {log: function () {}};

	$(window).unload(function () {
		unsubscribe();
	});

	function chatSubscribe() {
		var jabberRequest = {
			type: 'chat',
			url: 'jabber/chat/12345',
			trackMessageLength: true
		};
		Jabber.subscribe(jabberRequest);
	}

	function notificationSubscribe() {
		var jabberRequest = {
			type: 'notification',
			// note that the DefaultMeteorHandler uses the header below for setting and getting the broadcaster
			headers: {'X-AtmosphereMeteor-Mapping': '/jabber/notification/userName'},
			url: 'jabber/notification/userName',
			trackMessageLength: true
		};
		Jabber.subscribe(jabberRequest);
	}

	function unsubscribe() {
		Jabber.unsubscribe();
		$('#chat-window').html('');
		$('#notification').html('');
		$('#public-update').html('');
		$('button').each(function () {
			$(this).removeAttr('disabled');
		})
	}

	/*
	 The Jabber variable holds all JavaScript code required for communicating with the server.
	 It basically wraps the functions in atmosphere.js and jquery.atmosphere.js.
	 */
	var Jabber = {
		resource: null,
		socket: null,
		chatSubscription: null,
		notificationSubscription: null,
		publicSubscription: null,
		transport: null,

		publishOptions: {
			type: '',
			resource: '',
			message: ''
		},

		subscribe: function (options) {
			var defaults = {
						type: '',
						contentType: "application/json",
						shared: false,
						headers: {},
						//transport: 'websocket',
						transport: 'long-polling',
						fallbackTransport: 'long-polling',
						trackMessageLength: false
					},
					jabberRequest = $.extend({}, defaults, options);
			jabberRequest.onOpen = function (response) {
				Jabber.transport = response.transport;
				console.log('jabberOpen transport: ' + response.transport);
			};
			jabberRequest.onReconnect = function (request, response) {
				console.log("jabberReconnect");
			};
			jabberRequest.onMessage = function (response) {
				console.log('jabberMessage: message received');
				Jabber.onMessage(response);
			};
			jabberRequest.onError = function (response) {
				console.log('jabberError: ' + response);
			};
			jabberRequest.onTransportFailure = function (errorMsg, request) {
				console.log('jabberTransportFailure: ' + errorMsg);
			};
			jabberRequest.onMessagePublished = function (request, response) {
				console.log('jabberMessagePublished');
			};
			jabberRequest.onClose = function (response) {
				console.log('jabberClose: ' + response);
			};
			switch (options.type) {
				case 'chat':
					Jabber.chatSubscription = Jabber.socket.subscribe(jabberRequest);
					break;
				case 'notification':
					Jabber.notificationSubscription = Jabber.socket.subscribe(jabberRequest);
					break;
				case 'public':
					Jabber.publicSubscription = Jabber.socket.subscribe(jabberRequest);
					break;
				default:
					return false;
			}
		},

		unsubscribe: function () {
			Jabber.socket.unsubscribe();
		},

		onMessage: function (response) {
			var data = response.responseBody;
			if ((message == '')) {
				return;
			}
			//console.log(data);
			var message = JSON.parse(data);
			var type = message.type;
			//console.log('type: ' + message.type);
			//console.log('resource: ' + message.resource);
			//console.log('message: ' + message.message);
			if (type == 'chat') {
				var $chat = $('#chat-window');
				$chat.append('message: ' + message.message + '<br/>');
				$chat.scrollTop($chat.height());
			}
			if (type == 'notification') {
				$('#notification').html(message.message);
			}
			if (type == 'public') {
				$('#public-update').html(message.message);
				if (message.message == 'Finished.') {
					$('#public-trigger').removeAttr('disabled');
				}
			}
		},

		publish: function (options) {
			var settings = $.extend({}, Jabber.publishOptions, options),
					type = settings.type,
					resource = settings.resource,
					message = settings.message;
			if ((type == '') || (message == '')) {
				return false;
			}
			var data = {
				type: type,
				resource: resource,
				message: message
			};
			switch (type) {
				case 'chat':
					Jabber.chatSubscription.push(JSON.stringify(data));
					// TODO Remove this hack if AtmosphereResourceLifecycleInterceptor works
					if (Jabber.transport == 'long-polling') {
						chatSubscribe();
					}
					break;
				case 'notification':
					Jabber.notificationSubscription.push(JSON.stringify(data));
					// TODO Remove this hack if AtmosphereResourceLifecycleInterceptor works
					if (Jabber.transport == 'long-polling') {
						notificationSubscribe();
					}
					break;
				default:
					return false;
			}
		}
	};

	$(document).ready(function () {
		if (typeof atmosphere == 'undefined') {
			// if using jquery.atmosphere.js
			Jabber.socket = $.atmosphere;
		} else {
			// if using atmosphere.js
			Jabber.socket = atmosphere;
		}

		$('#chat-subscribe').on('click', function () {
			chatSubscribe();
			$(this).attr('disabled', 'disabled');
		});

		$('#chat-input').keypress(function (event) {
			if (event.which === 13) {
				event.preventDefault();
				var message = $(this).val();
				Jabber.publish({
					type: 'chat',
					resource: '/jabber/chat/12345',
					message: message
				});
				$(this).val('');
			}
		});

		$('#notification-subscribe').on('click', function () {
			notificationSubscribe();
			$(this).attr('disabled', 'disabled');
		});

		$('#notification-send').on('click', function () {
			Jabber.publish({
				type: 'notification',
				resource: '/jabber/notification/userName',
				message: 'This is a notification message sent at ' + new Date() + '.'
			});
		});

		$('#public-subscribe').on('click', function () {
			var jabberRequest = {
				type: 'public',
				url: 'jabber/public'
			};
			Jabber.subscribe(jabberRequest);
			$(this).attr('disabled', 'disabled');
		});

		$('#public-trigger').on('click', function () {
			$.ajax('atmosphereTest/triggerPublic');
			$(this).attr('disabled', 'disabled');
		});

		$('#unsubscribe').on('click', function () {
			unsubscribe();
		});
	});
</script>
</body>
</html>