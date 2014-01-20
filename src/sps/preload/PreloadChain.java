package sps.preload;

import sps.color.Color;
import sps.color.Colors;
import sps.display.Screen;
import sps.text.Text;
import sps.text.TextPool;
import sps.ui.Meter;
import sps.ui.MultiText;

import java.text.NumberFormat;
import java.util.LinkedList;

public abstract class PreloadChain {
    private LinkedList<PreloadChainLink> _preloadChain;
    private boolean processStep = false;
    private int _preloadedItemsTarget = 0;
    private int _preloadedItems = 0;
    private MultiText _loadingMessage;
    private Text _percentDisplay;
    private Meter _loadingMeter;
    private boolean _showGUI;
    private boolean _finished;

    public PreloadChain(boolean showGUI) {
        _showGUI = showGUI;
        _preloadChain = new LinkedList<>();
        if (showGUI) {
            _percentDisplay = TextPool.get().write("", Screen.pos(40, 20));
            _loadingMessage = new MultiText(Screen.pos(10, 50), 6, Color.GRAY.newAlpha(.5f), (int) Screen.width(80), (int) Screen.height(20));
            _loadingMeter = new Meter(90, 9, Colors.randomPleasant(), Screen.pos(5, 30), false);
        }
    }

    public PreloadChain() {
        this(true);
    }

    public void add(PreloadChainLink link) {
        _preloadChain.add(link);
        _preloadedItemsTarget += link.getRepetitions();
    }

    private String _lastMessage;

    public void update() {
        PreloadChainLink link = _preloadChain.peek();
        if (!processStep) {
            if (link == null) {
                _finished = true;
                finish();
            }
            else {
                if (_showGUI) {
                    _loadingMeter.setPercent((int) ((_preloadedItems / (float) _preloadedItemsTarget) * 100));
                    if (_lastMessage == null || !_lastMessage.equals(link.getMessage())) {
                        _loadingMessage.add(link.getMessage());
                        _lastMessage = link.getMessage();
                    }
                    _percentDisplay.setMessage(getProgress());
                }
            }
        }
        else {
            link.process();
            link.useLink();
            if (link.allLinksRun()) {
                _preloadChain.pop();
            }
            _preloadedItems++;
        }
        processStep = !processStep;
    }

    private static final NumberFormat _df = NumberFormat.getPercentInstance();

    private String getProgress() {
        return _df.format(((float) _preloadedItems / _preloadedItemsTarget)) + " complete";
    }

    public void draw() {
        if (_showGUI) {
            _loadingMeter.draw();
            _loadingMessage.draw();
        }
    }

    public boolean isFinished() {
        return _finished;
    }

    public abstract void finish();
}
