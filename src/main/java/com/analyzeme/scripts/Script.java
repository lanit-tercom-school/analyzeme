package com.analyzeme.scripts;

import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.repository.filerepository.FileRepository;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;

public class Script {
    private String name;
    private final String id;
    private final InputType inputType;
    private final int numberOfParams;
    private final TypeOfReturnValue typeOfReturnValue;

    Script(final String name, final String id,
                  InputType inputType, final int numberOfParams,
                  final TypeOfReturnValue typeOfReturnValue) {
        if (name == null || typeOfReturnValue == null
                || id == null || id.isEmpty()
                || inputType == null) {
            throw new IllegalArgumentException("Null argument");
        }
        this.name = name;
        this.id = id;
        this.numberOfParams = numberOfParams;
        this.inputType = inputType;
        this.typeOfReturnValue = typeOfReturnValue;
    }

    public static ScriptBuilder builder() {
        return new ScriptBuilder();
    }

    public ByteArrayInputStream getScriptStream() throws Exception {
        return FileRepository.getRepo()
                .getFileByID(this.id);
    }

    public String getScript() throws Exception {
        return IOUtils.toString(
                FileRepository.getRepo()
                        .getFileByID(this.id));
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public int getNumberOfParams() {
        return this.numberOfParams;
    }

    public InputType getInputType() {
        return this.inputType;
    }

    public TypeOfReturnValue getTypeOfReturnValue() {
        return this.typeOfReturnValue;
    }
}
