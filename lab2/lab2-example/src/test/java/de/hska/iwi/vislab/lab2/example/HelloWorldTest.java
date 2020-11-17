package de.hska.iwi.vislab.lab2.example;

import javax.swing.text.html.parser.Entity;
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
		Response delResponse = target.path("fibonaccinumber").request().accept(MediaType.TEXT_PLAIN).delete();
		assertEquals(204, delResponse.getStatus());
		String response = target.path("fibonaccinumber").request().accept(MediaType.TEXT_PLAIN).post(null, String.class);
		assertEquals(0, Integer.parseInt(response));

		int max = 25;
		int result = 1;
		for (int i = 1; i <= max; i++) {
			result = Integer.parseInt(target.path("fibonaccinumber").request().accept(MediaType.TEXT_PLAIN).post(null, String.class));
			System.out.println(result);
		}
		// test the 25th f*-nr
		assertEquals(75025, result);

		delResponse = target.path("fibonaccinumber").request().accept(MediaType.TEXT_PLAIN).delete();
		assertEquals(204, delResponse.getStatus());
		response = target.path("fibonaccinumber").request().accept(MediaType.TEXT_PLAIN).post(null, String.class);
		assertEquals(0, Integer.parseInt(response));
	}
}
