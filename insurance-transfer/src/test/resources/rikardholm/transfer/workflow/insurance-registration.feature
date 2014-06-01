# language: sv
Egenskap: Registrering av försäkring hos företaget
  För att få en försäkring
  Som en potentiell kund
  Vill jag kunna registrera en försäkring med mitt personnummer

  Bakgrund:
    Givet en person med personnummer 650416-0646 som inte är kund hos företaget
    Och en existerande kund med personnummer 900830-2037 utan försäkringar

  Scenario: En person som redan är kund hos företaget
    När vi tar emot en anmälan för personnummer 900830-2037
    Så skapas en försäkring kopplad till kundkonto 900830-2037

  Scenario: En person som registreras med address
    När vi tar emot en anmälan för personnummer 650416-0646 med address "Formulärsvägen 18, 384 90 Sundsvall"
    Så skapas ett kundkonto för personnummer 650416-0646 med address "Formulärsvägen 18, 384 90 Sundsvall"
    Och det skapas en försäkring kopplad till kundkonto 650416-0646

  Scenario: En person som registreras med enbart personnummer
    Givet att adressen för 650416-0646 i SPAR är "SPARtorget 80, 120 66 Stockholm"
    När vi tar emot en anmälan för personnummer 650416-0646
    Så skapas ett kundkonto för personnummer 650416-0646 med address "SPARtorget 80, 120 66 Stockholm"
    Och det skapas en försäkring kopplad till kundkonto 650416-0646

  Scenario: En person som inte finns i SPAR
    Men om 650416-0646 inte finns i SPAR
    När vi tar emot en anmälan för personnummer 650416-0646
    Så utreder MO att personen har adress "SPARtorget 80, 120 66 Stockholm"
    Och skapas en försäkring kopplad till ett kundkonto med personnummer 650416-0646 och adress "SPARtorget 80, 120 66 Stockholm"

  Scenario: En person med skyddad identitet i SPAR
    Men om 650416-0646 har skyddad identitet
    När vi tar emot en anmälan för personnummer 650416-0646
    Så utreder MO att personen har adress "SPARtorget 80, 120 66 Stockholm"
    Och det skapas en försäkring kopplad till ett kundkonto med personnummer 650416-0646 och adress "SPARtorget 80, 120 66 Stockholm"

  Scenario: SPAR-slagning lyckas inte i tid
    Men om SPAR är nere
    När vi tar emot en anmälan för personnummer 650416-0646
    Så utreder MO att personen har adress "SPARtorget 80, 120 66 Stockholm"
    Och det skapas en försäkring kopplad till ett kundkonto med personnummer 650416-0646 och adress "SPARtorget 80, 120 66 Stockholm"