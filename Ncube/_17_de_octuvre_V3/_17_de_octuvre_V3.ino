#include <SoftwareSerial.h>
#include <Adafruit_NeoPixel.h>
#include <TimerOne.h>

#define PINNP 6
#define PINNP1 5
#define PINNP2 4
#define PINNP3 3
#define NUMPIXELS 8

Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PINNP, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel pixels1 = Adafruit_NeoPixel(NUMPIXELS, PINNP1, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel pixels2 = Adafruit_NeoPixel(NUMPIXELS, PINNP2, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel pixels3 = Adafruit_NeoPixel(NUMPIXELS, PINNP3, NEO_GRB + NEO_KHZ800);

#define apagado (0,0,0)
#define whatsapp pixels.Color(RWpp, GWpp, BWpp)
#define instagram pixels.Color(RIg,  GIg , BIg)
#define youtube pixels.Color(RYt, GYt , BYt)
#define facebook pixels.Color(RFb, GFb, BFb)

int notificaciones=0;

uint32_t color_1=apagado;
uint32_t color_2=apagado;
uint32_t color_3=apagado;
uint32_t color_4=apagado;

uint32_t save1;
uint32_t save2;
uint32_t save3;
uint32_t save4; 

char state = 0;
char seg; //Variable conatdor de segundo 
int mSeg; //Variable contador de miliseg
char segp;

char dato;


//-------------------------------------
//Si cambiamos los valores de estas variables (0-255) cambian los colores predefinidos de los neopixels para cada app

char RWpp=1;
char GWpp=219;
char BWpp=44;

char RIg=255;
char GIg=0;
char BIg=128;

char RYt=255;
char GYt=0;
char BYt=0;

char RFb=0;
char GFb=0;
char BFb=255;

//---------------------------------------

int estado;

bool YaRecibiNotiWpp;
bool YaRecibiNotiIg;
bool YaRecibiNotiYt;
bool YaRecibiNotiFb;

SoftwareSerial MySerial(9, 8); // RX, TXv

void fade(void);
void prueba(void);

void setup()
{
  MySerial.begin(9600);
  Serial.begin(9600);
  
  pixels.begin();
  pixels1.begin();
  pixels2.begin();
  pixels3.begin();

  segp=0;
  seg=0;
  mSeg=0;
  
  Timer1.initialize(1000); //Configuro timer para que interrumpa cada 1000 uSeg (1000 micro Seg = 1 mili Seg) 
  Timer1.attachInterrupt(timer);//Configuro que funcion ejecuta la interrupcion del Timer 1
  
  Serial.println("Ready to connect\nDefualt password is 1234 or 000");
}

void loop()
{ 
  Serial.println("noti:");
  Serial.print(notificaciones);
  Serial.println("dato:");
  Serial.print(dato);  
  fade();
  prueba();
  /*
  if(MySerial.available() > 0)
  {
    while (MySerial.available())
    {
      Serial.println("lalala");
      dato = MySerial.read();
*/     
      // dato='A' Whatsapp;'B' Instagram;'C' Youtube;'D' Facebook;'E' se borro whatsapp,'F' se borro instagram,'G' se borro youtube, 'H' se borro Gmail
      switch (dato) {
        case 'A':
        if(!YaRecibiNotiWpp){
          switch(notificaciones)
          case 0:
              color_1=whatsapp;
              color_2=whatsapp;
              color_3=whatsapp;
              color_4=whatsapp;
              YaRecibiNotiWpp=true;
              notificaciones=1;
              Serial.println("primer noti wpp");
          break;
          case 1:
              color_1=whatsapp;
              color_3=whatsapp;
              YaRecibiNotiWpp=true;
              notificaciones=2;
          break;
          case 2:
              color_2=whatsapp;
              YaRecibiNotiWpp=true;
              notificaciones=3;
          break;
          case 3:
              color_3=whatsapp;
              YaRecibiNotiWpp=true;
              notificaciones=4;
          break;
         }
          if(YaRecibiNotiWpp){
            //titilar o algo asi pero todavia no se (Avisar que llego una nueva notificacion de wpp)
            Serial.println("ya recibiste noti de Wpp manija");
          }
        break;
        case 'B':
        if(!YaRecibiNotiIg)
          switch (notificaciones)
          {
           case 0:
            color_1=instagram;
            color_2=instagram;
            color_3=instagram;
            color_4=instagram;
            YaRecibiNotiIg=true;
            notificaciones=1;
            Serial.println("primer noti IG");
           break;

           case 1:
            color_1=instagram;
            color_3=instagram;
            YaRecibiNotiIg=true;
            notificaciones=2;
           break;
           
           case 2:
            color_2=instagram;
            YaRecibiNotiIg=true;
            notificaciones=3;
           break;
           
           case 3:
            color_3=instagram;
            YaRecibiNotiIg=true;
           break;
          }
          if(YaRecibiNotiIg){
            Serial.println("ya recibiste noti de Ig manija");
          }
          break;
        case 'C':
          if(!YaRecibiNotiYt){
            switch(notificaciones)
            {
             case 0:
              color_1=youtube;
              color_2=youtube;
              color_3=youtube;
              color_4=youtube;
              YaRecibiNotiYt=true;
              notificaciones=1;
              Serial.println("primer noti YT");
             break;
             case 1:
              color_1=youtube;
              color_3=youtube;
              YaRecibiNotiYt=true;
              notificaciones=2;
             break;
             case 2:
               color_2=youtube;
               YaRecibiNotiYt=true;
               notificaciones=3;
             break;
             case 3:
               color_3=youtube;
               YaRecibiNotiYt=true;
             break;
            }
          }
          if(YaRecibiNotiYt){
            //titilar o algo asi pero todavia no se
            Serial.println("ya recibiste noti de Yt manija");
          }
          break;
        case 'D':
        if(!YaRecibiNotiFb){
            switch(notificaciones)
            {
              case 0:
                color_1=facebook;
                color_2=facebook;
                color_3=facebook;
                color_4=facebook;
                YaRecibiNotiFb=true;
                notificaciones=1;
                Serial.println("primer noti FB");
              break;
              case 1:
                color_1=facebook;
                color_3=facebook;
                YaRecibiNotiFb=true;
                notificaciones=2;
              break;
              case 2:
                color_2=facebook;
                YaRecibiNotiFb=true;
                notificaciones=3;
              break;
              case 3:
                color_3=facebook;
                YaRecibiNotiFb=true;
              break;
            }
          }
          if(YaRecibiNotiYt){
            //titilar o algo asi pero todavia no se
            Serial.println("ya recibiste noti de Fb manija"); 
          }
          break;
        case 'E'://Mensaje que recibo si se elimina una notificacion de whatsapp
            //Hay solo una notificacion registrada, por lo que si se borra, se tienen que apagar todos los neopixels
            if (notificaciones==1 && color_1 == whatsapp){
              color_1 = apagado;
              color_2 = apagado;
              color_3 = apagado;
              color_4 = apagado;
              notificaciones = 0;
            }
              //Si hay dos notificaciones y se borra una la que se borro puede estar en 1 y 3 o 2 y 4, por lo que tengoque leer cual es y hacer que se pongan del color de la otra notificacion   
            if (notificaciones == 2)
            {
              if (color_1==whatsapp){
                color_1 = color_2;
                color_3 = color_4;
              }
              if(color_2 == whatsapp){
                color_2 = color_1;
                color_4 = color_3;
              }
              notificaciones = 1;  
            }
            //  tengo 3 notificaciones y se borra wpp---------------------------------------
            if (notificaciones == 3){
              if(color_1 == whatsapp){
                color_1 = color_2;
                color_3 = color_2;
                color_2 = color_4;
              }
              if (color_2 == whatsapp){
                color_2 = color_4;
              }
              if (color_4 == whatsapp){
                color_4 = color_2;
              }
              notificaciones = 2;
            }
            //tengo 4 notificacuiones y se borra wpp-------------------------------------
            if (notificaciones == 4){
              if(color_1 == whatsapp){
                color_1 = color_3;
              }
              if (color_2 == whatsapp){
                color_2 = color_3;
                color_3 = color_1;
              }
              if (color_3 == whatsapp){
                color_3 = color_1;
              }
              if (notificaciones == 4 && color_4 == whatsapp){
                color_4 = color_3;
                color_3 = color_1;
              }
              notificaciones=3;
            }
            YaRecibiNotiWpp=false;
          break;
        case 'F'://se borra noti ig
            if (notificaciones==1 && color_1 == instagram){
              color_1 = apagado;
              color_2 = apagado;
              color_3 = apagado;
              color_4 = apagado;
              notificaciones = 0;
            }
            if (notificaciones == 2)
            {
              if (color_1==instagram){
                color_1 = color_2;
                color_3 = color_4;
              }
              if(color_2 == instagram){
                color_2 = color_1;
                color_4 = color_3;
              }
              notificaciones = 1;  
            }
            if (notificaciones == 3){
              if(color_1 == instagram){
                color_1 = color_2;
                color_3 = color_2;
                color_2 = color_4;
              }
              if (color_2 == instagram){
                color_2 = color_4;
              }
              if (color_4 == instagram){
                color_4 = color_2;
              }
              notificaciones = 2;
            }            
            if (notificaciones == 4){
              if(color_1 == instagram){
                color_1 = color_3;
              }
              if (color_2 == instagram){
                color_2 = color_3;
                color_3 = color_1;
              }
              if (color_3 == instagram){
                color_3 = color_1;
              }
              if (notificaciones == 4 && color_4 == instagram){
                color_4 = color_3;
                color_3 = color_1;
              }
              notificaciones=3;
            }
            YaRecibiNotiIg=false;
          break;
        case 'G'://Se borra noti Yt
            if (notificaciones==1 && color_1 == youtube){
              color_1 = apagado;
              color_2 = apagado;
              color_3 = apagado;
              color_4 = apagado;
              notificaciones = 0;
            }
            if (notificaciones == 2)
            {
              if (color_1==youtube){
                color_1 = color_2;
                color_3 = color_4;
              }
              if(color_2 == youtube){
                color_2 = color_1;
                color_4 = color_3;
              }
              notificaciones = 1;  
            }
            if (notificaciones == 3){
              if(color_1 == youtube){
                color_1 = color_2;
                color_3 = color_2;
                color_2 = color_4;
              }
              if (color_2 == youtube){
                color_2 = color_4;
              }
              if (color_4 == youtube){
                color_4 = color_2;
              }
              notificaciones = 2;
            }
            if (notificaciones == 4){
              if(color_1 == youtube){
                color_1 = color_3;
              }
              if (color_2 == youtube){
                color_2 = color_3;
                color_3 = color_1;
              }
              if (color_3 == youtube){
                color_3 = color_1;
              }
              if (notificaciones == 4 && color_4 == youtube){
                color_4 = color_3;
                color_3 = color_1;
              }
              notificaciones=3;
            }
            YaRecibiNotiYt=false;
          break;
        case 'H'://Se borra noti Fb
            if (notificaciones==1 && color_1 == facebook){
              color_1 = apagado;
              color_2 = apagado;
              color_3 = apagado;
              color_4 = apagado;
              notificaciones = 0;
            }
            if (notificaciones == 2)
            {
              if (color_1==facebook){
                color_1 = color_2;
                color_3 = color_4;
              }
              if(color_2 == facebook){
                color_2 = color_1;
                color_4 = color_3;
              }
              notificaciones = 1;  
            }
            if (notificaciones == 3){
              if(color_1 == facebook){
                color_1 = color_2;
                color_3 = color_2;
                color_2 = color_4;
              }
              if (color_2 == facebook){
                color_2 = color_4;
              }
              if (color_4 == facebook){
                color_4 = color_2;
              }
              notificaciones = 2;
            }
            if (notificaciones == 4){
              if(color_1 == facebook){
                color_1 = color_3;
              }
              if (color_2 == facebook){
                color_2 = color_3;
                color_3 = color_1;
              }
              if (color_3 == facebook){
                color_3 = color_1;
              }
              if (notificaciones == 4 && color_4 == facebook){
                color_4 = color_3;
                color_3 = color_1;
              }
              notificaciones=3;
            }
            YaRecibiNotiFb=false;     
          break;
        case 'Z':
            color_1=apagado;
            color_2=apagado;
            color_3=apagado;
            color_4=apagado;
            notificaciones=0;
            YaRecibiNotiWpp=false;
            YaRecibiNotiIg=false;
            YaRecibiNotiYt=false;
            YaRecibiNotiFb=false;
            Serial.println("seapagoto");
          break;
      } 
    //}
  //}
  
}

void fade(void){
  if (state == 0){
    for (int g = 0; g < 150; g++)
    {
      for (int i = 0; i < NUMPIXELS; i++)
      {
        pixels.setPixelColor(i,color_1);
        pixels1.setPixelColor(i,color_2);
        pixels2.setPixelColor(i,color_3);
        pixels3.setPixelColor(i,color_4);
        
        pixels.setBrightness(g);
        pixels.show();
        pixels1.setBrightness(g);
        pixels1.show();
        pixels2.setBrightness(g);
        pixels2.show();
        pixels3.setBrightness(g);
        pixels3.show();
      }
    }
    state = 1;
  }
  if (state == 1)
  {
    for (int g = 150; g > 0; g--)
    {
      for (int i = 0; i < NUMPIXELS; i++)
      {
        pixels.setPixelColor(i,color_1);
        pixels1.setPixelColor(i,color_2);
        pixels2.setPixelColor(i,color_3);
        pixels3.setPixelColor(i,color_4);
        
        pixels.setBrightness(g);
        pixels.show();
        pixels1.setBrightness(g);
        pixels1.show();
        pixels2.setBrightness(g);
        pixels2.show();
        pixels3.setBrightness(g);
        pixels3.show();
      }
    }
    state = 0;
  }
}

void timer (void) //Interrumpe cada 1mSeg
{
  mSeg = mSeg + 1; //Incremento la variable contadora de mili seg
  if(mSeg >= 1000) //Si pasan 1000ms incremento la variable seg
  {
    mSeg = 0; //Reseteo mSeg
    seg++;    //incremeto en 1 la variable seg
    segp++;
  }
  if ( estado == 0 && seg == 3 )
  { 
    seg=0;
    estado=1;
  } 
  if ( estado == 1 && seg == 3 )
  { 
    seg=0;
    estado=0;
  } 
}

void prueba (void)
{

  if (segp < 5  && notificaciones==0)
  {
   dato='A';
  }
  if (segp < 10 &&  segp> 5 && notificaciones==1 )
  {
   dato='B';
  }
  if (segp < 15 &&  segp> 10 && notificaciones==2 )
  {
   dato='E';
  }
  if (segp < 20 &&  segp> 15 && notificaciones==1 )
  {
   dato='D';
  }
  if (segp < 25 &&  segp > 20)
  {
   dato='Z';
   segp=0;
  } 
}
