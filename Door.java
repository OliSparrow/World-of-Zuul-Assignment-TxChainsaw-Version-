
public class Door {
    private boolean isLocked;

    public Door() {
        this.isLocked = true; //door is locked initially
    }

    public boolean isLocked(){
        return isLocked;
    }

    public void lock() {
        this.isLocked = true;
    }

    public void unlock(){
        this.isLocked = false;
    }
}
