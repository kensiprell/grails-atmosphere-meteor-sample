<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Atmosphere Test</title>
	<r:require module="atmosphere-meteor-jquery"/>
	<r:layoutResources/>
%{--
	<asset:javascript src="atmosphere-meteor-jquery.js"/>
--}%

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
						transport: 'websocket',
						//transport: 'long-polling',
						fallbackTransport: 'long-polling',
						trackMessageLength: true
					},
					atmosphereRequest = $.extend({}, defaults, options);
			atmosphereRequest.onOpen = function (response) {
				console.log('atmosphereOpen transport: ' + response.transport);
			};
			atmosphereRequest.onReconnect = function (request, response) {
				console.log("atmosphereReconnect");
			};
			atmosphereRequest.onMessage = function (response) {
				//console.log('onMessage: ' + response.responseBody);
				Jabber.onMessage(response);
			};
			atmosphereRequest.onError = function (response) {
				console.log('atmosphereError: ' + response);
			};
			atmosphereRequest.onTransportFailure = function (errorMsg, request) {
				console.log('atmosphereTransportFailure: ' + errorMsg);
			};
			atmosphereRequest.onClose = function (response) {
				console.log('atmosphereClose: ' + response);
			};
			switch (options.type) {
				case 'chat':
					Jabber.chatSubscription = Jabber.socket.subscribe(atmosphereRequest);
					break;
				case 'notification':
					Jabber.notificationSubscription = Jabber.socket.subscribe(atmosphereRequest);
					break;
				case 'public':
					Jabber.publicSubscription = Jabber.socket.subscribe(atmosphereRequest);
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
			var atmosphereRequest = {
				type: 'chat',
				url: 'atmosphere/chat/12345'
			};
			Jabber.subscribe(atmosphereRequest);
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
			var atmosphereRequest = {
				type: 'notification',
				// note that the DefaultMeteorHandler uses the header below for setting and getting the broadcaster
				headers: {'X-AtmosphereMeteor-Mapping': '/atmosphere/notification/userName'},
				url: 'atmosphere/notification/userName'
			};
			Jabber.subscribe(atmosphereRequest);
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
			var atmosphereRequest = {
				type: 'public',
				url: 'atmosphere/public',
				trackMessageLength: false
			};
			Jabber.subscribe(atmosphereRequest);
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