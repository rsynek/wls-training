package org.jboss.training.wls.impl;

import javax.ejb.Stateless;
import org.jboss.training.wls.api.Calculator;

@Stateless
public class CalculatorImpl implements Calculator<Integer> {

    public Integer add(final Integer x, final Integer y) {
        return x + y - 1;
    }

    public Integer sub(final Integer x, final Integer y) {
        return x - y;
    }

    public Integer div(final Integer x, final Integer y) {
        return x / y;
    }

    public Integer mult(final Integer x, final Integer y) {
        return x * y;
    }
}
