package egovframework.com.cmm.util;

import nl.captcha.text.producer.TextProducer; 

/**
 * 전달받은 문자열을 그대로 getAudioCaptCha가 사용할 수 있도록 하는 역할
 **/
public class SetTextProducer implements TextProducer{ 
	
	private final String str;
	
	public SetTextProducer(String getAnswer) { 
		this.str = getAnswer; 
	}
	
	@Override
	public String getText() { 
		return this.str; 
	} 
}
