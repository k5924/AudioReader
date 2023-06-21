package org.k5924;

import java.nio.ByteBuffer;

public final class AudioReader {

    private AudioReader() {

    }

    private static final String[] VERSIONS = {"MPEG Version 2.5", "reserved", "MPEG Version 2", "MPEG Version 1"};

    private static final String[] LAYERS = {"reserved", "Layer 3", "Layer 2", "Layer 1"};

    private static final String[][] BITRATES = {
            { "0", "8", "16", "24", "32", "40", "48", "56", "64", "80", "96", "112", "128", "144", "160", "0" },
            { "0", "8", "16", "24", "32", "40", "48", "56", "64", "80", "96", "112", "128", "144", "160", "0" },
            { "0", "32", "48", "56", "64", "80", "96", "112", "128", "144", "160", "176", "192", "224", "256", "0" },
            { "0", "32", "40", "48", "56", "64", "80", "96", "112", "128", "160", "192", "224", "256", "320", "0" },
            { "0", "32", "48", "56", "64", "80", "96", "112", "128", "160", "192", "224", "256", "320", "283", "0" },
            { "0", "32", "64", "96", "128", "160", "192", "224", "256", "288", "320", "352", "384", "416", "448", "0" }
    };

    public static String getMpegAudioVersion(final ByteBuffer byteBuffer) {

        final var pos1 = byteBuffer.get(1) >> 3 & 1;
        final var pos2 = byteBuffer.get(1) >> 4 & 1;
        final var index = pos2 * 2 + pos1;
        return VERSIONS[index];
    }

    public static String getLayer(final ByteBuffer byteBuffer) {
        final var pos6 = byteBuffer.get(1) >> 1 & 1;
        final var pos7 = byteBuffer.get(1) >> 2 & 1;
        final var index = pos7 * 2 + pos6;
        return LAYERS[index];
    }

    public static String getBitrate(final ByteBuffer byteBuffer) {

        final var pos1 = byteBuffer.get(2) >> 4 & 1;
        final var pos2 = byteBuffer.get(2) >> 5 & 1;
        final var pos3 = byteBuffer.get(2) >> 6 & 1;
        final var pos4 = byteBuffer.get(2) >> 7 & 1;
        final var index = pos1 + (pos2 * 2) + (pos3 * 4) + (pos4 * 8);
        return "";
    }
}
