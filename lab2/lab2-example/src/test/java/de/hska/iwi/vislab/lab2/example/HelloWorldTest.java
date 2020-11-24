package de.hska.iwi.vislab.lab2.example;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.StringReader;

public class HelloWorldTest {

	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		// start the server
		server = Main.startServer();
		// create the client
		Client c = ClientBuilder.newClient();

		// uncomment the following line if you want to enable
		// support for JSON in the client (you also have to uncomment
		// dependency on jersey-media-json module in pom.xml and
		// Main.startServer())
		// --
		// c.configuration().enable(new
		// org.glassfish.jersey.media.json.JsonJaxbFeature());

		target = c.target(Main.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}

	/**
	 * Test to see that the message "Got it!" is sent in the response.
	 */
	@Test
	public void testSayHello() {
		String responseMsg = target.path("helloworld").request().accept(MediaType.TEXT_PLAIN).get(String.class);
		assertEquals("Hallo Welt!", responseMsg);
	}

	@Test
	public void testFibonacciNumber() {
		JsonReaderFactory factory = Json.createReaderFactory(null);

		Response delResponse = target.path("fibonaccinumber").request().accept(MediaType.TEXT_PLAIN).delete();
		assertEquals(204, delResponse.getStatus());
		String response = target.path("fibonaccinumber").request().accept(MediaType.APPLICATION_JSON).post(null, String.class);

		StringReader stringReader = new StringReader(response);
		JsonReader reader = factory.createReader(stringReader);
		JsonObject obj = reader.readObject();

		assertEquals(0, obj.getInt("value"));

		int max = 25;
		String result = "";
		for (int i = 1; i <= max; i++) {
			result = target.path("fibonaccinumber").request().accept(MediaType.APPLICATION_JSON).post(null, String.class);
			System.out.println(result);
		}
		// test the 25th f*-nr
		stringReader = new StringReader(result);
		reader = factory.createReader(stringReader);
		obj = reader.readObject();
		assertEquals(75025, obj.getInt("value"));

		delResponse = target.path("fibonaccinumber").request().accept(MediaType.TEXT_PLAIN).delete();
		assertEquals(204, delResponse.getStatus());
		response = target.path("fibonaccinumber").request().accept(MediaType.APPLICATION_JSON).post(null, String.class);
		
		stringReader = new StringReader(response);
		reader = factory.createReader(stringReader);
		obj = reader.readObject();
		
		assertEquals(0, obj.getInt("value"));
	}
}
