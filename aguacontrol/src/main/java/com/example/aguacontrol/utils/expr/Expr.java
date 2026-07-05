package com.example.aguacontrol.utils.expr;

import com.example.aguacontrol.utils.expr.action.Action;
import com.example.aguacontrol.utils.expr.action.Action1;
import com.example.aguacontrol.utils.expr.action.Action2;
import com.example.aguacontrol.utils.expr.func.*;

import java.util.ArrayList;
import java.util.List;

public class Expr {
    //>>>> CONDITIONALS
    //IF
    public static String If(boolean cond, String onTrue) {
        return If(cond, onTrue, "");
    }

    public static <O> O If(boolean cond, O onTrue) {
        return If(cond, onTrue, null);
    }

    public static <O> O If(boolean cond, O onTrue, O onFalse) {
        return cond ? onTrue : onFalse;
    }

    //IF: SUPPLIERS
    public static String If(boolean cond, StrFunc onTrue) {
        return If(cond, onTrue::get, () -> "");
    }

    public static <O> O If(boolean cond, Func<O> onTrue) {
        return If(cond, onTrue, () -> null);
    }

    public static <O> O If(boolean cond, Func<O> onTrue, Func<O> onFalse) {
        return cond ? onTrue.get() : onFalse.get();
    }

    //SWITCH
    public static String Switch(Switch.StrPattern patt) {
        var ctx = new Switch.Context<String>().Default("");
        patt.run(ctx);

        return ctx.exec();
    }

    public static <O> O Switch(Switch.Pattern<O> patt) {
        var ctx = new Switch.Context<O>();
        patt.run(ctx);

        return ctx.exec();
    }

    //>>>> LOOPS
    //FOR
    public static <I, O> List<O> For(Iterable<I> iList, Action2<I, Loop.Context<O>> action) {
        Loop.Context<O> ctx = new Loop.Context<>();
        for (var i : iList) {
            try {
                action.run(i, ctx);
            } catch (Loop.ContinueException ignored) {
            } catch (Loop.BreakException e) {
                break;
            }

            ctx.id++;
        }

        return ctx.list;
    }

    public static <O> List<O> For(int start, int end, Action1<Loop.Context<O>> action) {
        Loop.Context<O> ctx = new Loop.Context<>();
        for (int i = start; i < end; i++) {
            try {
                ctx.id = i;
                action.run(ctx);
            } catch (Loop.ContinueException ignored) {
            } catch (Loop.BreakException e) {
                break;
            }
        }

        return ctx.list;
    }

    //REPEAT
    public static <O> List<O> Repeat(int times, Action1<Loop.Context<O>> action) {
        return For(0, times, action);
    }

    //WHILE
    public static <O> List<O> While(BoolFunc cond, Action1<Loop.Context<O>> action) {
        Loop.Context<O> ctx = new Loop.Context<>();
        while (cond.get()) {
            try {
                action.run(ctx);
            } catch (Loop.ContinueException ignored) {
            } catch (Loop.BreakException e) {
                break;
            }

            ctx.id++;
        }

        return ctx.list;
    }

    //DO-WHILE
    public static <O> List<O> DoWhile(BoolFunc cond, Action1<Loop.Context<O>> action) {
        Loop.Context<O> ctx = new Loop.Context<>();
        try {
            action.run(ctx);
        } catch (Loop.ContinueException ignored) {
        } catch (Loop.BreakException e) {
            return ctx.list;
        }

        return While(cond, action);
    }

    //>>>> EXECUTION
    //DO
    public static void Do(Action action) {
        action.run();
    }

    //DO: EXPLICIT RETURN
    public static <O> O Do(Func<O> func) {
        return func.get();
    }

    //WITH
    public static <V> V With(V value, Action1<V> action) {
        action.run(value);
        return value;
    }

    //WITH: EXPLICIT RETURN
    public static <V, O> O With(V value, Func1<V, O> func) {
        return func.get(value);
    }
}
