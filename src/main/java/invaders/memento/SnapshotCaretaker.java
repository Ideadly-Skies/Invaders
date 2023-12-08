package invaders.memento;

/**
 * LastSnapshot caretaker. Only stores one instance of the LastSnapshot.
 */
public class SnapshotCaretaker {
    private LastSnapshot snapshot;

    public void saveSnapshot(LastSnapshot snapshot){
        this.snapshot = snapshot;
    }

    public LastSnapshot retrieveSnapshot(){
        return this.snapshot;
    }

}
