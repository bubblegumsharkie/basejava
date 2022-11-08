package org.resumebase.storage;

import org.resumebase.exceptions.StorageException;
import org.resumebase.model.Resume;
import org.resumebase.storage.serializer.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final Serializer serializer;

    protected FileStorage(File directory, Serializer serializer) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not read/write accessible");
        }
        this.directory = directory;
        this.serializer = serializer;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            if (!file.createNewFile()) {
                throw new StorageException("File creating error", file.getName());
            }
            serializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File saving error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File reading error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            serializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File updating error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExisting(File file) {
        return file.exists();
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File deletion error", file.getName());
        }
    }

    @Override
    public ArrayList<Resume> getResumes() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory reading error");
        }
        ArrayList<Resume> resumes = new ArrayList<>();
        for (File file : files) {
            resumes.add(doGet(file));
        }
        return resumes;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory reading error");
        }
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory reading error");
        }
        return files.length;
    }

}
