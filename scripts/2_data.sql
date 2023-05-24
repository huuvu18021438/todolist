INSERT INTO persons (first_name, last_name)
 VALUES
 ('Richard', 'Countin'),
 ('Nathalie', 'Queen'),
 ('Benito', 'Corazon'),
 ('Vince', 'Power');

 INSERT INTO items (status, description, assignee_id)
 VALUES
 ('TODO', 'Flight to JNB', 1),
 ('TODO', 'Organise an celebration for the Great Place to Work', 2),
 ('IN_PROGRESS', 'Organise a drink with the team', 3),
 ('DONE', 'Meet Ms Cosmic', 4);

 INSERT INTO tags (id, name)
 VALUES
 (1, 'Work'),
 (2, 'Private'),
 (3, 'Meeting'),
 (4, 'Sport'),
 (5, 'Meal'),
 (6, 'Drink'),
 (7, 'Vacation');

 INSERT INTO items_tags (item_id, tag_id)
 VALUES
 (1, 2),
 (1, 7),
 (2, 1),
 (2, 3),
 (2, 5),
 (2, 6),
 (3, 1),
 (3, 6),
 (4, 2);