package dtn.asm.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
	private String from = "";
	private String to;
	private String subject;
	private String body;
	private List<String> cc = new ArrayList<>();
	private List<String> bcc = new ArrayList<>();
	private List<File> files = new ArrayList<>();

	public MailInfo(String to, String subject, String body) {
		this.from = "";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}
