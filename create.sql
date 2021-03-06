create table cliente(
    codigo int not null primary key,
    nome varchar(50),
    email varchar(50)
);

create table produto (
    codigo int not null primary key,
    nome varchar(50),
    preco float
);

create table pedido(
    numero serial not null primary key,
    cod_cliente int not null,
    data_venda date,
    valor_total float,
    status varchar(15)
);

alter table
    pedido
add
    constraint fk_Cliente FOREIGN KEY (cod_cliente) REFERENCES Cliente (codigo);

create table item_pedido (
    num_pedido int not null,
    num_item int not null,
    cod_produto int not null,
    quantidade float,
    valor_unitario float
);

alter table
    item_pedido
add
    primary key(num_pedido, num_item);

alter table
    item_pedido
add
    constraint fk_Pedido FOREIGN KEY (num_pedido) REFERENCES Pedido (numero);

alter table
    item_pedido
add
    constraint fk_Produto FOREIGN KEY (cod_produto) REFERENCES Produto (codigo);