package com.example.aguacontrol.utils.expr.action;

@FunctionalInterface
public interface Action3<V1, V2, V3> {
    void run(V1 v1, V2 v2, V3 v3);
}