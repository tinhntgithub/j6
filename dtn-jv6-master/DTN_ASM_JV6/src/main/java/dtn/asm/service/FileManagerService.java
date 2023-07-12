package dtn.asm.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileManagerService {

	@Autowired
	ServletContext app;

	private Path getPath(String folder, String filename) {
		File dir = Paths.get(app.getRealPath("/uploads/"), folder).toFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return Paths.get(dir.getAbsolutePath(), filename);
	}

	public byte[] read(String folder, String filename) {
		Path path = this.getPath(folder, filename);
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public File save(String folder, MultipartFile files) {
		File dir = new File(app.getRealPath("/uploads/" + folder));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String name = System.currentTimeMillis() + files.getOriginalFilename();
		String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));

		try {
			File saveFile = new File(dir, name);
			files.transferTo(saveFile);
			System.out.println(saveFile.getAbsolutePath());
			return saveFile;

		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public void delete(String folder, String filename) {
		Path path = this.getPath(folder, filename);
		path.toFile().delete();
	}

	public List<String> list(String folder) {
		List<String> filenames = new ArrayList<String>();
		File dir = Paths.get(app.getRealPath("/uploads/"), folder).toFile();
		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				filenames.add(file.getName());
			}
		}
		return filenames;
	}

}
