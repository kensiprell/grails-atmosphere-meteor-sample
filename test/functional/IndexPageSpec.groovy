import geb.spock.GebReportingSpec
import org.openqa.selenium.Keys
import spock.lang.*
import pages.IndexPage

@Stepwise
class IndexPageSpec extends GebReportingSpec {

	def "IndexPage"() {
		when:
		to IndexPage

		then:
		at IndexPage
	}

	def "chat subscribe button is disabled on click"() {
		when:
		buttonChatSubscribe.click()

		then:
		sleep 1000
		buttonChatSubscribe.@disabled == "true"
	}

	//@Ignore
	def "chat sends and receives a test message"() {
		when:
		chatInput << "test" << Keys.ENTER

		then:
		sleep 1000
		chatWindow.text() == "message: test"
	}

	def "notification subscribe button is disabled on click"() {
		when:
		buttonNotificationSubscribe.click()

		then:
		buttonNotificationSubscribe.@disabled == "true"
	}

	def "notification send button click receives message"() {
		when:
		buttonNotificationSend.click()

		then:
		sleep 1000
		pNotification.text().startsWith("This")
	}

	def "public subscribe button is disabled on click"() {
		when:
		buttonPublicSubscribe.click()

		then:
		buttonPublicSubscribe.@disabled == "true"
	}

	def "public send button click receives message"() {
		when:
		buttonPublicTrigger.click()

		then:
		sleep 1000
		pPublicUpdate.text().startsWith("Updated")
		sleep 12000
		pPublicUpdate.text().contains("Finished")
	}

	def "unsubscribe button click enables buttons"() {
		when:
		buttonUnsubscribe.click()

		then:
		sleep 2000
		buttonChatSubscribe.@disabled == ""
		buttonNotificationSubscribe.@disabled == ""
		buttonNotificationSend.@disabled == ""
		buttonPublicSubscribe.@disabled == ""
		buttonPublicTrigger.@disabled == ""
		buttonUnsubscribe.@disabled == ""
	}
}
