package com.agilesolutions.quarkus.boundary;

import java.util.Properties;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.agilesolutions.quarkus.model.Profile;

@Path("/jenkins")
@Tag(name = "Jenkins Pipeline Execution", description = "Create and Execute DevOps Jenkins pipelines")
public class EndPoint {

	@Inject
	JenkinsService jenkinsService;

	@APIResponses(value = {
			@APIResponse(responseCode = "404", description = "We could not find anything", content = @Content(mediaType = "text/plain")),
			@APIResponse(responseCode = "200", description = "Jenkins Pipeline executed successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Properties.class))) })
	@Operation(summary = "Start a jenkins pipeline for a specific application and domain", description = "This method runs a deployment pipeline")
	@Timed(name = "get-all-books", description = "Monitor the time it takes to spawn a Jenkins pipeline Method takes", unit = MetricUnits.MILLISECONDS, absolute = true)
	@Metered(name = "create-books", unit = MetricUnits.MILLISECONDS, description = "Monitor the rate events occured", absolute = true)
	@POST
	public void startPipeline(Profile profile) {
		jenkinsService.startPipeline(profile);
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "404", description = "We could not find anything", content = @Content(mediaType = "text/plain")),
			@APIResponse(responseCode = "200", description = "We have a list of books", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Properties.class))) })
	@Operation(summary = "Create a jenkins pipeline for a specific application and domain", description = "This method creates a new deployment pipeline on Jenkins")
	@Timed(name = "get-all-books", description = "Monitor the time it takes to create a Jenkins pipeline Method takes", unit = MetricUnits.MILLISECONDS, absolute = true)
	@Metered(name = "create-books", unit = MetricUnits.MILLISECONDS, description = "Monitor the rate events occured", absolute = true)
	@POST
	public void createPipeline(Profile profile) {
		jenkinsService.createPipeline(profile);
	}
}