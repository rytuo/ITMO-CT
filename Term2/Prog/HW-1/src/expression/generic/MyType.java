package expression.generic;

public abstract class MyType<T extends Number> {
    protected T value;

    public MyType(T value) {
        this.value = value;
    }

    public T valueOf() {
        return value;
    }

    public abstract MyType<T> add(MyType<T> arg);

    public abstract MyType<T> sub(MyType<T> arg);

    public abstract MyType<T> mul(MyType<T> arg);

    public abstract MyType<T> div(MyType<T> arg);

    public abstract MyType<T> neg();
}