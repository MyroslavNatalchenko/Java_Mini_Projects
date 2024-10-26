package org.example.queries;

import org.example.model.Person;
import org.example.queries.calculations.GeneralCalculator;
import org.example.queries.calculations.ICalculate;
import org.example.queries.filters.*;
import org.example.queries.paging.ICutToPage;
import org.example.queries.paging.PageCutter;
import org.example.queries.results.FunctionResult;
import org.example.queries.results.Results;
import org.example.queries.search.FunctionsParameters;
import org.example.queries.search.Page;
import org.example.queries.search.SearchParameters;

import java.util.ArrayList;
import java.util.List;

public class QueryProcessor {
    List<IFilterPeople> filter = new ArrayList<IFilterPeople>();
    List<ICalculate> calculators = new ArrayList<ICalculate>();
    ICutToPage pageCutter;

    // Executes the query using provided parameters and data
    public Results GetResults(SearchParameters parameters, List<Person> data) {
        List<Person> people = data;
        Results res = new Results();

        // Applies each filter sequentially based on the search parameters
        for (IFilterPeople filter : filter) {
            filter.setSearchParameters(parameters);
            if (filter.canFilter()) {
                people = filter.filter(people);
            }
        }

        List<FunctionResult> results = new ArrayList<>();
        // Executes each specified function on the filtered data
        for (FunctionsParameters function : parameters.getFunctions()) {
            FunctionResult funres = new FunctionResult();
            funres.setFunction(function.getFunction());
            funres.setFieldName(function.getFieldName());

            double result_of_maths = 0;
            // Applies the calculation if the field matches
            for (ICalculate calculate : calculators) {
                if (funres.getFieldName().equals(calculate.getFieldName())) {
                    result_of_maths = calculate.calculate(function, people);
                }
            }
            funres.setValue(result_of_maths);
            results.add(funres);
        }

        //cut the amount to data for it cabality on Page
        pageCutter.cut(parameters.getPage(),people);

        // Sets the results including items, pagination, and function results
        res.setItems(people);
        res.setCurrentPage(parameters.getPage().getPageNumber());
        res.setPages(people.size() / parameters.getPage().getSize() + 1);
        res.setFunctionResults(results);
        return res;
    }

    // Adds a filter to the processor
    public QueryProcessor addFilter(IFilterPeople filter_name) {
        filter.add(filter_name);
        return this;
    }

    // Adds a calculation to the processor
    public QueryProcessor addCalculation(ICalculate iCalculate) {
        calculators.add(iCalculate);
        return this;
    }

    // Sets a pagination mechanism for the query results
    public void addPageCutter(ICutToPage pageCutter) {
        this.pageCutter = pageCutter;
    }
}
