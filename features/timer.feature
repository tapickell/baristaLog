Feature: Select start button on all devices

Scenario: Select Espresso and press start then stop
  When I select "Espresso" from "device_spinner"
  Then I should not see "Pre"
  Then I should see "00:00"
  Then I press the "START" button
  Then I wait for 3 seconds
  Then I press the "STOP" button
  Then I should see "00:05"

Scenario: Select Aeropress and press start and wait for timer to stop
  When I select "Aeropress" from "device_spinner"
  Then I should see "Steep"
  Then I should see "00:10"
  Then I should see "00:30"
  Then I press the "START" button
  Then I wait to see "Plunge"
  Then I should see "00:19"
  Then I wait for the "START" button to appear
  Then I should see "00:00"

Scenario: Select Chemex and press start and wait for timer to stop
  When I select "Chemex" from "device_spinner"
  Then I should see "Pre"
  Then I should see "00:30"
  Then I should see "04:00"
  Then I press the "START" button
  Then I wait to see "Bloom"
  Then I should see "00:28"
  Then I wait to see "Brew"
  Then I should see "02:58"
  Then I wait to see "02:00"
  Then I wait to see "01:30"
  Then I wait to see "01:00"
  Then I wait for the "START" button to appear
  Then I should see "00:00"

Scenario: Select Clever and press start and wait for timer to stop
  When I select "Clever" from "device_spinner"
  Then I should see "Pre"
  Then I should see "00:30"
  Then I should see "04:00"
  Then I press the "START" button
  Then I wait to see "Bloom"
  Then I should see "00:28"
  Then I wait to see "Brew"
  Then I should see "02:58"
  Then I wait to see "02:00"
  Then I wait to see "01:30"
  Then I wait to see "01:00"
  Then I wait for the "START" button to appear
  Then I should see "00:00"

Scenario: Select French Press and press start and wait for timer to stop
  When I select "French Press" from "device_spinner"
  Then I should see "Bloom"
  Then I should see "00:30"
  Then I should see "04:00"
  Then I press the "START" button
  Then I wait to see "Brew"
  Then I should see "02:58"
  Then I wait to see "02:00"
  Then I wait to see "01:30"
  Then I wait to see "01:00"
  Then I wait to see "Plunge"
  Then I should see "00:28"
  Then I wait for the "START" button to appear
  Then I should see "00:00"

Scenario: Select Pour Over and press start and wait for timer to stop
  When I select "Pour Over" from "device_spinner"
  Then I should see "Pre"
  Then I should see "00:30"
  Then I should see "02:30"
  Then I press the "START" button
  Then I wait to see "Bloom"
  Then I should see "00:28"
  Then I wait to see "Brew"
  Then I should see "01:28"
  Then I wait to see "01:00"
  Then I wait for the "START" button to appear
  Then I should see "00:00"








