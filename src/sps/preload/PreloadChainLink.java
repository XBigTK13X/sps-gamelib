package sps.preload;

public abstract class PreloadChainLink {
    private String _loadingMessage;
    private int _repetitions;

    public PreloadChainLink() {
        this("", 1);
    }

    public PreloadChainLink(String message) {
        this(message, 1);
    }

    public PreloadChainLink(String message, int repetitions) {
        _loadingMessage = message;
        _repetitions = repetitions;
    }

    public String getMessage() {
        return _loadingMessage;
    }

    public abstract void process();

    public int getRepetitions() {
        return _repetitions;
    }

    public void useLink() {
        _repetitions--;
    }

    public boolean allLinksRun() {
        return _repetitions == 0;
    }
}
