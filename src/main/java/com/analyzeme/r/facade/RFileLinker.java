package com.analyzeme.r.facade;

import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.data.resolvers.DataSetResolversFactory;
import com.analyzeme.data.resolvers.IDataSetResolver;
import com.analyzeme.data.resolvers.FileInRepositoryResolver;
import com.analyzeme.streamreader.StreamToString;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: unit-tests
class RFileLinker {
    private final static String REGEXP =
            "([A-Za-z]+)_from__([A-Za-z]+)__([A-Za-z0-9,_.]+)__";
    private final static int FIELD_GROUP = 1;
    private final static int SOURCE_GROUP = 2;
    private final static int FILE_GROUP = 3;

    private static <T> T findInList(List<T> list, T obj) {
        for (T item : list) {
            if (item.equals(obj))
                return item;
        }
        return null;
    }

    private static ArrayList<DataSet> formList(Matcher m,
                                               FileInRepositoryResolver resolver) throws Exception {
        ArrayList<DataSet> result = new ArrayList<DataSet>();
        while (m.find()) {
            IDataSetResolver res;
            if (m.group(SOURCE_GROUP).equalsIgnoreCase("repo")) {
                res = resolver;
            } else {
                res = DataSetResolversFactory.getDataSetResolver(null);
            }
            DataSet data = res.getDataSet(m.group(FILE_GROUP));
            DataSet found = findInList(result, data);
            if (found == null) {
                data.addField(m.group(FIELD_GROUP));
                result.add(data);
            } else {
                found.addField(m.group(FIELD_GROUP));
            }
        }
        return result;
    }

    /**
     * finds out what files  are necessary for the script to run
     *
     * @param rScript - script to call, correct .r file as a stream
     * @return DataSets of necessary files
     * @throws Exception if there were some mistakes in parsing or there are no necessary files
     */
    public static ArrayList<DataSet> parse(ByteArrayInputStream rScript,
                                           FileInRepositoryResolver resolver) throws Exception {
        if (rScript == null || resolver == null) {
            throw new IllegalArgumentException();
        }
        Pattern pattern = Pattern.compile(REGEXP);
        Matcher m = pattern.matcher(StreamToString.convertStream(rScript));
        return formList(m, resolver);
    }


    /**
     * finds out what files are necessary for the command to run
     *
     * @param rCommand = correct r command to parse
     * @return DataSets of necessary files
     * @throws Exception if there were some mistakes in parsing or there are no necessary files
     */
    public static ArrayList<DataSet> parse(final String rCommand,
                                           FileInRepositoryResolver resolver) throws Exception {
        if (rCommand == null || rCommand.equals("") || resolver == null) {
            throw new IllegalArgumentException();
        }
        Pattern pattern = Pattern.compile(REGEXP);
        Matcher m = pattern.matcher(rCommand);
        return formList(m, resolver);
    }

    /**
     * finds out what files are necessary for the command to run
     *
     * @param rCommand = correct r command to parse
     * @return names of files
     * @throws Exception if there were some mistakes in parsing or there are no necessary files
     */
    public static ArrayList<String> parseForTests(final String rCommand) throws Exception {
        if (rCommand == null || rCommand.equals("")) {
            throw new IllegalArgumentException();
        }
        ArrayList<String> result = new ArrayList<String>();

        Pattern pattern = Pattern.compile(REGEXP);
        Matcher m = pattern.matcher(rCommand);
        while (m.find()) {
            result.add(m.group(FILE_GROUP));
        }
        return result;
    }
}
