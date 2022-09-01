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
        return new Resume[0];
    }

    int size() {
        return 0;
    }
}
