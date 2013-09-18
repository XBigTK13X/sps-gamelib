package sps.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import sps.core.FileHelper;
import sps.core.Logger;
import sps.core.RNG;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Based on: https://gist.github.com/veryphatic/3190969

public class Markov {
    private static final String __startId = "$$start$$";

    public static void main(String[] args) throws IOException {
        FileHandle corpus = Gdx.files.internal("data/creature_name_seed.txt");
        for (int ii = 0; ii < 100000; ii++) {
            int gramSize = 2;
            int wordLength = RNG.next(6, 10);
            Markov markov = Markov.get(corpus, gramSize);
            Logger.info("WORD: " + markov.makeWord(wordLength) + ", " + gramSize + ", " + wordLength);
        }
    }

    private static Map<FileHandle, Map<Integer, Markov>> __cachedChains = new HashMap<>();

    public static Markov get(FileHandle seedText, int gramSize) {
        if (!__cachedChains.containsKey(seedText)) {
            __cachedChains.put(seedText, new HashMap<Integer, Markov>());
        }
        if (!__cachedChains.get(seedText).containsKey(gramSize)) {
            __cachedChains.get(seedText).put(gramSize, new Markov(seedText, gramSize));
        }
        return __cachedChains.get(seedText).get(gramSize);
    }


    private Map<String, ArrayList<String>> _chain = new HashMap<String, ArrayList<String>>();

    private Markov(FileHandle seedText, int gramSize) {
        if (_chain.keySet().size() == 0) {
            _chain.put(__startId, new ArrayList<String>());
        }
        String[] words = FileHelper.readLines(seedText);
        for (String word : words) {
            addWord(word, gramSize);
        }
    }

    private void addWord(String word, int order) {
        if (order < 1) {
            throw new RuntimeException("order must be larger than 0");
        }

        if (word.length() == 0 || word.length() < order * 2) {
            return;
        }

        word = word.toLowerCase();

        String[] nGrams = new String[(int) Math.ceil(word.length() / (double) order)];
        for (int ii = 0; ii < word.length(); ii += order) {
            int index = (int) Math.ceil(ii / order);
            nGrams[index] = "";
            for (int jj = 0; jj < order && jj + index * order < word.length(); jj++) {
                nGrams[index] += word.charAt(jj + index * order);
            }
        }
        ArrayList<String> startWords = _chain.get(__startId);
        if (!startWords.contains(nGrams[0])) {
            startWords.add(nGrams[0]);
        }

        if (_chain.get(nGrams[0]) == null) {
            _chain.put(nGrams[0], new ArrayList<String>());
        }
        boolean linksFound = false;
        for (int ii = 0; ii < nGrams.length - 1; ii++) {
            ArrayList<String> suffix = _chain.get(nGrams[ii]);
            if (suffix == null) {
                suffix = new ArrayList<>();
            }
            if (!nGrams[ii].equals(nGrams[ii + 1])) {
                suffix.add(nGrams[ii + 1]);
                _chain.put(nGrams[ii], suffix);
                linksFound = true;

            }
        }

        if (!linksFound) {
            _chain.get(__startId).remove(nGrams[0]);
        }
    }

    public String makeWord(int length) {
        String result = "";

        ArrayList<String> startWords = _chain.get(__startId);
        String nextGram = startWords.get(RNG.next(startWords.size()));
        result += nextGram;
        while (result.length() < length) {
            ArrayList<String> wordSelection = _chain.get(nextGram);
            if (wordSelection == null) {
                wordSelection = startWords;
            }

            nextGram = wordSelection.get(RNG.next(wordSelection.size()));
            result += nextGram;
        }

        return result;
    }
}