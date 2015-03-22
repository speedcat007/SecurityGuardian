package cn.edu.njust.securityguardian.privacyprotection.filecrypt;

import java.util.List;

public class FileList {

	private List<FileListEntry> children;
	
	public List<FileListEntry> getChildren() {
		return children;
	}
	public void setChildren(List<FileListEntry> children) {
		this.children = children;
	}
	
	public FileList(List<FileListEntry> children) {
		super();
		this.children = children;
	}

}
