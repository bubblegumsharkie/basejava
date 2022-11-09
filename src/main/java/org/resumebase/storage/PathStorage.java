package org.resumebase.storage;

import org.resumebase.exceptions.StorageException;
import org.resumebase.model.Resume;
import org.resumebase.storage.serializer.Serializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final Serializer serializer;

    protected PathStorage(String uri, Serializer serializer) {
        directory = Paths.get(uri);
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(uri + " is not a directory");
        }
        if (!Files.isWritable(directory) || !Files.isReadable(directory)) {
            throw new IllegalArgumentException(directory + " is not read/write accessible");
        }
        this.serializer = serializer;
    }

    private static String getFileName(Path path) {
        return path.getFileName().toString();
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("File creating error", getFileName(path), e);
        }
        try {
            serializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File saving error", getFileName(path), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path reading error", getFileName(path), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            serializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path updating error", getFileName(path), e);
        }
    }

    @Override
    protected boolean isExisting(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Path deletion error", getFileName(path), e);
        }
    }

    @Override
    public List<Resume> getResumes() {
        try (Stream<Path> items = getListOfFiles()) {
            return items.map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Path reading error", e);
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> items = getListOfFiles()) {
            items.forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path reading error", e);
        }
    }

    @Override
    public int size() {
        try (Stream<Path> items = getListOfFiles()) {
            return (int) items.count();
        } catch (IOException e) {
            throw new StorageException("Path reading error", e);
        }
    }

    private Stream<Path> getListOfFiles() throws IOException {
        return Files.list(directory);
    }

}
