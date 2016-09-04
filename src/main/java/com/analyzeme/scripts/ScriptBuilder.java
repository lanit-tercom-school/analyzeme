package com.analyzeme.scripts;

import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.repository.filerepository.FileRepository;

/**
 * Created by lagroffe on 27.07.2016 11:11
 */
public class ScriptBuilder{

    public IScriptFromBoxBuilder fromBox() {
        return new IScriptFromBoxBuilder();
    }

    public IScriptFromPartsBuilder fromParts() {
        return new IScriptFromPartsBuilder();
    }

    public IScriptFromRepositoryBuilder fromRepo() {
        return new IScriptFromRepositoryBuilder();
    }
}
