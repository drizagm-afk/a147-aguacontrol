package com.example.aguacontrol.utils.expr.func;

@FunctionalInterface
public interface Func3<V1, V2, V3, O> {
    O get(V1 v1, V2 v2, V3 v3);
}
