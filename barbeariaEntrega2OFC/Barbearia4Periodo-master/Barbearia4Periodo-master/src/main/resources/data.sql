-- Dados iniciais para todas as entidades

-- Clientes
INSERT INTO cliente (nome, celular, email, idade) VALUES 
('João Silva', '11-987654321', 'joao@email.com', 30),
('Maria Santos', '11-876543210', 'maria@email.com', 25),
('Pedro Costa', '11-765432109', 'pedro@email.com', 35);

-- Funcionários
INSERT INTO funcionario (nome, telefone, endereco) VALUES 
('Carlos Barbeiro', '11-999888777', 'Rua das Flores, 123'),
('Ana Cabeleireira', '11-888777666', 'Av. Principal, 456');

-- Serviços
INSERT INTO servico (nome, descricao, preco, duracao_minutos) VALUES 
('Corte Masculino', 'Corte de cabelo masculino tradicional', 25.00, 30),
('Barba', 'Aparar e modelar barba', 15.00, 20),
('Corte + Barba', 'Pacote completo corte e barba', 35.00, 45);

-- Produtos
INSERT INTO produto (nome, descricao, preco, quantidade_estoque) VALUES 
('Shampoo Premium', 'Shampoo para cabelos masculinos', 29.90, 50),
('Pomada Modeladora', 'Pomada para fixação e brilho', 19.90, 30),
('Óleo para Barba', 'Óleo hidratante para barba', 24.90, 25);