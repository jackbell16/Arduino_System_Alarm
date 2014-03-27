/**
This code should be installed in every client, who want to detected a motion via sensor PIR.
If the PIR detect a motion, the client will send via wireless a message to the Master,
who will make an insert to the MySQL DB
Version 1.0
Author Giacomo Bellazzi
 Copyright (C) 2014  Giacomo Bellazzi (http://ismanettoneblog.altervista.org/)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
*/
#include <DataCoder.h>
#include <VirtualWire.h>

const int baudRate = 800;
int pinPIR = 3;
int led = 13;
int timeLed = 800;
int pinTX = 12;
int ID = 2; // The ID of the client

void setup()
{
    Serial.begin(9600);   // Debugging only
    Serial.println("setup");
    SetupRFDataTxnLink(pinTX, baudRate);
}
 
void loop()
{
    if(motionDetected()){
      float outArray[1]; // this array keep the data to send
      outArray[0] = ID; // send the ID of the client
      union RFData outDataSeq;
      EncodeRFData(outArray, outDataSeq); 
      TransmitRFData(outDataSeq); 
      ledOnOff(led,timeLed);
      while(motionDetected()){
        // remain in this status, not to send motion twice
      }
   }  
}

//This method detects the presence of people
boolean motionDetected(){
 if(digitalRead(pinPIR)){
  return true;
 }else{
  return false;
 } 
}
// This method light a led, for time
void ledOnOff(int led,int timeLed){
  digitalWrite(led,HIGH);
  delay(timeLed);
  digitalWrite(led,LOW);
}    
