package de.hska.iwi.vislab.lab2.example;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.prefs.*;

@Path("fibonaccinumber")
public class FibonacciNumber {

    
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt() {
        int n = this.readPreference();
        int next = n + 1;
        this.savePreference(next);
        String json = "{\"value\": " + computeFibonacci(n) + "}";
        return json;
    }

    
    @DELETE
    public void resetIt() {
        this.savePreference(0);
    }

    private int computeFibonacci(int n) {
        if (n <= 2) {
            return (n > 0) ? 1 : 0;
        }
        return computeFibonacci(n - 1) + computeFibonacci(n - 2);
    }

    private void savePreference(int n) {
        Preferences prefs = Preferences.userNodeForPackage(FibonacciNumber.class);

        prefs.putInt("number", n);
    }

    private int readPreference() {
        Preferences prefs = Preferences.userNodeForPackage(FibonacciNumber.class);

        return prefs.getInt("number", 0);
    }
}
