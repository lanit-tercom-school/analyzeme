package com.analyzeme.scripts;

import com.analyzeme.r.facade.TypeOfReturnValue;

public class IScriptFromBoxBuilder {

    private String name;
    private String id;
    private int numberOfParams;
    private InputType inputType;
    private TypeOfReturnValue typeOfReturnValue;

    public Script build() {
        return new Script(
                name, id, inputType,
                numberOfParams, typeOfReturnValue);
    }

    public IScriptFromBoxBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public IScriptFromBoxBuilder numberOfParams(
            final int n) {
        this.numberOfParams = n;
        return this;
    }

    public IScriptFromBoxBuilder inputType(
            final InputType inputType) {
        this.inputType = inputType;
        return this;
    }

    public IScriptFromBoxBuilder returnValue(
            final TypeOfReturnValue typeOfReturnValue) {
        this.typeOfReturnValue = typeOfReturnValue;
        return this;
    }

    public IScriptFromBoxBuilder setId(
            final String id) throws Exception {
        this.id = id;
        return this;
    }

    public IScriptFromBoxBuilder uploadScriptWithMeta(
            final String text) throws Exception {
         return FormattedScriptUploader.upload(
                 text, name);
    }

}
