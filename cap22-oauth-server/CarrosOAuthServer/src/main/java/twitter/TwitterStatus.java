package twitter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TwitterStatus {
	@XmlElement(name = "created_at")
	private String createdAt;
	@XmlElement(name = "text")
	private String text;
	@XmlElement(name = "user")
	private TwitterUser user;

	public String getCreatedAt() {
		return createdAt;
	}

	public String getText() {
		return text;
	}

	public TwitterUser getUser() {
		return user;
	}
}
