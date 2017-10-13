package com.example.anna.cryptogranny;

import android.support.annotation.NonNull;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by anna on 9/23/17.
 */

// mapping = {}
// puzzle = "str"
// guess( N -> M ):
//    clean( M )
//    if M:
//       mapping[ N ] = M
// clean( M ):
//    N = mapping.keyof( M )
//    if N:
//       mapping.del( N )
// getPuzzleState():
//   curSol = mapping[ i ] if i in mapping else " " for i in str;
//   pool = [ A .. Z ] - mapping.keys()
//   return curSol, puzzle, pool

class Cryptogranny {
    private final String puzzle;
    private ConcurrentHashMap<Character, Character> mapping = new ConcurrentHashMap<>();

    public Cryptogranny(String puzzle) {
        this.puzzle = puzzle;
    }

    public PuzzleState getPuzzleState() {
        char[] curSol = new char[puzzle.length()];
        for (int i = 0; i < puzzle.length(); i++) {
            char m = puzzle.charAt(i);
            char n;
            if (m == ' ') {
                n = m;
            } else if (mapping.containsKey(m)) {
                n = mapping.get(m);
            } else {
                n = '_';
            }
            curSol[i] = n;
        }

        StringBuilder pool = new StringBuilder();
        for (Character m = 'A'; m <= 'Z'; m++) {
            if (!mapping.containsKey(m)) {
                pool.append(m);
            }
        }

        return new PuzzleState(String.valueOf(curSol), pool.toString());
    }

    public String getPuzzle() { return puzzle; }

    public boolean isNUsed(Character n){
        return mapping.containsValue(n);
    }

    public void guess(@NonNull Character fromM, @NonNull Character toN) {
        clearN(toN);
        this.mapping.put(fromM, toN);
    }

    private Character findM(@NonNull Character n) {
        for (Character m : mapping.keySet()) {
            if (mapping.get(m).equals(n)) return m;
        }
        return null;
    }

    public void clearN(@NonNull Character n) {
        Character m = findM(n);
        if (m != null) mapping.remove(m);
    }

    public void clearM(@NonNull Character m){
        mapping.remove(m);
    }

    public void resetPuzzle() {
        mapping.clear();
    }

    public class PuzzleState {
        private final String pool;
        private final String curSol;

        public PuzzleState(String curSol, String pool) {
            this.curSol = curSol;
            this.pool = pool;
        }

        public String getCurSol() {
            return curSol;
        }
        public String getPool() {
            return pool;
        }
    }
}
