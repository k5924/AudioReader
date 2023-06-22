package org.k5924;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

final class AudioReader {

    private static final Logger LOG = LoggerFactory.getLogger(AudioReader.class);
    private static final String RESERVED = "reserv.";
    private static final String[] VERSIONS = {"MPEG Version 2.5", RESERVED, "MPEG Version 2", "MPEG Version 1"};

    private static final String[] LAYERS = {RESERVED, "Layer 3", "Layer 2", "Layer 1"};

    private static final String[][] BITRATES = {
            { "0", "8", "16", "24", "32", "40", "48", "56", "64", "80", "96", "112", "128", "144", "160", "0" },
            { "0", "8", "16", "24", "32", "40", "48", "56", "64", "80", "96", "112", "128", "144", "160", "0" },
            { "0", "32", "48", "56", "64", "80", "96", "112", "128", "144", "160", "176", "192", "224", "256", "0" },
            { "0", "32", "40", "48", "56", "64", "80", "96", "112", "128", "160", "192", "224", "256", "320", "0" },
            { "0", "32", "48", "56", "64", "80", "96", "112", "128", "160", "192", "224", "256", "320", "283", "0" },
            { "0", "32", "64", "96", "128", "160", "192", "224", "256", "288", "320", "352", "384", "416", "448", "0" }
    };

    private static final String[][] SAMPLE_RATES = {
            { "44100 Hz", "22050 Hz", "11025 Hz" },
            { "48000 Hz", "24000 Hz", "12000 Hz" },
            { "32000 Hz", "16000 Hz", "8000 Hz" },
            { RESERVED, RESERVED, RESERVED }
    };

    private static final String[] CHANNEL_MODE = { "Stereo", "Joint stereo (Stereo)", "Dual channel (2 mono channels)",
            "Single channel (Mono)", };

    private AudioReader() {

    }

    public static int getMpegAudioVersion(final ByteBuffer byteBuffer) {

        final var bit = byteBuffer.get(1);
        final var index = calculateIndex(bit, 3, 4);

        LOG.debug("MPEG Audio Version is: {}", VERSIONS[index]);
        return index;
    }

    public static int getLayer(final ByteBuffer byteBuffer) {

        final var bit = byteBuffer.get(1);
        final var index = calculateIndex(bit, 1, 2);

        LOG.debug("Layer is: {}", LAYERS[index]);
        return index;
    }

    public static String getBitrate(final ByteBuffer byteBuffer) {

        final var bit = byteBuffer.get(2);
        final var index = calculateIndex(bit, 4, 7);
        final var column = index + 1;
        // add 1 to account for all 0s at start

        final var layer = getLayer(byteBuffer);
        final var version = getMpegAudioVersion(byteBuffer);
        final var row = layer + version - 2;
        // - 2 to adjust position in map

        final var bitrate  = BITRATES[row][column];

        LOG.debug("Bitrate is: {}", bitrate);
        return bitrate;
    }

    public static String getSampleRate(final ByteBuffer byteBuffer) {

        final var bit = byteBuffer.get(2);
        final var row = calculateIndex(bit, 2, 3);

        final var version = getMpegAudioVersion(byteBuffer);
        final var sampleRate = SAMPLE_RATES[row][version % 3];
        // % 3 so version will start from index 0 would be better to flip the SAMPLE_RATES data structure
        // as would only need to -3 from index instead of modulus

        LOG.debug("Sample rate is: {}", sampleRate);
        return sampleRate;
    }

    public static String getChannelMode(final ByteBuffer byteBuffer) {

        final var bit = byteBuffer.get(3);
        final var index = calculateIndex(bit, 6, 7);

        final var channelMode = CHANNEL_MODE[index];

        LOG.debug("Channel mode is: {}", channelMode);
        return channelMode;
    }

    private static int calculateIndex(final int bit, final int startPos, final int endPos) {
        int total = 0;
        for (int i = startPos; i <= endPos; i++) {
            final var value = bit >> i & 1;
            total += value * Math.pow(2, (double) i - startPos);
        }
        return total;
    }
}
