INSERT INTO APPLICATION_USERS (USER_FIRST_NAME, USER_LAST_NAME, USERNAME, USER_PASSWORD)
VALUES
  ('System', 'Admin', 'admin', '$2a$12$ZUcBydzOYpdY0F./Zt3dEOo1VHNH9ap2ConyfKUB5MpL6bNLshhaa'),
  ('Iason', 'Chatzopoulos', 'ichatzop', '$2a$12$YmasPNFpbbhk.8eHu/Em9ezc7oSB3uUrkJPDj9mZquhqJeXjlc47S'), -- > mysecurepass
  ('Nick', 'Stentoumis', 'nstentou', '$2a$12$063MA2wvX1IhKwmVNGl6wuMHdGFWomVQI31abSbxsnlAAtM687KVq')     -- > nickspassword
ON CONFLICT (USERNAME) DO NOTHING;
