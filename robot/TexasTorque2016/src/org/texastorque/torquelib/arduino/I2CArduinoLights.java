package org.texastorque.torquelib.arduino;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class I2CArduinoLights {

    private I2C i2c;
    private LightState state;

    /**
     * Create a new set of Arduino lights.
     *
     * @param address The Arduino's device address. Pass the same address you
     * used in the arduino code.
     */
    public I2CArduinoLights(int address) {
        i2c = new I2C(Port.kOnboard, address);
        state = LightState.WHITE;
    }

    /**
     * Set light state.
     *
     * @param newState New state.
     */
    public void set(LightState newState) {
        if (newState != state) {
            byte[] ary = new byte[]{newState.getData()};
            i2c.transaction(ary, ary.length, null, 0);
            state = newState;
        }
    }

    /**
     * Get the current state of the Arduino lights.
     *
     * @return The current LightState.
     */
    public LightState getState() {
        return state;
    }

    /**
     * The state that the lights indicate.
     */
    public enum LightState {

        WHITE((byte) 0),
        ENABLED_BLUE((byte) 1),
        YELLOW((byte) 2),
        GREEN((byte) 3),
        ENABLED_RED((byte) 4),
        BAD_RAINBOW((byte) 5),
        DISABLED((byte) 6),
        BLUE_GREEN((byte) 7),
        RED_GREEN((byte) 8),
        RED_GREEN_REVERSE((byte) 9),
        BLUE_GREEN_REVERSE((byte) 10),
        RED_REVERSE((byte) 11),
        BLUE_REVERSE((byte) 12),
        COLOR_FADE((byte) 13);

        private byte data;

        LightState(byte data) {
            this.data = data;
        }

        public byte getData() {
            return data;
        }
    }
}
