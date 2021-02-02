package su.grinev;

import java.util.*;

class ObjectItem {
    private Object object;
    private int state;
    public static int ACQUIRED = 1;
    public static int RELEASED = 0;

    public ObjectItem(Object object) {
        this.object = object;
        this.state = ObjectItem.RELEASED;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectItem that = (ObjectItem) o;
        return object.equals(that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object);
    }
}

public class ObjectPool {

    private final int maxObject;
    private Map<Class, List<ObjectItem>> freeObjects;
    private HashMap<Object, ObjectItem> occupiedObjects;
    private ObjectItem objectItem;

    public ObjectPool(int maxObj) {
        freeObjects = new HashMap<>();
        occupiedObjects = new HashMap<>();
        this.objectItem = new ObjectItem(null);
        this.maxObject = maxObj;
    }

    public boolean putObject(Object object) {
        if (object == null) throw new IllegalArgumentException("Object must not be null");
        if (freeObjects.get(object.getClass()) == null) freeObjects.put(object.getClass(), new LinkedList<>());
        freeObjects
                .get(object.getClass())
                .add(new ObjectItem(object));
        return true;
    }

    private void remObject(Object o) {
    }

    public Object acquireObject(Class c) {
        ObjectItem oi = freeObjects.get(c).remove(0);
        occupiedObjects.put(oi.getObject(), oi);
      //  oi.setState(ObjectItem.ACQUIRED);
        return oi.getObject();
    }

    public void releaseObject(Object o) {
        ObjectItem oi = occupiedObjects.remove(o);
        freeObjects.get(o.getClass())
                .add(oi);
      //  oi.setState(ObjectItem.RELEASED);
    }

}