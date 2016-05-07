#include <Adafruit_NeoPixel.h>

boolean firstPartyCycle;
boolean firstPanicCycle;
int top;
int bot;
int j;
double lastTime;
boolean up;

double stateRead;
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

Adafruit_NeoPixel ring;

void setup() {
  Serial.begin(9600);

  ring = Adafruit_NeoPixel(16, 6, NEO_GRB + NEO_KHZ800);
  strip = Adafruit_NeoPixel(18, 9, NEO_GRBW + NEO_KHZ800);

  red = strip.Color(255, 0, 0, 0);
  green = strip.Color(0, 255, 0, 0);
  blue = strip.Color(0, 0, 255, 0);
  black = strip.Color(0, 0, 0, 0);
  yellow = strip.Color(255, 255, 0, 0);

  strip.begin();
  ring.begin();

  state = 0;
  firstPartyCycle = true;
  firstPanicCycle = true;
}

void loop() {
  stateRead = analogRead(0);
  if (stateRead < 85.333) {
    state = 0;
  } else if (stateRead < 256.0) {
    state = 1;
  } else if (stateRead < 426.667) {
    state = 2;
  } else if (stateRead < 597.333) {
    state = 3;
  } else if (stateRead < 768.0) {
    state = 4;
  } else if (stateRead < 938.667) {
    state = 5;
  } else if (stateRead <= 1024) {
    state = 6;
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
      updateOff();
      break;
  }

  strip.show();

  if (state != 5) {
    for (int i = 0; i < ring.numPixels(); i++) {
      ring.setPixelColor(i, green);
    }
  } else if (!firstPartyCycle) {
    for (int i = 0; i < ring.numPixels(); i++) {
      ring.setPixelColor(i, Wheel(((i * 256 / 18) + j) & 255));
    }
  }

  ring.show();
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
}

void updateNotReadyRed() {
  for (int i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i, red);
  }
}

void updateNotReadyBlue() {
  for (int i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i, blue);
  }
}

void updateLoading() {
  for (int i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i,  yellow);
  }
}

void updateReady() {
  for (int i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i,  green);
  }
}

void updateParty() {
  if (firstPartyCycle) {
    firstPartyCycle = false;
    j = 0;
  } else {
    j++;
    for (int i = 0; i < 18; i++) {
      strip.setPixelColor(i, Wheel(((i * 256 / 18) + j) & 255));
    }
  }
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
  }
}

