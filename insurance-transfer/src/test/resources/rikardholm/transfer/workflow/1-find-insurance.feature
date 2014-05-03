# language: sv
Egenskap: Information om en persons innehav hos bolaget
  För att kunna se sitt innehav hos ett försäkringsbolag
  Som en person
  Vill jag veta om jag har försäkringar hos bolaget

  Scenario: En person som har en försäkring
    Givet en försäkring som tillhör en person
    När vi tar emot en förfrågan om personen
    Så svarar vi med information om försäkringen

  Scenario: En person som inte har en försäkring
    Givet att det inte finns någon försäkring som tillhör en person
    När vi tar emot en förfrågan om personen
    Så svarar vi att personen inte har några försäkringar
