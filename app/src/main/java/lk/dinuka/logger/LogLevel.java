package lk.dinuka.logger;

/**
 * Created by deuke on 12/1/17.
 */

public enum LogLevel {
    DEBUG(1),
    INFO(2),
    ERROR(3);

    private final int level;

    private LogLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
