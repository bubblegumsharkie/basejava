import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[6];
    int countResumes = 0;

    void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
        System.out.println("Resume base was successfully cleared");
    }

    void save(Resume r) {
        if ((!checkForResume(r.uuid)) && (storage[storage.length - 1] == null)) {
            storage[countResumes] = r;
            countResumes++;
            System.out.println("The resume with UUID: " + r.uuid + " was successfully saved");
        } else System.out.println("There was an error with saving resume. UUID: " + r.uuid);

    }

    Resume get(String uuid) {
        int resumeID = findResume(uuid);
        if (resumeID != -1) {
            if (Objects.equals(storage[resumeID].uuid, uuid)) {
                return storage[resumeID];
            }
        } else System.out.println("There was an error with getting resume. UUID: " + uuid);
        return null;
    }

    void update(Resume resume) {
        int resumeID = findResume(resume.uuid);
        if (resumeID != -1) {
            storage[resumeID] = resume;
            System.out.println("The resume with UUID: " + resume.uuid + " was successfully updated");
        }
    }

    void delete(String uuid) {
        int resumeID = findResume(uuid);
        if (resumeID != -1) {
            storage[resumeID] = storage[countResumes - 1];
            storage[countResumes - 1] = null;
            countResumes--;
            System.out.println("The resume with UUID: " + uuid + " was successfully deleted");
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

    int findResume(String uuid) {
        if (checkForResume(uuid)) {
            for (int i = 0; i < countResumes; i++) {
                if (Objects.equals(storage[i].uuid, uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }

    boolean checkForResume(String uuid) {
        if (countResumes > 0) {
            for (int i = 0; i < countResumes; i++) {
                if (Objects.equals(storage[i].uuid, uuid)) {
                    return true;
                }
            }
        }
        return false;
    }
}
