package j6.asm.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileManagerService {

    private final ResourceLoader resourceLoader;

    @Autowired
    public FileManagerService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private Path getPath(String folder, String filename) {
        String baseDir = "static/uploads/";
        Resource resource = resourceLoader.getResource("classpath:" + baseDir + folder);
        try {
            File dir = resource.getFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return Paths.get(dir.getAbsolutePath(), filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        String name = System.currentTimeMillis() + files.getOriginalFilename();
        String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));

        Path path = this.getPath(folder, filename);
        try {
            File saveFile = path.toFile();
            files.transferTo(saveFile);
            System.out.println(saveFile.getAbsolutePath());
            return saveFile;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String folder, String filename) {
        Path path = this.getPath(folder, filename);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> list(String folder) {
        List<String> filenames = new ArrayList<>();
        String baseDir = "static/uploads/";
        Resource resource = resourceLoader.getResource("classpath:" + baseDir + folder);
        try {
            File dir = resource.getFile();
            if (dir.exists()) {
                File[] files = dir.listFiles();
                for (File file : files) {
                    filenames.add(file.getName());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filenames;
    }
}


// package j6.asm.service;

// import java.io.File;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.ArrayList;
// import java.util.List;

// import javax.management.RuntimeErrorException;
// import javax.servlet.ServletContext;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.io.Resource;
// import org.springframework.core.io.ResourceLoader;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// @Service
// public class FileManagerService {

// 	@Autowired
// 	private ResourceLoader resourceLoader;

// 	@Autowired
// 	ServletContext app;

// 	private Path getPath(String folder, String filename) {
// 		String baseDir = "static/uploads/";
// 		Resource resource = resourceLoader.getResource("classpath:" + baseDir + folder);
// 		try {
// 			File dir = resource.getFile();
// 			if (!dir.exists()) {
// 				dir.mkdirs();
// 			}
// 			return Paths.get(dir.getAbsolutePath(), filename);
// 		} catch (IOException e) {
// 			throw new RuntimeException(e);
// 		}
// 	}

// 	public byte[] read(String folder, String filename) {
// 		Path path = this.getPath(folder, filename);
// 		try {
// 			return Files.readAllBytes(path);
// 		} catch (IOException e) {
// 			throw new RuntimeException(e);
// 		}
// 	}

// 	public File save(String folder, MultipartFile files) {
// 		String baseDir = "static/uploads/";
// 		Resource resource = resourceLoader.getResource("classpath:" + baseDir + folder);
// 		try {
// 			File dir = resource.getFile();
// 			if (!dir.exists()) {
// 				dir.mkdirs();
// 			}
// 			String name = System.currentTimeMillis() + files.getOriginalFilename();
// 			String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));

// 			File saveFile = new File(dir, name);
// 			files.transferTo(saveFile);
// 			System.out.println(saveFile.getAbsolutePath());
// 			return saveFile;

// 		} catch (Exception e) {
// 			throw new RuntimeException(e);
// 		}
// 	}

// 	public void delete(String folder, String filename) {
// 		Path path = this.getPath(folder, filename);
// 		path.toFile().delete();
// 	}

// 	public List<String> list(String folder) {
// 		List<String> filenames = new ArrayList<>();
// 		String baseDir = "static/uploads/";
// 		Resource resource = resourceLoader.getResource("classpath:" + baseDir + folder);
// 		try {
// 			File dir = resource.getFile();
// 			if (dir.exists()) {
// 				File[] files = dir.listFiles();
// 				for (File file : files) {
// 					filenames.add(file.getName());
// 				}
// 			}
// 		} catch (IOException e) {
// 			throw new RuntimeException(e);
// 		}
// 		return filenames;
// 	}

// }
