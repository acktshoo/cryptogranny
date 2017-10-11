package com.example.anna.cryptogranny;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

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

public class CryptogrannyUnitTest {
    @Test
    public void noGuesses_isCorrect() throws Exception {
        // base getPuzzleState with no guesses
        String puzzle = "XYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        assertEquals("___", puzzleState.getCurSol());
        assertEquals(puzzle, cryptogranny.getPuzzle());
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", puzzleState.getPool());
        assertEquals(26, puzzleState.getPool().length());
    }

    @Test
    public void oneGuess_isCorrect() throws Exception {
        String puzzle = "XYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Y', 'A');
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        assertEquals("_A_", puzzleState.getCurSol());
        assertEquals(puzzle, cryptogranny.getPuzzle());
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXZ", puzzleState.getPool());
        assertEquals(25, puzzleState.getPool().length());
    }

    @Test
    public void oneGuessSame_isCorrect() throws Exception {
        String puzzle = "XYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Y', 'Y');
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        assertEquals("_Y_", puzzleState.getCurSol());
        assertEquals(puzzle, cryptogranny.getPuzzle());
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXZ", puzzleState.getPool());
        assertEquals(25, puzzleState.getPool().length());
    }

    @Test
    public void oneGuessWithTwoOccurrences_isCorrect() throws Exception {
        String puzzle = "XYZY";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Y', 'R');
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        assertEquals("_R_R", puzzleState.getCurSol());
        assertEquals(puzzle, cryptogranny.getPuzzle());
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXZ", puzzleState.getPool());
        assertEquals(25, puzzleState.getPool().length());
    }

    @Test
    public void secondGuess_isCorrect() throws Exception {
        String puzzle = "XYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Z', 'B');
        cryptogranny.guess('Y', 'A');
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        assertEquals("_AB", puzzleState.getCurSol());
        assertEquals(puzzle, cryptogranny.getPuzzle());
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWX", puzzleState.getPool());
        assertEquals(24, puzzleState.getPool().length());
    }

    @Test
    public void secondGuessWithTwoOccurrences_isCorrect() throws Exception {
        String puzzle = "XYZY";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Z', 'B');
        cryptogranny.guess('Y', 'A');
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        assertEquals("_ABA", puzzleState.getCurSol());
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWX", puzzleState.getPool());
        assertEquals(24, puzzleState.getPool().length());
    }

    @Test
    public void clearGuess_isCorrect() throws Exception {
        String puzzle = "XYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Y', 'A');
        cryptogranny.clearM('Y');
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        assertEquals("___", puzzleState.getCurSol());
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", puzzleState.getPool());
        assertEquals(26, puzzleState.getPool().length());
    }

    @Test
    public void replaceGuess_isCorrect() throws Exception {
        String puzzle = "XYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Z', 'B');
        cryptogranny.guess('Y', 'B');
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        assertEquals("_B_", puzzleState.getCurSol());
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXZ", puzzleState.getPool());
        assertEquals(25, puzzleState.getPool().length());
    }
    @Test
    public void replaceGuessWithTwoOccurrences_isCorrect() throws Exception {
        String puzzle = "XYZYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Z', 'B');
        cryptogranny.guess('Y', 'B');
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        assertEquals("_B_B_", puzzleState.getCurSol());
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXZ", puzzleState.getPool());
        assertEquals(25, puzzleState.getPool().length());
    }

    @Test
    public void puzzleWithSpace_isCorrect() throws Exception {
        String puzzle = "XYZ YX";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Y', 'A');
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        assertEquals("_A_ A_", puzzleState.getCurSol());
        assertEquals(puzzle, cryptogranny.getPuzzle());
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXZ", puzzleState.getPool());
        assertEquals(25, puzzleState.getPool().length());
    }
}