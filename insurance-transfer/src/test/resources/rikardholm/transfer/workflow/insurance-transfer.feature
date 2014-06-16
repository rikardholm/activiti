# language: sv
Egenskap: Flytt av försäkring in i företaget

  Scenario: Ett personnummer som inte finns i SPAR
    Givet att personnummer 450918-5968 inte finns i SPAR
    När vi får ett meddelande med personnummer 450918-5968 och flyttId 3568940
    Och väntar 10 dagar
    Så skickar vi ett 6b meddelande för personnummer 450918-5968 och flyttId 3568940

  Scenario: Ett personnummer som finns i SPAR
    Givet att personnummer 670914-5687 finns i SPAR med address "Spartacusvägen 8"
    När vi får ett meddelande med personnummer 670914-5687 och flyttId 99667884
    Och väntar 10 dagar
    Så skapas ett kundkonto för personnummer 670914-5687 med address "Spartacusvägen 8"
    Och det skapas en försäkring kopplad till kundkonto 670914-5687
    Och det skickas ett 8z meddelande med flyttId 99667884, personnummer 670914-5687 och det nya försäkringsnummret

  Scenario: Vi får en bankgiroöverföring
    När vi får ett meddelande från bankgirocentralen med ocr 99667884 och 56000kr
    Så sätts pengar in på försäkringen