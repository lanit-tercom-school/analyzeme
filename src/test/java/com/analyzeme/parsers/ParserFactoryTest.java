package com.analyzeme.parsers;

import com.analyzeme.repository.filerepository.TypeOfFile;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ilya on 7/7/16.
 */
public class ParserFactoryTest {
    @Test
    public void testJsonParserCreation() {
        Assert.assertEquals(JsonParser.class, ParserFactory.createParser(TypeOfFile.SIMPLE_JSON).getClass());
    }

    @Test
    public void testCsvParserCreation() {
        Assert.assertEquals(CsvParser.class, ParserFactory.createParser(TypeOfFile.CSV).getClass());
    }

    @Test
    public void testExcelParserCreation() {
        Assert.assertEquals(ExcelParser.class, ParserFactory.createParser(TypeOfFile.EXCEL).getClass());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testWrongParam() {
       ParserFactory.createParser(TypeOfFile.SCRIPT);
    }
}
