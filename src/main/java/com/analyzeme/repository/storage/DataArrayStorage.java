package com.analyzeme.repository.storage;


import com.analyzeme.data.dataWithType.DataWithTypeArray;
import com.analyzeme.repository.storage.serialization.DataArrayModel;
import com.analyzeme.repository.storage.serialization.FileInfoModel;
import com.google.firebase.FirebaseOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * For storing dataArray
 */
public class DataArrayStorage {
    private static IStorage storage;
    private final static String credentialsPath = "/configuration/firebaseCredentials.json";

    static {
        storage = new FirebaseStorage();
        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setDatabaseUrl("https://hello-62cf7.firebaseio.com/")
                    .setServiceAccount(new FileInputStream(new File(DataArrayStorage.class.getResource(credentialsPath).getFile())))
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        storage.connect(options);
    }

    /**
     * Uploads given DataArray to the storage.
     * Adds new entry at fileInfos collection with random name which contains real filename and token
     * A DataArray is stored under this token
     *
     * @param dataArray array to store in storage
     * @param filename  real name of this file
     * @return generated token for access file's data
     */
    public static String upload(DataWithTypeArray dataArray, String filename) {
        DataArrayModel dataArrayModel = dataArray.serialize();
        String token = storage.saveEntry("files", dataArrayModel);
        FileInfoModel fileInfoModel = new FileInfoModel(filename, token);
        storage.saveEntry("fileInfos", fileInfoModel);
        return token;
    }

    /**
     * Lists all files in the storage
     * @param result callback. To onComplete method {@code List<FileInoModel>} with all fileInfos is passed
     */
    public static void getAllFileInfos(IStorageResult<List<FileInfoModel>> result) {
        storage.getAllFromCollection("fileInfos", FileInfoModel.class, result);
    }

    /**
     * Gets {@link com.analyzeme.data.dataWithType.DataWithTypeArray} from the storage by the file token
     * @param fileToken token of file to get
     * @param result callback function
     */
    public static void getFileContent(String fileToken, IStorageResult<DataWithTypeArray> result) {
        storage.getEntry("files", fileToken, DataArrayModel.class, new IStorageResult<DataArrayModel>() {
            @Override
            public void onComplete(DataArrayModel dataArrayModel) {
                result.onComplete(dataArrayModel.deserialize());
            }

            @Override
            public void onError(String message) {
                result.onError(message);
            }
        });
    }
}
