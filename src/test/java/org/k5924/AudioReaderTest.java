package org.k5924;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

final class AudioReaderTest {

    private static final String FILENAME = "LBCNews.mp3";
    private static final ThreadLocal<ByteBuffer> SHARED_BUFFER =
            ThreadLocal.withInitial(() -> ByteBuffer.allocateDirect(4));
    private static final URL URL = Thread.currentThread().getContextClassLoader()
            .getResource(FILENAME);
    private static final ThreadLocal<Path> PATH_TO_FILE = ThreadLocal.withInitial(() -> Path.of(URL.getPath()));
    private ByteBuffer byteBuffer;

    @BeforeEach
    void setup() {
        byteBuffer = SHARED_BUFFER.get();
        byteBuffer.clear();
    }

    @Test
    void shouldRetrieveCorrectAudioVersionFromAudioFile() throws IOException {
        // Arrange
        try (var inputFileChannel = FileChannel.open(PATH_TO_FILE.get())) {
            inputFileChannel.read(byteBuffer);
            byteBuffer.flip();
        }

        // Act
        final var underTest = AudioReader.getMpegAudioVersion(byteBuffer);

        // Assert
        assertThat(underTest).isEqualTo(3);
    }

    @Test
    void shouldRetrieveCorrectLayerVersionFromAudioFile() throws IOException {
        // Arrange
        try (var inputFileChannel = FileChannel.open(PATH_TO_FILE.get())) {
            inputFileChannel.read(byteBuffer);
            byteBuffer.flip();
        }

        // Act
        final var underTest = AudioReader.getLayer(byteBuffer);

        // Assert
        assertThat(underTest).isEqualTo(1);
    }

    @Test
    void shouldRetrieveCorrectBitrateFromAudioFile() throws IOException {
        // Arrange
        try (var inputFileChannel = FileChannel.open(PATH_TO_FILE.get())) {
            inputFileChannel.read(byteBuffer);
            byteBuffer.flip();
        }

        // Act
        final var underTest = AudioReader.getBitrate(byteBuffer);

        // Assert
        assertThat(underTest).isEqualTo("256");
    }

    @Test
    void shouldRetrieveCorrectSampleRateFromAudioFile() throws IOException {
        // Arrange
        try (var inputFileChannel = FileChannel.open(PATH_TO_FILE.get())) {
            inputFileChannel.read(byteBuffer);
            byteBuffer.flip();
        }

        // Act
        final var underTest = AudioReader.getSampleRate(byteBuffer);

        // Assert
        assertThat(underTest).isEqualTo("44100 Hz");
    }

    @Test
    void shouldRetrieveCorrectChannelModeFromAudioFile() throws IOException {
        // Arrange
        try (var inputFileChannel = FileChannel.open(PATH_TO_FILE.get())) {
            inputFileChannel.read(byteBuffer);
            byteBuffer.flip();
        }

        // Act
        final var underTest = AudioReader.getChannelMode(byteBuffer);

        // Assert
        assertThat(underTest).isEqualTo("Single channel (Mono)");
    }
}
