Feature: Select device from dropdown

Scenario: Select Aeropress from dropdown 
  When I launch the app
  Then I select "Aeropress" from "device_spinner"
  Then I should see "Steep"

Scenario: Select Chemex from dropdown 
  When I launch the app
  Then I select "Chemex" from "device_spinner"
  Then I should see "Pre"

Scenario: Select Clever from dropdown 
  When I launch the app
  Then I select "Clever" from "device_spinner"
  Then I should see "Pre"

Scenario: Select Espresso from dropdown 
  When I launch the app
  Then I select "Espresso" from "device_spinner"
  Then I should not see "Pre"

Scenario: Select French Press from dropdown 
  When I launch the app
  Then I select "French Press" from "device_spinner"
  Then I should see "Bloom"

Scenario: Select Pour Over from dropdown 
  When I launch the app
  Then I select "Pour Over" from "device_spinner"
  Then I should see "Pre"
