package com.example.aguacontrol.utils.expr.action;

@FunctionalInterface
public interface Action2<V1, V2> {
    void run(V1 v1, V2 v2);
}
