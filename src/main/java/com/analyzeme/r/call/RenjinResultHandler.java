package com.analyzeme.r.call;

import org.renjin.primitives.matrix.Matrix;
import org.renjin.sexp.AtomicVector;
import org.renjin.sexp.ListVector;
import org.renjin.sexp.SEXP;
import org.renjin.sexp.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenjinResultHandler {

    enum TypeOfResult {
        LOGICAL,
        INTEGER,
        DOUBLE,
        CHARACTER,
        COMPLEX,
        RAW,
        LIST,
        NULL,
        FUNCTION,
        SPECIAL,
        ENVIRONMENT,
        S4
    }

    private static TypeOfResult getTypeOfResult(final SEXP result) throws Exception {
        String type = result.getTypeName();
        if (type.equalsIgnoreCase("logical")) {
            return TypeOfResult.LOGICAL;
        }
        if (type.equalsIgnoreCase("integer")) {
            return TypeOfResult.INTEGER;
        }
        if (type.equalsIgnoreCase("double")) {
            return TypeOfResult.DOUBLE;
        }
        if (type.equalsIgnoreCase("character")) {
            return TypeOfResult.CHARACTER;
        }
        if (type.equalsIgnoreCase("complex")) {
            return TypeOfResult.COMPLEX;
        }
        if (type.equalsIgnoreCase("raw")) {
            return TypeOfResult.RAW;
        }
        if (type.equalsIgnoreCase("list")) {
            return TypeOfResult.LIST;
        }
        if (type.equalsIgnoreCase("NULL")) {
            return TypeOfResult.NULL;
        }
        if (type.equalsIgnoreCase("closure")) {
            return TypeOfResult.FUNCTION;
        }
        if (type.equalsIgnoreCase("special") || type.equalsIgnoreCase("builtin")) {
            return TypeOfResult.SPECIAL;
        }
        if (type.equalsIgnoreCase("environment")) {
            return TypeOfResult.ENVIRONMENT;
        }
        if (type.equalsIgnoreCase("S4")) {
            return TypeOfResult.S4;
        }
        throw new IllegalArgumentException("getTypeOfResult: not supported");
    }

    private static boolean isVector(final TypeOfResult type) {
        return type.equals(TypeOfResult.LOGICAL) || type.equals(TypeOfResult.INTEGER) || type.equals(TypeOfResult.DOUBLE) || type.equals(TypeOfResult.CHARACTER) || type.equals(TypeOfResult.COMPLEX) || type.equals(TypeOfResult.RAW);
    }

    private static boolean isDataFrame(final TypeOfResult type) {
        return type.equals(TypeOfResult.LIST);
    }

    private static boolean isNull(final TypeOfResult type) {
        return type.equals(TypeOfResult.NULL);
    }

    private static boolean isNotSupported(final TypeOfResult type) {
        return type.equals(TypeOfResult.FUNCTION) || type.equals(TypeOfResult.SPECIAL) || type.equals(TypeOfResult.ENVIRONMENT) || type.equals(TypeOfResult.S4);
    }

    private static void checkResult(final TypeOfResult type) throws Exception {
        if (type == null) {
            throw new IllegalArgumentException("Renjin resultHandler: null type");
        }
        if (isNull(type)) {
            throw new IllegalArgumentException("Renjin resultHandler: null result, impossible to handle");
        }
        if (isNotSupported(type)) {
            throw new IllegalArgumentException("Renjin resultHandler: not supported type of result");
        }
    }

    public static Map<String, List<Double>> resultToFile(final SEXP result) throws Exception {
        if (result == null) {
            throw new IllegalArgumentException("Renjin resultHandler: null argument, impossible to proceed");
        }
        TypeOfResult type = getTypeOfResult(result);
        checkResult(type);
        if (isDataFrame(type)) {
            return renjinDataFrameToFile(result);
        } else if (isVector(type)) {
            return renjinMatrixToFile(result);
        }
        throw new IllegalArgumentException("Renjin resultHandler: impossible to handle this result");
    }

    static Map<String, List<Double>> renjinDataFrameToFile(final SEXP result) throws Exception {
        if (result == null) {
            throw new IllegalArgumentException("Renjin to get FileResult: impossible to evaluate; cause: null result");
        }
        ListVector res = (ListVector) result;
        if (res.hasAttributes()) {
            AtomicVector names = res.getNames();
            //TODO: refactor not for double only
            Map<String, List<Double>> toReturn = new HashMap<>();
            for (int i = 0; i < names.length(); i++) {
                Vector tempVector = res.getElementAsVector(names.getElementAsString(i));
                List<Double> tempList = new ArrayList<>();
                for (int j = 0; j < tempVector.length(); j++) {
                    tempList.add(tempVector.getElementAsDouble(j));
                }
                toReturn.put(names.getElementAsString(i), tempList);
            }
            return toReturn;
        }
        return null;
    }

    static Map<String, List<Double>> renjinMatrixToFile(final SEXP result) throws Exception {
        if (result == null || !result.hasAttributes() || result.getAttributes().getDim() == null) {
            throw new IllegalArgumentException("Renjin to get FileResult: impossible to evaluate; cause: null result");
        }
        Matrix m = new Matrix((Vector) result);
        //TODO: refactor to work not only with double
        List<List<Double>> resultTemp = new ArrayList<List<Double>>();
        for (int i = 0; i < m.getNumCols(); i++) {
            resultTemp.add(new ArrayList<Double>());
        }
        for (int i = 0; i < m.getNumRows(); i++) {
            for (int j = 0; j < m.getNumCols(); j++) {
                resultTemp.get(j).add(m.getElementAsDouble(i, j));
            }
        }
        Map<String, List<Double>> r = new HashMap<String, List<Double>>();
        Vector colNames = m.getColNames();
        if (colNames == null || colNames.length() == 0) {
            for (int i = 0; i < resultTemp.size(); i++) {
                r.put("col" + (int) i, resultTemp.get(i));
            }
        } else {
            for (int i = 0; i < resultTemp.size(); i++) {
                r.put(colNames.getElementAsString(i), resultTemp.get(i));
            }
        }
        return r;
    }


    static List renjinNotNamedVectorToList(final SEXP v) {
        if (v == null) {
            throw new IllegalArgumentException("Renjin to get ColumnResult: impossible to evaluate; cause: null result");
        }
        List<Double> result = new ArrayList<Double>();
        for (int i = 0; i < v.length(); i++) {
            //TODO: refactor not for double only
            result.add(((Vector) v).getElementAsDouble(i));
        }
        return result;
    }
}
