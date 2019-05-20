package com.zm.common.rs.basic;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("{id}/service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface RsServiceExample {

	public static final String BEANID = "xxxx.xxxx";

	@POST
	@Path("sync")
	void sync(@PathParam(value = "id") String id, String body, @HeaderParam("Authorization") String authorization);

	@POST
	@Path("sync/batch")
	void syncBatch(@PathParam(value = "id") String id, List<String> body,
			@HeaderParam("Authorization") String authorization);

}