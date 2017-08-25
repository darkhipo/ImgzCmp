package home;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.bytedeco.javacpp.opencv_core.Mat;

public class Tests {
	String exampleImgUrl1Name = "http://i.imgur.com/dHq0BlN.png";
	String exampleImgUrl2Name = "http://i.imgur.com/apUNp99.png";
	String exampleImgUrl3Name = "http://i.imgur.com/1ogON.jpg";
	URL example1 = null;
	URL example2 = null;

	@Test
	public void testSameImages() {
		try {
			example1 = new URL(exampleImgUrl1Name);
			example2 = new URL(exampleImgUrl1Name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			String local1 = FileDownloader.downloadFromUrl(example1, UUID.randomUUID().toString());
			String local2 = FileDownloader.downloadFromUrl(example2, UUID.randomUUID().toString());
			Mat src1 = home.Main.toMat(local1);
			Mat src2 = home.Main.toMat(local2);
			Boolean same = home.Main.areSame(src1, src2);
			// Free mem.
			src1.deallocate();
			src2.deallocate();
			src1.close();
			src2.close();
			assertTrue(same);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDiffImages() {
		try {
			example1 = new URL(exampleImgUrl1Name);
			example2 = new URL(exampleImgUrl2Name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			String local1 = FileDownloader.downloadFromUrl(example1, UUID.randomUUID().toString());
			String local2 = FileDownloader.downloadFromUrl(example2, UUID.randomUUID().toString());
			Mat src1 = home.Main.toMat(local1);
			Mat src2 = home.Main.toMat(local2);
			Boolean same = home.Main.areSame(src1, src2);
			// Free mem.
			src1.deallocate();
			src2.deallocate();
			src1.close();
			src2.close();
			assertFalse(same);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDiffDim() {
		try {
			example1 = new URL(exampleImgUrl1Name);
			example2 = new URL(exampleImgUrl3Name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			String local1 = FileDownloader.downloadFromUrl(example1, UUID.randomUUID().toString());
			String local2 = FileDownloader.downloadFromUrl(example2, UUID.randomUUID().toString());
			Mat src1 = home.Main.toMat(local1);
			Mat src2 = home.Main.toMat(local2);
			Boolean same = home.Main.areSame(src1, src2);
			// Free mem.
			src1.deallocate();
			src2.deallocate();
			src1.close();
			src2.close();
			assertFalse(same);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}