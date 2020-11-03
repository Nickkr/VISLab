package de.hska.iwi.vislab.lab1.example.ws;

import javax.jws.*;

@WebService
public interface FibonacciService {
    int getFibonacci(@WebParam(name = "n") int n);
}
