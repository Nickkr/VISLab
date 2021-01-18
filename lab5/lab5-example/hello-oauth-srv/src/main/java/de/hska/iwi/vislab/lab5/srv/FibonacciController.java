package de.hska.iwi.vislab.lab5.srv;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.prefs.*;

@RestController
public class FibonacciController {

    
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @PostMapping("/fibonaccinumber")
    public String getIt() {
        int n = this.readPreference();
        int next = n + 1;
        this.savePreference(next);
        String json = "{\"value\": " + computeFibonacci(n) + "}";
        return json;
    }

    
    @DeleteMapping("/fibonaccinumber")
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
        Preferences prefs = Preferences.userNodeForPackage(FibonacciController.class);

        prefs.putInt("number", n);
    }

    private int readPreference() {
        Preferences prefs = Preferences.userNodeForPackage(FibonacciController.class);

        return prefs.getInt("number", 0);
    }
}
