CREATE SCHEMA forum;
USE forum;

CREATE TABLE `materia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `conta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `pontos` int DEFAULT 0, 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cargo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_conta` int NOT NULL,
  `id_materia` int NOT NULL,
  `tipo` int DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `conta_idx` (`id_conta`),
  CONSTRAINT `conta_cargo_fk` FOREIGN KEY (`id_conta`) REFERENCES `conta` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  KEY `materia_idx` (`id_materia`),
  CONSTRAINT `materia_cargo_fk` FOREIGN KEY (`id_materia`) REFERENCES `materia` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pergunta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_conta` int NOT NULL,
  `id_materia` int NOT NULL,
  `conteudo` varchar(1000) NOT NULL,
  `anonimo` int DEFAULT 0,
  `status` int DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `conta_idx` (`id_conta`),
  CONSTRAINT `conta_pergunta_fk` FOREIGN KEY (`id_conta`) REFERENCES `conta` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  KEY `materia_idx` (`id_materia`),
  CONSTRAINT `materia_pergunta_fk` FOREIGN KEY (`id_materia`) REFERENCES `materia` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `resposta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_conta` int NOT NULL,
  `id_pergunta` int NOT NULL,
  `conteudo` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `conta_idx` (`id_conta`),
  CONSTRAINT `conta_resposta_fk` FOREIGN KEY (`id_conta`) REFERENCES `conta` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  KEY `pergunta_idx` (`id_pergunta`),
  CONSTRAINT `pergunta_resposta_fk` FOREIGN KEY (`id_pergunta`) REFERENCES `pergunta` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
