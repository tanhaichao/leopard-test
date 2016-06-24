package io.leopard.test.mock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.mock.web.MockMultipartFile;

/**
 * 本机文件.
 * 
 * @author ahai
 *
 */
public class LocalMultipartFile extends MockMultipartFile {

	
	public LocalMultipartFile(String path) throws IOException {
		super(path, readFile(path));
	}

	protected static InputStream readFile(String path) throws FileNotFoundException {
		return new FileInputStream(new File(path));
	}
}
