package pl.edu.pw.elka.gis2014z;

import com.google.common.base.Strings;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import org.apache.commons.cli.CommandLine;

import java.util.Set;

public class Validator {

    private final CommandLine line;

    public Validator(CommandLine line) {
        this.line = line;
    }

    void validateOptions() throws ValidationException {
        assertType();
        String graphType = line.getOptionValue("type");
        assertV();
        switch (graphType) {
            case "r" :
                assertP();
                break;
            case "sw" :
                assertLvl();
                assertPc();
                break;
            case "sf" :
                assertE();
                assertSv();
                break;
            case "e" :
                assertR();
                break;
            default:
                throw new ValidationException("Something went wrong.");
        }
        assertF();
    }

    private void assertType() throws ValidationException {
        assertPresent("type");
        assertOptionInValues("type", Sets.newHashSet("r", "sw", "sf", "e"));
    }

    private void assertV() throws ValidationException {
        assertPresent("v");
        assertInteger("v");
        assertIntegerInRange("v", Range.closed(1, 25000));
    }

    private void assertP() throws ValidationException {
        assertPresent("p");
        assertFloat("p");
        assertFloatInRange("p", Range.closed(0.0f, 1.0f));
    }

    private void assertPc() throws ValidationException {
        assertPresent("pc");
        assertFloat("pc");
        assertFloatInRange("pc", Range.closed(0.0f, 1.0f));
    }

    private void assertLvl() throws ValidationException {
        int vertices = Integer.parseInt(line.getOptionValue("v"));
        assertPresent("lvl");
        assertInteger("lvl");
        assertIntegerInRange("lvl", Range.closed(0, vertices - 1));
    }

    private void assertE() throws ValidationException {
        int vertices = Integer.parseInt(line.getOptionValue("v"));
        assertPresent("e");
        assertInteger("e");
        assertIntegerInRange("lvl", Range.closed(0, vertices  * (vertices - 1) / 2));
    }

    private void assertSv() throws ValidationException {
        assertPresent("sv");
        assertInteger("sv");
        assertIntegerInRange("sv", Range.closed(1, 25000));
    }

    private void assertR() throws ValidationException {
        assertPresent("r");
        assertFloat("r");
        assertFloatInRange("r", Range.closed(0.0f, (float) Math.sqrt(2)));
    }

    private void assertF() throws ValidationException {
        assertPresent("f");
        assertOptionCondition(!Strings.isNullOrEmpty(line.getOptionValue("f").trim()), "Option f cannot be blank");
    }

    private void assertPresent(String argName) throws ValidationException {
        assertOptionCondition(line.hasOption(argName), "Lack of " + argName + " option.");
    }

    private void assertOptionInValues(String optionName, Set<String> availableValues) throws ValidationException {
        assertOptionCondition(availableValues.contains(line.getOptionValue(optionName)),
                "Option " + optionName + " has invalid value");
    }

    private void assertInteger(String optionName) throws ValidationException {
        String value = line.getOptionValue(optionName);
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ValidationException("Option " + optionName + " is not a valid integer.");
        }
    }

    private void assertIntegerInRange(String optionName, Range<Integer> range)
            throws ValidationException {
        assertOptionCondition(range.contains(Integer.parseInt(line.getOptionValue(optionName))),
                "Option " + optionName + " should be in range: " + range.lowerEndpoint() + "-" + range.upperEndpoint());

    }

    private void assertFloat(String optionName) throws ValidationException {
        String value = line.getOptionValue(optionName);
        try {
            Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new ValidationException("Option " + optionName + "is not a valid float number.");
        }
    }

    private void assertFloatInRange(String optionName, Range<Float> range)
            throws ValidationException {
        assertOptionCondition(range.contains(Float.parseFloat(line.getOptionValue(optionName))),
                "Option " + optionName + " should be in range: " + range.lowerEndpoint() + "-" + range.upperEndpoint());

    }

    private void assertOptionCondition(boolean condition, String message) throws ValidationException {
        if (!condition) {
            throw new ValidationException(message);
        }
    }

    static class ValidationException extends Exception {

        private ValidationException(String message) {
            super(message);
        }

    }
}
