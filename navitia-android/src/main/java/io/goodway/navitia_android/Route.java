package io.goodway.navitia_android;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Route {

    private String id;
    private String name;

    private Line line;

    public Route(String id, String name, Line line) {
        this.id = id;
        this.name = name;
        this.line = line;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Line getLine() {
        return line;
    }

    @Override
    public String toString(){
        return this.getLine().toString() + " direction " + this.name;
    }
}
