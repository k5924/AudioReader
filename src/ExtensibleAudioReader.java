import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ExtensibleAudioReader {
	final static String[] versions = { "MPEG Version 2.5", "reserved", "MPEG Version 2", "MPEG Version 1", };

	final static String[] layers = { "reserved", "Layer III", "Layer II", "Layer I", };

	final static String[][] bitrates = {
			{ "free", "8", "16", "24", "32", "40", "48", "56", "64", "80", "96", "112", "128", "144", "160", "bad" },
			{ "free", "8", "16", "24", "32", "40", "48", "56", "64", "80", "96", "112", "128", "144", "160", "bad" },
			{ "free", "32", "48", "56", "64", "80", "96", "112", "128", "144", "160", "176", "192", "224", "256",
					"bad" },
			{ "free", "32", "40", "48", "56", "64", "80", "96", "112", "128", "160", "192", "224", "256", "320",
					"bad" },
			{ "free", "32", "48", "56", "64", "80", "96", "112", "128", "160", "192", "224", "256", "320", "283",
					"bad" },
			{ "free", "32", "64", "96", "128", "160", "192", "224", "256", "288", "320", "352", "384", "416", "448",
					"bad" }, };

	final static String[][] samplerates = { { "44100 Hz", "22050 Hz", "11025 Hz" },
			{ "48000 Hz", "24000 Hz", "12000 Hz" }, { "32000 Hz", "16000 Hz", "8000 Hz" },
			{ "reserv.", "reserv.", "reserv." }, };

	final static String[] channels = { "Stereo", "Joint stereo (Stereo)", "Dual channel (2 mono channels)",
			"Single channel (Mono)", };

	public static int getVersion(final byte[] byteBuffer) {
		final String version = versions[-((byteBuffer[1] >> 5) << 1) - ((((byteBuffer[1] >> 4))))];
		System.out.println(version);

		switch (version) {
		case "MPEG Version 1":
			return 1;
		case "MPEG Version 2.5":
			return 3;
		case "MPEG Verison 2":
			return 2;
		default:
			return 0;
		}
	}

	public static int getLayer(final byte[] byteBuffer) {
		final String layer = layers[(-((byteBuffer[1] >> 7) << 1) + (((byteBuffer[1] >> 6))))];
		System.out.println(layer);

		switch (layer) {
		case "Layer I":
			return 1;
		case "Layer III":
			return 3;
		case "Layer II":
			return 2;
		default:
			return 0;
		}
	}

	public static void getBitrate(final byte[] byteBuffer, final int version, final int layer) {
		final String[] possibleBitrates = bitrates[((-((byteBuffer[1] >> 5) << 3) - (((byteBuffer[1] >> 4) << 2)))
				- ((byteBuffer[1] >> 7) << 1) - (((byteBuffer[1] >> 6)))) - 10];

		if ((version != 0) && (layer != 0) && (version != 3)) {
			System.out.println(possibleBitrates[Integer.parseInt("" + (((byteBuffer[2] >> 4) & 1))
					+ (((byteBuffer[2] >> 3) & 1)) + (((byteBuffer[2] >> 2) & 1)) + (((byteBuffer[2] >> 1) & 1)), 2)]);
		} else {
			System.out.println("Bitrate index not found");
		}
	}

	public static void getSamplerate(final byte[] byteBuffer, final int version) {
		final String[] possibleSamplerates = samplerates[-(((byteBuffer[2] >> 6) << 1)) + (((byteBuffer[2] >> 5)))];

		if (version != 0) {
			System.out.println(possibleSamplerates[version - 1]);
		} else {
			System.out.println("Sample rate not found");
		}

	}

	public static void getChannelMode(final byte[] byteBuffer) {
		System.out.println(
				channels[Integer.parseInt(("" + ((byteBuffer[3] >> 2) & 1) + (((byteBuffer[3] >> 1) & 1))), 2)]);
	}

	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		try {
			// Read file into a byte buffer
			final Path path = FileSystems.getDefault().getPath("LBCNews.mp3").toAbsolutePath();
			final File file = new File(path.toString());
			// Read the first frame of an audio file
			final byte[] byteBuffer = new byte[4];
			final FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(byteBuffer);
			// System.out.println(fileInputStream.read(byteBuffer));
			fileInputStream.close();

			// Bits are in reverse order
			// So on the website it lists the positions like this
			// 20, 19
			// 0 0
			// 0 1
			// 1 0
			// 1 1

//			1. the MPEG Audio Version ID of the file
			// This is located in the second byte of the byteBuffer at index 4 and 5
			final int version = getVersion(byteBuffer);

//			2. the MPEG Layer of the file
			// Located in second byte of the byteBuffer at index 6 and 7
			final int layer = getLayer(byteBuffer);

//			3. the bitrate of the file
			// Located in third byte of byteBuffer at index 1, 2, 3 and 4
			getBitrate(byteBuffer, version, layer);

//			4. the sample rate of the file
			getSamplerate(byteBuffer, version);

//			5. the channel mode of the file
			getChannelMode(byteBuffer);

			// Results to match this
//			1. MPEG Version 1
//			2. Layer 3
//			3. 256
//			4. 44100
//			5. 3 (Mono)

		} catch (final IOException ex) {
			ex.printStackTrace();
		}
	}

}