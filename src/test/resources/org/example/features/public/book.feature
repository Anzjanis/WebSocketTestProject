Feature: First Test


  @TestTag
  Scenario: Lets test this
    Given web socket client connection is created
    Then web socket client state should be "OPEN"