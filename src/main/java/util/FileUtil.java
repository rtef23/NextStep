/**
 copyright by NAVER corp
 writer : 송주용
 date : 2017-11-12
 */

package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author 송주용
 */
public class FileUtil {
	private FileUtil() {
	}

	public static byte[] readFile(String fileDirectoryPath) throws IOException {
		return Files.readAllBytes(Paths.get(fileDirectoryPath));
	}
}
