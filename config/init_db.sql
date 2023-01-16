CREATE TABLE resume
(
    uuid      CHARACTER(36) PRIMARY KEY NOT NULL,
    full_name TEXT                      NOT NULL
);

CREATE TABLE contact
(
    id          SERIAL        NOT NULL,
    resume_uuid CHARACTER(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT          NOT NULL,
    value       TEXT          NOT NULL
);

CREATE TABLE section
(
    id          SERIAL        NOT NULL,
    resume_uuid CHARACTER(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT          NOT NULL,
    value       TEXT          NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index ON contact (resume_uuid, type);
CREATE UNIQUE INDEX section_uuid_type_index ON section (resume_uuid, type);

