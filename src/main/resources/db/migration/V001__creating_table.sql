CREATE TABLE master_houses(
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE wands(
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  wood VARCHAR(50),
  core VARCHAR(50),
  length_in_inches DECIMAL(5,2)
);

CREATE TABLE wizards(
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(100) NOT NULL,
  species ENUM('HUMAN', 'HALF_GIANT', 'WEREWOLF', 'CAT'),
  gender ENUM('MALE', 'FEMALE'),
  house_id INTEGER NOT NULL,
  date_of_birth DATE,
  eye_color VARCHAR(30),
  hair_color VARCHAR(30),
  wand_id INTEGER,
  patronus VARCHAR(50),
  is_professor BIT(1),
  alive BIT(1),
  image_url VARCHAR(150),
  CONSTRAINT `wizards_fkey_house` FOREIGN KEY (house_id)
  REFERENCES master_houses (id),
  CONSTRAINT `wizards_fkey_wands` FOREIGN KEY (wand_id)
  REFERENCES wands (id)
);