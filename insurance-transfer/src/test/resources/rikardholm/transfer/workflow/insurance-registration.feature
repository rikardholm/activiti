# language: sv
Egenskap: Registrering av försäkring hos företaget
  För att få en försäkring
  Som en potentiell kund
  Vill jag kunna registrera en försäkring med mitt personnummer

  Bakgrund:
    Givet en person med personnummer 650416-0646 som inte är kund hos företaget

  Scenario: En person som registreras med angiven address
    När vi tar emot en anmälan för personnummer 650416-0646 med address "Formulärsvägen 18, 384 90 Sundsvall"
    Så skapas ett kundkonto för personnummer 650416-0646 med address "Formulärsvägen 18, 384 90 Sundsvall"
    Och det skapas en försäkring kopplad till kundkonto 650416-0646

  Scenario: En person som finns i SPAR
    Givet att personnummer 650416-0646 finns i SPAR med address "SPARtorget 80, 120 66 Stockholm"
    Och att SPAR är uppe
    När vi tar emot en anmälan för personnummer 650416-0646
    Så skapas ett kundkonto för personnummer 650416-0646 med address "SPARtorget 80, 120 66 Stockholm"
    Och det skapas en försäkring kopplad till kundkonto 650416-0646

  Scenario: SPAR är nere
    Men om SPAR är nere
    När vi tar emot en anmälan för personnummer 650416-0646
    Och MO utreder i ett ärende att personen har adress "Mosippevägen 138, Göteborg"
    Så skapas ett kundkonto för personnummer 650416-0646 med address "Mosippevägen 138, Göteborg"
    Och det skapas en försäkring kopplad till kundkonto 650416-0646

  Scenario: En person med skyddad identitet i SPAR
    Givet en person med personnummer 810514-9898 som inte är kund hos företaget
    Och att personnummer 810514-9898 har skyddad identitet i SPAR
    Och att SPAR är uppe
    När vi tar emot en anmälan för personnummer 810514-9898
    Och MO utreder i ett ärende att personen har adress "Mosippevägen 138, Göteborg"
    Så skapas ett kundkonto för personnummer 810514-9898 med address "Mosippevägen 138, Göteborg"
    Och det skapas en försäkring kopplad till kundkonto 810514-9898

  Scenario: En person som redan är kund hos företaget
    Givet en existerande kund med personnummer 900830-2037 utan försäkringar
    När vi tar emot en anmälan för personnummer 900830-2037
    Så skapas en försäkring kopplad till kundkonto 900830-2037