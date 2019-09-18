package PhageEngine;

import java.util.ArrayList;

public abstract class Loop {

    private static ArrayList<Loop> instances = new ArrayList<>(), deleteQueue = new ArrayList<>();

    public static void updateObjects() {


//        Deletes game objects without throwing an exception
        deleteQueue.forEach(object -> instances.remove(object));
        deleteQueue.clear();

//        This will update all game objects
        instances.forEach(Loop::onUpdate);

    }

    public abstract void onUpdate();

    public static void removeObject(Loop o) {
        deleteQueue.add(o);
    }

    public static void addObject(Loop o) {
        instances.add(o);
    }

}
