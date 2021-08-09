package co.uk.cbradbury.quackstats;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class QuackstatsUtils {

    SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter
            .serializeAllExcept("id");
    FilterProvider filters = new SimpleFilterProvider()
            .addFilter("myFilter", theFilter);
}
