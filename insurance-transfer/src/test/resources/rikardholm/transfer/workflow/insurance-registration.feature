# language: sv
Egenskap: Registrering av försäkring hos företaget
  För att få en försäkring
  Som en potentiell kund
  Vill jag kunna registrera en försäkring med mitt personnummer

  Scenario: En person som redan är kund hos företaget
    Givet en person som är kund hos företaget
    När vi tar emot en anmälan med bara personnummer
    Så skapas en försäkring med kundens existerande uppgifter

  Scenario: En person som inte är kund hos företaget
    Givet en person som inte är kund hos företaget
    Och som finns i SPAR
    När vi tar emot en anmälan med bara personnummer
    Så skapas en försäkring med personens uppgifter i SPAR

  Scenario: En person som inte finns i SPAR
    Givet en person som inte är kund hos företaget
    Och som inte finns i SPAR
    När vi tar emot en anmälan med bara personnummer
    Så utreder MO vilka uppgifter personen har
    Och försäkringen skapas med MOs uppgifter

  Scenario: En person med skyddad identitet i SPAR
    Givet en person som inte är kund hos företaget
    Och som har skyddad identitet i SPAR
    När vi tar emot en anmälan med bara personnummer
    Så utreder MO vilka uppgifter personen har
    Och försäkringen skapas med MOs uppgifter

  Scenario: SPAR-slagning lyckas inte i tid
    Givet en person som inte är kund hos företaget
    Och att SPAR är nere
    När vi tar emot en anmälan med bara personnummer
    Så utreder MO vilka uppgifter personen har
    Och försäkringen skapas med MOs uppgifter
