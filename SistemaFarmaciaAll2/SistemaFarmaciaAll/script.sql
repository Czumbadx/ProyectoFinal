/************** CREATE DATABASE ****************/
drop database if exists bdfarmacia;
create database bdfarmacia;
use bdfarmacia;

/************** CREATE TABLE ****************/
drop table if exists persona;
create table persona(
id int primary key auto_increment,
nombres varchar(50) not null,
apellidos varchar(50) not null,
dni int(8) unique not null,
direccion varchar(100),
estado int not null default 1
);

drop table if exists perfil;
create table perfil(
id int primary key auto_increment,
nombre varchar(100) not null,
estado int not null default 1
);

drop table if exists usuario;
create table usuario(
id int primary key auto_increment,
username varchar(50) unique not null,
password varchar(200) not null,
persona_id int not null,
perfil_id int not null,
estado int not null default 1
);

alter table usuario add foreign key(persona_id) references persona(id);
alter table usuario add foreign key(perfil_id) references perfil(id);

drop table if exists laboratorio;
create table laboratorio(
id int primary key auto_increment,
nombre varchar(100) not null,
direccion varchar(200) not null,
estado int not null default 1
);

drop table if exists tipo;
create table tipo(
id int primary key auto_increment,
nombre varchar(100) not null,
descripcion varchar(100) not null,
estado int not null default 1
);

drop table if exists producto;
create table producto(
id int primary key auto_increment,
nombre varchar(100) not null,
precio decimal(10,2) not null,
stock int not null,
tipo_id int not null,
laboratorio_id int not null,
estado int not null default 1,
fecha_vencimiento date
);

alter table producto add foreign key(laboratorio_id) references laboratorio(id);
alter table producto add foreign key(tipo_id) references tipo(id);

drop table if exists cliente_juridico;
create table cliente_juridico(
id int primary key auto_increment,
razon_social varchar(100) not null,
direccion varchar(200) not null,
ruc char(11) unique not null,
estado int not null default 1
);

drop table if exists comprobante_boleta;
create table comprobante_boleta(
id int primary key auto_increment,
fecha date,
usuario_id int,
persona_id int,
estado int not null default 1
);

alter table comprobante_boleta add foreign key(persona_id) references persona(id);
alter table comprobante_boleta add foreign key(usuario_id) references usuario(id);

drop table if exists detalle_comprobanteboleta;
create table detalle_comprobanteboleta(
id int primary key auto_increment,

comprobanteboleta_id int,
producto_id int,
cantidad int,
precio_venta decimal(10,2),
estado int not null default 1
);

alter table detalle_comprobanteboleta add foreign key(comprobanteboleta_id) references comprobante_boleta(id);
alter table detalle_comprobanteboleta add foreign key(producto_id) references producto(id);

drop table if exists comprobante_factura;
create table comprobante_factura(
id int primary key auto_increment,
fecha date,
usuario_id int,
clientejuridico_id int,
estado int not null default 1
);

alter table comprobante_factura add foreign key(clientejuridico_id) references cliente_juridico(id);
alter table comprobante_factura add foreign key(usuario_id) references usuario(id);

drop table if exists detalle_comprobantefactura;
create table detalle_comprobantefactura(
id int primary key auto_increment,
comprobantefactura_id int,
producto_id int,
cantidad int,
precio_venta decimal(10,2),
estado int not null default 1
);

alter table detalle_comprobantefactura add foreign key(comprobantefactura_id) references comprobante_factura(id);
alter table detalle_comprobantefactura add foreign key(producto_id) references producto(id);

/************** INSERT ****************/
insert into perfil(nombre) values('Administrador'),('Vendedor');
insert into persona(nombres, apellidos,dni,direccion) values('MR RECURSOS','INF. TEC. Y ELEC.',1111111,''),('MR VENDEDOR','INF. TEC. Y ELEC.',2222222,'');
insert into usuario(username,password,persona_id,perfil_id) values('mrrecursos','mrrecursos',1,1),('mrrecursosv','mrrecursosv',2,2);
insert into laboratorio(nombre,direccion) values ('Genéricos del Perú','20 de junio, Miraflores, Lima'),('Lab Vida','7 de Julio, Los Héroes, Trujillo');
insert into tipo(nombre,descripcion) values('Analgésicos',''),('Antinflamatorios',''),('Aines',''),('Antibioticos','');
insert into cliente_juridico(razon_social,ruc,direccion) values('Botica del Norte S.A.C.','3333333333','Calle Markahuamachuco 223');
insert into producto(nombre,precio,stock,tipo_id,laboratorio_id,fecha_vencimiento) values
	('Asgesic 10mg',2.3,100,1,1,'2018-06-01'),
    ('Dolalgial',3,76,1,1,'2018-06-01'),
    ('Aspirina',1,123,2,2,'2018-06-01'),
    ('Ibupronal ',8,54,3,1,'2018-06-01'),
    ('Medox',30,50,2,2,'2018-06-01'),
    ('Dolpiret Cmp',8,89,3,1,'2018-06-01'),
    ('Sigma Bron 100mg',10.5,32,2,2,'2018-06-01'),
    ('Flamadin',3,34,2,1,'2018-06-01');
    
/**************PROC USUARIO LOGIN ****************/

drop procedure if exists proc_login;
DELIMITER $$
create procedure proc_login(
in_username varchar(50),
in_password varchar(200)
)
begin
	select u.id,u.perfil_id,p.nombre as perfil,u.persona_id,pe.nombres,pe.apellidos,pe.dni,pe.direccion from usuario u join perfil p on p.id=u.perfil_id
    join persona pe on pe.id=u.persona_id
    where u.username=in_username and u.password=in_password and u.estado=1;
end $$
DELIMITER ;


/**************PROC LABORATORIO ****************/

drop procedure if exists proc_select_laboratorio;
DELIMITER $$
create procedure proc_select_laboratorio(
in_buscar varchar(50),
in_all int,
in_limit int
)
begin
	if in_all = 2 and in_limit=0 then /*in_all={2{todos},1{activos},0{inactivos}},n_limit ={0{todos}}*/
		select * from laboratorio l where l.nombre like CONCAT('%', in_buscar , '%') order by l.nombre;
	elseif in_all<2 and in_limit>0 then
		select * from laboratorio l where l.estado=in_all and l.nombre like CONCAT('%', in_buscar , '%') order by l.nombre limit in_limit;
    end if;
	
end $$
DELIMITER ;

drop procedure if exists proc_insert_laboratorio;
DELIMITER $$
create procedure proc_insert_laboratorio(
in_nombre varchar(100),
in_direccion varchar(200)
)
begin
	insert into laboratorio(nombre,direccion) values(in_nombre,in_direccion);
    set @id=LAST_INSERT_ID();
	select @id as id;
end $$
DELIMITER ;

drop procedure if exists proc_update_laboratorio;
DELIMITER $$
create procedure proc_update_laboratorio(
in_id int,
in_nombre varchar(100),
in_direccion varchar(200),
in_estado int
)
begin
update laboratorio set nombre=in_nombre, direccion = in_direccion, estado= in_estado where id = in_id;	
	select in_id as id;
end $$
DELIMITER ;

/**************PROC TIPO ****************/

drop procedure if exists proc_select_tipo;
DELIMITER $$
create procedure proc_select_tipo(
in_buscar varchar(50),
in_all int,
in_limit int
)
begin
	if in_all = 2 and in_limit=0 then /*in_all={2{todos},1{activos},0{inactivos}},n_limit ={0{todos}}*/
		select * from tipo t where t.nombre like CONCAT('%', in_buscar , '%') order by t.nombre;
    elseif in_all<2 and in_limit>0 then
		select * from tipo t where t.estado=in_all and t.nombre like CONCAT('%', in_buscar , '%') order by t.nombre limit in_limit; 
    end if;
	
end $$
DELIMITER ;

drop procedure if exists proc_insert_tipo;
DELIMITER $$
create procedure proc_insert_tipo(
in_nombre varchar(100),
in_descripcion varchar(200)
)
begin
	insert into tipo(nombre,descripcion) values(in_nombre,in_descripcion);
    set @id=LAST_INSERT_ID();
	select @id as id;
end $$
DELIMITER ;

drop procedure if exists proc_update_tipo;
DELIMITER $$
create procedure proc_update_tipo(
in_id int,
in_nombre varchar(100),
in_descripcion varchar(200),
in_estado int
)
begin
update tipo set nombre=in_nombre, descripcion = in_descripcion, estado= in_estado where id = in_id;	
	select in_id as id;
end $$
DELIMITER ;

/**************PROC PRODUCTO ****************/
drop procedure if exists proc_select_producto;
DELIMITER $$
create procedure proc_select_producto(
in_buscar varchar(50),
in_all int,
in_limit int
)
begin
	if in_all = 2 and in_limit=0 then /*in_all={2{todos},1{activos},0{inactivos}},n_limit ={0{todos}}*/
		 select p.*,t.nombre as tipo,l.nombre as laboratorio  from producto p 
         join tipo t on t.id=p.tipo_id 
         join laboratorio l on l.id=p.laboratorio_id
         where p.nombre like CONCAT('%', in_buscar , '%') order by p.nombre;
    elseif in_all<2 and in_limit>0 then
		select p.*,t.nombre as tipo,l.nombre as laboratorio  from producto p 
		join tipo t on t.id=p.tipo_id 
		join laboratorio l on l.id=p.laboratorio_id 
        where p.estado=in_all and p.nombre like CONCAT('%', in_buscar , '%') order by p.nombre
        limit in_limit; 
    end if;
	
end $$
DELIMITER ;

drop procedure if exists proc_insert_producto;
DELIMITER $$
create procedure proc_insert_producto(
in_nombre varchar(100),
in_precio decimal(10,2),
in_stock int,
in_tipo_id int,
in_laboratorio_id int,
in_fecha_vencimiento date
)
begin
	insert into producto(nombre,precio,stock,tipo_id,laboratorio_id,fecha_vencimiento) values(in_nombre,in_precio,in_stock,in_tipo_id,in_laboratorio_id,in_fecha_vencimiento);
    set @id=LAST_INSERT_ID();
	select @id as id;
end $$
DELIMITER ;

drop procedure if exists proc_update_producto;
DELIMITER $$
create procedure proc_update_producto(
in_id int,
in_nombre varchar(100),
in_precio decimal(10,2),
in_stock int,
in_tipo_id int,
in_laboratorio_id int,	
in_estado int,
in_fecha_vencimiento date
)
begin
	update producto set nombre=in_nombre, precio = in_precio, stock=in_stock,tipo_id=in_tipo_id,laboratorio_id=in_laboratorio_id,
    estado= in_estado,fecha_vencimiento=in_fecha_vencimiento where id = in_id;	
	select in_id as id;
end $$
DELIMITER ;

/**************PROC PERSONA ****************/

drop procedure if exists proc_select_persona;
DELIMITER $$
create procedure proc_select_persona(
in_buscar varchar(50),
in_all int,
in_limit int
)
begin
	if in_all = 2 and in_limit=0 then /*in_all={2{todos},1{activos},0{inactivos}},n_limit ={0{todos}}*/
		select * from persona p where p.nombres 
        like CONCAT('%', in_buscar , '%') or p.apellidos 
        like CONCAT('%', in_buscar , '%') or p.dni 
        like CONCAT('%', in_buscar , '%') order by p.apellidos,p.nombres;
    elseif in_all<2 and in_limit>0 then
		select * from persona p where p.estado=in_all and (p.nombres
        like CONCAT('%', in_buscar , '%') or p.apellidos 
        like CONCAT('%', in_buscar , '%') or p.dni
        like CONCAT('%', in_buscar , '%'))  order by p.apellidos,p.nombres
        limit in_limit;
    end if;	
end $$
DELIMITER ;

drop procedure if exists proc_insert_persona;
DELIMITER $$
create procedure proc_insert_persona(
in_nombres varchar(50),
in_apellidos varchar(50),
in_dni int(8),
in_direccion varchar(200)
)
begin
	insert into persona(nombres,apellidos,dni,direccion) values (in_nombres,in_apellidos,in_dni,in_direccion);
    set @id=LAST_INSERT_ID();
	select @id as id;
end $$
DELIMITER ;

drop procedure if exists proc_update_persona;
DELIMITER $$
create procedure proc_update_persona(
in_id int,
in_nombres varchar(50),
in_apellidos varchar(50),
in_dni int(8),
in_direccion varchar(200),
in_estado int
)
begin
	update persona set nombres=in_nombres,apellidos=in_apellidos,dni=in_dni,direccion=in_direccion,estado=in_estado where id= in_id;
    select in_id as id;
end $$
DELIMITER ;

/**************PROC CLIENTE JURIDICO ****************/

drop procedure if exists proc_select_clientejuridico;
DELIMITER $$
create procedure proc_select_clientejuridico(
in_buscar varchar(50),
in_all int,
in_limit int
)
begin
	if in_all = 2 and in_limit=0 then
		select * from cliente_juridico cj where cj.razon_social 
        like CONCAT('%', in_buscar , '%') or cj.ruc 
        like CONCAT('%', in_buscar , '%') order by cj.razon_social;
    elseif in_all<2 and in_limit>0 then
		select * from cliente_juridico cj where cj.estado=in_all and (cj.razon_social
        like CONCAT('%', in_buscar , '%') or cj.ruc 
        like CONCAT('%', in_buscar , '%')) order by cj.ruc, cj.razon_social
        limit in_limit;
    end if;	
end $$
DELIMITER ;

drop procedure if exists proc_insert_clientejuridico;
DELIMITER $$
create procedure proc_insert_clientejuridico(
in_razon_social varchar(100),
in_direccion varchar(200),
in_ruc char(11)
)
begin
	insert into cliente_juridico(razon_social,direccion,ruc) values (in_razon_social,in_direccion,in_ruc);
    set @id=LAST_INSERT_ID();
	select @id as id;
end $$
DELIMITER ;

drop procedure if exists proc_update_clientejuridico;
DELIMITER $$
create procedure proc_update_clientejuridico(
in_id int,
in_razon_social varchar(100),
in_direccion varchar(200),
in_ruc char(11),
in_estado int
)
begin
	update cliente_juridico set razon_social=in_razon_social,ruc=in_ruc,direccion=in_direccion,estado=in_estado where id= in_id;
    select in_id as id;
end $$
DELIMITER ;

/**************PROC BOLETA ****************/

drop procedure if exists proc_select_comprobanteboleta;
DELIMITER $$
create procedure proc_select_comprobanteboleta()
begin
	select cb.*,pu.nombres,pu.apellidos,pc.nombres,pc.apellidos,dcb.producto_id,dcb.cantidad,dcb.precio_venta,p.nombre,p.precio 
	from comprobante_boleta cb 
	join detalle_comprobanteboleta dcb on dcb.comprobanteboleta_id=cb.id
	join producto p on p.id=dcb.producto_id
	join usuario u on u.id=cb.usuario_id
	join persona pu on pu.id=u.id
	join persona pc on pc.id=cb.persona_id;
end $$
DELIMITER ;

drop procedure if exists proc_insert_comprobanteboleta;
DELIMITER $$
create procedure proc_insert_comprobanteboleta(
in_fecha date,
in_persona_id int,
in_usuario_id int
)
begin
insert into comprobante_boleta(fecha,persona_id,usuario_id) values(in_fecha,in_persona_id,in_usuario_id);
	set @id=LAST_INSERT_ID();
	select @id as id;
end $$
DELIMITER ;

drop procedure if exists proc_insert_detallecomprobanteboleta;
DELIMITER $$
create procedure proc_insert_detallecomprobanteboleta(
in_comprobanteboleta_id int,
in_producto_id int,
in_cantidad int,
in_precio_venta decimal(10,2)
)
begin
	insert into detalle_comprobanteboleta(comprobanteboleta_id,producto_id,cantidad,precio_venta) 
    values(in_comprobanteboleta_id,in_producto_id,in_cantidad,in_precio_venta);
	set @id=LAST_INSERT_ID();
    update producto set stock=stock-in_cantidad where id = in_producto_id;
	select @id as id;
end $$
DELIMITER 

/**************PROC FACTURA ****************/
drop procedure if exists proc_select_comprobantefactura;
DELIMITER $$
create procedure proc_select_comprobantefactura()
begin
	select cf.*,pu.nombres,pu.apellidos,cj.ruc,cj.razon_social,dcf.producto_id,dcf.cantidad,dcf.precio_venta,p.nombre,p.precio 
	from comprobante_factura cf 
	join detalle_comprobantefactura dcf on dcf.comprobantefactura_id=cf.id
	join producto p on p.id=dcf.producto_id
	join usuario u on u.id=cf.usuario_id
	join persona pu on pu.id=u.id
	join cliente_juridico cj on cj.id=cf.clientejuridico_id;
end $$
DELIMITER ;

drop procedure if exists proc_insert_comprobantefactura;
DELIMITER $$
create procedure proc_insert_comprobantefactura(
in_fecha date,
in_clientejuridico_id int,
in_usuario_id int
)
begin
insert into comprobante_factura(fecha,clientejuridico_id,usuario_id) values(in_fecha,in_clientejuridico_id,in_usuario_id);
	set @id=LAST_INSERT_ID();
	select @id as id;
end $$
DELIMITER ;

drop procedure if exists proc_insert_detallecomprobantefactura;
DELIMITER $$
create procedure proc_insert_detallecomprobantefactura(
in_comprobantefactura_id int,
in_producto_id int,
in_cantidad int,
in_precio_venta decimal(10,2)
)
begin
	insert into detalle_comprobantefactura(comprobantefactura_id,producto_id,cantidad,precio_venta) 
    values(in_comprobantefactura_id,in_producto_id,in_cantidad,in_precio_venta);
	set @id=LAST_INSERT_ID();
    update producto set stock=stock-in_cantidad where id = in_producto_id;
	select @id as id;
end $$
DELIMITER 

