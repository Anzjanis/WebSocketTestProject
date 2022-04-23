@Smoke
Feature: Quick smoke test to check if it is possible to open connection and close it

  Scenario: After WS client is disconnected it should starting changing states from Open->Closing->Closed
    Given web socket client connection is created
    Then web socket client state should be "OPEN"
    When web socket client connection is disconnected
    Then web socket client state should be "CLOSING"
    Then web socket client state should be "CLOSED"


  Scenario Outline: Send ping message with different ID's to check if connection is alive and working as expected
    Given web socket client connection is created
    Then web socket client state should be "OPEN"
    When I send ping request with "<id>" number
    Then I receive pong response

    Examples:
      | id     |
      | random |
      | 13     |
      | empty  |

