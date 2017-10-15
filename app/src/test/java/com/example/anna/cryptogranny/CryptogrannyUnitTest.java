package com.example.anna.cryptogranny;

import org.junit.Test;

import java.util.ArrayList;

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
// getCurSol():
//   curSol = mapping[ i ] if i in mapping else " " for i in str;
//   pool = [ A .. Z ] - mapping.keys()
//   return curSol, puzzle, pool

public class CryptogrannyUnitTest {
    @Test
    public void noGuesses_isCorrect() throws Exception {
        // base getCurSol with no guesses
        String puzzle = "XYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        assertEquals("___", cryptogranny.getCurSol());
        assertEquals(puzzle, cryptogranny.getPuzzle());
        assertFalse( cryptogranny.isNUsed('A') );
    }

    @Test
    public void oneGuess_isCorrect() throws Exception {
        String puzzle = "XYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Y', 'A');

        assertEquals("_A_", cryptogranny.getCurSol());
        assertEquals(puzzle, cryptogranny.getPuzzle());
        assertTrue( cryptogranny.isNUsed('A'));
        assertFalse( cryptogranny.isNUsed('Y') );
    }

    @Test
    public void oneGuessSame_isCorrect() throws Exception {
        String puzzle = "XYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Y', 'Y');
       
        assertEquals("_Y_", cryptogranny.getCurSol());
        assertEquals(puzzle, cryptogranny.getPuzzle());
        assertTrue( cryptogranny.isNUsed('Y'));
    }

    @Test
    public void oneGuessWithTwoOccurrences_isCorrect() throws Exception {
        String puzzle = "XYZY";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Y', 'R');
       
        assertEquals("_R_R", cryptogranny.getCurSol());
        assertEquals(puzzle, cryptogranny.getPuzzle());
        assertTrue( cryptogranny.isNUsed('R'));
    }

    @Test
    public void secondGuess_isCorrect() throws Exception {
        String puzzle = "XYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Z', 'B');
        cryptogranny.guess('Y', 'A');
       
        assertEquals("_AB", cryptogranny.getCurSol());
        assertEquals(puzzle, cryptogranny.getPuzzle());
        assertTrue( cryptogranny.isNUsed('A'));
        assertTrue( cryptogranny.isNUsed('B'));
    }

    @Test
    public void secondGuessWithTwoOccurrences_isCorrect() throws Exception {
        String puzzle = "XYZY";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Z', 'B');
        cryptogranny.guess('Y', 'A');
       
        assertEquals("_ABA", cryptogranny.getCurSol());
        assertTrue( cryptogranny.isNUsed('A'));
        assertTrue( cryptogranny.isNUsed('B'));
    }

    @Test
    public void clearGuess_isCorrect() throws Exception {
        String puzzle = "XYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Y', 'A');
        cryptogranny.clearM('Y');
       
        assertEquals("___", cryptogranny.getCurSol());
        assertFalse( cryptogranny.isNUsed('A'));
    }

    @Test
    public void replaceGuess_isCorrect() throws Exception {
        String puzzle = "XYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Z', 'B');
        cryptogranny.guess('Y', 'B');
       
        assertEquals("_B_", cryptogranny.getCurSol());
        assertTrue( cryptogranny.isNUsed('B'));
    }
    @Test
    public void replaceGuessWithTwoOccurrences_isCorrect() throws Exception {
        String puzzle = "XYZYZ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Z', 'B');
        cryptogranny.guess('Y', 'B');
       
        assertEquals("_B_B_", cryptogranny.getCurSol());
        assertTrue( cryptogranny.isNUsed('B') );
    }

    @Test
    public void puzzleWithSpace_isCorrect() throws Exception {
        String puzzle = "XYZ YX";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        cryptogranny.guess('Y', 'A');
       
        assertEquals("_A_ A_", cryptogranny.getCurSol());
        assertEquals(puzzle, cryptogranny.getPuzzle());
        assertTrue( cryptogranny.isNUsed('A'));
    }

    @Test
    public void puzzleWords_isCorrect() throws Exception{
        String puzzle = "XYZ YX";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        ArrayList<String> puzzleWords = cryptogranny.getPuzzleWords();
        assertEquals(2, puzzleWords.size());
        assertEquals("XYZ", puzzleWords.get(0));
        assertEquals("YX", puzzleWords.get(1));
    }

    @Test
    public void puzzleWordsWithTrailingSpaces_isCorrect() throws Exception{
        String puzzle = "  XYZ  YX   ";
        Cryptogranny cryptogranny = new Cryptogranny(puzzle);
        ArrayList<String> puzzleWords = cryptogranny.getPuzzleWords();
        assertEquals(2, puzzleWords.size());
        assertEquals("XYZ", puzzleWords.get(0));
        assertEquals("YX", puzzleWords.get(1));
    }
}