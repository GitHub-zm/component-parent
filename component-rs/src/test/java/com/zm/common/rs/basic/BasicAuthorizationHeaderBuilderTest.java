package com.zm.common.rs.basic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasicAuthorizationHeaderBuilderTest {

	@Test
	public void testBuild() {
		assertEquals("Basic aGRwb3M6aGRwb3M=", BasicAuthorizationHeaderBuilder.build("hdpos", "hdpos"));

		assertEquals("Basic Z3Vlc3Q6Z3Vlc3Q=", BasicAuthorizationHeaderBuilder.build("guest", "guest"));

		assertEquals("Basic Og==", BasicAuthorizationHeaderBuilder.build("", ""));
	}
}
