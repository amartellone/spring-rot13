package testspring;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class SampleController {

	@RequestMapping("/")
	@ResponseBody
	String home()  {
		String host = "";
		try {
			host = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			host = "unknown";
		}
		return "<h1>Hello! This is ROT13!</h1><br><p>Hostname: "+ host + "</p> ";
	}

	// Apply the ROT13 algorithm to a string
	@RequestMapping(value = "/calculate/{value}", method = RequestMethod.GET)
	@ResponseBody
	Message calculate(@PathVariable String value) {
		char[] charsArray = value.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < charsArray.length; i++) {
			if (((charsArray[i] >= 'a' && charsArray[i] <= 'm')) || ((charsArray[i] >= 'A' && charsArray[i] <= 'M')))
				sb.append((char) (charsArray[i] + 13));
			else if (((charsArray[i] >= 'n' && charsArray[i] <= 'z'))
					|| ((charsArray[i] >= 'N' && charsArray[i] <= 'Z')))
				sb.append((char) (charsArray[i] - 13));
			else if (charsArray[i] == ' ')
				sb.append((char) (charsArray[i]));
		}
		return new Message(sb.toString());
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleController.class, args);
	}
}
