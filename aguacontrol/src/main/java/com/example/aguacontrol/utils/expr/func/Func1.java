package com.example.aguacontrol.utils.expr.func;

@FunctionalInterface
public interface Func1<V1, O> {
    O get(V1 v1);
}
