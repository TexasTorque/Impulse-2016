#include <Adafruit_NeoPixel.h>
#define SIZE 38
#define NUM_STRIP 2

boolean firstPartyCycle;
boolean firstPanicCycle;
int top;
int bot;
int j;
double lastTime;
boolean up;

char ch;
int state;
//0 off - prematch (never sent by robot)
//1 not ready red - during match
//2 not ready blue
//3 loading
//4 ready
//5 party - disabled
//5 ring party
//6 last 30 seconds

Adafruit_NeoPixel strip;
uint32_t red;
uint32_t green;
uint32_t blue;
uint32_t black;
uint32_t yellow;

//Adafruit_NeoPixel ring;

void setup() {
  Serial.begin(9600);

  //ring = Adafruit_NeoPixel(16, 7, NEO_GRBW + NEO_KHZ800);
  strip = Adafruit_NeoPixel(38, 8, NEO_GRBW + NEO_KHZ800);

  red = strip.Color(255, 0, 0, 0);
  green = strip.Color(0, 255, 0, 0);
  blue = strip.Color(0, 0, 255, 0);
  black = strip.Color(0, 0, 0, 0);
  yellow = strip.Color(255, 255, 0, 0);

  strip.begin();
  strip.show();

  state = 0;
  firstPartyCycle = true;
  firstPanicCycle = true;
}

void loop() {
  if (Serial.available()) {
    ch = Serial.read();
    if (isDigit(ch)) {
      state = int(ch) - 48;
    }
  }

  if (state != 5) {
    firstPartyCycle = true;
  }

  if (state != 6) {
    firstPanicCycle = true;
  }

  switch (state) {
    case 0:
      updateOff();
      break;
    case 1:
      updateNotReadyRed();
      break;
    case 2:
      updateNotReadyBlue();
      break;
    case 3:
      updateLoading();
      break;
    case 4:
      updateReady();
      break;
    case 5:
      updateParty();
      break;
    case 6:
      updatePanic();
      break;
    default:
      updateLoading();
      break;
  }

  //ring.show();
  strip.show();
}

uint32_t Wheel(byte pos) {
  if (pos < 85) {
    return strip.Color(pos * 3, 255 - pos * 3, 0, 0);
  } else if (pos < 170) {
    pos -= 85;
    return strip.Color(255 - pos * 3, 0, pos * 3);
  } else {
    pos -= 170;
    return strip.Color(0, pos * 3, 255 - pos * 3);
  }
}

void updateOff() {
  for (int i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i, black);
  }
  //for (int i = 0; i < ring.numPixels(); i++) {
  //  ring.setPixelColor(i, black);
  //}
}

void updateNotReadyRed() {
  for (int i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i, red);
  }
  //for (int i = 0; i < ring.numPixels(); i++) {
  //  ring.setPixelColor(i, green);
  //}
}

void updateNotReadyBlue() {
  for (int i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i, blue);
  }
  //for (int i = 0; i < ring.numPixels(); i++) {
  //  ring.setPixelColor(i, green);
  //}
}

void updateLoading() {
  for (int i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i,  yellow);
  }
  //for (int i = 0; i < ring.numPixels(); i++) {
  //  ring.setPixelColor(i, green);
  //}
}

void updateReady() {
  for (int i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i,  green);
  }
  //for (int i = 0; i < ring.numPixels(); i++) {
  //  ring.setPixelColor(i, green);
  //}
}

void updateParty() {
  if (firstPartyCycle) {
    firstPartyCycle = false;
    j = 0;
  } else {
    j++;
    for (int i = 0; i < 18; i++) {
      strip.setPixelColor(i, Wheel(((i * 256 / 18) + j) & 255));
      strip.setPixelColor(i + 19, Wheel(((i * 256 / 18) + j) & 255));
    }
  }
  //for (int i = 0; i < ring.numPixels(); i++) {
  //  ring.setPixelColor(i, Wheel(((i * 256 / ring.numPixels()) + j) & 255));
  //}
}

void updatePanic() {
  if (firstPanicCycle) {
    firstPanicCycle = false;
    top = 0;
    bot = 0;
    j = 0;
    lastTime = millis();
    up = true;
  } else {
    if (millis() - lastTime > 10) {
      lastTime = millis();
      j++;

      if (up) {
        if (top > 9) {
          bot++;
          if (bot > 9) {
            up = !up;
          }
        } else {
          top++;
        }
      } else {
        if (bot < 0) {
          top--;
          if (top < 0) {
            up = !up;
          }
        } else {
          bot--;
        }
      }

      for (int i = 0; i < 9; i++) {
        if (i < top && i > bot) {
          strip.setPixelColor(i + 9, Wheel(((i * 256 / 8) + j) & 255));
          strip.setPixelColor(8 - i, Wheel(((i * 256 / 8) + j) & 255));
        } else {
          strip.setPixelColor(i + 9, black);
          strip.setPixelColor(8 - i, black);
        }
      }
    }

    //for (int i = 0; i < ring.numPixels(); i++) {
    //  ring.setPixelColor(i, green);
    //}
  }
}

