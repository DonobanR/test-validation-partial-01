package ec.edu.epn.skyroute.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

public class BaggageFeeCalculatorTest {
    	
    private final PassengerService passengerService;
    private BaggageFeeCalculator bfCalculator;
	
    @BeforeEach
    public void setUp(){
    	
    	passengerService = mock(passengerService.class);

    	bfCalculator = new BaggageFeeCalculator();
    }
    
    @ParameterizedTest
    @DisplayName("Casos 1 y 2: Cálculo de costo para pasajero regular")
    @CsvSource({
        "1, 20, 30.00",
        "1, 25, 80.00"
    })

    public void equipajeEstandar(int maletas, int peso, double costoEsperado) {
    	long pasajeString = 12312;
        when(passengerService.isVip(pasajeString)).thenReturn(false);

        double resultado = bfCalculator.calculateFee(maletas, peso, pasajeString);
        
        assertEquals(costoEsperado, resultado);
    }

    @Test
    @DisplayName("Caso 3: Beneficio VIP - 1 maleta gratis")
    void testBeneficioVIP() {
        String pasajeroId = "VIP-777";
        // Mockito simula que el servicio externo reconoce al pasajero como VIP
        when(passengerService.isVip(pasajeroId)).thenReturn(true);

        double resultado = bfCalculator.calculateFee(1, 15, pasajeroId);

        assertEquals(0.00, resultado);
    }

    @Test
    @DisplayName("Caso 4: Caso límite VIP - 1ra gratis, 2da cobro normal")
    void testCasoLimiteVIP() {
        String pasajeroId = "VIP-777";
        when(passengerService.isVip(pasajeroId)).thenReturn(true);

        // 2 maletas de 15 kg cada una
        double resultado = bfCalculator.calculateFee(2, 15, pasajeroId);

        assertEquals(30.00, resultado);
    }

    @ParameterizedTest
    @DisplayName("Caso 5: Validación de excepción para peso cero o negativo")
    @CsvSource({
        "1,  0", // Peso cero
        "1, -5"  // Peso negativo
    })
    void testValidacionDeExcepcion(int maletas, int peso) {
        String pasajeroId = "REG-01";

        assertThrows(IllegalArgumentException.class, () -> {
            bfCalculator.calculateFee(maletas, peso, pasajeroId);
        });
    }
}
