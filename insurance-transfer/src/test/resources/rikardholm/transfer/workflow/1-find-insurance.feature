Feature: Find existing insurances for person
  In order to know which insurances can be transferred from the company
  As a person
  I want to know when I have an insurance at the company

  Scenario: One insurance exists
    Given insurance 345829 belongs to customer 450627-3108
    When a find-insurance message contains personal identifier 450627-3108
    Then the outgoing message should contain the insurance information

  Scenario: No insurances exist
    Given no insurance belongs to customer 560913-4503
    When a find-insurance message contains personal identifier 560913-4503
    Then the outgoing message should be no-information