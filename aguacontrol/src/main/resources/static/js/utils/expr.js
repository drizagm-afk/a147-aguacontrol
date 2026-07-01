export function If(cond, onTrue, onFalse) {
    //ON TRUE
    if (cond) {
        if (typeof onTrue === "function")
            return onTrue();

        return onTrue;
    }
    //ON FALSE
    if (onFalse === undefined) {
        if (typeof onTrue === "string")
            return "";

        return null;
    } else if (typeof onFalse === "function")
        return onFalse();

    return onFalse;
}
export function With(item, method) {
    if (item)
        return method(item);

    return null;
}