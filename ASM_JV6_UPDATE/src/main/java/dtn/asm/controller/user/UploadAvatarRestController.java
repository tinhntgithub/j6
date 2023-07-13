package dtn.asm.controller.user;

import java.io.File;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dtn.asm.service.FileManagerService;

@RestController
public class UploadAvatarRestController {

	@Autowired
	FileManagerService fileService;

//	@GetMapping("/update_account.html/{folder}/{file}")
//	public byte[] download(@PathVariable("folder") String folder,@PathVariable("file") String file) {
//		return fileService.read(folder, file);
//	}
//	
	@PostMapping("/update_account.html/{folder}")
	public String upload(@PathVariable("folder") String folder,@PathParam("files") MultipartFile files) {
		return fileService.save(folder, files).getAbsolutePath();
	}
	
	@GetMapping("/update_account.html/{folder}")
	public List<String> name(@PathVariable("folder") String folder) {
		return fileService.list(folder);
	}


}
