package com.analyzeme.scripts;

import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.repository.filerepository.FileRepository;

public class IScriptFromPartsBuilder {
    private String name;
    private String id;
    private int numberOfParams;
    private InputType inputType;
    private TypeOfReturnValue typeOfReturnValue;

    public Script build() {
        return new Script(name, id, inputType,
                numberOfParams, typeOfReturnValue);
    }

    public IScriptFromPartsBuilder name(
            final String name) {
        this.name = name;
        return this;
    }

    public IScriptFromPartsBuilder numberOfParams(
            final int n) {
        this.numberOfParams = n;
        return this;
    }

    public IScriptFromPartsBuilder inputType(
            final InputType inputType) {
        this.inputType = inputType;
        return this;
    }

    public IScriptFromPartsBuilder returnValue(
            final TypeOfReturnValue typeOfReturnValue) {
        this.typeOfReturnValue = typeOfReturnValue;
        return this;
    }

    public IScriptFromPartsBuilder uploadText(
            final String text) throws Exception {
        if (this.name == null) {
            throw new IllegalArgumentException(
                    "uploadText(): set name before uploading");
        }
        this.id = FileRepository.getRepo()
                .persist(text, this.name);
        return this;
    }
}
