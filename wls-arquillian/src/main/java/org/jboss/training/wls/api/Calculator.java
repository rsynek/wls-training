package org.jboss.training.wls.api;

import javax.ejb.Local;

@Local
public interface Calculator<T extends Number> {

    T add(T x, T y);

    T sub(T x, T y);

    T div(T x, T y);

    T mult(T x, T y);
}
