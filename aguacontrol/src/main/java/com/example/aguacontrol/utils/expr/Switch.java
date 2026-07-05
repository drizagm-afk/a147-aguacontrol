package com.example.aguacontrol.utils.expr;

import com.example.aguacontrol.utils.expr.func.BoolFunc;
import com.example.aguacontrol.utils.expr.func.Func;

import java.util.ArrayList;
import java.util.List;

public class Switch {
    //OBJECT: STRING
    @FunctionalInterface
    public interface StrPattern {
        void run(Context<String> ctx);
    }

    //OBJECT: ANY
    @FunctionalInterface
    public interface Pattern<O> {
        void run(Context<O> ctx);
    }
    public static class Context<O> {
        //CASES
        List<BoolFunc> conds = new ArrayList<>();
        List<Func<O>> funcs = new ArrayList<>();

        public Context<O> Case(boolean cond, O obj) {
            return Case(() -> cond, () -> obj);
        }

        public Context<O> Case(BoolFunc cond, O obj) {
            return Case(cond, () -> obj);
        }

        public Context<O> Case(boolean cond, Func<O> func) {
            return Case(() -> cond, func);
        }

        public Context<O> Case(BoolFunc cond, Func<O> func) {
            conds.add(cond);
            funcs.add(func);
            return this;
        }

        //DEFAULT
        Func<O> defFunc = () -> null;

        public Context<O> Default(O obj) {
            return Default(() -> obj);
        }

        public Context<O> Default(Func<O> func) {
            defFunc = func;
            return this;
        }

        //EXECUTE
        O exec() {
            for (int i = 0; i < conds.size(); i++) {
                if (conds.get(i).get())
                    return funcs.get(i).get();
            }
            return defFunc.get();
        }
    }
}
