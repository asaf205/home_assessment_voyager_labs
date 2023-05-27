package service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterValidatorTest {

    @Test
    public void testValidateParameters_InsufficientArguments() {
        String[] args = new String[0];
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validateParameters(args);
        });
        Assertions.assertEquals("Insufficient number of arguments. Expected 4 arguments.", exception.getMessage());
    }

    @Test
    public void testValidateParameters_InvalidArgumentTypes() {
        String[] args = {"http://example.com", "10", "5", "notBoolean"};
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validateParameters(args);
        });
        Assertions.assertEquals("Invalid argument type. maxUrls and maxDepth should be integers, and crossLevelUniqueness should be a boolean.", exception.getMessage());
    }

    @Test
    public void testValidateParameters_StartUrlNull() {
        String[] args = {null, "10", "5", "true"};
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validateParameters(args);
        });
        Assertions.assertEquals("startUrl cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void testValidateParameters_StartUrlEmpty() {
        String[] args = {"", "10", "5", "true"};
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validateParameters(args);
        });
        Assertions.assertEquals("startUrl cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void testValidateParameters_MaxUrlsZero() {
        String[] args = {"http://example.com", "0", "5", "true"};
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validateParameters(args);
        });
        Assertions.assertEquals("maxUrls must be a positive integer.", exception.getMessage());
    }

    @Test
    public void testValidateParameters_MaxDepthZero() {
        String[] args = {"http://example.com", "10", "0", "true"};
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validateParameters(args);
        });
        Assertions.assertEquals("maxDepth must be a positive integer.", exception.getMessage());
    }

    @Test
    public void testValidateParameters_ValidArguments() {
        String[] args = {"http://example.com", "10", "5", "true"};
        Assertions.assertDoesNotThrow(() -> ParameterValidator.validateParameters(args));
    }
}
