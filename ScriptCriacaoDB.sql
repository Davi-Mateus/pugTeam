drop database if exists dbcolegio;
create database dbcolegio;
use dbcolegio;
create table curso(
	id_curso int not null auto_increment primary key,
    nome_curso varchar(100) unique not null
);


create table turma(
	id_turma int not null auto_increment primary key,
    id_curso int not null,
    nome_turma varchar(100) not null,
    ano int,
    dt_inicio date,
    dt_final date,
    foreign key(id_curso) references curso(id_curso)
);


create table aluno(
	id_aluno int not null auto_increment primary key,
    numero_matricula bigint unique not null,
    nome_aluno varchar(255) not null
);


create table matricula(
	id_matricula int not null auto_increment primary key,
    id_aluno int not null,
    id_turma int not null,
    dt_matricula date not null,
    dt_cancelamento_matricula date,
    foreign key(id_aluno) references aluno(id_aluno),
    foreign key(id_turma) references turma(id_turma)
);


create table horario(
	id_horario int not null auto_increment primary key,
    hr_inicio_aula time not null,
    hr_final_aula time not null,
    dia_semana_int int not null,
    dia_semana varchar(50) not null
);


create table registro(
	id_registro int not null auto_increment primary key,
	id_horario int not null,
    id_aluno int not null,
    id_turma int not null,
    dt_hr_entrada datetime,
    dt_hr_saida datetime,
    foreign key(id_horario) references horario(id_horario),
    foreign key(id_aluno) references aluno(id_aluno),
    foreign key(id_turma) references turma(id_turma)
);


create table possui(
	id_horario int not null,
    id_turma int not null,
    foreign key(id_horario) references horario(id_horario),
    foreign key(id_turma) references turma(id_turma)
);


create table permissao(
	id_permissao int not null auto_increment primary key,
    responsavel varchar(255) not null,
    dt_permissao date not null,
    tipo enum('entrada', 'saída', 'entrada e saída') not null
);	


create table visitante(
	id_visitante int not null auto_increment primary key,
    id_permissao int not null,
    nome_visitante varchar(255) not null,
    motivo text not null,
    foreign key(id_permissao) references permissao(id_permissao)
);


create table permite_aluno(
	id_aluno int not null,
    id_permissao int not null,
    primary key(id_aluno, id_permissao),
    foreign key(id_aluno) references aluno(id_aluno),
    foreign key(id_permissao) references permissao(id_permissao)
);


create table permite_turma(
	id_turma int not null,
    id_permissao int not null,
    primary key(id_turma, id_permissao),
    foreign key(id_turma) references turma(id_turma),
    foreign key(id_permissao) references permissao(id_permissao)
);


create table dia_aula(
	id_dia_aula int not null auto_increment primary key,
    id_horario int not null,
    id_turma int not null,
    dia date not null,
	foreign key(id_turma) references turma(id_turma),
	foreign key(id_horario) references horario(id_horario)
);

create view
	registro_com_horario as
		select
			r.*,
			h.hr_inicio_aula,
			h.hr_final_aula,
			h.dia_semana_int,
			h.dia_semana
		from
			registro r
		inner join horario h on h.id_horario = r.id_horario;
        
        
create view
	registro_aluno_presente_dia as
		select
		r.*,
        h.hr_inicio_aula,
        h.hr_final_aula,
        h.dia_semana,
        h.dia_semana_int
	from
		registro r
	inner join
		horario h on h.id_horario = r.id_horario
	where 
		dt_hr_saida is null
		and date(dt_hr_entrada) = date(now());


create view
	dias_curso as
		select
			h.*,
            d.id_dia_aula,
            d.id_turma,
            d.dia
		from
			dia_aula d
		inner join
			horario h on h.id_horario = d.id_horario
		order by
			id_dia_aula;


create view
	cursos_deletaveis as
		select
			c.*
		from
			curso c
		where 
			not exists(select * from turma t where t.id_curso = c.id_curso);


create view
	matriculas_por_aluno as
		select
			a.*,
			t.*,
			m.dt_matricula,
			m.dt_cancelamento_matricula
		from
			matricula m
		inner join
			aluno a on a.id_aluno = m.id_aluno
		inner join turma t on t.id_turma = m.id_turma;


create view
	permissoes_aluno as
		select
			p.*,
			a.*
		from
			permissao p
		inner join
			permite_aluno pa on p.id_permissao = pa.id_permissao
		inner join
			aluno a on pa.id_aluno = a.id_aluno
		order by
			p.dt_permissao desc;
    

create view
	permissoes_aluno_dia as
		select
			p.*,
			a.*
		from
			permissao p
		inner join
			permite_aluno pa on p.id_permissao = pa.id_permissao
		inner join
			aluno a on pa.id_aluno = a.id_aluno
		where
			p.dt_permissao = Date(now())
		order by
			p.dt_permissao desc;


create view
	permissoes_turma as
		select
			p.*,
			t.*,
			c.nome_curso
		from
			permissao p
		inner join
			permite_turma pt on pt.id_permissao = p.id_permissao
		inner join
			turma t on t.id_turma = pt.id_turma
		inner join
			curso c on c.id_curso = t.id_curso
		order by
			p.dt_permissao desc;
    
    
create view
	permissoes_turma_dia as
		select
			p.*,
			t.*,
			c.nome_curso
		from
			permissao p
		inner join
			permite_turma pt on pt.id_permissao = p.id_permissao
		inner join
			turma t on t.id_turma = pt.id_turma
		inner join
			curso c on c.id_curso = t.id_curso
		where
			p.dt_permissao = Date(now())
		order by
			p.dt_permissao desc;


create view
	permissoes_visitante as
		select
			p.*,
			v.id_visitante,
			v.nome_visitante,
			v.motivo
		from
			permissao p
		inner join
			visitante v on p.id_permissao = v.id_permissao
		order by
			p.dt_permissao desc;


create view
	horarios as
		select
			*
		from
			horario
		order by(
			field(
				dia_semana, 
				'Domingo', 
				'Segunda-feira', 
				'Terça-feira', 
				'Quarta-feira', 
				'Quinta-feira', 
				'Sexta-feira', 
				'Sábado'
			)
		);


create view
	horarios_deletaveis as
		select
			h.*
		from
			horario h
		where
			not exists (select * from possui where id_horario = h.id_horario) 
		order by(
			field(
				dia_semana, 
				'Domingo', 
				'Segunda-feira', 
				'Terça-feira', 
				'Quarta-feira', 
				'Quinta-feira', 
				'Sexta-feira', 
				'Sábado'
			)
		);


create view
	resistros_alunos as
		select
			r.*,
			a.numero_matricula,
			a.nome_aluno
		from
			registro r
		inner join
			aluno a on r.id_aluno = r.id_aluno;


create view
	horarios_turma as
		select 
			t.*,
			h.*
		from
			turma t
		inner join
			possui p on p.id_turma = t.id_turma
		inner join
			horario h on h.id_horario = p.id_horario;
            
            
create view
	horarios_aluno as
		select 
			a.*,
			t.*,
			h.*
		from
			aluno a
		inner join
			matricula m on a.id_aluno = m.id_aluno
		inner join
			turma t on t.id_turma = m.id_turma
		inner join
			possui p on p.id_turma = t.id_turma
		inner join
			horario h on h.id_horario = p.id_horario;
            
            
create view
	horarios_aluno_dia as
		select 
			a.*,
			t.*,
			h.*
		from
			aluno a
		inner join
			matricula m on a.id_aluno = m.id_aluno
		inner join
			turma t on t.id_turma = m.id_turma
		inner join
			possui p on p.id_turma = t.id_turma
		inner join
			horario h on h.id_horario = p.id_horario
		where
			dia_semana_int = DATE_FORMAT(now(),'%w');


create view
	turmas as
		select
			t.*,
			c.nome_curso
		from 
			turma t
		inner join
			curso c on c.id_curso = t.id_curso
		order by
			t.ano desc,
			t.nome_turma;


create view
	turmas_por_aluno as
		select
			t.*,
			c.nome_curso,
			a.id_aluno
		from
			turma t
		inner join
			curso c on c.id_curso = t.id_curso
		inner join
			matricula m on m.id_turma = t.id_turma
		inner join
			aluno a on a.id_aluno = m.id_aluno
		where
			m.dt_cancelamento_matricula is null
		order by
			t.ano desc,
			t.nome_turma;


create view
	turmas_deletaveis as
		select
			t.*,
			c.nome_curso
		from
			turma t
            left join curso c on
            c.id_curso = t.id_curso
		where
			not exists (select * from matricula where id_turma = t.id_turma) 
			and not exists (select * from registro where id_turma = t.id_turma) 
		order by
			t.ano desc,
			t.nome_turma;

 

create view
	visitantes as 
		select
			v.*,
			p.responsavel,
			p.dt_permissao,
			p.tipo
		from
			visitante v
		inner join
			permissao p on p.id_permissao = v.id_permissao
		order by
			p.dt_permissao desc;
            

create view
	aulas_dia as
		select
			t.*,
			h.*,
			d.id_dia_aula,
			d.dia
		from
			dia_aula d
		inner join 
			horario h on h.id_horario = d.id_horario
		inner join 
			turma t on t.id_turma = d.id_turma
		where
			d.dia = date(now());


create view
	visitantes_do_dia as
		select
			v.*,
			p.responsavel,
			p.dt_permissao,
			p.tipo
		from
			visitante v
		inner join
			permissao p on p.id_permissao = v.id_permissao
		where
			p.dt_permissao = date(now());


create view
	presencas as
		select
			r.*,
			a.numero_matricula,
			a.nome_aluno,
			m.id_matricula,
			m.dt_matricula,
			m.dt_cancelamento_matricula,
			t.id_curso,
			t.nome_turma,
			t.ano,
			t.dt_inicio,
			t.dt_final
		from
			registro r
		inner join
			aluno a on r.id_aluno = a.id_aluno
		inner join
			matricula m on m.id_aluno = a.id_aluno
		inner join
			turma t on t.id_turma = m.id_turma;


create view
	faltas as
		select
			t.*,
			a.*,
			d.id_dia_aula,
			d.dia,
			m.id_matricula,
			m.dt_matricula,
			m.dt_cancelamento_matricula,
			r.id_registro,
			r.dt_hr_entrada,
			r.dt_hr_saida
		from
			dia_aula d
		inner join
			turma t on t.id_turma = d.id_turma
		inner join
			matricula m on m.id_turma = t.id_turma
		inner join
			aluno a on a.id_aluno = m.id_aluno
		left join
			registro r on r.id_aluno = a.id_aluno 
			and r.id_turma = t.id_turma 
			and Date(r.dt_hr_entrada) = d.dia
            and m.dt_cancelamento_matricula is null
		where
			r.dt_hr_entrada is null
		order by
			t.nome_turma,
            d.dia,
			a.nome_aluno;
    

create view
	atrasados as
		select
			t.*,
			a.*,
			d.id_dia_aula,
			d.dia,
			m.id_matricula,
			m.dt_matricula,
			m.dt_cancelamento_matricula,
			r.id_registro,
			r.dt_hr_entrada,
			r.dt_hr_saida,
			h.hr_inicio_aula
		from
			dia_aula d
		inner join
			turma t on t.id_turma = d.id_turma
		inner join
			matricula m on m.id_turma = t.id_turma
		inner join
			aluno a on a.id_aluno = m.id_aluno
		left join
			registro r on r.id_aluno = a.id_aluno and r.id_turma = t.id_turma and date(r.dt_hr_entrada) = d.dia
		left join
			horario h on h.id_horario = d.id_horario
		where
			r.dt_hr_entrada is null 
			and time(now()) between h.hr_inicio_aula and h.hr_final_aula 
			and d.dia = date(now())
            and m.dt_cancelamento_matricula is null;
    
    
create view
	frequencia as
		select
			t.*,
			a.*,
			d.id_dia_aula,
			d.dia,
			m.id_matricula,
			m.dt_matricula,
			m.dt_cancelamento_matricula,
			r.id_registro,
			r.dt_hr_entrada,
			r.dt_hr_saida
		from
			dia_aula d
		inner join
			turma t on t.id_turma = d.id_turma
		inner join
			matricula m on m.id_turma = t.id_turma
		inner join
			aluno a on a.id_aluno = m.id_aluno
		left join
			registro r on r.id_aluno = a.id_aluno 
			and r.id_turma = t.id_turma 
			and Date(r.dt_hr_entrada) = d.dia
            and m.dt_cancelamento_matricula is null
		order by
			a.nome_aluno,
			t.nome_turma,
			d.dia;
            

create view
	contagem_faltas as
		select
			t.id_turma,
			t.nome_turma,
			a.*,
			d.id_dia_aula,
			d.dia,
			m.id_matricula,
			m.dt_matricula,
			m.dt_cancelamento_matricula,
			r.id_registro,
			r.dt_hr_entrada,
			r.dt_hr_saida
		from
			dia_aula d
		inner join
			turma t on t.id_turma = d.id_turma
		inner join
			matricula m on m.id_turma = t.id_turma
		inner join
			aluno a on a.id_aluno = m.id_aluno
		left join
			registro r on r.id_aluno = a.id_aluno 
			and r.id_turma = t.id_turma 
			and Date(r.dt_hr_entrada) = d.dia
            and m.dt_cancelamento_matricula is null
		where
			r.dt_hr_entrada is null
            and d.dia < date(now())
		order by
			t.id_turma,
			d.dia,
			a.nome_aluno;
    
    
create view 
	numero_faltas_mes as
		select 
			nome_aluno, 
            id_aluno, 
            numero_matricula,
            month(dia) as mes,
            year(dia) as ano,
            count(*) as faltas
		from
			contagem_faltas
		group by 
			nome_aluno, 
            id_aluno, 
            numero_matricula,
            month(dia),
            year(dia);
    
create view
	faltas_seguidas as
		select
			t.id_turma,
			t.nome_turma,
			a.*,
			d.id_dia_aula,
			d.dia,
            year(dia) as ano,
            month(dia) as mes,
			m.id_matricula,
			m.dt_matricula,
			m.dt_cancelamento_matricula,
			r.id_registro,
			r.dt_hr_entrada,
			r.dt_hr_saida,
			(select count(*) from frequencia as dia where dia.id_turma = t.id_turma and dia.dia < d.dia and dt_hr_entrada is null) as qtde
		from
			dia_aula d
		inner join
			turma t on t.id_turma = d.id_turma
		inner join
			matricula m on m.id_turma = t.id_turma
		inner join
			aluno a on a.id_aluno = m.id_aluno
		left join
			registro r on r.id_aluno = a.id_aluno 
			and r.id_turma = t.id_turma 
			and Date(r.dt_hr_entrada) = d.dia
            and m.dt_cancelamento_matricula is null
		where
			dt_hr_entrada is null
            and d.dia <= date(now())
		order by
			id_aluno,
            dia;

    
create view
	alunos_criticos_mes as
		select 
			id_aluno,
			nome_aluno,
			numero_matricula,
			dia,
			(case when 
				exists(select * from faltas_seguidas where id_aluno = aluno.id_aluno and qtde >= 4 and aluno.dia > subdate(last_day(now()), interval 1 month)) 
                or exists(select * from numero_faltas_mes n where faltas >= 6)
			  then 'S' ELSE 'N' END) as critico
		from
			faltas_seguidas AS aluno
		group by 
			nome_aluno
		having
			critico = "S"
		order by
			id_aluno,
			dia;
            
            
create view
	alunos_APOIA_mes as
		select 
			id_aluno,
			nome_aluno,
			numero_matricula,
			dia,
			(case when 
				exists(select * from faltas_seguidas where id_aluno = aluno.id_aluno and qtde >= 5 and aluno.dia > subdate(last_day(now()), interval 1 month)) 
                or exists(select * from numero_faltas_mes n where faltas >= 7)
			  then 'S' ELSE 'N' END) as critico
		from
			faltas_seguidas AS aluno
		group by 
			nome_aluno
		having
			critico = "S"
		order by
			id_aluno,
			dia;
            

delimiter $$
create procedure 
	popula_dia_aula(p_dInicio date, p_dFinal date, p_idTurma int, p_idHorario int)
begin
	declare v_dt_loop date;
    set v_dt_loop = p_dInicio;
	repeat
		if date_format(v_dt_loop,'%w') in (select dia_semana_int from horario h inner join possui p on p.id_horario = h.id_horario inner join turma t on t.id_turma = p.id_turma where t.id_turma = p.id_turma) and date_format(v_dt_loop,'%w') = (select dia_semana_int from horario where id_horario = p_idHorario) then
			insert into dia_aula(dia, id_turma, id_horario) values (v_dt_loop, p_idTurma, p_idHorario);
		end if;
        set v_dt_loop = adddate(v_dt_loop, interval 1 day);
	until
		v_dt_loop = adddate(p_dFinal, interval 1 day)
	end repeat;
end $$
delimiter ;


DELIMITER $$

create trigger trigger_popula_dia_aula after insert on possui FOR EACH ROW 
begin
	call popula_dia_aula((select dt_inicio from turma where id_turma = new.id_turma), (select dt_final from turma where id_turma = new.id_turma), new.id_turma, new.id_horario);
end $$
 
DELIMITER ;

DELIMITER $$

create trigger trigger_exclui_dia_aula after delete on possui FOR EACH ROW 
begin
	delete from dia_aula where old.id_turma;
end $$
 
DELIMITER ;


CREATE TABLE temporaria (
	id_temporaria INT NOT NULL PRIMARY KEY AUTO_INCREMENT
	, matriz VARCHAR(100)
	, curso VARCHAR(100)
	, turno VARCHAR(100)
	, serie VARCHAR(100)
	, turma VARCHAR(100)
	, sala VARCHAR(100)
	, numero VARCHAR(100)
	, matricula VARCHAR(100)
	, nome VARCHAR(100)
	, sexo VARCHAR(100)
	, dt_nascimento VARCHAR(100)
	, identidade VARCHAR(100)
	, dt_matricula VARCHAR(100)
	, cpf VARCHAR(100)
);


delimiter $$
create procedure 
	pr_add_csv()
begin
insert into 
	curso (nome_curso) 
select distinct 	curso	
from 
	temporaria tab
where not exists 
	(select 0 from curso where nome_curso = tab.curso	);

insert into 
	turma (id_curso, nome_turma)
select distinct 
	curso.id_curso, tab.turma
from 
temporaria tab
inner join 
	curso on curso.nome_curso = tab.curso
where not exists 
	(select 0 from turma where turma.nome_turma = tab.turma and turma.id_curso = curso.id_curso);

insert into 
	aluno(numero_matricula,nome_aluno)
select distinct
	matricula, nome
from 
	temporaria as tab
where not exists 
	(select 0 from aluno where numero_matricula = tab.matricula);
    
insert into 
	matricula(id_aluno,id_turma , dt_matricula) 
select distinct 
	aluno.id_aluno, turma.id_turma, 
	concat(substr(tab.dt_matricula, 7, 4), '-', substr(tab.dt_matricula, 4, 2), '-', substr(tab.dt_matricula, 1, 2))
from 
	temporaria tab inner join aluno on
	tab.nome = aluno.nome_aluno and tab.matricula = aluno.numero_matricula
inner join 
	turma on tab.turma = turma.nome_turma
where not exists 
	(select 0 from matricula where id_aluno = aluno.id_aluno and id_turma = turma.id_turma);

truncate table temporaria;
end $$
delimiter ;


-- aluno trigger insert pra validar nome maior q 3 carc e retirar espaço em branco
delimiter $$
create trigger tr_valida_nome_aluno before insert on aluno for each row 
begin
	if length(trim(new.nome_aluno)) < 3 then
		signal sqlstate '45000' set message_text = 'Nome do aluno muito pequeno' ;
	else
		set new.nome_aluno=trim(new.nome_aluno);
	end if;
end $$
delimiter ;


-- aluno trigger update pra validar nome maior q 3 carc e retirar espaço em branco
delimiter $$
create trigger tr_update_valida_nome before update on aluno for each row 
begin
	if length(trim(new.nome_aluno)) < 3 then
		signal sqlstate '45000' set message_text = 'Nome do aluno muito pequeno' ;
	else
		set new.nome_aluno=trim(new.nome_aluno);
	end if;
end $$
delimiter ;


-- curso trigger update pra validar nome maior q 3 carc e retirar espaço em branco
delimiter $$
create trigger tr_insert_valida_nome_curso before insert on curso for each row 
begin
	if length(trim(new.nome_curso)) < 3 then
		signal sqlstate '45000' set message_text = 'Nome do curso muito pequeno' ;
	else
		set new.nome_curso = trim(new.nome_curso);
	end if;
end $$
delimiter ;


-- curso trigger update pra validar nome maior q 3 carc e retirar espaço em branco
delimiter $$
create trigger tr_update_valida_nome_curso before update on curso for each row 
begin
	if length(trim(new.nome_curso)) < 3 then
		signal sqlstate '45000' set message_text = 'Nome do curso muito pequeno' ;
	else
		set new.nome_curso = trim(new.nome_curso);
	end if;
end $$
delimiter ;


-- permissao trigger update pra validar nome maior q 3 carc e retirar espaço em branco
delimiter $$
create trigger tr_insert_valida_nome_responsavel before insert on permissao for each row 
begin
	if length(trim(new.responsavel)) < 3 then
		signal sqlstate '45000' set message_text = 'Nome do responsavel muito pequeno' ;
	else
		set new.responsavel = trim(new.responsavel);
	end if;
end $$
delimiter ;


-- permissao trigger update pra validar nome maior q 3 carc e retirar espaço em branco
delimiter $$
create trigger tr_update_valida_nome_responsavel before update on permissao for each row 
begin
	if length(trim(new.responsavel)) < 3 then
		signal sqlstate '45000' set message_text = 'Nome do responsavel muito pequeno' ;
	else
		set new.responsavel = trim(new.responsavel);
	end if;
end $$
delimiter ;


-- turma trigger insert pra validar nome maior q 3 carc e retirar espaço em branco
-- data inicio menor q data fim
delimiter $$
create trigger tr_insert_valida_nome_turma before insert on turma for each row 
begin
	if length(trim(new.nome_turma)) < 3 then
		signal sqlstate '45000' set message_text = 'Nome da turma muito pequeno' ;
	else
		set new.nome_turma = trim(new.nome_turma);
	end if;
    if (new.dt_inicio>new.dt_final) then
		signal sqlstate '45000' set message_text = 'Data final anterior a data inicial';		
	end if;
end $$
delimiter ;


-- turma trigger update pra validar nome maior q 3 carc e retirar espaço em branco
-- data inicio menor q data fim
delimiter $$
create trigger tr_update_valida_nome_turma before update on turma for each row 
begin
	if length(trim(new.nome_turma)) < 3 then
		signal sqlstate '45000' set message_text = 'Nome da turma muito pequeno' ;
	else
		set new.nome_turma = trim(new.nome_turma);
	end if;
    if (new.dt_inicio>new.dt_final) then
		signal sqlstate '45000' set message_text = 'Data final anterior a data inicial';		
	end if;
end $$
delimiter ;


-- visitante trigger update pra validar nome maior q 3 carc e retirar espaço em branco
delimiter $$
create trigger tr_insert_valida_nome_visitante before insert on visitante for each row 
begin
	if length(trim(new.nome_visitante)) < 3 then
		signal sqlstate '45000' set message_text = 'Nome do visitante muito pequeno' ;
	else
		set new.nome_visitante = trim(new.nome_visitante);
	end if;
end $$
delimiter ;


-- visitante trigger update pra validar nome maior q 3 carc e retirar espaço em branco
delimiter $$
create trigger tr_update_valida_nome_visitante before update on visitante for each row 
begin
	if length(trim(new.nome_visitante)) < 3 then
		signal sqlstate '45000' set message_text = 'Nome do visitante muito pequeno' ;
	else
		set new.nome_visitante = trim(new.nome_visitante);
	end if;
end $$
delimiter ;


-- tr horario insert hora final tem q ser > que hora inicial
delimiter $$
create trigger tr_horas_on_insert before insert on horario for each row
begin
	if (new.hr_inicio_aula > new.hr_final_aula) then
		signal sqlstate '45000' set message_text = 'Hora invalida';
	end if;
end $$
delimiter ;


-- tr horario update hora final tem q ser > que hora inicial
delimiter $$
create trigger tr_horas_on_update before update on horario for each row
begin
	if (new.hr_inicio_aula > new.hr_final_aula) then
		signal sqlstate '45000' set message_text = 'Hora invalida';
	end if;
end $$
delimiter ;


-- tr matricula insert data matricula < que dia de hoje
delimiter $$
create trigger tr_dia_matricula_on_insert before insert on matricula for each row
begin
	if (new.dt_matricula>date(now())) then
		signal sqlstate '45000' set message_text = 'Dia cadastrado no futuro, invalida';
	elseif (new.dt_cancelamento_matricula<new.dt_matricula) then
		signal sqlstate '45000' set message_text = 'Dia de cancelamento anterior ao dia de matricula';		
	end if;
end $$
delimiter ;


-- tr matricula update data matricula < que dia de hoje
delimiter $$
create trigger tr_dia_matricula_on_update before update on matricula for each row
begin
	if (new.dt_matricula>date(now())) then
		signal sqlstate '45000' set message_text = 'Dia cadastrado no futuro, invalida';
	elseif (new.dt_cancelamento_matricula<new.dt_matricula) then
		signal sqlstate '45000' set message_text = 'Dia de cancelamento anterior ao dia de matricula';		
	end if;
end $$
delimiter ;