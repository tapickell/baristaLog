Feature: Launch

Scenario: See text on launch
  When I launch the app
  Then I should see "Hello world!"