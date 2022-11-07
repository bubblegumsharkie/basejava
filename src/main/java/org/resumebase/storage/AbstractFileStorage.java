package org.resumebase.storage;

import org.resumebase.exceptions.StorageException;
import org.resumebase.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not read/write accessible");
        }
        this.directory = directory;
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
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File saving error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File reading error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
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

    protected abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;

    protected abstract Resume doRead(InputStream inputStream) throws IOException;

}
