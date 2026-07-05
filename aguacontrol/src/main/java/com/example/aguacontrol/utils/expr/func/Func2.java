package com.example.aguacontrol.utils.expr.func;

@FunctionalInterface
public interface Func2<V1, V2, O> {
    O get(V1 v1, V2 v2);
}
