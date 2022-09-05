import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ExtensibleAudioReader {

	public static int getVersion(byte[] byteBuffer) {
		final String[] versions = { "MPEG Version 2.5", "reserved", "MPEG Version 2", "MPEG Version 1" };

		String version = versions[Integer
				.parseInt(String.valueOf((byteBuffer[1] >> 5) & 1) + String.valueOf(((byteBuffer[1] >> 4) & 1)), 2)];
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

	public static int getLayer(byte[] byteBuffer) {
		final String[] layers = { "reserved", "Layer III", "Layer II", "Layer I" };

		String layer = layers[(Integer
				.parseInt(String.valueOf((byteBuffer[1] >> 7) & 1) + String.valueOf(((byteBuffer[1] >> 6) & 1)), 2))];
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

	public static void getBitrate(byte[] byteBuffer, int version, int layer) {
		final String[][] bitrates = { { "free", "free", "free", "free", "free" }, { "32", "32", "32", "32", "8" },
				{ "64", "48", "40", "48", "16" }, { "96", "56", "48", "56", "24" }, { "128", "64", "56", "64", "32" },
				{ "160", "80", "64", "80", "40" }, { "192", "96", "80", "96", "48" },
				{ "224", "112", "96", "112", "56" }, { "256", "128", "112", "128", "64" },
				{ "288", "160", "128", "144", "80" }, { "320", "192", "160", "160", "96" },
				{ "352", "224", "192", "176", "112" }, { "384", "256", "224", "192", "128" },
				{ "416", "320", "256", "224", "144" }, { "448", "384", "320", "256", "160" },
				{ "bad", "bad", "bad", "bad", "bad" } };

		final String[] possibleBitrates = bitrates[Integer
				.parseInt(String.valueOf(((byteBuffer[2] >> 4) & 1)) + String.valueOf(((byteBuffer[2] >> 3) & 1))
						+ String.valueOf(((byteBuffer[2] >> 2) & 1)) + String.valueOf(((byteBuffer[2] >> 1) & 1)), 2)];

		if (version == 1) {
			if (layer == 1) {
				System.out.println(possibleBitrates[0]);
			} else if (layer == 2) {
				System.out.println(possibleBitrates[1]);
			} else {
				System.out.println(possibleBitrates[2]);
			}
		} else {
			if (layer == 1) {
				System.out.println(possibleBitrates[3]);
			} else {
				System.out.println(possibleBitrates[4]);
			}
		}
	}

	public static void getSamplerate(byte[] byteBuffer, int version) {
		final String[][] samplerates = { { "44100 Hz", "22050 Hz", "11025 Hz" }, { "48000 Hz", "24000 Hz", "12000 Hz" },
				{ "32000 Hz", "16000 Hz", "8000 Hz" }, { "reserv.", "reserv.", "reserv." } };

		final String[] possibleSamplerates = samplerates[Integer
				.parseInt(String.valueOf(((byteBuffer[2] >> 6) & 1)) + String.valueOf(((byteBuffer[2] >> 5) & 1)), 2)];

		if (version == 1) {
			System.out.println(possibleSamplerates[0]);
		} else if (version == 2) {
			System.out.println(possibleSamplerates[1]);
		} else {
			System.out.println(possibleSamplerates[2]);
		}
	}

	public static void getChannelMode(byte[] byteBuffer) {
		final String[] channels = {
				"Stereo",
				"Joint stereo (Stereo)",
				"Dual channel (2 mono channels)",
				"Single channel (Mono)"
		};
		
		System.out.println(channels[Integer.parseInt((String.valueOf((byteBuffer[3] >> 2) & 1) + String.valueOf(((byteBuffer[3] >> 1) & 1))), 2)]);
	}

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		// TODO Auto-generated method stub
		try {
			// Read file into a byte buffer
			Path path = FileSystems.getDefault().getPath("LBCNews.mp3").toAbsolutePath();
			File file = new File(path.toString());
			// Read the first frame of an audio file
			byte[] byteBuffer = new byte[4];
			FileInputStream fileInputStream = new FileInputStream(file);
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
			int version = getVersion(byteBuffer);

//			2. the MPEG Layer of the file
			// Located in second byte of the byteBuffer at index 6 and 7
			int layer = getLayer(byteBuffer);

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

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		long endTime = System.nanoTime();
		System.out.println("Took " + (endTime - startTime) + " ns");

	}

}
