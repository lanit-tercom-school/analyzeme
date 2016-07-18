package com.analyzeme.scripts;

import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.repository.filerepository.FileRepository;

import java.io.ByteArrayInputStream;

public class Script {
    private String name;
    private final String id;
    private final int numberOfParams;
    private final TypeOfReturnValue typeOfReturnValue;
    private final ScriptSource scriptSource;

    public Script(final String name, final String id,
                  final int numberOfParams,
                  final TypeOfReturnValue typeOfReturnValue,
                  final ScriptSource scriptSource) {
        this.name = name;
        this.id = id;
        this.numberOfParams = numberOfParams;
        this.typeOfReturnValue = typeOfReturnValue;
        this.scriptSource = scriptSource;
    }

    public ByteArrayInputStream getScript() throws Exception {
        return FileRepository.getRepo().getFileByID(this.id);
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

    public TypeOfReturnValue getTypeOfReturnValue() {
        return this.typeOfReturnValue;
    }

    public ScriptSource getScriptSource() {
        return this.scriptSource;
    }
}
