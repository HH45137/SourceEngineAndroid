package com.valvesoftware;

public class ValveActivity2 {
    public static native void setArgs(String args);
    public static native int setenv(String name, String value, int overwrite);

    public static void initNatives() {

        setArgs("-game hl2 -console");
        System.out.println();
    }
}
