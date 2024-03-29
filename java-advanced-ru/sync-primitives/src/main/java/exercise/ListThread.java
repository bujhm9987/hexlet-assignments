package exercise;

// BEGIN
public class ListThread extends Thread {
    SafetyList safetyList;

    ListThread(SafetyList safetyList) {
        this.safetyList = safetyList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            safetyList.add(i);
            try {
                sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
};
// END
