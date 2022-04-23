Feature: Subscribe :)

  @Dev
  Scenario Outline: I can create subscription for my favorite currency pair
    Given web socket client connection is created
    Then web socket client state should be "OPEN"

    When I create event "subscribe" with the following information:
      | reqid   | pair   | depth   | interval   | name   | ratecounter   | snapshot   |
      | <reqid> | <pair> | <depth> | <interval> | <name> | <ratecounter> | <snapshot> |

    Examples:
      | reqid  | pair    | depth | interval | name   | ratecounter | snapshot |
      | random | XBT/USD | null  | null     | ticker | false       | null     |