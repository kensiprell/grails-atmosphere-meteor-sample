package pages

import geb.Page

class IndexPage extends Page {
	static url = ""
	static at = { title == "Atmosphere Test" }
	static content = {
		buttonChatSubscribe { $("button", id: "chat-subscribe") }
		chatWindow { $("div", id: "chat-window") }
		chatInput { $("input", id: "chat-input") }
		buttonNotificationSubscribe { $("button", id: "notification-subscribe") }
		buttonNotificationSend { $("button", id: "notification-send") }
		pNotification { $("p", id: "notification") }
		pPublicUpdate { $("p", id: "public-update") }
		buttonPublicSubscribe { $("button", id: "public-subscribe") }
		buttonPublicTrigger { $("button", id: "public-trigger") }
		buttonUnsubscribe { $("button", id: "unsubscribe") }
	}
}
