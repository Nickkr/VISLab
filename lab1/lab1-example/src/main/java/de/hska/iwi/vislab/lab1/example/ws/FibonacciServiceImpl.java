package de.hska.iwi.vislab.lab1.example.ws;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "de.hska.iwi.vislab.lab1.example.ws.FibonacciService")
public class FibonacciServiceImpl implements FibonacciService {

    public void FibonacciService() {}

    @WebMethod
    @Override
    public int getFibonacci(int n) {
        int number = this.computeFibonacci(n);
        return number;
    }

    private int computeFibonacci(int n) {
        if (n <= 2) {
            return (n > 0) ? 1 : 0;
        }
        return computeFibonacci(n - 1) + computeFibonacci(n - 2);
    }
}
