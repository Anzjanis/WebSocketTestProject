@Trade @All
Feature: Trade functionality testing

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