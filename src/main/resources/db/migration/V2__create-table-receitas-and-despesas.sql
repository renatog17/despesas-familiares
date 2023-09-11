create table receitas(
	id bigint not null auto_increment,
	descricao varchar(255),
	valor decimal(10,2),
	data date,
	primary key(id)
);

create table despesas(
	id bigint not null auto_increment,
	descricao varchar(255),
	valor decimal(10,2),
	data date,
	primary key(id)
);