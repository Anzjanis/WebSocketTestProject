@Spread @All
Feature: Spread functionality testing

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


  Scenario Outline: I create successful spread subscription and I receive at least 5 updates about it
    Given web socket client connection is created
    Then web socket client state should be "OPEN"

    When I create event "subscribe" with the following information:
      | reqid   | pair   | depth   | interval   | name   | ratecounter   | snapshot   |
      | <reqid> | <pair> | <depth> | <interval> | <name> | <ratecounter> | <snapshot> |
    Then I expect to receive at least 5 updates about my spread subscription
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
    Then I expect to receive at least 5 updates about my spread subscription
    And I check following information in spread: timestamp is increasing for each entry
    Examples:
      | reqid  | pair    | depth | interval | name   | ratecounter | snapshot |
      | random | XBT/USD | null  | null     | spread | false       | null     |