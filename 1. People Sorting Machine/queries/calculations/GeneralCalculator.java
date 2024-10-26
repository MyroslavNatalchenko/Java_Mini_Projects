package org.example.queries.calculations;

import org.example.model.Person;
import org.example.queries.search.FunctionsParameters;

import java.util.List;
import java.util.function.Function;

public class GeneralCalculator implements ICalculate {
    private String fieldName;
    private Function<Person, Number> FieldGetter;

    // Constructor to initialize field and the getter function for the calculation
    public GeneralCalculator(String fieldName, Function<Person, Number> fieldGetter) {
        this.fieldName = fieldName;
        this.FieldGetter = fieldGetter;
    }

    // Calculates the specified function (SUM, AVERAGE, MAX, MIN) on the field for a list of Person data
    @Override
    public double calculate(FunctionsParameters parameters, List<Person> data) {
        switch (parameters.getFunction()) {
            case SUM:
                // Summing the specified field values
                return data.stream()
                        .mapToDouble(p -> FieldGetter.apply(p).doubleValue())
                        .sum();
            case AVERAGE:
                // Calculating the average of the specified field values
                return data.stream()
                        .mapToDouble(p -> FieldGetter.apply(p).doubleValue())
                        .average().orElse(0); //OptionalDouble -> Double
            case MAX:
                // Finding the maximum value of the specified field
                return data.stream()
                        .mapToDouble(p -> FieldGetter.apply(p).doubleValue())
                        .max().orElse(0); //OptionalDouble -> Double
            case MIN:
                // Finding the minimum value of the specified field
                return data.stream()
                        .mapToDouble(p -> FieldGetter.apply(p).doubleValue())
                        .min().orElse(0); //OptionalDouble -> Double
            default:
                return 0;
        }
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }
}

