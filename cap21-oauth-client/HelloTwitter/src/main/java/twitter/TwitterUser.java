package twitter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TwitterUser {
	@XmlElement(name = "name")
	private String name;

	public String getName() {
		return name;
	}
}
