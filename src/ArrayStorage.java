import java.util.Arrays;
import java.util.Objects;

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
        Resume resume = null;

        for (int i = 0; i < currentAmountOfResumes; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                resume = storage[i];
                break;
            }
        }
        return resume;
    }

    void delete(String uuid) {
        if (currentAmountOfResumes > 0) {
            Resume[] arrayAfterRemovingSelectedResume = new Resume[10000];
            for (int i = 0; i < currentAmountOfResumes; i++) {
                if (Objects.equals(storage[i].uuid, uuid)) {
                    System.out.println("Deleted the resume with uuid: " + uuid);
                } else {
                    arrayAfterRemovingSelectedResume[i] = storage[i];
                }
            }
            arrayAfterRemovingSelectedResume = Arrays.stream(arrayAfterRemovingSelectedResume)
                    .filter(Objects::nonNull)
                    .toArray(Resume[]::new);
            currentAmountOfResumes--;
            System.arraycopy(arrayAfterRemovingSelectedResume, 0, storage, 0, currentAmountOfResumes);
        } else {
            System.out.println("There are currently no resumes in storage");
        }
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
