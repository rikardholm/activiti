# language: sv
Egenskap: Information om en persons innehav hos bolaget
  För att kunna se mitt innehav hos ett försäkringsbolag
  Som en person
  Vill jag veta om jag har försäkringar hos bolaget

  Scenario: En person som har en försäkring
    Givet en person som har en försäkring på företaget
    När vi tar emot en förfrågan om personen
    Och ger systemet 2 sekunder att behandla meddelandet
    Så svarar vi med information om försäkringen

  Scenario: En person som inte har en försäkring
    Givet en person som saknar försäkringar hos bolaget
    När vi tar emot en förfrågan om personen
    Och ger systemet 2 sekunder att behandla meddelandet
    Så svarar vi att personen inte har några försäkringar hos oss
