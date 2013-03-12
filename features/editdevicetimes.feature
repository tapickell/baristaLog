Feature: Select edit button and edit times for device

Scenario: Press edit button and select new times for Chemex
  When I select "Chemex" from "device_spinner"
  Then I select "Edit Times" from the menu
  Then I should see "Hello World!"
  Then I press the "Save" Button