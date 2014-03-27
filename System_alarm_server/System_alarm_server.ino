/**
This project allows you to create a video surveillance system, using PIR sensors, wireless sensors and 
an Internet connection. The data relating to business records of movements, are loaded on a MySQL server.
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

#include "SPI.h"
#include "Ethernet.h"
#include "mysql.h"
#include "sha1.h"
#include "WebServer.h"
#include "DataCoder.h"
#include "VirtualWire.h"
#define PREFIX ""
WebServer webserver(PREFIX, 80);
const int rx_pin = 11;
const int baudRate = 800;
boolean systemON = true;
Connector my_conn;        // The Connector/Arduino reference
byte mac_addr[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
IPAddress server_addr(10,0,0,8); // MySQL Server IP
char user[] = "username";
char password[] = "password";
const char SEND_MOTION[] = "INSERT INTO Sistema_allarme.Rilevamenti VALUES (%d,CURRENT_DATE, CURRENT_TIME)";
int pinPIR = 4;
int led = 13;
int timeLed = 2000;
int idPIRServer = 1;

void defaultCmd(WebServer &server, WebServer::ConnectionType type, char *, bool)
{
  server.httpSuccess();
  if (type != WebServer::HEAD)
  {
    P(msg1) = "<h1>Welcome to alarm system page</h1>";
    P(msg2) = "<h3>Through the secure page, you can turn on and turn off the alarm system</h3> ";
    P(msg3) = "<a href=\"private.html\">Private page</a>";
    P(msg4) = "<a href=\"private.html\"><img src = http://ismanettoneblog.altervista.org/blog/wp-content/uploads/2014/03/allarme.jpg></a>";
    server.printP(msg1);
    server.printP(msg2);
    server.printP(msg3);
    server.println("</form><br />");
    server.printP(msg4);
  }
}

void privateCmd(WebServer &server, WebServer::ConnectionType type, char *url_param, bool param_complete)
{
  //in other words: "YWRtaW46YWRtaW4=" is the Base64 representation of "admin:admin" */
  if (server.checkCredentials("YWRtaW46YWRtaW4="))
  {
    server.httpSuccess();
    if (type != WebServer::HEAD)
    {
      P(msg1) = "<h1>Welcome to the console page</h1>";
      P(msg2) = "<h3> You can choise to disable/enable the alarm system</h3>";
      server.printP(msg1);
      server.printP(msg2);
      String s = "";
       if (param_complete == true)
    {
      s = url_param;
      if (s=="on"){
        systemON = true;
     }
     if(s =="off"){
        systemON = false;
     }
     // Viene cambiata la pagina WEB a seconda che il LED sia spento, oppure systemON
      if(systemON){
          server.println("<a href=\"./private.html?off\"> <img src = \"http://ismanettoneblog.altervista.org/blog/wp-content/uploads/2014/02/bt_OFF.png\"alt = \"Spegni\" ></a>");
          server.println("<h3> The alarm system is enable </h3>");
        }else{
          server.println("<a href=\"./private.html?on\"> <img src = \"http://ismanettoneblog.altervista.org/blog/wp-content/uploads/2014/02/bt_ON.png\"alt = \"Accendi\" ></a>");
          server.println("<h3> The alarm system is disabled </h3>");
        } 
     }
  }
   server.println("<a href=\"./private.html\"> <img src = \"http://ismanettoneblog.altervista.org/blog/wp-content/uploads/2014/03/alarmlogo.png\"alt = \"Accendi\" ></a>");
}
  else
  {
    /* send a 401 error back causing the web browser to prompt the user for credentials */
    server.httpUnauthorized();
  }
}

void setup() {
  pinMode(pinPIR,INPUT);  
  pinMode(led,OUTPUT);
  Serial.begin(9600);
  Ethernet.begin(mac_addr);
  webserver.setDefaultCommand(&defaultCmd);
  webserver.addCommand("index.html", &defaultCmd);
  webserver.addCommand("private.html", &privateCmd);
  webserver.begin();  
  Serial.println("Connecting...");
  if (my_conn.mysql_connect(server_addr, 3306, user, password)) {
    delay(1000);
  }
  else
    Serial.println("Connection failed.");
  SetupRFDataRxnLink(rx_pin, baudRate);
}

void loop() {
  char buff[64];
  int len = 64;
  uint8_t buf[VW_MAX_MESSAGE_LEN];
  uint8_t buflen = VW_MAX_MESSAGE_LEN;
  union RFData inDataSeq;//To store incoming data
  float inArray[1];//To store decoded information
  if(RFLinkDataAvailable(buf, &buflen) && systemON)
  {
        for(int i =0; i< buflen; i++)
        {
          inDataSeq.s[i] = buf[i];
        }
        DecodeRFData(inArray, inDataSeq);
        int idNetworkPIR =(int)inArray[0];
        sendDBmotion(idNetworkPIR);
        ledOnOff(led,timeLed);
  }
  /* process incoming connections one at a time forever */
  webserver.processConnection(buff, &len);
  if(motionDetected() && systemON){
    Serial.println("Movimento rilevato");
    sendDBmotion(idPIRServer);
    ledOnOff(led,timeLed);
    while(motionDetected()){
      webserver.processConnection(buff, &len);
    }
  }
}
//This method detects the presence of people
boolean motionDetected(){
 if(digitalRead(pinPIR)==HIGH){
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

// This method allow to send the motion to the MySQL DB
void sendDBmotion(int ID){
char query[60];
sprintf(query, SEND_MOTION, ID);
my_conn.cmd_query(query);
}
