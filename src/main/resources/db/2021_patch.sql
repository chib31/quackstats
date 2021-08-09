INSERT INTO team (id, name)
VALUES (default, 'Butterlords CC');


INSERT INTO team (id, name)
VALUES (default, 'Winchmore Hill Lions');

INSERT INTO player (id, team_id, first_name, middle_names, last_name, preferred_name, scorecard_name, cap_number, date_of_membership, shirt_number)
VALUES
  (default, 1, 'Chris', NULL, 'Milton', NULL, 'C Milton', NULL, NULL, NULL)
, (default, 1, 'Bruce', NULL, 'Torrance', NULL, 'B Torrance', NULL, NULL, NULL)
, (default, 1, 'Fred', NULL, 'Combe', NULL, 'F Combe', NULL, NULL, NULL);


INSERT INTO team (id, name)
VALUES (default, 'Woodford Green');

INSERT INTO player (id, team_id, first_name, middle_names, last_name, preferred_name, scorecard_name, cap_number, date_of_membership, shirt_number)
VALUES
  (default, 1, 'Murtaza', NULL, 'Rizvi', NULL, 'M Rizvi', NULL, NULL, NULL)
, (default, 1, 'Varun', NULL, 'Devjani', NULL, 'V Devjani', NULL, NULL, NULL)
, (default, 1, 'Josh', NULL, 'Dangerfield', NULL, 'J Dangerfield', NULL, NULL, NULL)
, (default, 1, 'Jack', NULL, 'Schofield', NULL, 'J Schofield', NULL, NULL, NULL);

COMMIT;