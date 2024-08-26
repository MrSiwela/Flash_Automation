Feature: Open Demo Webshop Tricetis
Scenario Outline:
  Given A user navigates to the correct URL "<url>"
  When The user Navigates to the registration Page and Registers Using The following details : "<Gender>","<FirstName>","<LastName>","<Email>" and "<Password>".
  Then The user Logs In with the given Details.
  And Adds all desktop Items to the cart.
  And Removes an item in the cart.
  And Validates the cart with details "<Country>","<StateProvince>","<Zipcode>".
  And Checkout items from cart and validate checkout details "<Country>","<StateProvince>","<Zipcode>","<PhoneNumber>","<City>", "<Address1>" and "<Address2>".
  Then Navigates to my account and validate order is created successful.
  Examples:
    | url                                | Gender | FirstName | LastName | Email                      | Password         | Country      | StateProvince  | Zipcode | PhoneNumber  | City         | Address1     | Address2 |
    | https://demowebshop.tricentis.com/ | Male   | Test      | User     | testUser102@flashemail.com | testUser_Pass123 | South Africa | Other (Non US) | 1234    | +27122345678 | Johannesburg | Johannesburg | City     |