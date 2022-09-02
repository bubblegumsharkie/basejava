import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int countResumes = 0;

    void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    void save(Resume r) {
        storage[countResumes] = r;
        countResumes++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (countResumes > 0) {
            for (int i = 0; i < countResumes; i++) {
                if (Objects.equals(storage[i].uuid, uuid)) {
                    System.out.println("Deleted the resume with uuid: " + uuid);
                    storage[i] = storage[countResumes-1];
                }
            }
            countResumes--;
        } else {
            System.out.println("There are currently no resumes in storage");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    int size() {
        return countResumes;
    }
}
