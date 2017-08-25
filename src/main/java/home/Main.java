package home;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.indexer.UByteRawIndexer;
import org.bytedeco.javacpp.opencv_core.Mat;

public class Main {

	static Mat toMat(String fname) {
		Mat mat = null;
		try {
			File file = new File(fname);
			BufferedImage image = ImageIO.read(file);
			if(image == null) {
				System.err.println("File " + file + " can't be read.");
			}
			byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
			mat = new Mat(pixels);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mat;
	}

	public static Boolean areSame(Mat A, Mat B) {
		UByteRawIndexer sI1 = A.createIndexer();
		UByteRawIndexer sI2 = B.createIndexer();
		Integer countLz = 0;
		Integer countEz = 0;
		Integer countGz = 0;
		if ((A.rows() != B.rows()) || (A.cols() != B.cols())) {
			return false;
		}
		for (Integer y = 0; y < A.rows(); y++) {
			for (Integer x = 0; x < A.cols(); x++) {
				if (sI1.get(y, x) < sI2.get(y, x)) {
					countLz++;
				}
				if (sI1.get(y, x) == sI2.get(y, x)) {
					countEz++;
				}
				if (sI1.get(y, x) > sI2.get(y, x)) {
					countGz++;
				}
			}
		}
		// System.out.println(countLz + " " + countEz + " " + countGz);
		Boolean eq = (countLz + countGz) == 0;

		// Clear mem.
		A.deallocate();
		B.deallocate();
		A.close();
		B.close();
		return eq;
	}

	/** Only one main.
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("The program must recieve 2 valid image urls as its arguments.");
		} else {
			Mat img1 = toMat(args[0]);
			Mat img2 = toMat(args[1]);
			Boolean same = areSame(img1, img2);
			img1.deallocate();
			img2.deallocate();
			img1.close();
			img2.close();
			System.out.println(same ? 1 : 0);
		}
	}
	**/

	private static String myurl = "https://upload.wikimedia.org/wikipedia/en/2/2d/ST_TOS_Cast.jpg";
	
	public static char[] compareWebImageToLocalFile(String string) {
		Mat src1 = home.Main.toMat(string);
		Mat src2 = null;
		try {
			String buff = home.FileDownloader.downloadFromUrl(new URL(myurl), UUID.randomUUID().toString() );
			src2 = home.Main.toMat(buff);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Boolean same = home.Main.areSame(src1, src2);
		return same.toString().toCharArray();
	}
}