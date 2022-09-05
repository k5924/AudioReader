
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * 
 */

/**
 * @author tr1x
 *
 */
public class AudioReader {

	/**
	 * @param args
	 */
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
			fileInputStream.close();

			int version = 0;
			int layer = 0;

			// Bits are in reverse order
			// So on the website it lists the positions like this
			// 20, 19
			// 0 0
			// 0 1
			// 1 0
			// 1 1

//			1. the MPEG Audio Version ID of the file
			// This is located in the second byte of the byteBuffer at index 4 and 5
			if ((((byteBuffer[1] >> 4) & 1) == 1) && (((byteBuffer[1] >> 5) & 1) == 1)) {
				System.out.println("MPEG Version 1");
				version = 1;
			} else if ((((byteBuffer[1] >> 4) & 1) == 1) && (((byteBuffer[1] >> 5) & 1) == 0)) {
				System.out.println("reserved");
			} else if ((((byteBuffer[1] >> 4) & 1) == 0) && (((byteBuffer[1] >> 5) & 1) == 0)) {
				System.out.println("MPEG Version 2.5");
				version = 3;
			} else {
				System.out.println("MPEG Version 2");
				version = 2;
			}

//			2. the MPEG Layer of the file
			// Located in second byte of the byteBuffer at index 6 and 7
			if ((((byteBuffer[1] >> 6) & 1) == 1) && (((byteBuffer[1] >> 7) & 1) == 1)) {
				System.out.println("Layer I");
				layer = 1;
			} else if ((((byteBuffer[1] >> 6) & 1) == 1) && (((byteBuffer[1] >> 7) & 1) == 0)) {
				System.out.println("Layer III");
				layer = 3;
			} else if ((((byteBuffer[1] >> 6) & 1) == 0) && (((byteBuffer[1] >> 7) & 1) == 0)) {
				System.out.println("reserved");
			} else {
				System.out.println("Layer II");
				layer = 2;
			}

//			3. the bitrate of the file
			// Located in third byte of byteBuffer at index 1, 2, 3 and 4
			if ((((byteBuffer[2] >> 4) & 1) == 0) && (((byteBuffer[2] >> 3) & 1) == 0)
					&& (((byteBuffer[2] >> 2) & 1) == 0) && (((byteBuffer[2] >> 1) & 1) == 0)) {
				System.out.println("free");
			} else if ((((byteBuffer[2] >> 4) & 1) == 0) && (((byteBuffer[2] >> 3) & 1) == 0)
					&& (((byteBuffer[2] >> 2) & 1) == 0) && (((byteBuffer[2] >> 1) & 1) == 1)) {
				if ((version == 2) && (layer != 1)) {
					System.out.println(8);
				} else {
					System.out.println(32);
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 0) && (((byteBuffer[2] >> 3) & 1) == 0)
					&& (((byteBuffer[2] >> 2) & 1) == 1) && (((byteBuffer[2] >> 1) & 1) == 0)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(64);
						break;
					case 2:
						System.out.println(48);
						break;
					case 3:
						System.out.println(40);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(48);
						break;
					default:
						System.out.println(16);
						break;
					}
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 0) && (((byteBuffer[2] >> 3) & 1) == 0)
					&& (((byteBuffer[2] >> 2) & 1) == 1) && (((byteBuffer[2] >> 1) & 1) == 1)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(96);
						break;
					case 2:
						System.out.println(56);
						break;
					case 3:
						System.out.println(48);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(56);
						break;
					default:
						System.out.println(24);
						break;
					}
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 0) && (((byteBuffer[2] >> 3) & 1) == 1)
					&& (((byteBuffer[2] >> 2) & 1) == 0) && (((byteBuffer[2] >> 1) & 1) == 0)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(128);
						break;
					case 2:
						System.out.println(64);
						break;
					case 3:
						System.out.println(56);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(64);
						break;
					default:
						System.out.println(32);
						break;
					}
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 0) && (((byteBuffer[2] >> 3) & 1) == 1)
					&& (((byteBuffer[2] >> 2) & 1) == 0) && (((byteBuffer[2] >> 1) & 1) == 1)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(160);
						break;
					case 2:
						System.out.println(80);
						break;
					case 3:
						System.out.println(64);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(80);
						break;
					default:
						System.out.println(40);
						break;
					}
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 0) && (((byteBuffer[2] >> 3) & 1) == 1)
					&& (((byteBuffer[2] >> 2) & 1) == 1) && (((byteBuffer[2] >> 1) & 1) == 0)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(192);
						break;
					case 2:
						System.out.println(96);
						break;
					case 3:
						System.out.println(80);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(96);
						break;
					default:
						System.out.println(48);
						break;
					}
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 0) && (((byteBuffer[2] >> 3) & 1) == 1)
					&& (((byteBuffer[2] >> 2) & 1) == 1) && (((byteBuffer[2] >> 1) & 1) == 1)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(224);
						break;
					case 2:
						System.out.println(112);
						break;
					case 3:
						System.out.println(96);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(112);
						break;
					default:
						System.out.println(56);
						break;
					}
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 1) && (((byteBuffer[2] >> 3) & 1) == 0)
					&& (((byteBuffer[2] >> 2) & 1) == 0) && (((byteBuffer[2] >> 1) & 1) == 0)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(256);
						break;
					case 2:
						System.out.println(128);
						break;
					case 3:
						System.out.println(112);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(128);
						break;
					default:
						System.out.println(64);
						break;
					}
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 1) && (((byteBuffer[2] >> 3) & 1) == 0)
					&& (((byteBuffer[2] >> 2) & 1) == 0) && (((byteBuffer[2] >> 1) & 1) == 1)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(288);
						break;
					case 2:
						System.out.println(160);
						break;
					case 3:
						System.out.println(128);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(144);
						break;
					default:
						System.out.println(80);
						break;
					}
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 1) && (((byteBuffer[2] >> 3) & 1) == 0)
					&& (((byteBuffer[2] >> 2) & 1) == 1) && (((byteBuffer[2] >> 1) & 1) == 0)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(320);
						break;
					case 2:
						System.out.println(192);
						break;
					case 3:
						System.out.println(160);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(160);
						break;
					default:
						System.out.println(96);
						break;
					}
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 1) && (((byteBuffer[2] >> 3) & 1) == 0)
					&& (((byteBuffer[2] >> 2) & 1) == 1) && (((byteBuffer[2] >> 1) & 1) == 1)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(352);
						break;
					case 2:
						System.out.println(224);
						break;
					case 3:
						System.out.println(192);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(176);
						break;
					default:
						System.out.println(112);
						break;
					}
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 1) && (((byteBuffer[2] >> 3) & 1) == 1)
					&& (((byteBuffer[2] >> 2) & 1) == 0) && (((byteBuffer[2] >> 1) & 1) == 0)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(384);
						break;
					case 2:
						System.out.println(256);
						break;
					case 3:
						System.out.println(224);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(192);
						break;
					default:
						System.out.println(128);
						break;
					}
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 1) && (((byteBuffer[2] >> 3) & 1) == 1)
					&& (((byteBuffer[2] >> 2) & 1) == 0) && (((byteBuffer[2] >> 1) & 1) == 1)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(416);
						break;
					case 2:
						System.out.println(320);
						break;
					case 3:
						System.out.println(256);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(224);
						break;
					default:
						System.out.println(144);
						break;
					}
				}
			} else if ((((byteBuffer[2] >> 4) & 1) == 1) && (((byteBuffer[2] >> 3) & 1) == 1)
					&& (((byteBuffer[2] >> 2) & 1) == 1) && (((byteBuffer[2] >> 1) & 1) == 0)) {
				if (version == 1) {
					switch (layer) {
					case 1:
						System.out.println(448);
						break;
					case 2:
						System.out.println(384);
						break;
					case 3:
						System.out.println(320);
						break;
					}
				} else {
					switch (layer) {
					case 1:
						System.out.println(256);
						break;
					default:
						System.out.println(160);
						break;
					}
				}
			} else {
				System.out.println("bad");
			}

//			4. the sample rate of the file
			if ((((byteBuffer[2] >> 6) & 1) == 1) && (((byteBuffer[2] >> 5) & 1) == 1)) {
				System.out.println("reserv.");
			} else {
				if ((((byteBuffer[2] >> 6) & 1) == 0) && (((byteBuffer[2] >> 5) & 1) == 0)) {
					switch (version) {
					case 1:
						System.out.println(44100);
						break;
					case 2:
						System.out.println(22050);
						break;
					case 3:
						System.out.println(11025);
						break;
					default:
						System.out.println("reserv.");
					}
				} else if ((((byteBuffer[2] >> 6) & 1) == 0) && (((byteBuffer[2] >> 5) & 1) == 1)) {
					switch (version) {
					case 1:
						System.out.println(48000);
						break;
					case 2:
						System.out.println(24000);
						break;
					case 3:
						System.out.println(12000);
						break;
					default:
						System.out.println("reserv.");
					}
				} else {
					switch (version) {
					case 1:
						System.out.println(32000);
						break;
					case 2:
						System.out.println(16000);
						break;
					case 3:
						System.out.println(8000);
						break;
					default:
						System.out.println("reserv.");
					}
				}
			}

//			5. the channel mode of the file
			if ((((byteBuffer[3] >> 2) & 1) == 0) && (((byteBuffer[3] >> 1) & 1) == 0)) {
				System.out.println("Stereo");
			} else if ((((byteBuffer[3] >> 2) & 1) == 0) && (((byteBuffer[3] >> 1) & 1) == 1)) {
				System.out.println("Joint stereo (Stereo)");
			} else if ((((byteBuffer[3] >> 2) & 1) == 1) && (((byteBuffer[3] >> 1) & 1) == 0)) {
				System.out.println("Dual channel (2 mono channels)");
			} else {
				System.out.println("Single channel (Mono)");
			}

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
