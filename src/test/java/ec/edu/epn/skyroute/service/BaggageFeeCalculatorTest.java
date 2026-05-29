package ec.edu.epn.skyroute.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class BaggageFeeCalculatorTest {
    	
    private final PassengerService passengerService;
    private BaggageFeeCalculator bfCalculator;
	
    @BeforeEach
    public void setUp(){
    	
    	passengerService = mock(passengerService.class);

    	bfCalculator.calculateFee(passengerService);
    }
    
    @Test
    public void equipajeEstandar(int maletas, int peso, double costoEsperado) {
    	long pasajeString = 12312;
        when(passengerService.isVip(pasajeString)).thenReturn(false);

        double resultado = bfCalculator.calculateFee(maletas, peso, pasajeString);
        
        assertEquals(costoEsperado, resultado);
    }
}
