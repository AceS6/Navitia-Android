package io.goodway.navitia_android;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public interface Action<T> {
    public void action(T e);
}