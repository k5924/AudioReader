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
		Map<List<Integer>, String> versions = new HashMap<List<Integer>, String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put(Arrays.asList(1, 1), "MPEG Version 1");
				put(Arrays.asList(1, 0), "reserved");
				put(Arrays.asList(0, 0), "MPEG Version 2.5");
				put(Arrays.asList(0, 1), "MPEG Version 2");
			}
		};

		String version = versions.get(Arrays.asList(((byteBuffer[1] >> 4) & 1), ((byteBuffer[1] >> 5) & 1)));
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
		Map<List<Integer>, String> layers = new HashMap<List<Integer>, String>() {
			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			{
				put(Arrays.asList(1, 1), "Layer I");
				put(Arrays.asList(1, 0), "Layer III");
				put(Arrays.asList(0, 0), "reserved");
				put(Arrays.asList(0, 1), "Layer II");
			}
		};

		String layer = layers.get(Arrays.asList(((byteBuffer[1] >> 6) & 1), ((byteBuffer[1] >> 7) & 1)));
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
		Map<List<Integer>, String[]> bitrates = new HashMap<List<Integer>, String[]>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put(Arrays.asList(0, 0, 0, 0), new String[] { "free", "free", "free", "free", "free" });
				put(Arrays.asList(0, 0, 0, 1), new String[] { "32", "32", "32", "32", "8" });
				put(Arrays.asList(0, 0, 1, 0), new String[] { "64", "48", "40", "48", "16" });
				put(Arrays.asList(0, 0, 1, 1), new String[] { "96", "56", "48", "56", "24" });
				put(Arrays.asList(0, 1, 0, 0), new String[] { "128", "64", "56", "64", "32" });
				put(Arrays.asList(0, 1, 0, 1), new String[] { "160", "80", "64", "80", "40" });
				put(Arrays.asList(0, 1, 1, 0), new String[] { "192", "96", "80", "96", "48" });
				put(Arrays.asList(0, 1, 1, 1), new String[] { "224", "112", "96", "112", "56" });
				put(Arrays.asList(1, 0, 0, 0), new String[] { "256", "128", "112", "128", "64" });
				put(Arrays.asList(1, 0, 0, 1), new String[] { "288", "160", "128", "144", "80" });
				put(Arrays.asList(1, 0, 1, 0), new String[] { "320", "192", "160", "160", "96" });
				put(Arrays.asList(1, 0, 1, 1), new String[] { "352", "224", "192", "176", "112" });
				put(Arrays.asList(1, 1, 0, 0), new String[] { "384", "256", "224", "192", "128" });
				put(Arrays.asList(1, 1, 0, 1), new String[] { "416", "320", "256", "224", "144" });
				put(Arrays.asList(1, 1, 1, 0), new String[] { "448", "384", "320", "256", "160" });
				put(Arrays.asList(1, 1, 1, 1), new String[] { "bad", "bad", "bad", "bad", "bad" });
			}
		};

		String[] possibleBitrates = bitrates.get(Arrays.asList(((byteBuffer[2] >> 4) & 1), ((byteBuffer[2] >> 3) & 1),
				((byteBuffer[2] >> 2) & 1), ((byteBuffer[2] >> 1) & 1)));
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
		Map<List<Integer>, String[]> samplerates = new HashMap<List<Integer>, String[]>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			{
				put(Arrays.asList(0, 0), new String[] { "44100 Hz", "22050 Hz", "11025 Hz" });
				put(Arrays.asList(0, 1), new String[] { "48000 Hz", "24000 Hz", "12000 Hz" });
				put(Arrays.asList(1, 0), new String[] { "32000 Hz", "16000 Hz", "8000 Hz" });
				put(Arrays.asList(1, 1), new String[] { "reserv.", "reserv.", "reserv." });

			}
		};

		String[] possibleSamplerates = samplerates
				.get(Arrays.asList(((byteBuffer[2] >> 6) & 1), ((byteBuffer[2] >> 5) & 1)));
		if (version == 1) {
			System.out.println(possibleSamplerates[0]);
		} else if (version == 2) {
			System.out.println(possibleSamplerates[1]);
		} else {
			System.out.println(possibleSamplerates[2]);
		}
	}

	public static void getChannelMode(byte[] byteBuffer) {
		Map<List<Integer>, String> channels = new HashMap<List<Integer>, String>() {
			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			{
				put(Arrays.asList(1, 1), "Single channel (Mono)");
				put(Arrays.asList(1, 0), "Dual channel (2 mono channels)");
				put(Arrays.asList(0, 0), "Stereo");
				put(Arrays.asList(0, 1), "Joint stereo (Stereo)");
			}
		};

		System.out.println(channels.get(Arrays.asList(((byteBuffer[3] >> 2) & 1), ((byteBuffer[3] >> 1) & 1))));
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
