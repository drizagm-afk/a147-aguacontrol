package com.example.aguacontrol.utils.expr;

import java.util.ArrayList;
import java.util.List;

public class Loop {
    public static class Context<O> {
        int id = 0;

        public int Id() {
            return id;
        }

        public void Continue() {
            throw new ContinueException();
        }

        public void Break() {
            throw new BreakException();
        }

        List<O> list = new ArrayList<>();

        public void Yield(O o) {
            list.add(o);
        }

        public void YieldAll(Iterable<O> oList) {
            for (var o : oList)
                list.add(o);
        }
    }

    public static class Exception extends RuntimeException {
        public Exception() {
            super("", null, false, false);
        }
    }

    public static class ContinueException extends Exception {
    }

    public static class BreakException extends Exception {
    }
}
