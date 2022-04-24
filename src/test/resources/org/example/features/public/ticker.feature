@Ticker @All @Dev
Feature: Ticker functionality testing


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

