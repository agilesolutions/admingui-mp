package com.agilesolutions.quarkus.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.agilesolutions.quarkus.model.Profile;

@RequestScoped
public class JenkinsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsService.class);

	@Inject
	@ConfigProperty(name = "jenkins.url", defaultValue = "http://localhost:8080")
	private String jenkinsUrl;


	public String createPipeline(Profile profile) {

		MDC.put("ticket.id", "CRM-32");
		MDC.put("span.id", "create pipeline");

		LOGGER.info(String.format("start job"));

		Client client = ClientBuilder.newClient();

		// create folder

		String answer = null;

		try {

			// https://stackoverflow.com/questions/50408059/create-folder-in-jenkins-ui-using-curl

			String json = String.format(
					"{\"name\":\"%s\",\"mode\":\"com.cloudbees.hudson.plugins.folder.Folder\",\"from\":\"\",\"Submit\":\"OK\"}",
					profile.getDomain());
			Form form = new Form();
			form.param("name", profile.getDomain()).param("mode", "com.cloudbees.hudson.plugins.folder.Folder")
					.param("from", "").param("json", json).param("Submit", "OK");
			Entity<Form> entity = Entity.form(form);

			WebTarget target = client.target(String.format("%screateItem", jenkinsUrl));

			Response response = target.request(MediaType.APPLICATION_JSON).post(entity);

			String value = response.readEntity(String.class);
			response.close();

		} catch (Exception e2) {
			LOGGER.info(String.format("folder already exists"));
		}

		// get template

		String config = null;
		
		try {

			WebTarget target = client.target(jenkinsUrl + "/job/" + profile.getTemplate() + "/config.xml");

			Response response = target.request().get();
			// Read output in string format
			config = response.readEntity(String.class);

		} catch (Exception e1) {
			LOGGER.info(String.format("job template not found"));
		}

		// create new job from template

		try {
			
			WebTarget target = client.target(jenkinsUrl + "/job/" + profile.getDomain() + "/createItem?name="
					+ String.format("%s-%s", profile.getName(), profile.getEnvironment()));

			Response response = target.request().post(Entity.entity(config, MediaType.APPLICATION_JSON));
			 
			answer = response.readEntity(String.class);


		} catch (Exception e) {
			LOGGER.info(String.format("job already exists"));
		}

		return answer;
	}
	
	public String startPipeline(Profile profile) {

		MDC.put("ticket.id", "CRM-32");
		MDC.put("span.id", "start pipeline");

		LOGGER.info(String.format("start job"));
		
		Client client = ClientBuilder.newClient();
		
		// create folder

		String answer = null;

		try {

			// https://stackoverflow.com/questions/50408059/create-folder-in-jenkins-ui-using-curl

			String json = String.format(
					"{\"name\":\"%s\",\"mode\":\"com.cloudbees.hudson.plugins.folder.Folder\",\"from\":\"\",\"Submit\":\"OK\"}",
					profile.getDomain());
			Form form = new Form();
			form.param("name", profile.getDomain()).param("mode", "com.cloudbees.hudson.plugins.folder.Folder")
					.param("from", "").param("json", json).param("Submit", "OK");
			Entity<Form> entity = Entity.form(form);

			WebTarget target = client.target(String.format("%screateItem", jenkinsUrl));

			Response response = target.request(MediaType.APPLICATION_JSON).post(entity);

			String value = response.readEntity(String.class);
			response.close();

		} catch (Exception e2) {
			LOGGER.info(String.format("folder already exists"));
		}

		// get template

		String config = null;
		
		try {

			WebTarget target = client.target(jenkinsUrl + "/job/" + profile.getTemplate() + "/config.xml");

			Response response = target.request().get();
			// Read output in string format
			config = response.readEntity(String.class);

		} catch (Exception e1) {
			LOGGER.info(String.format("job template not found"));
		}

		// create new job from template

		try {
			
			WebTarget target = client.target(jenkinsUrl + "/job/" + profile.getDomain() + "/createItem?name="
					+ String.format("%s-%s", profile.getName(), profile.getEnvironment()));

			Response response = target.request().post(Entity.entity(config, MediaType.APPLICATION_JSON));
			 
			answer = response.readEntity(String.class);


		} catch (Exception e) {
			LOGGER.info(String.format("job already exists"));
		}

		// https://stackoverflow.com/questions/15909650/create-jobs-and-execute-them-in-jenkins-using-rest

		// start job with parameters

		try {
			
			
			WebTarget target = client.target(jenkinsUrl + "/job/" + profile.getDomain() + "/job/"
					+ String.format("%s-%s", profile.getName(), profile.getEnvironment())
					+ "/buildWithParameters?service=" + profile.getId());
			
			Response response = target.request().post(Entity.entity(config, MediaType.APPLICATION_JSON));
					
			answer = response.readEntity(String.class);

		} catch (Exception e) {
			LOGGER.info(String.format("error starting job"));
		}

		return answer;
	}


}
