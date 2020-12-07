package com.jtriemstra.wonders.api.dto.request;

import java.net.URLDecoder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlDecodeTests {
	@Test
	public void when_decoding_single_coded_then_success() {
		ChooseGuildRequest cgr = new ChooseGuildRequest();
		cgr.setCardName("Scientists%20Guild");
		
		Assertions.assertEquals("Scientists Guild", URLDecoder.decode(URLDecoder.decode(cgr.getCardName())));
	}
	
	@Test
	public void when_decoding_double_coded_then_success() {
		ChooseGuildRequest cgr = new ChooseGuildRequest();
		cgr.setCardName("Scientists%2520Guild");
		
		Assertions.assertEquals("Scientists Guild", URLDecoder.decode(URLDecoder.decode(cgr.getCardName())));
	}
}
