package org.example;

import org.example.model.Gender;
import org.example.model.PeopleSample;
import org.example.model.Person;
import org.example.queries.QueryProcessor;
import org.example.queries.calculations.GeneralCalculator;
import org.example.queries.calculations.ICalculate;
import org.example.queries.filters.*;
import org.example.queries.paging.ICutToPage;
import org.example.queries.paging.PageCutter;
import org.example.queries.results.Results;
import org.example.queries.search.Funcs;
import org.example.queries.search.FunctionsParameters;
import org.example.queries.search.Page;
import org.example.queries.search.SearchParameters;

public class Main {
    public static void main(String[] args) {
        // Instantiate specific filters based on name and age criteria
        IFilterPeople byName = new ByNameFilter();
        IFilterPeople byAgeFromFilter = new ByAgeFromFilter();
        IFilterPeople byAgeToFilter = new ByAgeToFilter();

        // Generic filter for gender selection based on provided predicate logic
        IFilterPeople genericFilter = new GeneralFilter(
                (searchParams) -> !searchParams.getSelectedGenders().isEmpty(), // Checks if genders list is not empty
                (searchParams, person) -> searchParams.getSelectedGenders().contains(person.getGender()) // Filters based on gender match
        );

        // Generic filters for income criteria using upper and lower bounds
        IFilterPeople byIncomeToGenericFilter = new GeneralFilter(
                (searchParams) -> searchParams.getIncomeTo() > 0, // Checks if the upper income limit is set
                (searchParams, person) -> searchParams.getIncomeTo() > person.getIncome() // Filters by max income
        );
        IFilterPeople byIncomeFromGenericFilter = new GeneralFilter(
                searchParams -> searchParams.getIncomeFrom() > 0, // Checks if the lower income limit is set
                (searchParams, person) -> searchParams.getIncomeFrom() < person.getIncome() // Filters by min income
        );

        // Initialize QueryProcessor and add filters for filtering the data
        QueryProcessor queryProcessor = new QueryProcessor()
                .addFilter(byName)
                .addFilter(byAgeToFilter)
                .addFilter(byAgeFromFilter)
                .addFilter(genericFilter)
                .addFilter(byIncomeToGenericFilter)
                .addFilter(byIncomeFromGenericFilter);

        // Create income and age calculators for aggregating functions (sum, average, etc.)
        ICalculate incomeCalculator = new GeneralCalculator("income", p -> p.getIncome());
        ICalculate ageCalculator = new GeneralCalculator("age", p -> p.getAge());
        queryProcessor.addCalculation(incomeCalculator)
                .addCalculation(ageCalculator);

        // Set up page cutter for paginating data in the `QueryProcessor`
        ICutToPage pageCutter = new PageCutter();
        queryProcessor.addPageCutter(pageCutter);

        // Run GetResults to process data based on sample search parameters and a sample list of people
        Results results = queryProcessor.GetResults(sampleSearchParams(), PeopleSample.Data);

        // Check if filtering results are correct; if not, output an error message and terminate
        if (!resultsAreGood(results)) {
            System.out.println("Filtering does not work correctly :(");
            return;
        }
        // Confirm that the results appear acceptable
        System.out.println("Looks acceptable.");
    }

    // Provides sample search parameters for age, income, gender, and function aggregations
    private static SearchParameters sampleSearchParams() {
        SearchParameters params = new SearchParameters();
        params.setAgeFrom(20);
        params.setAgeTo(40);
        params.setIncomeFrom(2000);
        params.setPage(new Page(9, 1));
        params.getSelectedGenders().add(Gender.FEMALE);
        params.getSelectedGenders().add(Gender.OTHER);
        params.getFunctions().add(new FunctionsParameters("age", Funcs.AVERAGE));
        params.getFunctions().add(new FunctionsParameters("income", Funcs.SUM));
        params.getFunctions().add(new FunctionsParameters("income", Funcs.AVERAGE));
        return params;
    }

    // Method to validate the filtering results against expected values for testing
    private static boolean resultsAreGood(Results result) {
        return result.getItems().size() == 3
                && result.getItems().contains(PeopleSample.AnnaBuda)
                && result.getItems().contains(PeopleSample.ConchitaWurst)
                && result.getItems().contains(PeopleSample.AnetaUrban)
                && result.getCurrentPage() == 1
                && result.getPages() == 1
                && result.getFunctionResults().size() == 3;
    }
}
