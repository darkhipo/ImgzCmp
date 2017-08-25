package home;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;

public class FileDownloader {

	/**
	 * Returns a path to the downloaded file
	 *
	 * @param url
	 * @param localFilename
	 * @return
	 * @throws IOException
	 */
	public static String downloadFromUrl(URL url, String localFilename) throws IOException {
		InputStream       is = null;
		FileOutputStream fos = null;

		String tempDir = System.getProperty("java.io.tmpdir");
		String outputPath = Paths.get(tempDir, localFilename).toAbsolutePath().toString();

		try {
			// connect
			URLConnection urlConn = url.openConnection();

			// get inputstream from connection
			is = urlConn.getInputStream();
			fos = new FileOutputStream(outputPath);

			// 4KB buffer
			byte[] buffer = new byte[4096];
			int length;

			// read from source and write into local file
			while ((length = is.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
			return outputPath;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} finally {
				if (fos != null) {
					fos.close();
				}
			}
		}
	}
}