Feature: Select device from dropdown

Scenario: Select Aeropress from dropdown 
  When I launch the app
  Then I select "Aeropress" from "device_spinner"
  Then I should see "Aeropress"
  Then I should see "Steep"
  Then I should see "Plunge"

Scenario: Select Chemex from dropdown 
  When I launch the app
  Then I select "Chemex" from "device_spinner"
  Then I should see "Chemex"
  Then I should see "Pre"
  Then I should see "Bloom"
  Then I should see "Brew"

Scenario: Select Clever from dropdown 
  When I launch the app
  Then I select "Clever" from "device_spinner"
  Then I should see "Clever"
  Then I should see "Pre"
  Then I should see "Bloom"
  Then I should see "Brew"

Scenario: Select Espresso from dropdown 
  When I launch the app
  Then I select "Espresso" from "device_spinner"
  Then I should see "Espresso"
  Then I should not see "Pre"
  Then I should not see "Bloom"
  Then I should not see "Brew"

Scenario: Select French Press from dropdown 
  When I launch the app
  Then I select "French Press" from "device_spinner"
  Then I should see "French Press"
  Then I should see "Bloom"
  Then I should see "Brew"
  Then I should see "Plunge"

Scenario: Select Pour Over from dropdown 
  When I launch the app
  Then I select "Pour Over" from "device_spinner"
  Then I should see "Pour Over"
  Then I should see "Pre"
  Then I should see "Bloom"
  Then I should see "Brew"
