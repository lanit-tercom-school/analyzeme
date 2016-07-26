package com.analyzeme.scripts;

import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.repository.filerepository.FileRepository;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;

public class Script {
    private String name;
    private final String id;
    private final int numberOfParams;
    private final TypeOfReturnValue typeOfReturnValue;
    private final ScriptSource scriptSource;
    private final String scriptText;

    public Script(final String name, final String id,
                  final int numberOfParams,
                  final TypeOfReturnValue typeOfReturnValue,
                  final ScriptSource scriptSource,
                  final String scriptText, InputType inputType) {
        if (name == null || typeOfReturnValue == null) {
            throw new IllegalArgumentException("Null argument");
        }
        if(scriptSource == ScriptSource.LIBRARY && scriptText == null
                || (scriptSource == ScriptSource.FILE_REPOSITORY
                        || scriptSource == ScriptSource.DISK_DEFAULT)
                        && id == null) {
            throw new IllegalArgumentException("Illegal combination of values");
        }
        this.name = name;
        this.id = id;
        this.numberOfParams = numberOfParams;
        this.typeOfReturnValue = typeOfReturnValue;
        this.scriptSource = scriptSource;
        this.scriptText = scriptText;
    }

    public ByteArrayInputStream getScriptStream() throws Exception {
        if(scriptSource == ScriptSource.LIBRARY) {
            return new ByteArrayInputStream(scriptText.getBytes());
        }
        return FileRepository.getRepo().getFileByID(this.id);
    }

    public String getScript() throws Exception {
        if(scriptSource == ScriptSource.LIBRARY) {
            return scriptText;
        }
        return IOUtils.toString(FileRepository.getRepo().getFileByID(this.id));
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
