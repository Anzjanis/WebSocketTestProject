@Deprecated
Feature: Subscribe functionality testing


  Scenario Outline: I create successful subscription about ticker and then receive confirmation about it
    Given web socket client connection is created
    Then web socket client state should be "OPEN"

    When I create event "subscribe" with the following information:
      | reqid   | pair   | depth   | interval   | name   | ratecounter   | snapshot   |
      | <reqid> | <pair> | <depth> | <interval> | <name> | <ratecounter> | <snapshot> |
    Then I expect to receive successful confirmation message about my subscription
    Examples:
      | reqid  | pair    | depth | interval | name   | ratecounter | snapshot |
      | random | XBT/USD | null  | null     | ticker | false       | null     |
      | random | XBT/EUR | null  | null     | ticker | false       | null     |


  Scenario Outline: I create unsuccessful subscription about ticker and then receive notification about it
    Given web socket client connection is created
    Then web socket client state should be "OPEN"

    When I create event "subscribe" with the following information:
      | reqid   | pair   | depth   | interval   | name   | ratecounter   | snapshot   |
      | <reqid> | <pair> | <depth> | <interval> | <name> | <ratecounter> | <snapshot> |
    Then I expect to receive unsuccessful confirmation message about my subscription with error message "<message>"
    Examples:
      | reqid  | pair    | depth | interval | name   | ratecounter | snapshot | message                             |
      | random | XBT/USD | null  | null     | ticker | false       | true     | Unsupported field: 'snapshot'       |
      | random | USD/BTC | null  | null     | ticker | false       | null     | Currency pair not supported USD/BTC |


  Scenario: I create subscription, receive update about ticker and validate if ask price is bigger then bid price
    Given web socket client connection is created
    Then web socket client state should be "OPEN"

    When I create event "subscribe" with the following information:
      | reqid  | pair    | depth | interval | name   | ratecounter | snapshot |
      | random | XBT/USD | null  | null     | ticker | false       | null     |
    Then I expect to receive information about ticker
    And I check following information: ask price is bigger then bid price


  Scenario Outline: I create successful subscription about spread and then receive confirmation about it
    Given web socket client connection is created
    Then web socket client state should be "OPEN"

    When I create event "subscribe" with the following information:
      | reqid   | pair   | depth   | interval   | name   | ratecounter   | snapshot   |
      | <reqid> | <pair> | <depth> | <interval> | <name> | <ratecounter> | <snapshot> |
    Then I expect to receive successful confirmation message about my subscription
    Examples:
      | reqid  | pair    | depth | interval | name   | ratecounter | snapshot |
      | random | XBT/USD | null  | null     | spread | false       | null     |
      | random | XBT/EUR | null  | null     | spread | false       | null     |


  Scenario Outline: I create successful spread subscription and I receive at least 10 updates about it
    Given web socket client connection is created
    Then web socket client state should be "OPEN"

    When I create event "subscribe" with the following information:
      | reqid   | pair   | depth   | interval   | name   | ratecounter   | snapshot   |
      | <reqid> | <pair> | <depth> | <interval> | <name> | <ratecounter> | <snapshot> |
    Then I expect to receive at least 10 updates about my spread subscription
    Examples:
      | reqid  | pair    | depth | interval | name   | ratecounter | snapshot |
      | random | XBT/USD | null  | null     | spread | false       | null     |


  Scenario Outline: I create unsuccessful subscription about spread and then receive notification about it
    Given web socket client connection is created
    Then web socket client state should be "OPEN"

    When I create event "subscribe" with the following information:
      | reqid   | pair   | depth   | interval   | name   | ratecounter   | snapshot   |
      | <reqid> | <pair> | <depth> | <interval> | <name> | <ratecounter> | <snapshot> |
    Then I expect to receive unsuccessful confirmation message about my subscription with error message "<message>"
    Examples:
      | reqid  | pair    | depth | interval | name   | ratecounter | snapshot | message                                   |
      | random | USD/BTC | null  | null     | ticker | false       | null     | Currency pair not supported USD/BTC       |
      | random | USD/BTC | 99    | null     | ticker | false       | null     | Subscription ticker doesn't require depth |


  Scenario Outline:  I create spread subscription, receive update about spread and validate it
    Given web socket client connection is created
    Then web socket client state should be "OPEN"

    When I create event "subscribe" with the following information:
      | reqid   | pair   | depth   | interval   | name   | ratecounter   | snapshot   |
      | <reqid> | <pair> | <depth> | <interval> | <name> | <ratecounter> | <snapshot> |
    Then I expect to receive information about spread
    Examples:
      | reqid  | pair    | depth | interval | name   | ratecounter | snapshot |
      | random | XBT/USD | null  | null     | spread | false       | null     |


  Scenario Outline: I create successful spread subscription and I validate update timestamp
    Given web socket client connection is created
    Then web socket client state should be "OPEN"

    When I create event "subscribe" with the following information:
      | reqid   | pair   | depth   | interval   | name   | ratecounter   | snapshot   |
      | <reqid> | <pair> | <depth> | <interval> | <name> | <ratecounter> | <snapshot> |
    Then I expect to receive at least 10 updates about my spread subscription
    And I check following information in spread: timestamp is increasing for each entry
    Examples:
      | reqid  | pair    | depth | interval | name   | ratecounter | snapshot |
      | random | XBT/USD | null  | null     | spread | false       | null     |


  Scenario Outline: I create successful trade subscription, then I cancel it and expect no more trade related subscriptions are coming
    Given web socket client connection is created
    Then web socket client state should be "OPEN"

    When I create event "subscribe" with the following information:
      | reqid   | pair   | depth   | interval   | name   | ratecounter   | snapshot   |
      | <reqid> | <pair> | <depth> | <interval> | <name> | <ratecounter> | <snapshot> |

    And I expect to receive at least 2 updates about my trade subscription

    When I create event "unsubscribe" with the following information:
      | reqid   | pair   | depth   | interval   | name   |
      | <reqid> | <pair> | <depth> | <interval> | <name> |
    Then I expect no more new updates are coming from trade subscription - waiting time 10 seconds

    Examples:
      | reqid | pair    | depth | interval | name  | ratecounter | snapshot |
      | 10    | XBT/USD | null  | null     | trade | false       | null     |


  Scenario Outline: I create unsuccessful subscription about trade and then receive notification about it
    Given web socket client connection is created
    Then web socket client state should be "OPEN"

    When I create event "subscribe" with the following information:
      | reqid   | pair   | depth   | interval   | name   | ratecounter   | snapshot   |
      | <reqid> | <pair> | <depth> | <interval> | <name> | <ratecounter> | <snapshot> |
    Then I expect to receive unsuccessful confirmation message about my subscription with error message "<message>"
    Examples:
      | reqid  | pair    | depth | interval | name  | ratecounter | snapshot | message                   |
      | random | USD/BTC | null  | null     | trade | false       | null     | Currency pair not supported USD/BTC       |
      | random | USD/BTC | 99    | null     | trade | false       | null     | Subscription trade doesn't require depth  |
      | random | USD/BTC | 99    | null     | trad1 | false       | null     | Subscription name invalid |