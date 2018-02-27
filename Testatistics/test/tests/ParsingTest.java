/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.io.File;
import java.util.TreeMap;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utility.TemplateParser;
import utility.AnswerCardParser;
import utility.ResultsStatistics;

/**
 *
 * @author buczak
 */
public class ParsingTest {
    
    TemplateParser tp = new TemplateParser();
    AnswerCardParser ap = new AnswerCardParser();
    ResultsStatistics stats = new ResultsStatistics();
    
    public ParsingTest() {
    }
    
    @Test
    public void testParsingTemplate() {
        File f = new File("test/testTemplate.txt");
        TreeMap<String, String> map = tp.generateAnswerPattern(f);
        assertEquals(map.get("1"), "A");
        assertEquals(map.get("2"), "B");
        assertEquals(map.get("3"), "C");
        assertEquals(map.get("4"), "D");
    }
    
    @Test
    public void testParsingCards() {
        Vector<TreeMap<String, String>> vec = ap.generateAnswerCardCollection("test/testCards.csv");
        assertEquals(vec.get(0).get("0"), "100000");
        assertEquals(vec.get(0).get("1"), "A");
        assertEquals(vec.get(0).get("2"), "B");
        assertEquals(vec.get(0).get("3"), "C");
        assertEquals(vec.get(0).get("4"), "D");
        assertEquals(vec.get(1).get("0"), "100001");
        assertEquals(vec.get(1).get("1"), "A");
        assertEquals(vec.get(1).get("2"), "C");
        assertEquals(vec.get(1).get("3"), "C");
        assertEquals(vec.get(1).get("4"), "D");
    }
    
    @Test
    public void testStatisticsBuilder() {
        File f = new File("test/testTemplate.txt");
        TreeMap<String, String> map = tp.generateAnswerPattern(f);
        Vector<TreeMap<String, String>> vec = ap.generateAnswerCardCollection("test/testCards.csv");
        TreeMap<Integer, Integer> rh = stats.createResultsHistogram(map, vec);
        assertEquals((long)(rh.get(4)), 1);
        assertEquals((long)(rh.get(3)), 1);
        TreeMap<Float, Integer> mh = stats.createMarksHistogram(map, vec);
        assertEquals((long)(mh.get(5.5f)), 1);
        assertEquals((long)(mh.get(4.0f)), 1);
        TreeMap<Integer, Integer> gah = stats.createGoodAnswersHistogram(map, vec);
        assertEquals((long)(gah.get(1)), 2);
        assertEquals((long)(gah.get(2)), 1);
        assertEquals((long)(gah.get(3)), 2);
        assertEquals((long)(gah.get(4)), 2);
    }
    
}

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

