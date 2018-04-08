package com.example.setpathissue

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT

@SpringBootTest(webEnvironment = DEFINED_PORT)
class SetPathIssueIntegrationSpec extends Specification {

    @Autowired
    WebTestClient webClient

    def accountId = '2610 (Deferred Income)'

    def responseBodyListElementType = new ParameterizedTypeReference<AccountEntry>() {}

    def "should return account entries in case API is called directly"() {
        given:
            WebTestClient.RequestBodySpec request = webClient.get()
                .uri('accounts/{accountId}/entries', [accountId: accountId])

        when:
            WebTestClient.ResponseSpec response = request.exchange()

        then:
            response
                .expectStatus().is2xxSuccessful()
                .expectBodyList(responseBodyListElementType).hasSize(3)
    }

    def "should fail request routing in case SetPath filter is called with path that contains encoded characters"() {
        given:
            WebTestClient.RequestBodySpec request = webClient.get()
                    .uri('api/countries/se/accounting/accounts/{accountId}/entries', [accountId: accountId])

        when:
            WebTestClient.ResponseSpec response = request.exchange()

        then:
            response.expectStatus().is5xxServerError()
    }

    def "should return account entries in case SCG's fixed SetPath filter is used"() {
        given:
            WebTestClient.RequestBodySpec request = webClient.get()
                .uri('/api-fixed/countries/se/accounting/accounts/{accountId}/entries', [accountId: accountId])

        when:
            WebTestClient.ResponseSpec response = request.exchange()

        then:
            response
                .expectStatus().is2xxSuccessful()
                .expectBodyList(responseBodyListElementType).hasSize(3)
    }
}
