# Sample [Spring Cloud Gateway](https://github.com/spring-cloud/spring-cloud-gateway) application demonstrating issue [#253](https://github.com/spring-cloud/spring-cloud-gateway/issues/253)

## Context
Suppose there's 3rd party accounting product as a service that allows to receive general ledger account journal entries for specified account. Account identifier can contain whitespaces.
  
## Reproducing issue
[SetPathIssueIntegrationSpec](src/test/groovy/com/example/setpathissue/SetPathIssueIntegrationSpec.groovy) integration test demonstrates SetPath filter behaviour in case path contains characters that should be encoded (e.g. whitespace character). This test also demonstrates how this issue can be fixed by replacing decoded path with encoded.
Same can be tested manually by:
* launching sample application by executing `./gradlew bootRun` in project directory
* Executing a request to route containing SetPath filter `curl -i -H "Accept: application/json" -X GET "http://127.0.0.1:8447/api/countries/se/accounting/accounts/2610%20(Deferred%20Income)/entries"`. HTTP status code 500 is returned.
* Executing a request to route containing fixed SetPath filter `curl -i -H "Accept: application/json" -X GET "http://127.0.0.1:8447/api-fixed/countries/se/accounting/accounts/2610%20(Deferred%20Income)/entries"`. HTTP status code 200 and valid JSON payload is returned.
