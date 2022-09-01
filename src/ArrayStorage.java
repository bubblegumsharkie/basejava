import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int currentAmountOfResumes = 0;

    void clear() {
        // подумать, сочетается ли с условиями и замечаниями из гитхаба
        Arrays.fill(storage, null);
        currentAmountOfResumes = 0;
    }

    void save(Resume r) {
        storage[currentAmountOfResumes] = r;
        currentAmountOfResumes++;
    }

    Resume get(String uuid) {
        return null;
    }

    void delete(String uuid) {
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] presentResumes = new Resume[currentAmountOfResumes];
        System.arraycopy(storage, 0, presentResumes, 0, currentAmountOfResumes);
        return presentResumes;
    }

    int size() {
        return currentAmountOfResumes;
    }
}
