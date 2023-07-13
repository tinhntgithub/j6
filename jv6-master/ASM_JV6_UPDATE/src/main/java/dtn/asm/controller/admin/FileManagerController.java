package dtn.asm.controller.admin;

import java.io.File;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dtn.asm.service.FileManagerService;

@CrossOrigin("*")
@RestController
public class FileManagerController {

	@Autowired
	FileManagerService fileService;

	@GetMapping("/rest/uploads/{folder}/{file}")
	public byte[] download(@PathVariable("folder") String folder, @PathVariable("file") String file) {
		return fileService.read(folder, file);
	}

	@PostMapping("/rest/uploads/{folder}")
	public JsonNode upload(@PathVariable("folder") String folder, @PathParam("file") MultipartFile files ){
		File savedFile = fileService.save(folder, files);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name", savedFile.getName());
		node.put("size", savedFile.length());
		return node;

	}

	@DeleteMapping("/rest/uploads/{folder}/{file}")
	public void delete(@PathVariable("folder") String folder, @PathVariable("file") String file) {
		fileService.delete(folder, file);
	}

	@GetMapping("/rest/uploads/{folder}")
	public List<String> list(@PathVariable("folder") String folder) {
		return fileService.list(folder);
	}

}
