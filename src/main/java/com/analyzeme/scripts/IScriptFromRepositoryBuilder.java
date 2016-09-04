package com.analyzeme.scripts;

import com.analyzeme.r.facade.TypeOfReturnValue;

public class IScriptFromRepositoryBuilder {
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

    public IScriptFromRepositoryBuilder name(
            final String name) {
        this.name = name;
        return this;
    }

    public IScriptFromRepositoryBuilder inputType(
            final InputType inputType) {
        this.inputType = inputType;
        return this;
    }

    public IScriptFromRepositoryBuilder numberOfParams(
            final int n) {
        this.numberOfParams = n;
        return this;
    }

    public IScriptFromRepositoryBuilder returnValue(
            final TypeOfReturnValue typeOfReturnValue) {
        this.typeOfReturnValue = typeOfReturnValue;
        return this;
    }

    public IScriptFromRepositoryBuilder setId(
            final String id) throws Exception {
        this.id = id;
        return this;
    }
}
