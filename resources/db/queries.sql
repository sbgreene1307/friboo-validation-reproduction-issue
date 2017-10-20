-- name: create-memory!
INSERT INTO ehr_data.memories (text)
  SELECT text FROM unnest(ARRAY[ :textlist ]) text;
