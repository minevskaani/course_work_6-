package io.raytracing.geometry.abstractentity;

public class Ref<T> {
    public T value;

    public Ref() {
        this.value = null;
    }

    public Ref(T value) {
        this.value = value;
    }

}
