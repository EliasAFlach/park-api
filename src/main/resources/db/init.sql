-- Criação da tabela de usuários
CREATE TABLE `usuarios` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `criado_por` varchar(255) DEFAULT NULL,
    `data_criacao` datetime(6) DEFAULT NULL,
    `data_modificacao` datetime(6) DEFAULT NULL,
    `modificado_por` varchar(255) DEFAULT NULL,
    `password` varchar(200) NOT NULL,
    `role` enum('ROLE_ADMIN','ROLE_CLIENTE') NOT NULL,
    `username` varchar(100) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKm2dvbwfge291euvmk6vkkocao` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Inserção de usuário admin inicial (senha: Admin@123)
INSERT INTO usuarios (`username`, `role`, `password`) VALUES ("elias@email.com", "ROLE_CLIENTE", "123456"), ("larissa@email.com", "ROLE_CLIENTE", "123456");