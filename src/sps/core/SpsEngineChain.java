package sps.core;

import java.util.LinkedList;

public class SpsEngineChain {
    private LinkedList<SpsEngineChainLink> _links;

    public SpsEngineChain() {
        _links = new LinkedList<>();
    }

    public void add(SpsEngineChainLink link) {
        _links.add(link);
    }

    public void update() {
        _links.peek().update();
    }

    public void draw() {
        _links.peek().draw();
    }

    public boolean isLinkAvailable() {
        if(_links.peek() == null || _links.peek().isFinished()) {
            _links.pop();
            return false;
        }
        return true;
    }
}
