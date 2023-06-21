package org.k5924;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class AudioReaderTest {

    @Test
    void shouldRetrieveCorrectAudioVersionFromAudioFile() throws IOException {
        // Arrange
        final var url = Thread.currentThread().getContextClassLoader()
                .getResource("LBCNews.mp3");
        final ByteBuffer byteBuffer;
        try (var inputFileChannel = FileChannel.open(Path.of(url.getPath()))) {
            byteBuffer = ByteBuffer.allocateDirect(4);
            inputFileChannel.read(byteBuffer);
        }

        // Act
        final var underTest = AudioReader.getMpegAudioVersion(byteBuffer);

        // Assert
        assertThat(underTest).isEqualTo("MPEG Version 1");
    }

    @Test
    void shouldRetrieveCorrectLayerVersionFromAudioFile() throws IOException {
        // Arrange
        final var url = Thread.currentThread().getContextClassLoader()
                .getResource("LBCNews.mp3");
        final ByteBuffer byteBuffer;
        try (var inputFileChannel = FileChannel.open(Path.of(url.getPath()))) {
            byteBuffer = ByteBuffer.allocateDirect(4);
            inputFileChannel.read(byteBuffer);
        }

        // Act
        final var underTest = AudioReader.getLayer(byteBuffer);

        // Assert
        assertThat(underTest).isEqualTo("Layer 3");
    }

    @Test
    void shouldRetrieveCorrectBitrateFromAudioFile() throws IOException {
        // Arrange
        final var url = Thread.currentThread().getContextClassLoader()
                .getResource("LBCNews.mp3");
        final ByteBuffer byteBuffer;
        try (var inputFileChannel = FileChannel.open(Path.of(url.getPath()))) {
            byteBuffer = ByteBuffer.allocateDirect(4);
            inputFileChannel.read(byteBuffer);
        }

        // Act
        final var underTest = AudioReader.getBitrate(byteBuffer);

        // Assert
        assertThat(underTest).isEqualTo("256");
    }

}
