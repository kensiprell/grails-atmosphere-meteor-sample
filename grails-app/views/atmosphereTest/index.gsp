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
	if (!window.console) console = {log: function () {
	}};

	/*
	 The Jabber variable holds all JavaScript code required for communicating with the server.
	 It basically wraps the functions in atmosphere.js and jquery.atmosphere.js.
	 */
	var Jabber = {
		socket: null,
		chatSubscription: null,
		notificationSubscription: null,
		publicSubscription: null,
		transport: null,

		subscribe: function (options) {
			var defaults = {
						type: '',
						contentType: "application/json",
						shared: false,
						//transport: 'websocket',
						transport: 'long-polling',
						fallbackTransport: 'long-polling',
						trackMessageLength: true
					},
					jabberRequest = $.extend({}, defaults, options);
			jabberRequest.onOpen = function (response) {
				console.log('jabberOpen transport: ' + response.transport);
			};
			jabberRequest.onReconnect = function (request, response) {
				console.log("jabberReconnect");
			};
			jabberRequest.onMessage = function (response) {
				Jabber.onMessage(response);
			};
			jabberRequest.onError = function (response) {
				console.log('jabberError: ' + response);
			};
			jabberRequest.onTransportFailure = function (errorMsg, request) {
				console.log('jabberTransportFailure: ' + errorMsg);
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
			$('#chat-window').html('');
			$('#notification').html('');
			$('#public-update').html('');
			$('button').each(function () {
				$(this).removeAttr('disabled');
			})
		},

		onMessage: function (response) {
			var data = response.responseBody;
			if ((message == '')) {
				return;
			}
			console.log(data);
			var message = JSON.parse(data);
			var type = message.type;
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
		}
	};

	$(window).unload(function () {
		Jabber.unsubscribe();
	});

	$(document).ready(function () {
		if (typeof atmosphere == 'undefined') {
			// if using jquery.atmosphere.js
			Jabber.socket = $.atmosphere;
		} else {
			// if using atmosphere.js
			Jabber.socket = atmosphere;
		}

		$('#chat-subscribe').on('click', function () {
			var jabberRequest = {
				type: 'chat',
				url: 'jabber/chat/12345'
			};
			Jabber.subscribe(jabberRequest);
			$(this).attr('disabled', 'disabled');
			$('#chat-input').focus();
		});

		$('#chat-input').keypress(function (event) {
			if (event.which === 13) {
				event.preventDefault();
				var data = {
					type: 'chat',
					message: $(this).val()
				};
				Jabber.chatSubscription.push(JSON.stringify(data));
				$(this).val('');
			}
		});

		$('#notification-subscribe').on('click', function () {
			var jabberRequest = {
				type: 'notification',
				// note that the DefaultMeteorHandler uses the header below for setting and getting the broadcaster
				headers: {'X-AtmosphereMeteor-Mapping': '/jabber/notification/userName'},
				url: 'jabber/notification/userName'
			};
			Jabber.subscribe(jabberRequest);
			$(this).attr('disabled', 'disabled');
		});

		$('#notification-send').on('click', function () {
			var data = {
				type: 'notification',
				message: 'This is a notification message sent at ' + new Date() + '.'
			};
			Jabber.notificationSubscription.push(JSON.stringify(data));
		});

		$('#public-subscribe').on('click', function () {
			var jabberRequest = {
				type: 'public',
				url: 'jabber/public',
				trackMessageLength: false
			};
			Jabber.subscribe(jabberRequest);
			$(this).attr('disabled', 'disabled');
		});

		$('#public-trigger').on('click', function () {
			$.ajax('atmosphereTest/triggerPublic');
			$(this).attr('disabled', 'disabled');
		});

		$('#unsubscribe').on('click', function () {
			Jabber.unsubscribe();
		});
	});
</script>
</body>
</html>