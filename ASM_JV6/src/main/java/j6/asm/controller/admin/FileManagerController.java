package j6.asm.controller.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import j6.asm.service.FileManagerService;

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
	public JsonNode upload(@PathVariable("folder") String folder, @PathParam("file") MultipartFile files) {
		File savedFile = fileService.save(folder, files);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name", savedFile.getName());
		node.put("size", savedFile.length());
		System.out.println(node);
		return node;

	}

	@PostMapping("/rest/uploadmulti/{folder}")
	public List<JsonNode> upload2(@PathVariable("folder") String folder,
			@RequestBody MultipartFile[] files) {
		List<JsonNode> responseList = new ArrayList<>();

		for (MultipartFile file : files) {
			File savedFile = fileService.save(folder, file);
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			node.put("name", savedFile.getName());
			node.put("size", savedFile.length());
			responseList.add(node);
		}

		return responseList;
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
